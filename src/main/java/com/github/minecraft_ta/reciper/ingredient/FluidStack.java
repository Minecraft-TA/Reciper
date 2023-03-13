package com.github.minecraft_ta.reciper.ingredient;

public class FluidStack {

    private final int amount;

    private final String fluidName;

    public FluidStack(String fluidName, int amount) {
        this.fluidName = fluidName;
        this.amount = amount;
    }

    public FluidStack(String fluidName) {
        this(fluidName, 0);
    }
}
