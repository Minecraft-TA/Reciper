import com.github.minecraft_ta.reciper.calculation.RecipeGraphBuilder;
import com.github.minecraft_ta.reciper.calculation.RecipeNode;
import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.recipe.*;
import com.github.minecraft_ta.reciper.registry.RecipeRegistry;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ItemSearchUI extends JFrame {
    private final JTextField searchField;
    private final JList<ItemStack> itemList;
    private final DefaultListModel<ItemStack> itemListModel;
    private final JScrollPane scrollPane;

    public ItemSearchUI() {
        super("Item Search");

        // Create the search field
        searchField = new JTextField(20);
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                updateItemList();
            }
        });
        add(searchField, BorderLayout.NORTH);

        // Create the item list
        itemListModel = new DefaultListModel<>();
        itemList = new JList<>(itemListModel);
        itemList.setCellRenderer(new ItemListCellRenderer());
        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    ItemStack selectedItem = itemList.getSelectedValue();
                    // Shedule the recipe calculation on a new thread
                    new Thread(() -> {
                        RecipeNode recipeTree = RecipeGraphBuilder.buildRecipeGraph(selectedItem, RecipeRegistry.RECIPES);
                        exportRecipeTreeToGraphviz(recipeTree, "src/test/resources/recipe-tree.dot");
                        // Convert the dot file to a png and display it
                        try {
                            Process p = Runtime.getRuntime().exec("dot -Tpng src/test/resources/recipe-tree.dot -o src/test/resources/recipe-tree.png");
                            p.waitFor();
                            Thread.sleep(1000);
                            File input = new File("src/test/resources/recipe-tree.png");
                            BufferedImage image = ImageIO.read(input);
                            // Run this on the main thread
                            /*SwingUtilities.invokeLater(() -> {
                                JFrame frame = new JFrame("Recipe Tree");
                                frame.add(new JLabel(new ImageIcon(image)));
                                frame.pack();
                                frame.setVisible(true);
                            });*/
                            JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(image)));
                        } catch (IOException | InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }).start();
                }
            }
        });
        // Add all items to the list
        for (ItemStack itemStack : RecipeRegistry.RECIPES.keySet()) {
            itemListModel.addElement(itemStack);
        }
        scrollPane = new JScrollPane(itemList);
        add(scrollPane, BorderLayout.CENTER);

        // Set the size and position of the window
        setSize(600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void updateItemList() {
        String searchText = searchField.getText();
        itemListModel.clear();
        for (ItemStack itemStack : RecipeRegistry.RECIPES.keySet()) {
            if (itemStack.getLabel().toLowerCase().contains(searchText.toLowerCase())) {
                itemListModel.addElement(itemStack);
            }
        }
    }

    private static class ItemListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            ItemStack itemStack = (ItemStack) value;
            label.setIcon(new ImageIcon("recipe_icons/" + itemStack.hashCode() + ".png"));
            label.setText(itemStack.getLabel());
            label.setToolTipText(itemStack.getLabel());
            return label;
        }
    }

    public BufferedImage getRecipeImage(IRecipe recipe, int scale) {
        // Get the dimensions of the recipe
        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
            int width = shapedRecipe.getWidth();
            int height = shapedRecipe.getHeight();

            // Create a new image with the scaled dimensions
            BufferedImage image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = image.getGraphics();

            // Iterate through the recipe's grid of ItemStacks
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    ItemStack stack = shapedRecipe.getInputs()[x + y * width];
                    if (stack != null) {
                        // Get the image for the ItemStack
                        BufferedImage itemImage = null;
                        try {
                            itemImage = ImageIO.read(new File("recipe_icons/" + stack.hashCode() + ".png"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        // Draw the image onto the recipe image
                        graphics.drawImage(itemImage, x * scale, y * scale, scale, scale, null);
                    } else {
                        // Draw a blank space
                        graphics.setColor(Color.WHITE);
                        graphics.fillRect(x * scale, y * scale, scale, scale);
                    }
                }
            }
            return image;
        } else if (recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe shapedOreRecipe = (ShapedOreRecipe) recipe;
            int width = shapedOreRecipe.getWidth();
            int height = shapedOreRecipe.getHeight();

            // Create a new image with the scaled dimensions
            BufferedImage image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = image.getGraphics();

            // Iterate through the recipe's grid of ItemStacks
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    List<ItemStack> ingredient = shapedOreRecipe.getIngredients()[x + y * width];
                    if (ingredient == null || ingredient.isEmpty()) continue;
                    ItemStack stack = ingredient.get(0);
                    if (stack != null) {
                        // Get the image for the ItemStack
                        BufferedImage itemImage = null;
                        try {
                            File input = new File("recipe_icons/" + stack.hashCode() + ".png");
                            if (!input.exists()) {
                                // Use a default image
                                input = new File("recipe_icons/Iron Nugget.png");
                            }
                            itemImage = ImageIO.read(input);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        // Draw the image onto the recipe image
                        graphics.drawImage(itemImage, x * scale, y * scale, scale, scale, null);
                    }
                }
            }
            return image;
        } else if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
            ItemStack[] inputs = shapelessRecipe.getInputs();
            int width = 3;
            int height = 3;

            // Create a new image with the scaled dimensions
            BufferedImage image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = image.getGraphics();

            int x = 0;
            int y = 0;
            for (ItemStack ingredient : inputs) {
                if (ingredient != null) {
                    // Get the image for the ItemStack
                    BufferedImage itemImage = null;
                    try {
                        itemImage = ImageIO.read(new File("recipe_icons/" + ingredient.hashCode() + ".png"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // Draw the image onto the recipe image
                    graphics.drawImage(itemImage, x * scale, y * scale, scale, scale, null);
                }

                x++;
                if (x == width) {
                    x = 0;
                    y++;
                }
            }
            return image;
        } else if (recipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe shapelessRecipe = (ShapelessOreRecipe) recipe;
            List<ItemStack>[] inputs = shapelessRecipe.getIngredients();
            int width = 3;
            int height = 3;

            // Create a new image with the scaled dimensions
            BufferedImage image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = image.getGraphics();

            int x = 0;
            int y = 0;
            for (List<ItemStack> ingredient : inputs) {
                if (ingredient != null) {
                    // Get the image for the ItemStack
                    BufferedImage itemImage = null;
                    try {
                        itemImage = ImageIO.read(new File("recipe_icons/" + ingredient.get(0).hashCode() + ".png"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // Draw the image onto the recipe image
                    graphics.drawImage(itemImage, x * scale, y * scale, scale, scale, null);
                }

                x++;
                if (x == width) {
                    x = 0;
                    y++;
                }
            }
            return image;
        } else {
            try {
                if (recipe.getInputs().length == 0) {
                    return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                }
                ItemStack itemStack = recipe.getInputs()[0];
                if (itemStack == null) {
                    return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                }
                String label = itemStack.getLabel();
                //System.out.println(label);
                File input = new File("recipe_icons/" + itemStack.hashCode() + ".png");
                if (!input.exists()) {
                    return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                }

                return ImageIO.read(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void exportRecipeTreeToGraphviz(RecipeNode recipeTree, String filePath) {
        try {
            // Create a new dot file
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("digraph G {");
            bw.write("concentrate=true;");
            bw.newLine();

            // Recursively export the recipe tree and prevent infinite loops
            Set<RecipeNode> visited = new ObjectOpenHashSet<>();
            exportRecipeTreeToGraphviz(recipeTree, bw, visited);

            bw.write("}");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportRecipeTreeToGraphviz(RecipeNode recipeTree, BufferedWriter bw, Set<RecipeNode> discoveredNodes) throws IOException {
        // Write the recipe node
        IRecipe recipe = recipeTree.getRecipe();
        BufferedImage recipeImage = getRecipeImage(recipe, 64);
        File recipeImageFile = new File("src/test/resources/recipe-tree/" + recipe.hashCode() + ".png");
        // Create the directory if it doesn't exist and the file if it doesn't exist
        if (!recipeImageFile.getParentFile().exists()) {
            recipeImageFile.getParentFile().mkdirs();
        }
        if (!recipeImageFile.exists()) recipeImageFile.createNewFile();
        ImageIO.write(recipeImage, "png", recipeImageFile);
        bw.write(recipe.hashCode() + " [label=\"\", shape=none, image=\"" + recipeImageFile.getAbsolutePath() + "\"];");
        bw.newLine();

        // Write the edges to the children
        for (RecipeNode child : recipeTree.getChildren()) {
            if (discoveredNodes.contains(child)) continue;
            bw.write("\"" + recipe.hashCode() + "\" -> \"" + child.getRecipe().hashCode() + "\";");
            bw.newLine();
            discoveredNodes.add(child);
            exportRecipeTreeToGraphviz(child, bw, discoveredNodes);
        }
    }

    public static void main(String[] args) {
        File lookupFile = new File("src/test/resources/recipe-export.bin");
        RecipeRegistry.loadRecipesFromFile(lookupFile);

        //Print current directory
        System.out.println(System.getProperty("user.dir"));

        EventQueue.invokeLater(() -> {
            ItemSearchUI ui = new ItemSearchUI();
            ui.setVisible(true);
        });
    }
}