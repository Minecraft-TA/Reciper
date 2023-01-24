import com.github.minecraft_ta.reciper.calculation.RecipeCalculator;
import com.github.minecraft_ta.reciper.calculation.RecipeTree;
import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.recipe.IRecipe;
import com.github.minecraft_ta.reciper.recipe.ShapedRecipe;
import com.github.minecraft_ta.reciper.registry.RecipeRegistry;

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
                        RecipeTree recipeTree = RecipeCalculator.calculateRecipes(RecipeRegistry.RECIPES, selectedItem);
                        exportRecipeTreeToGraphviz(recipeTree, "src/test/resources/recipe-tree.dot");
                        // Convert the dot file to a png and display it
                        try {
                            Process p = Runtime.getRuntime().exec("dot -Tpng src/test/resources/recipe-tree.dot -o src/test/resources/recipe-tree.png");
                            p.waitFor();
                            BufferedImage image = ImageIO.read(new File("src/test/resources/recipe-tree.png"));
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
            if (itemStack.getLabel().contains(searchText)) {
                itemListModel.addElement(itemStack);
            }
        }
    }

    private static class ItemListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            ItemStack itemStack = (ItemStack) value;
            label.setIcon(new ImageIcon("itempanel_icons/" + itemStack.getLabel() + ".png"));
            label.setText(itemStack.getLabel());
            label.setToolTipText(itemStack.getLabel());
            return label;
        }
    }

    public BufferedImage getRecipeImage(IRecipe recipe, int scale) {
        // Get the dimensions of the recipe
        if (!(recipe instanceof ShapedRecipe)) {
            try {
                ItemStack itemStack = recipe.getInputs()[0];
                if (itemStack == null) {
                    return null;
                }
                String label = itemStack.getLabel();
                System.out.println(label);
                File input = new File("itempanel_icons/" + label + ".png");
                if (!input.exists()) {
                    return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                }

                return ImageIO.read(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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
                        itemImage = ImageIO.read(new File("itempanel_icons/" + stack.getLabel() + ".png"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // Draw the image onto the recipe image
                    graphics.drawImage(itemImage, x * scale, y * scale, scale, scale, null);
                }
            }
        }
        return image;
    }

    public void exportRecipeTreeToGraphviz(RecipeTree recipeTree, String filePath) {
        try {
            // Create a new dot file
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("digraph G {");
            bw.newLine();

            // Recursively export the recipe tree
            exportRecipeTreeToGraphviz(recipeTree, bw);

            bw.write("}");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportRecipeTreeToGraphviz(RecipeTree recipeTree, BufferedWriter bw) throws IOException {
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
        for (RecipeTree child : recipeTree.getChildren()) {
            bw.write("\"" + recipe.hashCode() + "\" -> \"" + child.getRecipe().hashCode() + "\";");
            bw.newLine();
            exportRecipeTreeToGraphviz(child, bw);
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