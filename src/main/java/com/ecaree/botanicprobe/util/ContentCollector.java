package com.ecaree.botanicprobe.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ContentCollector {
    private static final List<BoxContent> contents = new ArrayList<>();

    public static void addText(String text) {
        contents.add(new BoxContent.Builder(ContentType.TEXT_WITHOUT_ICON).text(text).build());
    }

    public static void addText(ItemStack item, String text) {
        contents.add(new BoxContent.Builder(ContentType.TEXT).item(item).text(text).build());
    }

    public static void addText(List<ItemStack> items, String text, boolean checkPlaceable, boolean setCount) {
        contents.add(new BoxContent.Builder(ContentType.ITEMS).items(items).text(text).checkPlaceable(checkPlaceable).setCount(setCount).build());
    }

    public static void addText(List<ItemStack> items, String text, boolean checkPlaceable, boolean setCount, int count) {
        contents.add(new BoxContent.Builder(ContentType.ITEMS).items(items).text(text).checkPlaceable(checkPlaceable).setCount(setCount).count(count).build());
    }

    public static void addText(ItemStack item, String text, String text2) {
        contents.add(new BoxContent.Builder(ContentType.MULTITEXT).item(item).text(text).text2(text2).build());
    }

    public static void addText(ResourceLocation iconRL, String text) {
        contents.add(new BoxContent.Builder(ContentType.TEXT_WITH_ICONRL).iconRL(iconRL).text(text).build());
    }

    public static void addText(Block block, String text) {
        contents.add(new BoxContent.Builder(ContentType.TEXT).item(new ItemStack(block)).text(text).build());
    }

    public static void addProgressBar(int progress, int maxProgress) {
        contents.add(new BoxContent.Builder(ContentType.PROGRESS_BAR).progress(progress, maxProgress).build());
    }

    public static void addTextWithProgressBar(ItemStack item, String text, int progress, int maxProgress) {
        contents.add(new BoxContent.Builder(ContentType.TEXT_WITH_PROGRESS_BAR).item(item).text(text).progress(progress, maxProgress).build());
    }

    public static List<BoxContent> getAndClearContents() {
        List<BoxContent> copy = new ArrayList<>(contents);
        contents.clear();
        return copy;
    }
}