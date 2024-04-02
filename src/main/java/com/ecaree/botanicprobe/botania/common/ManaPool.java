package com.ecaree.botanicprobe.botania.common;

import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.mana.TilePool;

public class ManaPool implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("manapool");
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

            if (level.getBlockState(data.getPos().below()).is(ModBlocks.alchemyCatalyst)) {
                iProbeInfo.text(I18n.get("botanicprobe.text.catalyst") + ModBlocks.alchemyCatalyst.getName().getString());
            } else if (level.getBlockState(data.getPos().below()).is(ModBlocks.conjurationCatalyst)) {
                iProbeInfo.text(I18n.get("botanicprobe.text.catalyst") + ModBlocks.conjurationCatalyst.getName().getString());
            }

            TOPUtil.displayAnalogOutputSignal(iProbeInfo, blockState, level, data.getPos());
        }
    }
}