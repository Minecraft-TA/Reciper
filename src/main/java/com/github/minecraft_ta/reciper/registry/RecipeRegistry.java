package com.github.minecraft_ta.reciper.registry;

import com.github.minecraft_ta.reciper.ingredient.FluidStack;
import com.github.minecraft_ta.reciper.ingredient.ItemStack;
import com.github.minecraft_ta.reciper.recipe.IRecipe;
import com.github.minecraft_ta.reciper.recipe.RecipeFactory;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeRegistry {
    public static final Map<ItemStack, List<IRecipe>> RECIPES = new Object2ObjectOpenHashMap<>();
    public static final Int2ObjectMap<ItemStack> ITEMSTACK_LOOKUP_MAP = new Int2ObjectOpenHashMap<>();

    public static final Int2ObjectMap<FluidStack> FLUIDSTACK_LOOKUP_MAP = new Int2ObjectOpenHashMap<>();

    /**
     * Loads all recipes from the given binary file.
     *
     * @param recipeFile The binary file to load from.
     */
    public static void loadRecipesFromFile(File recipeFile) {
        // Read the lookup file
        try (DataInputStream in = new DataInputStream(Files.newInputStream(recipeFile.toPath()))) {
            // Read the lookup file
            int lookupSize = in.readInt();

            for (int i = 0; i < lookupSize; i++) {
                int id = in.readInt();
                ItemStack itemStack = new ItemStack(in.readUTF(), in.readUTF(), in.readInt(), in.readUTF());
                ITEMSTACK_LOOKUP_MAP.put(id, itemStack);
            }

            // Read the fluid lookup file
            int fluidLookupSize = in.readInt();
            for (int i = 0; i < fluidLookupSize; i++) {
                int id = in.readInt();
                FluidStack fluidStack = new FluidStack(in.readUTF());
                FLUIDSTACK_LOOKUP_MAP.put(id, fluidStack);
            }

            // Read the recipes
            int recipeSize = in.readInt();

            // Read the ItemStack using the lookup map
            for (int i = 0; i < recipeSize; i++) {
                ItemStack stack = ITEMSTACK_LOOKUP_MAP.get(in.readInt());

                // Recipe list size
                int recipeListSize = in.readInt();

                for (int y = 0; y < recipeListSize; y++) {
                    // Recipe type
                    String type = in.readUTF();

                    // Create the recipe
                    IRecipe recipe = RecipeFactory.createRecipe(type);

                    // Load the recipe
                    recipe.loadRecipe(in);

                    // Add the recipe to the recipe list
                    RECIPES.computeIfAbsent(stack, k -> new ArrayList<>()).add(recipe);
                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
