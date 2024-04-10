package com.ecaree.botanicprobe.botania.common.tile.mana;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.botania.api.recipe.IManaInfusionRecipe;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.mana.TilePool;

public class ManaPool implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("manapool");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TilePool tile) {
            final int mana = tile.getCurrentMana();
            final int manaCap = tile.manaCap;
            ItemStack itemStack = player.getMainHandItem();

            if (ForgeRegistries.BLOCKS.getKey(blockState.getBlock()).toString().equals("botania:creative_pool")) {
                ContentCollector.addText(TOPUtil.MANA_STACK, "Mana: ∞");
            } else {
                ContentCollector.addText(TOPUtil.MANA_STACK, "Mana: " + mana + "/" + manaCap);
            }

            if (level.getBlockState(data.getPos().below()).is(ModBlocks.alchemyCatalyst)) {
                ContentCollector.addText(ModBlocks.alchemyCatalyst,
                        I18n.get("botanicprobe.text.catalyst") + ModBlocks.alchemyCatalyst.getName().getString());
            } else if (level.getBlockState(data.getPos().below()).is(ModBlocks.conjurationCatalyst)) {
                ContentCollector.addText(ModBlocks.conjurationCatalyst,
                        I18n.get("botanicprobe.text.catalyst") + ModBlocks.conjurationCatalyst.getName().getString());
            } else if (level.getBlockState(data.getPos().below()).is(ModBlocks.manaVoid)) {
                ContentCollector.addText(ModBlocks.manaVoid,
                        I18n.get("botanicprobe.text.mana_void"));
            }

            if (!itemStack.isEmpty()) {
                IManaInfusionRecipe recipe = tile.getMatchingRecipe(itemStack, level.getBlockState(data.getPos().below()));

                if (recipe != null) {
                    ItemStack displayStack = new ItemStack(itemStack.getItem());
                    displayStack.setCount(1);
                    int manaToConsume = recipe.getManaToConsume();

                    ContentCollector.addText(displayStack,
                            I18n.get("botanicprobe.text.holding_item") + itemStack.getDisplayName().getString()
                                    + " " + I18n.get("botanicprobe.text.will_consume") + manaToConsume + " Mana");
                }
            }
        }
    }
}