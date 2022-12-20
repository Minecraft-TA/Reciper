package com.github.minecraft_ta.reciper.recipe;

import java.io.InputStream;

/**
 * Base interface for all recipes.
 */
public interface IRecipe {

    /**
     * Loads the recipe from the given input stream.
     * @param inputStream The input stream to load from.
     */
    void load(InputStream inputStream);


}
