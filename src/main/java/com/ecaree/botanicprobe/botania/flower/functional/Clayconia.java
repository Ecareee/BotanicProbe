package com.ecaree.botanicprobe.botania.flower.functional;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.subtile.functional.SubTileClayconia;

public class Clayconia implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("clayconia");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileClayconia tile) {
            BlockPos coordsToPut = tile.getCoordsToPut();

            if (coordsToPut != null) {
                ContentCollector.addText(new ItemStack(Items.COMPASS),
                        I18n.get("botanicprobe.text.coords_to_put")
                                + TOPUtil.getPosString(coordsToPut));
            }
        }
    }
}