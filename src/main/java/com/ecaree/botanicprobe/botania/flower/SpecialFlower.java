package com.ecaree.botanicprobe.botania.flower;

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

                if (generatingFlower.isValidBinding()) {
                    iProbeInfo.text(I18n.get("botanicprobe.text.binding")
                            + TOPUtil.getPosString(generatingFlower.getBindingPos()));
                } else {
                    iProbeInfo.text(I18n.get("botanicprobe.text.nonbound"));
                    if (generatingFlower.getBindingPos() != null) {
                        iProbeInfo.text(I18n.get("botanicprobe.text.last_binding")
                                + TOPUtil.getPosString(generatingFlower.getBindingPos()));
                    }
                }
                iProbeInfo.text("Mana: " + mana + "/" + manaMax);
            } else if (tile instanceof TileEntityFunctionalFlower functionalFlower) {
                final int mana = functionalFlower.getMana();
                final int manaMax = functionalFlower.getMaxMana();

                if (functionalFlower.isValidBinding()) {
                    iProbeInfo.text(I18n.get("botanicprobe.text.binding")
                            + TOPUtil.getPosString(functionalFlower.getBindingPos()));
                } else {
                    iProbeInfo.text(I18n.get("botanicprobe.text.nonbound"));
                    if (functionalFlower.getBindingPos() != null) {
                        iProbeInfo.text(I18n.get("botanicprobe.text.last_binding")
                                + TOPUtil.getPosString(functionalFlower.getBindingPos()));
                    }
                }
                iProbeInfo.text("Mana: " + mana + "/" + manaMax);
            }
        }
    }
}