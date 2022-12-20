package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;

public abstract class RecipeBase implements IRecipe {

    protected ItemStack[] inputs;

    protected ItemStack[] outputs;

    protected ItemStack[] catalysts;

    public ItemStack[] getInputs() {
        return inputs;
    }

    public ItemStack[] getOutputs() {
        return outputs;
    }

    public ItemStack[] getCatalysts() {
        return catalysts;
    }
}
