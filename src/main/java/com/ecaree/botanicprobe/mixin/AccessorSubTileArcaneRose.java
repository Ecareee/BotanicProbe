package com.ecaree.botanicprobe.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import vazkii.botania.common.block.subtile.generating.SubTileArcaneRose;

@Mixin(value = SubTileArcaneRose.class, remap = false)
public interface AccessorSubTileArcaneRose {
    @Accessor("MANA_PER_XP_ORB")
    int getManaPerXpOrb();

    @Invoker("getEnchantmentXpValue")
    int invokeGetEnchantmentXpValue(ItemStack stack);
}
