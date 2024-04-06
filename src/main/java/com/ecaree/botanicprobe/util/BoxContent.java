package com.ecaree.botanicprobe.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class BoxContent {
    private final ResourceLocation iconRL;
    private final ItemStack item;
    private final String text;
    private final String text2;
    private final int progress;
    private final int maxProgress;
    private final ContentType type;

    public BoxContent(String text) {
        this(null, null, text, null, 0, 0, ContentType.TEXT_WITHOUT_ICON);
    }

    public BoxContent(ItemStack item) {
        this(null, item, null, null, 0, 0, ContentType.ITEMS);
    }

    public BoxContent(ItemStack item, String text) {
        this(null, item, text, null, 0, 0, ContentType.TEXT);
    }

    public BoxContent(ItemStack item, String text, String text2) {
        this(null, item, text, text2, 0, 0, ContentType.MULTITEXT);
    }

    public BoxContent(ResourceLocation iconRL, String text) {
        this(iconRL, null, text, null, 0, 0, ContentType.TEXT_WITH_ICONRL);
    }

    public BoxContent(int progress, int maxProgress) {
        this(null, null, null, null, progress, maxProgress, ContentType.PROGRESS_BAR);
    }

    public BoxContent(ItemStack item, String text, int progress, int maxProgress) {
        this(null, item, text, null, progress, maxProgress, ContentType.TEXT_WITH_PROGRESS_BAR);
    }

    private BoxContent(ResourceLocation iconRL, ItemStack item, String text, String text2, int progress, int maxProgress, ContentType type) {
        this.iconRL = iconRL;
        this.item = item;
        this.text = text;
        this.text2 = text2;
        this.progress = progress;
        this.maxProgress = maxProgress;
        this.type = type;
    }

    public ResourceLocation getIconRL() {
        return iconRL;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getText() {
        return text;
    }

    public String getText2() {
        return text2;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public ContentType getType() {
        return type;
    }
}