package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileHourglass;

public class Hourglass implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("hourglass");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileHourglass tile) {
            final int time = tile.getUpdateTag().getInt("time");
            final int totalTime = tile.getTotalTime();
            final boolean locked = tile.lock;
            ItemStack itemStack = player.getMainHandItem();
            final int itemTime = TileHourglass.getStackItemTime(itemStack);

            ContentCollector.addTextWithProgressBar(TOPUtil.STATUS_STACK,
                    I18n.get("botanicprobe.text.time")
                            + StringUtil.formatTickDuration(time) + "/" + StringUtil.formatTickDuration(totalTime),
                    time, totalTime);

            if (locked) {
                ContentCollector.addText(new ItemStack(Items.BARRIER),
                        I18n.get("botanicprobe.text.locked"));
            }

            if (itemTime != 0) {
                ItemStack displayStack = new ItemStack(itemStack.getItem());
                displayStack.setCount(1);

                ContentCollector.addText(displayStack,
                        I18n.get("botanicprobe.text.holding_item") + itemStack.getDisplayName().getString()
                                + " " + I18n.get("botanicprobe.text.will_increase_time") + StringUtil.formatTickDuration(itemTime));
            }
        }
    }
}