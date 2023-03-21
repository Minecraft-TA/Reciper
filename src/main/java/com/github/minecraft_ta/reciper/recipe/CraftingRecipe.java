package com.github.minecraft_ta.reciper.recipe;


import java.io.DataInputStream;
import java.io.IOException;

/**
 * Base class for all crafting recipes.
 * Supports multiple items per input slot but only one output.
 * Output can have a size greater than 1 but not the input items.
 */
public class CraftingRecipe extends RecipeBase {


    @Override
    public String getRecipeName() {
        return null;
    }

    @Override
    public void loadRecipe(DataInputStream inputStream) throws IOException {

    }
}
