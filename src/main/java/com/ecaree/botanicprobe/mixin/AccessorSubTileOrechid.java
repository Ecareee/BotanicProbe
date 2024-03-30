package com.ecaree.botanicprobe.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vazkii.botania.common.block.subtile.functional.SubTileOrechid;

@Mixin(value = SubTileOrechid.class, remap = false)
public interface AccessorSubTileOrechid {
    @Invoker("getOreToPut")
    BlockState invokeGetOreToPut(BlockPos coords, BlockState state);

    @Invoker("getCoordsToPut")
    BlockPos invokeGetCoordsToPut();
}
