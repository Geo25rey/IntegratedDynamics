package org.cyclops.integrateddynamics.core.evaluate;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.integrateddynamics.api.item.IProxyVariableFacade;
import org.cyclops.integrateddynamics.api.item.IVariableFacadeHandler;
import org.cyclops.integrateddynamics.core.item.ProxyVariableFacade;

/**
 * Handler for proxy variable facades.
 * @author rubensworks
 */
public class ProxyVariableFacadeHandler implements IVariableFacadeHandler<IProxyVariableFacade> {

    private static final IProxyVariableFacade INVALID_FACADE = new ProxyVariableFacade(false, -1);
    private static ProxyVariableFacadeHandler _instance;

    private ProxyVariableFacadeHandler() {

    }

    public static ProxyVariableFacadeHandler getInstance() {
        if(_instance == null) _instance = new ProxyVariableFacadeHandler();
        return _instance;
    }

    @Override
    public String getTypeId() {
        return "proxy";
    }

    @Override
    public IProxyVariableFacade getVariableFacade(int id, CompoundNBT tag) {
        if(!tag.contains("partId", Constants.NBT.TAG_INT)) {
            return INVALID_FACADE;
        }
        return new ProxyVariableFacade(id, tag.getInt("partId"));
    }

    @Override
    public void setVariableFacade(CompoundNBT tag, IProxyVariableFacade variableFacade) {
        tag.putInt("partId", variableFacade.getProxyId());
    }
}
