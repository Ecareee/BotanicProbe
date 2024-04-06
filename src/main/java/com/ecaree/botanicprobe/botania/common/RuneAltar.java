package com.ecaree.botanicprobe.botania.common;

import com.ecaree.botanicprobe.mixin.AccessorTileRuneAltar;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileRuneAltar;

import java.util.List;

public class RuneAltar implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("runealtar");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileRuneAltar tile) {
            final int mana = tile.getCurrentMana();
            final int targetMana = tile.getTargetMana();

            if (targetMana != 0) {
                ContentCollector.addTextWithProgressBar(TOPUtil.MANA_STACK, "Mana: " + mana + "/" + targetMana, mana, targetMana);
            }

            if (player.isCrouching()) {
                List<ItemStack> lastRecipe = ((AccessorTileRuneAltar) tile).getLastRecipe();
                if (lastRecipe != null) {
                    iProbeInfo.text(I18n.get("botanicprobe.text.last_recipe"));
                    int rows = 0;
                    int idx = 0;
                    IProbeInfo horizontal = null;
                    for (ItemStack stackInSlot : lastRecipe) {
                        if (idx % 10 == 0) {
                            horizontal = iProbeInfo.vertical(iProbeInfo
                                            .defaultLayoutStyle()
                                            .borderColor(TOPUtil.LIGHT_BLUE)
                                            .spacing(0))
                                    .horizontal(new LayoutStyle().spacing(0));
                            rows++;
                            if (rows > 4) {
                                break;
                            }
                        }
                        horizontal.item(stackInSlot);
                        idx++;
                    }
                }
            }
        }
    }
}