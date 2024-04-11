package com.ecaree.botanicprobe.botanicalmachinery;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import de.melanx.botanicalmachinery.blocks.tiles.BlockEntityManaBattery;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class ManaBattery implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("manabattery");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof BlockEntityManaBattery tile) {
            final int mana = tile.getCurrentMana();
            final int manaCap = tile.getManaCap();

            if (ForgeRegistries.BLOCKS.getKey(blockState.getBlock()).toString().equals("botanicalmachinery:mana_battery_creative")) {
                ContentCollector.addText(TOPUtil.MANA_STACK, "Mana: ∞");
            } else {
                ContentCollector.addText(TOPUtil.MANA_STACK, "Mana: " + mana + "/" + manaCap);
            }
        }
    }
}