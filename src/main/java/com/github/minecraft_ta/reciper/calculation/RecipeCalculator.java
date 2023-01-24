package com.github.minecraft_ta.reciper.calculation;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.recipe.IRecipe;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.List;
import java.util.Map;

public class RecipeCalculator {

    public static RecipeTree calculateRecipes(Map<ItemStack, List<IRecipe>> recipes, ItemStack item) {
        RecipeTree tree = new RecipeTree(item);
        Object2IntMap<IRecipe> visited = new Object2IntOpenHashMap<>();
        List<IRecipe> itemRecipes = recipes.get(item);
        if (itemRecipes != null) {
            for (IRecipe recipe : itemRecipes) {
                calculateRecipes(recipes, visited, tree, recipe, 0);
            }
        }
        return tree;
    }

    private static void calculateRecipes(Map<ItemStack, List<IRecipe>> recipes, Object2IntMap<IRecipe> visited, RecipeTree tree, IRecipe recipe, int level) {
        if (visited.containsKey(recipe) && visited.getInt(recipe) <= level) {
            // Recipe loop detected
            return;
        }

        // Mark recipe as visited
        visited.put(recipe, level);

        // Add recipe to tree
        tree.addChild(new RecipeTree(recipe));

        for (ItemStack item : recipe.getInputs()) {
            List<IRecipe> itemRecipes = recipes.get(item);
            if (itemRecipes != null) {
                for (IRecipe r : itemRecipes) {
                    calculateRecipes(recipes, visited, tree, r, level + 1);
                }
            }
        }
    }

}
