package com.ecaree.botanicprobe.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.entity.EntityManaBurst;

@Mixin(value = TileSpreader.class, remap = false)
public interface AccessorTileSpreader {
    @Invoker("getBurst")
    EntityManaBurst invokeGetBurst(boolean fake);
}