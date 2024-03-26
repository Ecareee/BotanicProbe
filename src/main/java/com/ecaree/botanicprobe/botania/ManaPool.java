package com.ecaree.botanicprobe.botania;

import com.ecaree.botanicprobe.BotanicProbe;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.botania.common.block.tile.mana.TilePool;

public class ManaPool implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(BotanicProbe.MOD_ID, "manapool");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TilePool tile) {
            final int mana = tile.getCurrentMana();
            final int manaCap = tile.manaCap;

            if (ForgeRegistries.BLOCKS.getKey(blockState.getBlock()).toString().equals("botania:creative_pool")) {
                iProbeInfo.text("Mana: ∞");
            } else {
                iProbeInfo.text("Mana: " + mana + "/" + manaCap);
            }
        }
    }
}
