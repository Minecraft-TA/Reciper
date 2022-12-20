package com.github.minecraft_ta.reciper;

import com.github.minecraft_ta.reciper.registry.RecipeRegistry;

import java.io.File;

public class Reciper {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        long startTime = System.currentTimeMillis();
        File lookupFile = new File("src/test/resources/recipes.csv");
        RecipeRegistry.loadRecipesFromFile(lookupFile, null);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }
}