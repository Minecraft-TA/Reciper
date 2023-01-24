import com.github.minecraft_ta.reciper.calculation.RecipeCalculator;
import com.github.minecraft_ta.reciper.calculation.RecipeTree;
import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.registry.RecipeRegistry;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class GraphvizTest {


    public static void main(String[] args) {
        new GraphvizTest().testGraphviz();
    }

    @Test
    public void testGraphviz() {
        File lookupFile = new File("src/test/resources/recipe-export.bin");
        RecipeRegistry.loadRecipesFromFile(lookupFile);

        RecipeTree recipeTree = RecipeCalculator.calculateRecipes(RecipeRegistry.RECIPES, (ItemStack) RecipeRegistry.RECIPES.keySet().toArray()[0]);
        exportToGraphviz(recipeTree, "src/test/resources/recipe-tree.dot");
    }


    public void exportToGraphviz(RecipeTree root, String filename) {
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            writer.println("digraph {");
            exportToGraphviz(root, writer);
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportToGraphviz(RecipeTree node, PrintWriter writer) {
        if (node == null) {
            return;
        }
        writer.println(node.getRecipe().getRecipeName() + ";");
        for (RecipeTree child : node.getChildren()) {
            writer.println(node.getRecipe().getRecipeName() + " -> " + child.getRecipe().getRecipeName() + ";");
            exportToGraphviz(child, writer);
        }
    }


}
