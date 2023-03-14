package com.github.minecraft_ta.reciper.recipe;

import com.github.minecraft_ta.reciper.ingredient.FluidStack;

public interface IFluidRecipe {

    FluidStack[] getFluidInputs();

    FluidStack[] getFluidOutputs();

}
