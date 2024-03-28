package com.ecaree.botanicprobe.botania.common;

import com.ecaree.botanicprobe.BotanicProbe;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.botania.common.entity.EntityManaSpark;
import vazkii.botania.common.item.ModItems;

public class Spark implements IProbeInfoEntityProvider {
    @Override
    public String getID() {
        return BotanicProbe.MOD_ID + ":spark";
    }

    @Override
    public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntityManaSpark sparkEntity) {

            ItemStack itemStack = null;
            String upgradeName = "null";
            switch (sparkEntity.getUpgrade()) {
                case DISPERSIVE:
                    itemStack = new ItemStack(ModItems.sparkUpgradeDispersive);
                    upgradeName = I18n.get("item.botania.spark_upgrade_dispersive");
                    break;
                case DOMINANT:
                    itemStack = new ItemStack(ModItems.sparkUpgradeDominant);
                    upgradeName = I18n.get("item.botania.spark_upgrade_dominant");
                    break;
                case RECESSIVE:
                    itemStack = new ItemStack(ModItems.sparkUpgradeRecessive);
                    upgradeName = I18n.get("item.botania.spark_upgrade_recessive");
                    break;
                case ISOLATED:
                    itemStack = new ItemStack(ModItems.sparkUpgradeIsolated);
                    upgradeName = I18n.get("item.botania.spark_upgrade_isolated");
                    break;
                case NONE:
                    break;
            }
            if (itemStack != null && !upgradeName.equals("null")) {
                iProbeInfo
                        .horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                        .item(itemStack, iProbeInfo.defaultItemStyle().width(16).height(16))
                        .text(upgradeName);
            }
        }
    }
}
