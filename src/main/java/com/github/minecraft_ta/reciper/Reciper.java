package com.github.minecraft_ta.reciper;

import com.github.minecraft_ta.reciper.recipe.IRecipe;
import com.github.minecraft_ta.reciper.recipe.RecipeFactory;
import com.github.minecraft_ta.reciper.registry.RecipeRegistry;

import java.io.File;
import java.util.function.Supplier;

public class Reciper {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        long startTime = System.currentTimeMillis();
        File file = new File("src/test/resources/recipe-export.bin");
        RecipeRegistry.loadRecipesFromFile(file);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }


    public void registerRecipes(String identifier, Supplier<IRecipe> recipeSupplier) {
        RecipeFactory.registerRecipe(identifier, recipeSupplier);
    }


}