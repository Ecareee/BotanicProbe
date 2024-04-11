package com.ecaree.botanicprobe.botania.common.entity;

import com.ecaree.botanicprobe.BotanicProbe;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import vazkii.botania.common.entity.EntitySparkBase;

public class Spark implements IProbeInfoEntityProvider {
    @Override
    public String getID() {
        return BotanicProbe.MOD_ID + ":spark";
    }

    @Override
    public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntitySparkBase) {
            TOPUtil.renderContents(iProbeInfo);
        }
    }
}