package org.cyclops.integrateddynamics.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.cyclops.cyclopscore.helper.BlockHelpers;
import org.cyclops.cyclopscore.helper.TileHelpers;
import org.cyclops.integrateddynamics.api.block.IFacadeable;
import org.cyclops.integrateddynamics.capability.facadeable.FacadeableConfig;

/**
 * An item that represents a facade of a certain type.
 * @author rubensworks
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ItemFacade extends Item {

    public ItemFacade(Properties properties) {
        super(properties);
    }

    public BlockState getFacadeBlock(ItemStack itemStack) {
        if(!itemStack.isEmpty() && itemStack.hasTag()) {
            CompoundNBT tag = itemStack.getTag();
            return BlockHelpers.deserializeBlockState(tag.get("block"));
        }
        return null;
    }

    public ItemStack getFacadeBlockItem(ItemStack itemStack) {
        BlockState blockState = getFacadeBlock(itemStack);
        if(blockState != null) {
            return BlockHelpers.getItemStackFromBlockState(blockState);
        }
        return null;
    }

    public void writeFacadeBlock(ItemStack itemStack, BlockState blockState) {
        CompoundNBT tag = itemStack.getOrCreateTag();
        INBT serializedBlockState = BlockHelpers.serializeBlockState(blockState);
        tag.put("block", serializedBlockState);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack itemStack) {
        ITextComponent suffix = new TranslationTextComponent("general.integrateddynamics.info.none")
                .applyTextStyle(TextFormatting.ITALIC);
        ItemStack itemStackInner = getFacadeBlockItem(itemStack);
        if(itemStackInner != null) {
            suffix = getFacadeBlockItem(itemStack).getDisplayName();
        }
        return super.getDisplayName(itemStack)
                .appendText(" - ")
                .appendSibling(suffix);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack itemStack = context.getItem();
        if(!context.getWorld().isRemote()) {
            IFacadeable facadeable = TileHelpers.getCapability(context.getWorld(), context.getPos(), null, FacadeableConfig.CAPABILITY).orElse(null);
            BlockState blockState = getFacadeBlock(itemStack);
            if(facadeable != null && blockState != null) {
                // Add facade to existing cable
                if (!facadeable.hasFacade()) {
                    facadeable.setFacade(blockState);
                    ItemBlockCable.playPlaceSound(context.getWorld(), context.getPos());
                    itemStack.shrink(1);
                }
            }
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

}
