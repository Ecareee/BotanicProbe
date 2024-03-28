package com.ecaree.botanicprobe.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import vazkii.botania.common.block.subtile.generating.SubTileGourmaryllis;

@Mixin(value = SubTileGourmaryllis.class, remap = false)
public interface AccessorSubTileGourmaryllis {
    @Accessor("STREAK_MULTIPLIERS")
    double[] getStreakMultipliers();

    @Invoker("getMaxStreak")
    int invokeGetMaxStreak();

    @Invoker("getMultiplierForStreak")
    double invokeGetMultiplierForStreak(int index);
//    FUCK YOU WIKI
//    double getMultiplierForStreak(int index);

    @Invoker("processFood")
    int invokeProcessFood(ItemStack food);
}