package org.cyclops.integrateddynamics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.cyclops.cyclopscore.config.extendedconfig.BlockConfig;
import org.cyclops.integrateddynamics.IntegratedDynamics;

/**
 * Config for the Menril Door.
 * @author josephcsible
 *
 */
public class BlockMenrilDoorConfig extends BlockConfig {

    public BlockMenrilDoorConfig() {
        super(
                IntegratedDynamics._instance,
                "menril_door",
                eConfig -> new DoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_CYAN)
                        .strength(3.0F)
                        .sound(SoundType.WOOD)),
                getDefaultItemConstructor(IntegratedDynamics._instance)
        );
    }

}
