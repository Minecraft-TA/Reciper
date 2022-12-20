package com.github.minecraft_ta.reciper.registry;

import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.recipe.IRecipe;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class RecipeRegistry {
    public static final Map<ItemStack, List<IRecipe>> RECIPES = new Object2ObjectOpenHashMap<>();

    public static void loadRecipesFromFile(File itemStackLookupFile, File recipeFile) {
        // Read the item stack lookup file which is in csv format
        // Read the recipe file which is a custom binary format
        // For each recipe, add it to the RECIPES map

        // The RECIPES map is a map of item stacks to a list of recipes
        // The item stack is the output of the recipe
        // The list of recipes is a list of recipes that output the item stack

        // The recipe file is a binary file that contains the following:
        // - The number of recipes
        // - For each ItemStack the lookup ID (int)
        // - Then the amount of recipes for that ItemStack (int)
        // - The recipe ID (int)
        // The recipe data which is a array of ints (the lookup ID of the input item stacks)

        // Try to read the item stack lookup file

        Int2ObjectMap<ItemStack> lookup = new Int2ObjectOpenHashMap<>();
        try {
            Files.readAllLines(itemStackLookupFile.toPath(), StandardCharsets.ISO_8859_1).forEach(line -> {
                // skip the header
                if (line.startsWith("ItemStackID"))
                    return;


                String[] split = line.split(";");
                int lookupId = Integer.parseInt(split[0]);
                String ingredientName = split[1];
                String label = split[2];
                int meta = Integer.parseInt(split[3]);
                String nbt = split[4];

                // Create the item stack and add it to the lookup map
                ItemStack itemStack = new ItemStack(ingredientName, label, meta, 0, nbt);
                lookup.put(lookupId, itemStack);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Print the lookup map
        lookup.forEach((lookupId, itemStack) -> System.out.println(lookupId + " -> " + itemStack));


    }


}
