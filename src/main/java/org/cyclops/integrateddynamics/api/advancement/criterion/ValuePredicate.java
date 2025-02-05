package org.cyclops.integrateddynamics.api.advancement.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.GsonHelper;
import org.cyclops.integrateddynamics.IntegratedDynamics;
import org.cyclops.integrateddynamics.api.evaluate.variable.IValue;
import org.cyclops.integrateddynamics.api.evaluate.variable.IValueType;
import org.cyclops.integrateddynamics.api.item.IVariableFacadeHandlerRegistry;
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueHelpers;

import javax.annotation.Nullable;

/**
 * A predicate for values of a certain type.
 * @author rubensworks
 */
public class ValuePredicate<V extends IValue> {

    private static final IVariableFacadeHandlerRegistry VARIABLE_FACADE_HANDLER_REGISTRY = IntegratedDynamics._instance
            .getRegistryManager().getRegistry(IVariableFacadeHandlerRegistry.class);

    public static final ValuePredicate ANY = new ValuePredicate<>(null, null);

    private final IValueType valueType;
    private final IValue value;

    public ValuePredicate(@Nullable IValueType valueType, @Nullable IValue value) {
        this.valueType = valueType;
        this.value = value;
    }

    public final boolean test(IValue value) {
        return (this.value == null || ValueHelpers.areValuesEqual(this.value, value))
                && (this.valueType == null || value.getType() == this.valueType) && testTyped((V) value);
    }

    protected boolean testTyped(V value) {
        return true;
    }

    public static ValuePredicate deserialize(JsonObject jsonObject, @Nullable IValueType valueType) {
        JsonElement valueElement = jsonObject.get("value");
        IValue value = null;
        if (valueElement != null && !valueElement.isJsonNull()) {
            if (valueElement.isJsonPrimitive()) {
                String valueString = GsonHelper.getAsString(jsonObject, "value");
                if (valueType == null) {
                    throw new JsonSyntaxException("A value '" + valueString + "' requires a corresponding valueType to be defined");
                }
                try {
                    Tag tag = TagParser.parseTag(valueString);
                    if (((CompoundTag) tag).contains("Primitive")) {
                        tag = ((CompoundTag) tag).get("Primitive");
                    }
                    value = ValueHelpers.deserializeRaw(valueType, tag);
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
            } else if (valueType != null && valueElement.isJsonObject()) {
                return valueType.deserializeValuePredicate(valueElement.getAsJsonObject(), null);
            }

            if (value == null) {
                throw new JsonSyntaxException("value '" + valueElement.toString() + "' has an incorrect syntax");
            }
            return new ValuePredicate(valueType, value);
        }
        return ANY;
    }

}
