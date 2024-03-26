package com.ecaree.botanicprobe.botania;

import com.ecaree.botanicprobe.BotanicProbe;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
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
        return new ResourceLocation(BotanicProbe.MOD_ID, "specialflower");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileEntitySpecialFlower tile) {
            if (tile instanceof TileEntityGeneratingFlower generating) {
                final int mana = generating.getMana();
                final int manaMax = generating.getMaxMana();
                final int x = generating.getBindingPos().getX();
                final int y = generating.getBindingPos().getY();
                final int z = generating.getBindingPos().getZ();
                iProbeInfo.text(I18n.get("text.botanicprobe.binding") + x + " " + y + " " + z);
                iProbeInfo.text("Mana: " + mana + "/" + manaMax);
            } else if (tile instanceof TileEntityFunctionalFlower functional) {
                final int mana = functional.getMana();
                final int manaMax = functional.getMaxMana();
                final int x = functional.getBindingPos().getX();
                final int y = functional.getBindingPos().getY();
                final int z = functional.getBindingPos().getZ();
                iProbeInfo.text(I18n.get("text.botanicprobe.binding") + x + " " + y + " " + z);
                iProbeInfo.text("Mana: " + mana + "/" + manaMax);
            }
        }
    }
}
