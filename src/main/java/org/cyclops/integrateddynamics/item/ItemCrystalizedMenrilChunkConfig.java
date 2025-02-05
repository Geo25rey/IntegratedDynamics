package org.cyclops.integrateddynamics.item;

import net.minecraft.world.item.Item;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.integrateddynamics.IntegratedDynamics;

/**
 * Config for the Crystalized Menril Chunk.
 * @author rubensworks
 *
 */
public class ItemCrystalizedMenrilChunkConfig extends ItemConfig {

    public ItemCrystalizedMenrilChunkConfig() {
        super(
                IntegratedDynamics._instance,
                "crystalized_menril_chunk",
                eConfig -> new Item(new Item.Properties()
                        .tab(IntegratedDynamics._instance.getDefaultItemGroup()))
        );
    }

}
