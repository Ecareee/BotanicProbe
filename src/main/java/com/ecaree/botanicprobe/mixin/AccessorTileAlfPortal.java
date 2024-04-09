package com.ecaree.botanicprobe.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.botania.common.block.tile.TileAlfPortal;

import java.util.List;

@Mixin(value = TileAlfPortal.class, remap = false)
public interface AccessorTileAlfPortal {
    @Accessor("stacksIn")
    List<ItemStack> getStacksIn();
}