package com.ecaree.botanicprobe.botania.flower.generating;

import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.subtile.generating.SubTileSpectrolus;
import vazkii.botania.common.helper.ColorHelper;

public class Spectrolus implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("spectrolus");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileSpectrolus tile) {
            final int nextColor = tile.getUpdateTag().getInt("nextColor");
            Block block = ColorHelper.WOOL_MAP.apply(DyeColor.byId(nextColor));
            ItemStack itemStack = new ItemStack(block);

            iProbeInfo.text(I18n.get("botanicprobe.text.required_wool"));
            iProbeInfo
                    .horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                    .item(itemStack, iProbeInfo.defaultItemStyle().width(16).height(16))
                    .text(itemStack.getDescriptionId());
        }
    }
}
