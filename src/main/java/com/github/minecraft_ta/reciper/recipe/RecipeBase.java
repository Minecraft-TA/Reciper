package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.Set;

public abstract class RecipeBase implements IRecipe {

    protected ItemStack[] inputs;

    protected ItemStack[] outputs;

    @Override
    public Set<ItemStack> getUniqueInputs() {
        return new ObjectOpenHashSet<>(inputs);
    }

    public ItemStack[] getInputs() {
        return inputs;
    }

    public ItemStack[] getOutputs() {
        return outputs;
    }

}
