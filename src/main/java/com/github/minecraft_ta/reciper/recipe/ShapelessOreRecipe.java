package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.registry.RecipeRegistry;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShapelessOreRecipe implements IRecipe {

    private List<ItemStack>[] ingredients;
    private int outputAmount;

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
        this.outputAmount = inputStream.readInt();
        this.ingredients = new List[inputStream.readInt()];
        for (int i = 0; i < ingredients.length; i++) {
            int length = inputStream.readInt();
            for (int j = 0; j < length; j++) {
                int id = inputStream.readInt();
                if (id != -1) {
                    this.ingredients[i] = new ArrayList<>();
                    this.ingredients[i].add(RecipeRegistry.ITEMSTACK_LOOKUP_MAP.get(id));
                }
            }
        }
    }

    @Override
    public ItemStack[] getInputs() {
        // Return all possible inputs
        List<ItemStack> allInputs = new ArrayList<>();
        for (List<ItemStack> input : ingredients) {
            if (input != null) {
                allInputs.addAll(input);
            }
        }
        return allInputs.toArray(new ItemStack[0]);
    }

    @Override
    public Set<ItemStack> getUniqueInputs() {
        Set<ItemStack> uniqueInputs = new ObjectOpenHashSet<>();
        for (List<ItemStack> input : ingredients) {
            if (input != null) {
                uniqueInputs.addAll(input);
            }
        }
        return uniqueInputs;
    }

    @Override
    public ItemStack[] getOutputs() {
        return new ItemStack[0];
    }

    @Override
    public String getRecipeName() {
        return "shapeless_ore";
    }

    public List<ItemStack>[] getIngredients() {
        return ingredients;
    }

}
