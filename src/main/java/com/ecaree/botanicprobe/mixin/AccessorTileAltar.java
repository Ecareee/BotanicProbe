package com.ecaree.botanicprobe.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.botania.common.block.tile.TileAltar;

import java.util.List;

@Mixin(value = TileAltar.class, remap = false)
public interface AccessorTileAltar {
    @Accessor("lastRecipe")
    List<ItemStack> getLastRecipe();

    @Accessor("recipeKeepTicks")
    int getRecipeKeepTicks();
}