package com.ecaree.botanicprobe.mixin;

import de.melanx.botanicalmachinery.blocks.BlockMechanicalApothecary;
import de.melanx.botanicalmachinery.blocks.base.BotanicalBlock;
import io.github.noeppi_noeppi.libx.block.RotationShape;
import net.minecraft.world.phys.shapes.Shapes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.world.level.block.Block.box;

@Mixin(value = BlockMechanicalApothecary.class, remap = false)
public abstract class MixinBlockMechanicalApothecary {
    @Final
    @Mutable
    @Shadow
    public static RotationShape SHAPE;

    // https://github.com/MelanX/botanical-machinery/blob/1.19.x/src/main/java/de/melanx/botanicalmachinery/blocks/BlockMechanicalApothecary.java#L50-L68
    static {
        SHAPE = new RotationShape(Shapes.or(
                BotanicalBlock.FRAME_SHAPE,
                box(0, 0, 0, 16, 1, 16),
                box(0, 1, 0, 1, 15, 1),
                box(15, 1, 0, 16, 15, 1),
                box(0, 1, 15, 1, 15, 16),
                box(15, 1, 15, 16, 15, 16),
                box(0, 15, 1, 1, 16, 15),
                box(15, 15, 1, 16, 16, 15),
                box(0, 15, 0, 16, 16, 1),
                box(0, 15, 15, 16, 16, 16),
                box(12, 10, 4, 13, 13, 12),
                box(3, 1, 3, 13, 2, 13),
                box(5, 2, 5, 11, 9, 11),
                box(3, 9, 3, 13, 10, 13),
                box(3, 10, 12, 13, 13, 13),
                box(3, 10, 3, 13, 13, 4),
                box(3, 10, 4, 4, 13, 12)
        ));
    }
}