package com.ecaree.botanicprobe.mixin;

import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import vazkii.botania.common.block.subtile.generating.SubTileRafflowsia;

@Mixin(value = SubTileRafflowsia.class, remap = false)
public interface AccessorSubTileRafflowsia {
    @Accessor("STREAK_OUTPUTS")
    int[] getStreakOutputs();

    @Invoker("getMaxStreak")
    int invokeGetMaxStreak();

    @Invoker("getValueForStreak")
    int invokeGetValueForStreak(int index);

    @Invoker("processFlower")
    int invokeProcessFlower(Block flower);
}
