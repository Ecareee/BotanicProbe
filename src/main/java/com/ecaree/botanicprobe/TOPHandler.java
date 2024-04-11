package com.ecaree.botanicprobe;

import com.ecaree.botanicprobe.botania.common.*;
import com.ecaree.botanicprobe.botania.common.entity.*;
import com.ecaree.botanicprobe.botania.common.tile.corporea.CorporeaFunnel;
import com.ecaree.botanicprobe.botania.common.tile.mana.ManaPool;
import com.ecaree.botanicprobe.botania.common.tile.mana.Spreader;
import com.ecaree.botanicprobe.botania.common.tile.*;
import com.ecaree.botanicprobe.botania.common.tile.mana.Turntable;
import com.ecaree.botanicprobe.botania.common.tile.string.RedString;
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
        iTheOneProbe.registerProvider(new AlfPortal());
        iTheOneProbe.registerProvider(new Altar());
        iTheOneProbe.registerProvider(new AnimatedTorch());
        iTheOneProbe.registerProvider(new Avatar());
        iTheOneProbe.registerProvider(new Bifrost());
        iTheOneProbe.registerProvider(new Brewery());
        iTheOneProbe.registerProvider(new Cacophonium());
        iTheOneProbe.registerProvider(new CellBlock());
        iTheOneProbe.registerProvider(new Cocoon());
        iTheOneProbe.registerProvider(new CorporeaFunnel());
        iTheOneProbe.registerProvider(new Enchanter());
        iTheOneProbe.registerProvider(new Hourglass());
        iTheOneProbe.registerProvider(new IncensePlate());
        iTheOneProbe.registerProvider(new LightRelay());
        iTheOneProbe.registerProvider(new ManaPool());
        iTheOneProbe.registerProvider(new Platform());
        iTheOneProbe.registerEntityProvider(new PoolMinecart());
        iTheOneProbe.registerProvider(new RedString());
        iTheOneProbe.registerProvider(new RuneAltar());
        iTheOneProbe.registerProvider(new SpawnerClaw());
        iTheOneProbe.registerProvider(new Spreader());
        iTheOneProbe.registerProvider(new TerraPlate());
        iTheOneProbe.registerProvider(new Turntable());

        iTheOneProbe.registerProvider(new TileMod());

        iTheOneProbe.registerEntityProvider(new SparkBase());
        // 使其注册晚于 SparkBase
        iTheOneProbe.registerEntityProvider(new CorporeaSpark());
        iTheOneProbe.registerEntityProvider(new ManaSpark());

        iTheOneProbe.registerProvider(new SpecialFlower());
        // 使其注册晚于 SpecialFlower
        iTheOneProbe.registerProvider(new Clayconia());
        iTheOneProbe.registerProvider(new Daffomill());
        iTheOneProbe.registerProvider(new Orechid());
        iTheOneProbe.registerProvider(new Rannuncarpus());
        iTheOneProbe.registerProvider(new Spectranthemum());
        iTheOneProbe.registerProvider(new ArcaneRose());
        iTheOneProbe.registerProvider(new Endoflame());
        iTheOneProbe.registerProvider(new Gourmaryllis());
        iTheOneProbe.registerProvider(new Hydroangeas());
        iTheOneProbe.registerProvider(new Munchdew());
        iTheOneProbe.registerProvider(new Rafflowsia());
        iTheOneProbe.registerProvider(new Spectrolus());
        iTheOneProbe.registerProvider(new PureDaisy());

        if (ModList.get().isLoaded("botanicalmachinery")) {
            iTheOneProbe.registerProvider(new ManaBattery());
            iTheOneProbe.registerProvider(new MechanicalApothecary());
            iTheOneProbe.registerProvider(new MechanicalDaisy());
            iTheOneProbe.registerProvider(new MechanicalManaPool());
            iTheOneProbe.registerProvider(new WorkingTile());
        }

        // 晚于作为容器的植物机械的注册
        iTheOneProbe.registerProvider(new Hopperhock());

        // 晚于其他任何注册
        iTheOneProbe.registerProvider(new Tile());
        iTheOneProbe.registerEntityProvider(new Spark());

        return null;
    }
}
