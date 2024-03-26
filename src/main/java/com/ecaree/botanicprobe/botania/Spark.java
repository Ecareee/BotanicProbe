package com.ecaree.botanicprobe.botania;

import com.ecaree.botanicprobe.BotanicProbe;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import vazkii.botania.common.entity.EntityManaSpark;

public class Spark implements IProbeInfoEntityProvider {
    @Override
    public String getID() {
        return BotanicProbe.MOD_ID + ":spark";
    }

    @Override
    public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntityManaSpark sparkEntity) {

            String upgradeName = "null";
            switch (sparkEntity.getUpgrade()) {
                case DISPERSIVE:
                    upgradeName = I18n.get("item.botania.spark_upgrade_dispersive");
                    break;
                case DOMINANT:
                    upgradeName = I18n.get("item.botania.spark_upgrade_dominant");
                    break;
                case RECESSIVE:
                    upgradeName = I18n.get("item.botania.spark_upgrade_recessive");
                    break;
                case ISOLATED:
                    upgradeName = I18n.get("item.botania.spark_upgrade_isolated");
                    break;
                case NONE:
                    break;
            }
            if (!upgradeName.equals("null")) {
                iProbeInfo.text(upgradeName);
            }
        }
    }
}
