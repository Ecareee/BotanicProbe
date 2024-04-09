package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.mixin.AccessorTileAlfPortal;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileAlfPortal;
import vazkii.botania.common.block.tile.mana.TilePool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlfPortal implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("alfportal");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileAlfPortal tile) {
            List<Integer> manaValues = new ArrayList<>();
            List<Integer> manaCapValues = new ArrayList<>();
            List<BlockPos> pools = new ArrayList<>();
            String text1;
            String text2 = null;
            String pos1;
            String pos2 = null;
            List<ItemStack> stacksIn = ((AccessorTileAlfPortal) tile).getStacksIn();

            for (BlockPos pos : tile.locatePylons()) {
                if (level.getBlockEntity(pos.below()) instanceof TilePool tilePool) {
                    manaValues.add(tilePool.getCurrentMana());
                    manaCapValues.add(tilePool.manaCap);
                    pools.add(tilePool.getBlockPos());

                    if (manaValues.size() >= 2) {
                        break;
                    }
                }
            }

            if (!manaValues.isEmpty()) {
                text1 = "Mana: " + manaValues.get(0) + "/" + manaCapValues.get(0);
                pos1 = "(" + TOPUtil.getPosString(pools.get(0)) + ")";

                if (manaValues.size() > 1) {
                    text2 = "Mana: " + manaValues.get(1) + "/" + manaCapValues.get(1);
                    pos2 = "(" + TOPUtil.getPosString(pools.get(1)) + ")";
                }

                if (text2 == null && pos2 == null) {
                    ContentCollector.addText(TOPUtil.MANA_STACK, text1, pos1);
                } else {
                    ContentCollector.addText(TOPUtil.MANA_STACK, Arrays.asList(text1, pos1, text2, pos2));
                }
            }

            if (!stacksIn.isEmpty()) {
                ContentCollector.addText(stacksIn, I18n.get("botanicprobe.text.items"), false, false);
            }
        }
    }
}