package com.github.minecraft_ta.reciper.ingredient;

public class FluidStack {

    private final String fluidName;

    private int amount;

    public FluidStack(String fluidName, int amount) {
        this.fluidName = fluidName;
        this.amount = amount;
    }

    public FluidStack(String fluidName) {
        this(fluidName, 0);
    }

    public String getFluidName() {
        return fluidName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
