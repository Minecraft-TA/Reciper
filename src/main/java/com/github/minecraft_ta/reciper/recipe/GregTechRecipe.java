package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.FluidStack;
import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.registry.RecipeRegistry;

import java.io.DataInputStream;
import java.io.IOException;

public class GregTechRecipe extends RecipeBaseFluid {
    @Override
    public String getRecipeName() {
        return null;
    }

    @Override
    public void loadRecipe(DataInputStream inputStream) throws IOException {
        this.inputs = new ItemStack[inputStream.readInt()];
        for (int i = 0; i < inputs.length; i++) {
            int id = inputStream.readInt();
            if (id == -1) continue;
            ItemStack stack = RecipeRegistry.ITEMSTACK_LOOKUP_MAP.get(id);
            stack.setAmount(inputStream.readInt());
            this.inputs[i] = stack;
        }

        this.outputs = new ItemStack[inputStream.readInt()];
        for (int i = 0; i < outputs.length; i++) {
            int id = inputStream.readInt();
            if (id == -1) continue;
            ItemStack stack = RecipeRegistry.ITEMSTACK_LOOKUP_MAP.get(id);
            stack.setAmount(inputStream.readInt());
            this.outputs[i] = stack;
        }

        this.fluidInputs = new FluidStack[inputStream.readInt()];
        for (int i = 0; i < fluidInputs.length; i++) {
            FluidStack stack = RecipeRegistry.FLUIDSTACK_LOOKUP_MAP.get(inputStream.readInt());
            stack.setAmount(inputStream.readInt());
            this.fluidInputs[i] = stack;
        }

        this.fluidOutputs = new FluidStack[inputStream.readInt()];
        for (int i = 0; i < fluidOutputs.length; i++) {
            FluidStack stack = RecipeRegistry.FLUIDSTACK_LOOKUP_MAP.get(inputStream.readInt());
            stack.setAmount(inputStream.readInt());
            this.fluidOutputs[i] = stack;
        }
    }
}
