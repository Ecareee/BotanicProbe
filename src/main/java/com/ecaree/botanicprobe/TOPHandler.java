package com.ecaree.botanicprobe;

import com.ecaree.botanicprobe.botania.*;
import com.ecaree.botanicprobe.botanicalmachinery.*;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.minecraftforge.fml.ModList;

import java.util.function.Function;

public class TOPHandler implements Function<ITheOneProbe, Void>  {
    @Override
    public Void apply(ITheOneProbe iTheOneProbe) {
        iTheOneProbe.registerProvider(new ManaPool());
        iTheOneProbe.registerProvider(new RuneAltar());
        iTheOneProbe.registerEntityProvider(new Spark());
        iTheOneProbe.registerProvider(new SpecialFlower());
        iTheOneProbe.registerProvider(new Spreader());
        iTheOneProbe.registerProvider(new TerraPlate());
        iTheOneProbe.registerProvider(new CellBlock());

        if (ModList.get().isLoaded("botanicalmachinery")) {
            iTheOneProbe.registerProvider(new ManaBattery());
            iTheOneProbe.registerProvider(new MechanicalApothecary());
            iTheOneProbe.registerProvider(new MechanicalDaisy());
            iTheOneProbe.registerProvider(new MechanicalManaPool());
            iTheOneProbe.registerProvider(new WorkingTile());
        }

        return null;
    }
}
