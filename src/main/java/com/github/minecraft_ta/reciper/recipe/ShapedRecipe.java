package com.github.minecraft_ta.reciper.recipe;

import java.io.InputStream;

public class ShapedRecipe extends RecipeBase {

    private int width;

    private int height;

    @Override
    public void loadRecipe(InputStream inputStream) {


    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
