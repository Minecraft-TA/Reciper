package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class RootRecipe implements IRecipe {

    private final ItemStack itemStack;

    public RootRecipe(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public ItemStack[] getInputs() {
        return new ItemStack[]{itemStack};
    }

    @Override
    public Set<ItemStack> getUniqueInputs() {
        return Collections.singleton(itemStack);
    }

    @Override
    public ItemStack[] getOutputs() {
        return new ItemStack[]{itemStack};
    }

    @Override
    public String getRecipeName() {
        return "root";
    }

    @Override
    public void loadRecipe(DataInputStream inputStream) throws IOException {
        // NOOP
    }
}
