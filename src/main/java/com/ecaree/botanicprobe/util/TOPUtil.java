package com.ecaree.botanicprobe.util;

import com.ecaree.botanicprobe.BotanicProbe;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.styles.IconStyle;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.ItemHandlerHelper;
import vazkii.botania.api.item.IFlowerPlaceable;
import vazkii.botania.common.item.ModItems;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TOPUtil {
    public static final int LIGHT_BLUE = new Color(39, 255, 247).getRGB();
    public static final int GRAY = Color.gray.getRGB();
    public static final int WHITE = Color.white.getRGB();
    public static final int ORANGE = Color.orange.getRGB();
    public static final int PADDING = 2;
    public static final ItemStack MANA_STACK = new ItemStack(ModItems.manasteelNugget);
    public static final ItemStack STATUS_STACK = new ItemStack(Items.CLOCK);
    public static final ItemStack WAND_STACK = new ItemStack(ModItems.twigWand);
    public static final ItemStack COOLDOWN_STACK = new ItemStack(Items.SNOWBALL);
    public static final ItemStack COMPASS = new ItemStack(Items.COMPASS);

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

    // 灵感来自 eiobox
    @SuppressWarnings("DuplicatedCode")
    public static void renderContents(IProbeInfo probeInfo) {
        List<BoxContent> contents = ContentCollector.getAndClearContents();

        if (!contents.isEmpty()) {
            IProbeInfo box = getBox(probeInfo);
            IProbeInfo itemHorizontal = null;

            for (BoxContent content : contents) {
                IProbeInfo horizontal = box.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER).spacing(PADDING));

                switch (content.getType()) {
                    case TEXT_WITHOUT_ICON:
                        horizontal.text(content.getText());
                        break;
                    case TEXT:
                        horizontal.item(content.getItem(), probeInfo.defaultItemStyle().width(16).height(16));

                        addTextComponent(horizontal, content.getText(), content.getTextColor());
                        break;
                    case TWO_LINES:
                        horizontal.item(content.getItem(), probeInfo.defaultItemStyle().width(16).height(16));
                        IProbeInfo vertical1 = horizontal.vertical();

                        addTextComponent(vertical1, content.getText(), content.getTextColor());
                        addTextComponent(vertical1, content.getText2(), content.getTextColor2());
                        break;
                    case THREE_LINES:
                        horizontal.item(content.getItem(), probeInfo.defaultItemStyle().width(16).height(16));
                        IProbeInfo vertical2 = horizontal.vertical();

                        addTextComponent(vertical2, content.getText(), content.getTextColor());
                        addTextComponent(vertical2, content.getText2(), content.getTextColor2());
                        addTextComponent(vertical2, content.getText3(), content.getTextColor3());
                        break;
                    case MULTILINE:
                        horizontal.item(content.getItem(), probeInfo.defaultItemStyle().width(16).height(16));

                        IProbeInfo textVertical = horizontal.vertical();

                        for (String text : content.getTexts()) {
                            textVertical.text(text);
                        }
                        break;
                    case TEXT_WITH_ICONRL:
                        horizontal
                                .icon(content.getIconRL(), 0, 0, 16, 16, new IconStyle().bounds(16, 16).textureBounds(16, 16))
                                .text(content.getText());
                        break;
                    case ITEMS:
                        // 尝试修复两个边框间间距问题
                        if (content.getItems() != null) {
                            int rows = 0;
                            int idx = 0;
                            boolean isTextSet = false;
                            IProbeInfo vertical = null;
                            IProbeInfo itemVertical = null;
                            List<ItemStack> mergedStacks = new ArrayList<>();

                            for (ItemStack stackInSlot : content.getItems()) {
                                mergeItemStacks(mergedStacks, stackInSlot);
                            }

                            for (ItemStack stackInSlot : mergedStacks) {
                                // 提取方法有 BUGS
                                if (!stackInSlot.isEmpty()) { // 跳过空气物品
                                    if (!content.isCheckPlaceable()) {
                                        if (vertical == null) {
                                            vertical = horizontal.vertical();
                                        }

                                        if (!isTextSet) {
                                            vertical.text(content.getText());
                                            isTextSet = true;
                                            itemVertical = box.vertical(probeInfo.defaultLayoutStyle().borderColor(LIGHT_BLUE).spacing(0));
                                        }

                                        if (idx % 10 == 0 || itemHorizontal == null) {
                                            itemHorizontal = itemVertical.vertical().horizontal(new LayoutStyle().spacing(0));

                                            rows++;
                                            if (rows > 4) {
                                                break;
                                            }
                                        }

                                        itemHorizontal.item(stackInSlot);

                                        if (content.isSetCount()) {
                                            mergedStacks.get(0).setCount(content.getCount());
                                        }

                                        idx++;
                                    } else {
                                        if (stackInSlot.getItem() instanceof BlockItem || stackInSlot.getItem() instanceof IFlowerPlaceable) { // 必须是可放置的方块
                                            if (vertical == null) {
                                                vertical = horizontal.vertical();
                                            }

                                            if (!isTextSet) {
                                                vertical.text(content.getText());
                                                isTextSet = true;
                                                itemVertical = box.vertical(probeInfo.defaultLayoutStyle().borderColor(LIGHT_BLUE).spacing(0));
                                            }

                                            if (idx % 10 == 0 || itemHorizontal == null) {
                                                itemHorizontal = itemVertical.vertical().horizontal(new LayoutStyle().spacing(0));

                                                rows++;
                                                if (rows > 4) {
                                                    break;
                                                }
                                            }

                                            itemHorizontal.item(stackInSlot);

                                            if (content.isSetCount()) {
                                                mergedStacks.get(0).setCount(content.getCount());
                                            }

                                            idx++;
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case PROGRESS_BAR:
                        setProgressBar(horizontal, content.getProgress(), content.getMaxProgress());
                        break;
                    case TEXT_WITH_PROGRESS_BAR:
                        horizontal.item(content.getItem(), probeInfo.defaultItemStyle().width(16).height(16 + PADDING));
                        IProbeInfo vertical3 = horizontal.vertical();

                        if (content.getTextColor() != 0) {
                            net.minecraft.network.chat.Component cmp = new net.minecraft.network.chat.TextComponent(content.getText()).setStyle(Style.EMPTY.withColor(content.getTextColor()));
                            vertical3.mcText(cmp, probeInfo.defaultTextStyle().topPadding(PADDING));
                        } else {
                            vertical3.text(content.getText(), probeInfo.defaultTextStyle().topPadding(PADDING));
                        }
                        setProgressBar(vertical3, content.getProgress(), content.getMaxProgress());
                        break;
                }
            }
        }
    }

    public static IProbeInfo getBox(IProbeInfo probeInfo) {
        return probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(ORANGE).spacing(PADDING));
    }

    public static IProbeInfo getHorizontal(IProbeInfo probeInfo) {
        return getBox(probeInfo).horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER).spacing(PADDING));
    }

    public static void mergeItemStacks(List<ItemStack> stacks, ItemStack newStack) {
        if (newStack.isEmpty()) {
            return;
        }

        for (ItemStack stack : stacks) {
            if (ItemHandlerHelper.canItemStacksStack(stack, newStack)) {
                stack.grow(newStack.getCount());
                return;
            }
        }

        stacks.add(newStack.copy());
    }

    private static void addTextComponent(IProbeInfo probeInfo, String text, int textColor) {
        if (textColor != 0) {
            net.minecraft.network.chat.Component cmp = new net.minecraft.network.chat.TextComponent(text).setStyle(Style.EMPTY.withColor(textColor));
            probeInfo.mcText(cmp);
        } else {
            probeInfo.text(text);
        }
    }

    public static ResourceLocation rl(String rlName) {
        return new ResourceLocation(BotanicProbe.MOD_ID, rlName);
    }

    public static String posToString(BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        return x + " " + y + " " + z;
    }

    public static String booleanToString(boolean b) {
        return b ? I18n.get("botanicprobe.text.yes") : I18n.get("botanicprobe.text.no");
    }

    public static String directionToString(Direction direction) {
        return switch (direction) {
            case NORTH -> I18n.get("botanicprobe.text.north");
            case SOUTH -> I18n.get("botanicprobe.text.south");
            case WEST -> I18n.get("botanicprobe.text.west");
            case EAST -> I18n.get("botanicprobe.text.east");
            default -> "null";
        };
    }

    public static String floatToPercentage(float f) {
        java.text.NumberFormat percentFormat = java.text.NumberFormat.getPercentInstance();
        return percentFormat.format(f);
    }
}