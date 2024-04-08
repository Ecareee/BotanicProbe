package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.mixin.AccessorTileLightRelay;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block.ITileBound;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileLightRelay;

public class LightRelay implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("lightrelay");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileLightRelay tile) {
            String text1;
            String text2 = null;
            BlockPos nextDestination = tile.getNextDestination();
            BlockPos endpoint = ((AccessorTileLightRelay) tile).invokeGetEndpoint();

            if (((AccessorTileLightRelay) tile).invokeIsValidBinding()) {
                ContentCollector.addText(TOPUtil.WAND_STACK,
                        I18n.get("botanicprobe.text.binding")
                                + TOPUtil.getPosString(tile.getBinding()));
            } else {
                text1 = I18n.get("botanicprobe.text.nonbound");

                if (!tile.getBinding().equals(ITileBound.UNBOUND_POS)) {
                    text2 = I18n.get("botanicprobe.text.last_binding")
                            + TOPUtil.getPosString(tile.getBinding());
                }

                if (text2 == null) {
                    ContentCollector.addText(TOPUtil.WAND_STACK, text1);
                } else {
                    ContentCollector.addText(TOPUtil.WAND_STACK, text1, text2);
                }
            }

            if (nextDestination != ITileBound.UNBOUND_POS) {
                ContentCollector.addText(ModBlocks.lightRelayDefault,
                        I18n.get("botanicprobe.text.next_destination") + TOPUtil.getPosString(nextDestination),
                        I18n.get("botanicprobe.text.endpoint") + TOPUtil.getPosString(endpoint));
            }
        }
    }
}