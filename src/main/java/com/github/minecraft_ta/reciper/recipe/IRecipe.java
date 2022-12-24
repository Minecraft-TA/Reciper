package com.github.minecraft_ta.reciper.recipe;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Base interface for all recipes.
 */
public interface IRecipe {

    /**
     * Loads the recipe from the given input stream.
     *
     * @param inputStream The input stream to load from.
     */
    void loadRecipe(DataInputStream inputStream) throws IOException;


}
