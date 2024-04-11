package com.ecaree.botanicprobe.botania.common.entity;

import com.ecaree.botanicprobe.BotanicProbe;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import vazkii.botania.common.entity.EntitySparkBase;
import vazkii.botania.common.helper.ColorHelper;

public class SparkBase implements IProbeInfoEntityProvider {
    @Override
    public String getID() {
        return BotanicProbe.MOD_ID + ":sparkbase";
    }

    @Override
    public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntitySparkBase spark) {
            DyeColor networkColor = spark.getNetwork();
            final int textColor = ColorHelper.getColorValue(networkColor);
            final String networkColorName = I18n.get("color.minecraft." + networkColor.getName());
            final boolean invisible = spark.serializeNBT().getBoolean("invis");
            final String invisibleStr = invisible ? I18n.get("botanicprobe.text.yes") : I18n.get("botanicprobe.text.no");

            ContentCollector.addText(TOPUtil.STATUS_STACK,
                    I18n.get("botanicprobe.text.network") + networkColorName, I18n.get("botanicprobe.text.invisible") + invisibleStr,
                    textColor, 0);
        }
    }
}