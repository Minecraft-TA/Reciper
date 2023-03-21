package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.registry.RecipeRegistry;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShapedOreRecipe implements IRecipe {

    private int width;
    private int height;
    private boolean mirrored;
    private int outputAmount;

    private List<ItemStack>[] ingredients;

    @Override
    public String getRecipeName() {
        return "shaped_ore";
    }

    @Override
    public void loadRecipe(DataInputStream inputStream) throws IOException {
        this.outputAmount = inputStream.readInt();
        this.width = inputStream.readInt();
        this.height = inputStream.readInt();

        this.ingredients = new List[this.width * this.height];

        this.mirrored = inputStream.readBoolean();

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<ItemStack>[] getIngredients() {
        return ingredients;
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
        for (List<ItemStack> ingredient : ingredients) {
            if (ingredient != null) {
                uniqueInputs.addAll(ingredient);
            }
        }
        return uniqueInputs;
    }

    @Override
    public ItemStack[] getOutputs() {
        return new ItemStack[0];
    }

}
