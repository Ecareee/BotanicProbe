package com.ecaree.botanicprobe.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.botania.common.block.tile.TileRuneAltar;

import java.util.List;

@Mixin(value = TileRuneAltar.class, remap = false)
public interface AccessorTileRuneAltar {
    @Accessor("lastRecipe")
    List<ItemStack> getLastRecipe();

    @Accessor("recipeKeepTicks")
    int getRecipeKeepTicks();
}