package com.github.minecraft_ta.reciper.recipe;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class ShapelessRecipe extends RecipeBase {


    @Override
    public String getRecipeName() {
        return "shapeless";
    }

    @Override
    public void loadRecipe(DataInputStream inputStream) throws IOException {

    }

    @Override
    public UUID getUUID() {
        return null;
    }
}
