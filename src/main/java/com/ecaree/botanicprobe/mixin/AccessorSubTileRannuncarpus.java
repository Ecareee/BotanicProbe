package com.ecaree.botanicprobe.mixin;

import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vazkii.botania.common.block.subtile.functional.SubTileRannuncarpus;

@Mixin(value = SubTileRannuncarpus.class, remap = false)
public interface AccessorSubTileRannuncarpus {
    @Invoker("getFilterPos")
    BlockPos invokeGetFilterPos();
}