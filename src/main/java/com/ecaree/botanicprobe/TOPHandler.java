package com.ecaree.botanicprobe;

import com.ecaree.botanicprobe.botania.common.*;
import com.ecaree.botanicprobe.botania.flower.PureDaisy;
import com.ecaree.botanicprobe.botania.flower.SpecialFlower;
import com.ecaree.botanicprobe.botania.flower.functional.*;
import com.ecaree.botanicprobe.botania.flower.generating.*;
import com.ecaree.botanicprobe.botanicalmachinery.*;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.minecraftforge.fml.ModList;

import java.util.function.Function;

public class TOPHandler implements Function<ITheOneProbe, Void>  {
    @Override
    public Void apply(ITheOneProbe iTheOneProbe) {
        iTheOneProbe.registerProvider(new CellBlock());
        iTheOneProbe.registerProvider(new ManaPool());
        iTheOneProbe.registerProvider(new RuneAltar());
        iTheOneProbe.registerEntityProvider(new Spark());
        iTheOneProbe.registerProvider(new Spreader());
        iTheOneProbe.registerProvider(new TerraPlate());

        iTheOneProbe.registerProvider(new SpecialFlower());

        // 使其注册晚于 `SpecialFlower` 以调整显示顺序
        iTheOneProbe.registerProvider(new ArcaneRose());
        iTheOneProbe.registerProvider(new Endoflame());
        iTheOneProbe.registerProvider(new Gourmaryllis());
        iTheOneProbe.registerProvider(new Hydroangeas());
        iTheOneProbe.registerProvider(new Munchdew());
        iTheOneProbe.registerProvider(new Rafflowsia());
        iTheOneProbe.registerProvider(new Spectrolus());
        iTheOneProbe.registerProvider(new PureDaisy());
        iTheOneProbe.registerProvider(new Spectranthemum());
        iTheOneProbe.registerProvider(new Clayconia());
        iTheOneProbe.registerProvider(new Orechid());

        if (ModList.get().isLoaded("botanicalmachinery")) {
            iTheOneProbe.registerProvider(new ManaBattery());
            iTheOneProbe.registerProvider(new MechanicalApothecary());
            iTheOneProbe.registerProvider(new MechanicalDaisy());
            iTheOneProbe.registerProvider(new MechanicalManaPool());
            iTheOneProbe.registerProvider(new WorkingTile());
        }

        // 晚于植物机械容器的注册
        iTheOneProbe.registerProvider(new Hopperhock());

        return null;
    }
}
