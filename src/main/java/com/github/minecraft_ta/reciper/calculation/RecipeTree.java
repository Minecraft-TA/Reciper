package com.github.minecraft_ta.reciper.calculation;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.recipe.IRecipe;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeTree {

    private final IRecipe recipe;
    private final List<RecipeTree> children;

    public RecipeTree(IRecipe recipe) {
        this.recipe = recipe;
        this.children = new ArrayList<>();
    }

    public RecipeTree() {
        this((IRecipe) null);
    }

    public RecipeTree(ItemStack item) {
        // How can I set the root node to an item instead of a recipe?
        this.recipe = new IRecipe() {
            @Override
            public ItemStack[] getInputs() {
                return new ItemStack[]{item};
            }

            @Override
            public ItemStack[] getOutputs() {
                return new ItemStack[0];
            }

            @Override
            public String getRecipeName() {
                return "Dummy";
            }

            @Override
            public void loadRecipe(DataInputStream inputStream) throws IOException {

            }

            @Override
            public UUID getUUID() {
                return UUID.randomUUID();
            }
        };
        this.children = new ArrayList<>();
    }

    public IRecipe getRecipe() {
        return recipe;
    }

    public List<RecipeTree> getChildren() {
        return children;
    }

    public void addChild(RecipeTree child) {
        this.children.add(child);
    }

}
