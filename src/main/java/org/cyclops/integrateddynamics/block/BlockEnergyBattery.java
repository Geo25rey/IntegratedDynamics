package org.cyclops.integrateddynamics.block;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.cyclops.cyclopscore.client.icon.Icon;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.integrateddynamics.IntegratedDynamics;
import org.cyclops.integrateddynamics.capability.energystorage.IEnergyStorageCapacity;
import org.cyclops.integrateddynamics.core.item.ItemBlockEnergyContainer;

/**
 * A block that can hold defined variables so that they can be referred to elsewhere in the network.
 *
 * @author rubensworks
 */
public class BlockEnergyBattery extends BlockEnergyBatteryBase {

    @Icon(location = "block/energy_battery_overlay")
    @OnlyIn(Dist.CLIENT)
    public TextureAtlasSprite iconOverlay;

    public BlockEnergyBattery(Block.Properties properties) {
        super(properties);
        if(MinecraftHelpers.isClientSide()) {
            IntegratedDynamics._instance.getIconProvider().registerIconHolderObject(this);
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        ItemStack itemStack = new ItemStack(this);

        int capacityOriginal = BlockEnergyBatteryConfig.capacity;
        int capacity = capacityOriginal;
        int lastCapacity;
        do {
            ItemStack currentStack = itemStack.copy();
            IEnergyStorageCapacity energyStorage = (IEnergyStorageCapacity) ((ItemBlockEnergyContainer) currentStack.getItem()).getEnergyBattery(currentStack).orElse(null);
            energyStorage.setCapacity(capacity);
            list.add(currentStack.copy());
            fill(energyStorage);
            list.add(currentStack.copy());
            lastCapacity = capacity;
            capacity = capacity << 2;
        } while (capacity < Math.min(BlockEnergyBatteryConfig.maxCreativeCapacity, BlockEnergyBatteryConfig.maxCreativeTabCapacity) && capacity > lastCapacity);
    }

    public boolean isCreative() {
        return false;
    }

}
