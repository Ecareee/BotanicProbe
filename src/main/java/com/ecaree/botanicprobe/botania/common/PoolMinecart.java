package com.ecaree.botanicprobe.botania.common;

import com.ecaree.botanicprobe.BotanicProbe;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.entity.EntityPoolMinecart;
import vazkii.botania.common.item.ModItems;

public class PoolMinecart implements IProbeInfoEntityProvider {
    @Override
    public String getID() {
        return BotanicProbe.MOD_ID + ":poolminecart";
    }

    @Override
    public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntityPoolMinecart minecart) {
            final int mana = minecart.getMana();
            final int manaCap = TilePool.MAX_MANA;

            TOPUtil.getHorizontal(iProbeInfo)
                    .item(new ItemStack(ModItems.manasteelNugget), new ItemStyle().width(16).height(16))
                    .text("Mana: " + mana + "/" + manaCap);
        }
    }
}