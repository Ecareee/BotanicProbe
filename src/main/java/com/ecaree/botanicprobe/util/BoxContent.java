package com.ecaree.botanicprobe.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BoxContent {
    private final ResourceLocation iconRL;
    private final ItemStack item;
    private final List<ItemStack> items;
    private final boolean checkPlaceable;
    private final boolean setCount;
    private final int count;
    private final String text;
    private final String text2;
    private final String text3;
    private final int textColor;
    private final int textColor2;
    private final int textColor3;
    private final List<String> texts;
    private final int progress;
    private final int maxProgress;
    private final ContentType type;

    public static class Builder {
        private ResourceLocation iconRL;
        private ItemStack item;
        private List<ItemStack> items;
        private boolean checkPlaceable;
        private boolean setCount;
        private int count;
        private String text;
        private String text2;
        private String text3;
        private int textColor;
        private int textColor2;
        private int textColor3;
        private List<String> texts;
        private int progress;
        private int maxProgress;
        private final ContentType type;

        public Builder(ContentType type) {
            this.type = type;
        }

        public Builder iconRL(ResourceLocation iconRL) {
            this.iconRL = iconRL;
            return this;
        }

        public Builder item(ItemStack item) {
            this.item = item;
            return this;
        }

        public Builder items(List<ItemStack> items) {
            this.items = items;
            return this;
        }

        public Builder checkPlaceable(boolean checkPlaceable) {
            this.checkPlaceable = checkPlaceable;
            return this;
        }

        public Builder setCount(boolean setCount) {
            this.setCount = setCount;
            return this;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder text2(String text2) {
            this.text2 = text2;
            return this;
        }

        public Builder text3(String text3) {
            this.text3 = text3;
            return this;
        }

        public Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder textColor2(int textColor2) {
            this.textColor2 = textColor2;
            return this;
        }

        public Builder textColor3(int textColor3) {
            this.textColor3 = textColor3;
            return this;
        }

        public Builder texts(List<String> texts) {
            this.texts = texts;
            return this;
        }

        public Builder progress(int progress, int maxProgress) {
            this.progress = progress;
            this.maxProgress = maxProgress;
            return this;
        }

        public BoxContent build() {
            return new BoxContent(this);
        }
    }

    private BoxContent(Builder builder) {
        this.iconRL = builder.iconRL;
        this.item = builder.item;
        this.items = builder.items;
        this.checkPlaceable = builder.checkPlaceable;
        this.setCount = builder.setCount;
        this.count = builder.count;
        this.text = builder.text;
        this.text2 = builder.text2;
        this.text3 = builder.text3;
        this.textColor = builder.textColor;
        this.textColor2 = builder.textColor2;
        this.textColor3 = builder.textColor3;
        this.texts = builder.texts;
        this.progress = builder.progress;
        this.maxProgress = builder.maxProgress;
        this.type = builder.type;
    }

    public ResourceLocation getIconRL() {
        return iconRL;
    }

    public ItemStack getItem() {
        return item;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public boolean isCheckPlaceable() {
        return checkPlaceable;
    }

    public boolean isSetCount() {
        return setCount;
    }

    public int getCount() {
        return count;
    }

    public String getText() {
        return text;
    }

    public String getText2() {
        return text2;
    }

    public String getText3() {
        return text3;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTextColor2() {
        return textColor2;
    }

    public int getTextColor3() {
        return textColor3;
    }

    public List<String> getTexts() {
        return texts;
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