package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;

public abstract class RecipeBase implements IRecipe {

    protected final ItemStack[] inputs;

    protected final ItemStack[] outputs;

    protected final ItemStack[] catalysts;

    protected RecipeBase(ItemStack[] inputs, ItemStack[] outputs, ItemStack[] catalysts) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.catalysts = catalysts;
    }

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
