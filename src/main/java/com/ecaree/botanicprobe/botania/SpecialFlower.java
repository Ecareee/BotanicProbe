package com.ecaree.botanicprobe.botania;

import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;
import vazkii.botania.api.subtile.TileEntitySpecialFlower;

public class SpecialFlower implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("specialflower");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileEntitySpecialFlower tile) {
            if (tile instanceof TileEntityGeneratingFlower generatingFlower) {
                final int mana = generatingFlower.getMana();
                final int manaMax = generatingFlower.getMaxMana();

                if (generatingFlower.getBindingPos() != null) {
                    final int x = generatingFlower.getBindingPos().getX();
                    final int y = generatingFlower.getBindingPos().getY();
                    final int z = generatingFlower.getBindingPos().getZ();
                    iProbeInfo.text(I18n.get("botanicprobe.text.binding") + x + " " + y + " " + z);
                } else {
                    iProbeInfo.text(I18n.get("botanicprobe.text.nonbound"));
                }
                iProbeInfo.text("Mana: " + mana + "/" + manaMax);
            } else if (tile instanceof TileEntityFunctionalFlower functionalFlower) {
                final int mana = functionalFlower.getMana();
                final int manaMax = functionalFlower.getMaxMana();

                if (functionalFlower.getBindingPos() != null) {
                    final int x = functionalFlower.getBindingPos().getX();
                    final int y = functionalFlower.getBindingPos().getY();
                    final int z = functionalFlower.getBindingPos().getZ();
                    iProbeInfo.text(I18n.get("botanicprobe.text.binding") + x + " " + y + " " + z);
                } else {
                    iProbeInfo.text(I18n.get("botanicprobe.text.nonbound"));
                }
                iProbeInfo.text("Mana: " + mana + "/" + manaMax);
            }
        }
    }
}