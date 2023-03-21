package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.FluidStack;

public abstract class RecipeBaseFluid extends RecipeBase implements IFluidRecipe {

    protected FluidStack[] fluidInputs;

    protected FluidStack[] fluidOutputs;

    public FluidStack[] getFluidInputs() {
        return fluidInputs;
    }

    public FluidStack[] getFluidOutputs() {
        return fluidOutputs;
    }


}
