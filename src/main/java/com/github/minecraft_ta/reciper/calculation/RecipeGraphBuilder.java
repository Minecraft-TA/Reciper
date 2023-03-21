package com.github.minecraft_ta.reciper.calculation;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.recipe.IRecipe;
import com.github.minecraft_ta.reciper.recipe.RootRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipeGraphBuilder {

    public static RecipeNode buildRecipeGraph(ItemStack itemStack, Map<ItemStack, List<IRecipe>> recipes) {
        // Create a dummy recipe for the item stack
        RootRecipe rootRecipe = new RootRecipe(itemStack);
        RecipeNode rootNode = new RecipeNode(rootRecipe);

        Map<IRecipe, List<RecipeNode>> recipeNodeCache = new Object2ObjectOpenHashMap<>();

        buildRecipeGraph(rootRecipe, rootNode, recipes, recipeNodeCache);

        return rootNode;
    }


    private static RecipeNode buildRecipeGraph(IRecipe recipe, RecipeNode parent, Map<ItemStack, List<IRecipe>> recipes, Map<IRecipe, List<RecipeNode>> cache) {
        // Check the cache
        List<RecipeNode> cachedNodes = cache.get(recipe);
        if (cachedNodes != null) {
            // Add the cached nodes to the parent node
            parent.addChildren(cachedNodes);
            return null;
        }

        RecipeNode node = new RecipeNode(recipe);
        cache.computeIfAbsent(recipe, r -> new ArrayList<>()).add(node);

        Set<ItemStack> inputs = recipe.getUniqueInputs();

        for (ItemStack input : inputs) {
            List<IRecipe> recipeList = recipes.get(input);
            if (recipeList == null)
                continue;

            for (IRecipe iRecipe : recipeList) {
                RecipeNode child = buildRecipeGraph(iRecipe, node, recipes, cache);
                if (child != null) {
                    node.addChild(child);
                }
            }
        }

        // Connect the newly created node to its parent
        parent.addChild(node);

        return node;
    }


}
