package com.ecaree.botanicprobe.mixin;

import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import vazkii.botania.api.recipe.IPureDaisyRecipe;
import vazkii.botania.common.block.subtile.SubTilePureDaisy;

@Mixin(value = SubTilePureDaisy.class, remap = false)
public interface AccessorSubTilePureDaisy {
    @Accessor("POSITIONS")
    BlockPos[] getPositions();

    @Invoker("findRecipe")
    IPureDaisyRecipe invokeFindRecipe(BlockPos coords);
}
