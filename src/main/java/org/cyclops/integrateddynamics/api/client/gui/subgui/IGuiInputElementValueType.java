package org.cyclops.integrateddynamics.api.client.gui.subgui;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.inventory.container.Container;
import org.cyclops.integrateddynamics.api.evaluate.variable.IValue;

import java.util.function.Predicate;

/**
 * A value type element inside the logic programmer.
 * @param <G> The type of gui.
 * @param <C> The type of container.
 * @param <S> The sub gui box type.
 * @author rubensworks
 */
public interface IGuiInputElementValueType<S extends ISubGuiBox, G extends AbstractGui, C extends Container> extends IGuiInputElement<S, G, C> {

    public void setValidator(Predicate<IValue> validator);

    public IValue getValue();

    void setValue(IValue value, S propertyConfigPattern);
}
