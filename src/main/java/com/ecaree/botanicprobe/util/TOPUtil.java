package com.ecaree.botanicprobe.util;

import com.ecaree.botanicprobe.BotanicProbe;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.styles.IconStyle;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import vazkii.botania.common.item.ModItems;

import java.awt.*;
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
                        horizontal
                                .item(content.getItem(), probeInfo.defaultItemStyle().width(16).height(16))
                                .text(content.getText());
                        break;
                    case MULTITEXT:
                        horizontal.item(content.getItem(), probeInfo.defaultItemStyle().width(16).height(16));
                        IProbeInfo textVertical = horizontal.vertical();
                        textVertical.text(content.getText()).text(content.getText2());
                        break;
                    case TEXT_WITH_ICONRL:
                        horizontal
                                .icon(content.getIconRL(), 0, 0, 16, 16, new IconStyle().bounds(16, 16).textureBounds(16, 16))
                                .text(content.getText());
                        break;
                    case ITEMS:
                        if (itemHorizontal == null) { // 防止每个物品都有一个边框
                            itemHorizontal = horizontal.vertical(probeInfo
                                            .defaultLayoutStyle()
                                            .borderColor(TOPUtil.LIGHT_BLUE)
                                            .spacing(0))
                                    .horizontal(new LayoutStyle().spacing(0));
                        }
                        itemHorizontal.item(content.getItem());
                        break;
                    case PROGRESS_BAR:
                        setProgressBar(horizontal, content.getProgress(), content.getMaxProgress());
                        break;
                    case TEXT_WITH_PROGRESS_BAR:
                        horizontal.item(content.getItem(), probeInfo.defaultItemStyle().width(16).height(16 + PADDING));
                        IProbeInfo vertical = horizontal.vertical();
                        vertical.text(content.getText(), probeInfo.defaultTextStyle().topPadding(PADDING));
                        setProgressBar(vertical, content.getProgress(), content.getMaxProgress());
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

    public static ResourceLocation RL(String rlName) {
        return new ResourceLocation(BotanicProbe.MOD_ID, rlName);
    }

    public static String getPosString(BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        return x + " " + y + " " + z;
    }
}