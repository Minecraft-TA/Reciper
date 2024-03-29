package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.registry.RecipeRegistry;

import java.io.DataInputStream;
import java.io.IOException;

public class ShapelessRecipe extends RecipeBase {

    private int outputAmount;

    /**
     * {@inheritDoc}
     * Reads the number of inputs and then reads every ItemStack in the recipe.
     *
     * @param inputStream The input stream to load from.
     * @throws IOException
     */
    @Override
    public void loadRecipe(DataInputStream inputStream) throws IOException {
        this.outputAmount = inputStream.readInt();
        this.inputs = new ItemStack[inputStream.readInt()];
        for (int i = 0; i < inputs.length; i++) {
            int id = inputStream.readInt();
            if (id != -1) {
                this.inputs[i] = RecipeRegistry.ITEMSTACK_LOOKUP_MAP.get(id);
            }
        }
    }

    @Override
    public String getRecipeName() {
        return "shapeless";
    }

}
