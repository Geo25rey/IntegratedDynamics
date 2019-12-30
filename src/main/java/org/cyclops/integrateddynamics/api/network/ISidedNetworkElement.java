package org.cyclops.integrateddynamics.api.network;

import net.minecraft.util.Direction;

/**
 * A network element that exists at a certain side.
 * @author rubensworks
 */
public interface ISidedNetworkElement extends INetworkElement {

    public Direction getSide();

}
