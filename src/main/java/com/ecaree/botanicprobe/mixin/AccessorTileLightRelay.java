package com.ecaree.botanicprobe.mixin;

import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vazkii.botania.common.block.tile.TileLightRelay;

@Mixin(value = TileLightRelay.class, remap = false)
public interface AccessorTileLightRelay {
    @Invoker("isValidBinding")
    boolean invokeIsValidBinding();

    @Invoker("getEndpoint")
    BlockPos invokeGetEndpoint();
}