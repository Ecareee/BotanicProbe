package com.ecaree.botanicprobe;

import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;

import java.awt.*;

public class TOPUtil {
    private static final int LIGHT_BLUE = new Color(39, 255, 247).getRGB();
    private static final int GRAY = java.awt.Color.gray.getRGB();
    private static final int WHITE = java.awt.Color.white.getRGB();

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
}
