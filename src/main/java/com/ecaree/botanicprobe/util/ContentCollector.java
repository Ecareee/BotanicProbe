package com.ecaree.botanicprobe.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ContentCollector {
    private static final List<BoxContent> contents = new ArrayList<>();

    public static void addText(String text) {
        contents.add(new BoxContent(text));
    }

    public static void addItem(ItemStack item) {
        contents.add(new BoxContent(item));
    }

    public static void addText(ItemStack item, String text) {
        contents.add(new BoxContent(item, text));
    }

    public static void addText(ItemStack item, String text, String text2) {
        contents.add(new BoxContent(item, text, text2));
    }

    public static void addText(ResourceLocation iconRL, String text) {
        contents.add(new BoxContent(iconRL, text));
    }

    public static void addText(Block block, String text) {
        contents.add(new BoxContent(new ItemStack(block), text));
    }

    public static void addProgressBar(int progress, int maxProgress) {
        contents.add(new BoxContent(progress, maxProgress));
    }

    public static void addTextWithProgressBar(ItemStack item, String text, int progress, int maxProgress) {
        contents.add(new BoxContent(item, text, progress, maxProgress));
    }

    public static List<BoxContent> getAndClearContents() {
        List<BoxContent> copy = new ArrayList<>(contents);
        contents.clear();
        return copy;
    }
}