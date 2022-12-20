package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;

import java.io.InputStream;

public class ShapedRecipe extends RecipeBase{

    private final int width;

    private final int height;

    protected ShapedRecipe(ItemStack[] inputs, ItemStack[] outputs, ItemStack[] catalysts, int width, int height) {
        super(inputs, outputs, catalysts);
        this.width = width;
        this.height = height;
    }

    @Override
    public void load(InputStream inputStream) {



    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
