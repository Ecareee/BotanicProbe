package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.mixin.AccessorTileRuneAltar;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
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
                int recipeKeepTicks = ((AccessorTileRuneAltar) tile).getRecipeKeepTicks();

                if (lastRecipe != null) {
                    ContentCollector.addText(lastRecipe, I18n.get("botanicprobe.text.last_recipe"), false, false);
                }

                if (recipeKeepTicks != 0 && recipeKeepTicks != 1) {
                    ContentCollector.addText(TOPUtil.COOLDOWN_STACK,
                            I18n.get("botanicprobe.text.remaining_last_recipe_keep_time") + recipeKeepTicks + " Ticks");
                } else if (recipeKeepTicks == 1) {
                    ContentCollector.addText(TOPUtil.COOLDOWN_STACK,
                            I18n.get("botanicprobe.text.remaining_last_recipe_keep_time") + recipeKeepTicks + " Tick");
                }
            }
        }
    }
}