package com.ecaree.botanicprobe;

import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TOPUtil {
    public static final int LIGHT_BLUE = new Color(39, 255, 247).getRGB();
    public static final int GRAY = Color.gray.getRGB();
    public static final int WHITE = Color.white.getRGB();

    public static void setProgressBar(IProbeInfo iProbeInfo, int progress, int maxProgress) {
        iProbeInfo.progress(progress, maxProgress, new ProgressStyle()
                .width(90)
                .height(4)
                .numberFormat(NumberFormat.NONE)
                .borderColor(WHITE)
                .backgroundColor(GRAY)
                .filledColor(LIGHT_BLUE)
                .alternateFilledColor(LIGHT_BLUE));
    }

    public static ResourceLocation RL(String rlName) {
        return new ResourceLocation(BotanicProbe.MOD_ID, rlName);
    }

    // 将存储了 ItemStack 类型的 ListTag 类型转换为 ItemStack 类型
    public static List<ItemStack> convertListTagToItemStack(ListTag listTag) {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag itemTag = listTag.getCompound(i);
            ItemStack itemStack = ItemStack.of(itemTag);
            items.add(itemStack);
        }

        return items;
    }

    // 将存储了 Block 类型注册名的 ListTag 类型转换为 ItemStack 类型
    public static List<ItemStack> convertBlockListTagToItemStack(ListTag listTag) {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < listTag.size(); i++) {
            String blockName = listTag.getString(i);
            Block block = Registry.BLOCK.get(new ResourceLocation(blockName));
            if (block != Blocks.AIR) {
                items.add(new ItemStack(block.asItem()));
            }
        }

        return items;
    }

    // 将存储了 Block 类型注册名的 ListTag 类型转换为 Block 类型
    public static List<Block> convertBlockListTagToBlock(ListTag listTag) {
        List<Block> blocks = new ArrayList<>();

        for (int i = 0; i < listTag.size(); i++) {
            String blockName = listTag.getString(i);
            Block block = Registry.BLOCK.get(new ResourceLocation(blockName));
            if (block != Blocks.AIR) {
                blocks.add(block);
            }
        }

        return blocks;
    }
}
