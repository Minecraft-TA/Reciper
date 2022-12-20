package com.github.minecraft_ta.reciper.recipe;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Factory for creating recipes.
 */
public class RecipeFactory {

    private static final Map<String, Supplier<IRecipe>> recipeMap = new Object2ObjectOpenHashMap<>();

    static {
        recipeMap.put("shaped", ShapedRecipe::new);
        recipeMap.put("shapeless", ShapelessRecipe::new);
    }

    /**
     * Creates a new recipe instance based on the given identifier.
     *
     * @param type The identifier of the recipe.
     * @return The new recipe instance.
     */
    public static IRecipe createRecipe(String type) {
        return recipeMap.get(type).get();
    }

    /**
     * Registers a new recipe type.
     *
     * @param type           The identifier of the recipe.
     * @param recipeSupplier The supplier for creating new instances of the recipe.
     */
    public static void registerRecipe(String type, Supplier<IRecipe> recipeSupplier) {
        recipeMap.put(type, recipeSupplier);
    }


}
