package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Set;

/**
 * Base interface for all recipes.
 */
public interface IRecipe {

    ItemStack[] getInputs();

    Set<ItemStack> getUniqueInputs();

    ItemStack[] getOutputs();

    String getRecipeName();

    /**
     * Loads the recipe from the given input stream.
     *
     * @param inputStream The input stream to load from.
     */
    void loadRecipe(DataInputStream inputStream) throws IOException;

}
