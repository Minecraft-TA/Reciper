package com.github.minecraft_ta.reciper.ingredient;

import java.util.Objects;

public class ItemStack {

    private final String ingredientName;
    private final String label;
    private final int meta;
    private int amount;
    private final String nbt;

    public ItemStack(String ingredientName, String label, int meta, String nbt, int amount) {
        this(ingredientName, label, meta, nbt);
        this.amount = amount;
    }

    public ItemStack(String ingredientName, String label, int meta, String nbt) {
        this.ingredientName = ingredientName;
        this.label = label;
        this.meta = meta;
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

    @Override
    public String toString() {
        return "ItemStack{" +
                "ingredientName='" + ingredientName + '\'' +
                ", label='" + label + '\'' +
                ", meta=" + meta +
                ", amount=" + amount +
                ", nbt='" + nbt + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ingredientName, this.meta, this.nbt);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
