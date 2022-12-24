package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.registry.RecipeRegistry;

import java.io.DataInputStream;
import java.io.IOException;

public class ShapedRecipe extends RecipeBase {

    private int width;

    private int height;

    /**
     * {@inheritDoc}
     * First reads the width and height of the recipe.
     * Then reads the every ItemStack in the recipe.
     *
     * @param inputStream The input stream to load from.
     * @throws IOException
     */
    @Override
    public void loadRecipe(DataInputStream inputStream) throws IOException {
        this.width = inputStream.readInt();
        this.height = inputStream.readInt();
        this.inputs = new ItemStack[this.width * this.height];

        for (int i = 0; i < inputs.length; i++) {
            this.inputs[i] = RecipeRegistry.ITEMSTACK_LOOKUP_MAP.get(inputStream.readInt());
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
