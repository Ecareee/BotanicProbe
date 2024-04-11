package com.ecaree.botanicprobe.botania.common.entity;

import com.ecaree.botanicprobe.BotanicProbe;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.botania.common.entity.EntityCorporeaSpark;

public class CorporeaSpark implements IProbeInfoEntityProvider {
    @Override
    public String getID() {
        return BotanicProbe.MOD_ID + ":corporeaspark";
    }

    @Override
    public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof EntityCorporeaSpark corporeaSpark) {
            ItemStack itemStack = corporeaSpark.getPickResult();
            final boolean master = corporeaSpark.isMaster();
            final boolean creative = corporeaSpark.isCreative();
            final String isMaster = TOPUtil.getBooleanString(master);
            final String isCreative = TOPUtil.getBooleanString(creative);
            BlockPos attachPos = null;

            if (corporeaSpark.getMaster() != null) {
                attachPos = corporeaSpark.getMaster().getAttachPos();
            }

            if (master || attachPos == null) {
                ContentCollector.addText(itemStack,
                        I18n.get("botanicprobe.text.master") + isMaster,
                        I18n.get("botanicprobe.text.creative") + isCreative);
            } else {
                ContentCollector.addText(itemStack,
                        I18n.get("botanicprobe.text.master") + isMaster,
                        I18n.get("botanicprobe.text.master_pos") + TOPUtil.getPosString(attachPos),
                        I18n.get("botanicprobe.text.creative") + isCreative);
            }
        }
    }
}