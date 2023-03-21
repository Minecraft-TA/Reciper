package com.github.minecraft_ta.reciper.calculation;


import com.github.minecraft_ta.reciper.recipe.IRecipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeNode {

    private final IRecipe recipe;

    private final List<RecipeNode> inputMap;

    public RecipeNode(IRecipe recipe) {
        this.recipe = recipe;
        this.inputMap = new ArrayList<>();
    }

    public void addChild(RecipeNode node) {
        inputMap.add(node);
    }

    public void addChildren(List<RecipeNode> nodes) {
        inputMap.addAll(nodes);
    }

    public IRecipe getRecipe() {
        return recipe;
    }

    public List<RecipeNode> getChildren() {
        return inputMap;
    }
}

