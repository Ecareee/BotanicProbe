package com.ecaree.botanicprobe.botania.common.tile.corporea;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.corporea.TileCorporeaRetainer;

public class CorporeaRetainer implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("corporearetainer");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileCorporeaRetainer tile) {
            final int color = ChatFormatting.GRAY.getColor();
            final int requestX = tile.getUpdateTag().getInt("requestX");
            final int requestY = tile.getUpdateTag().getInt("requestY");
            final int requestZ = tile.getUpdateTag().getInt("requestZ");
            BlockPos requestPos = new BlockPos(requestX, requestY, requestZ);
            final String requestType = tile.getUpdateTag().getString("requestType");
            ItemStack requestStack = ItemStack.of((CompoundTag) tile.getUpdateTag().get("requestStack"));
            final boolean retainMissing = tile.getUpdateTag().getBoolean("retainMissing");
            final String mode = I18n.get("botaniamisc.retainer." + (retainMissing ? "retain_missing" : "retain_all"));

            ContentCollector.addText(TOPUtil.STATUS_STACK,
                    I18n.get("botanicprobe.text.mode") + mode, color);

            if (requestX != 0 && requestY != 0 && requestZ != 0) {
                ContentCollector.addText(level.getBlockState(requestPos).getBlock(),
                        I18n.get("botanicprobe.text.request_from") + TOPUtil.getPosString(requestPos),
                        I18n.get("botanicprobe.text.request_type") + requestType);
                ContentCollector.addText(requestStack, I18n.get("botanicprobe.text.request_item")
                        + requestStack.getDisplayName().getString() + " x" + requestStack.getCount());
            }
        }
    }
}