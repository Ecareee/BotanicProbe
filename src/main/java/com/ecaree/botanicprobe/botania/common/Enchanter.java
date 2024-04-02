package com.ecaree.botanicprobe.botania.common;

import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileEnchanter;

public class Enchanter implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("enchanter");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileEnchanter tile) {
            final int currentMana = tile.getCurrentMana();
            final int manaRequired = tile.getUpdateTag().getInt("manaRequired");
            TileEnchanter.State stage = tile.stage;
            final String stageName = switch (stage) {
                case IDLE -> I18n.get("botanicprobe.text.status_idle");
                case GATHER_ENCHANTS -> I18n.get("botanicprobe.text.status_gather_enchants");
                case GATHER_MANA -> I18n.get("botanicprobe.text.status_gather_mana");
                case DO_ENCHANT -> I18n.get("botanicprobe.text.status_do_enchant");
                case RESET -> I18n.get("botanicprobe.text.status_reset");
            };
            final int stageTicks = tile.stageTicks;
            ItemStack itemToEnchant = tile.itemToEnchant;

            if (manaRequired != -1 && manaRequired != 0) {
                iProbeInfo.text("Mana: " + currentMana + "/" + manaRequired);
            }
            iProbeInfo.text(I18n.get("botanicprobe.text.status") + stageName);
            if (stage == TileEnchanter.State.GATHER_MANA) {
                TOPUtil.setProgressBar(iProbeInfo, currentMana, manaRequired);
            }
            if (stage == TileEnchanter.State.DO_ENCHANT) {
                TOPUtil.setProgressBar(iProbeInfo, stageTicks, 100); // 硬编码 100，https://github.com/VazkiiMods/Botania/blob/1.18.x/Xplat/src/main/java/vazkii/botania/common/block/tile/TileEnchanter.java#L280
            }
            if (!itemToEnchant.isEmpty()) {
                iProbeInfo.text(I18n.get("botanicprobe.text.item_to_enchant")
                        + itemToEnchant.getDisplayName().getString()); // 带中括号的本地化名
            }
        }
    }
}