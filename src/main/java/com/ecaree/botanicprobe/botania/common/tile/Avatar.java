package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileAvatar;

public class Avatar implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("avatar");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileAvatar tile) {
            final int mana = tile.getCurrentMana();
//            Map<UUID, Integer> boostCooldowns = tile.getBoostCooldowns(); 不知道有什么用。
            Direction direction = tile.getAvatarFacing();
            String directionName = switch (direction) {
                case NORTH -> I18n.get("botanicprobe.text.north");
                case SOUTH -> I18n.get("botanicprobe.text.south");
                case WEST -> I18n.get("botanicprobe.text.west");
                case EAST -> I18n.get("botanicprobe.text.east");
                default -> "null";
            };

            ContentCollector.addText(TOPUtil.MANA_STACK, "Mana: " + mana + "/" + 6400); // 硬编码 6400，TileAvatar.MAX_MANA = 6400
            ContentCollector.addText(TOPUtil.COMPASS,
                    I18n.get("botanicprobe.text.direction") + directionName);
        }
    }
}