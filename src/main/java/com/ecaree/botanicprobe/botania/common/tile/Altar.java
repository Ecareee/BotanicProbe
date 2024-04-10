package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.mixin.AccessorTileAltar;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileAltar;

import java.util.List;

public class Altar implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("altar");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileAltar tile) {
            if (player.isCrouching()) {
                List<ItemStack> lastRecipe = ((AccessorTileAltar) tile).getLastRecipe();
                int recipeKeepTicks = ((AccessorTileAltar) tile).getRecipeKeepTicks();

                if (lastRecipe != null) {
                    ContentCollector.addText(lastRecipe, I18n.get("botanicprobe.text.last_recipe"), false, false);
                }

                if (recipeKeepTicks != 0 && recipeKeepTicks != 1) {
                    ContentCollector.addText(TOPUtil.COOLDOWN_STACK,
                            I18n.get("botanicprobe.text.last_recipe_keep_time") + recipeKeepTicks + " Ticks");
                } else if (recipeKeepTicks == 1) {
                    ContentCollector.addText(TOPUtil.COOLDOWN_STACK,
                            I18n.get("botanicprobe.text.last_recipe_keep_time") + recipeKeepTicks + " Tick");
                }
            }
        }
    }
}