package com.github.minecraft_ta.reciper.ingredient;

import org.jetbrains.annotations.Nullable;

public class IngredientStack {

    private final String ingredientName;
    private final String label;
    private final int meta;
    private final int amount;
    private final String nbt;

    public IngredientStack(String ingredientName, String label, int meta, int amount, String nbt) {
        this.ingredientName = ingredientName;
        this.label = label;
        this.meta = meta;
        this.amount = amount;
        this.nbt = nbt;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getLabel() {
        return label;
    }

    public int getMeta() {
        return meta;
    }

    public int getAmount() {
        return amount;
    }

    public String getNbt() {
        return nbt;
    }
}
