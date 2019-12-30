package org.cyclops.integrateddynamics.part.aspect.write.redstone;

import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import org.cyclops.cyclopscore.datastructure.DimPos;
import org.cyclops.integrateddynamics.api.block.IDynamicRedstone;
import org.cyclops.integrateddynamics.api.part.PartTarget;

/**
 * Interface for redstone writing the component.
 * @author rubensworks
 */
public interface IWriteRedstoneComponent {

    public void setRedstoneLevel(PartTarget target, int level, boolean strongPower);

    public void setLastPulseValue(PartTarget target, int value);

    public int getLastPulseValue(PartTarget target);

    public void deactivate(PartTarget target);

    public LazyOptional<IDynamicRedstone> getDynamicRedstoneBlock(DimPos dimPos, Direction side);

}
