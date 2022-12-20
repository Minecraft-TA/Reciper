package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;

import java.io.InputStream;

public class ShapelessRecipe extends RecipeBase {

    protected ShapelessRecipe(ItemStack[] inputs, ItemStack[] outputs, ItemStack[] catalysts) {
        super(inputs, outputs, catalysts);
    }


    @Override
    public void load(InputStream inputStream) {

    }
}
