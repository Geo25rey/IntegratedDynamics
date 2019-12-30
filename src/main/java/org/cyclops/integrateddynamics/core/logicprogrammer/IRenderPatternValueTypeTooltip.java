package org.cyclops.integrateddynamics.core.logicprogrammer;

import com.google.common.collect.Lists;
import org.cyclops.cyclopscore.helper.L10NHelpers;
import org.cyclops.integrateddynamics.api.evaluate.variable.IValueType;
import org.cyclops.integrateddynamics.client.gui.container.ContainerScreenLogicProgrammerBase;
import org.cyclops.integrateddynamics.inventory.container.ContainerLogicProgrammerBase;

import java.util.List;

/**
 * @author rubensworks
 */
public interface IRenderPatternValueTypeTooltip {

    public default List<String> getValueTypeTooltip(IValueType<?> valueType) {
        List<String> lines = Lists.newLinkedList();
        lines.add(valueType.getDisplayColorFormat() + L10NHelpers.localize(valueType.getTranslationKey()));
        return lines;
    }

    public abstract boolean isRenderTooltip();

    public abstract void setRenderTooltip(boolean renderTooltip);

    public default void drawTooltipForeground(ContainerScreenLogicProgrammerBase gui, ContainerLogicProgrammerBase container, int guiLeft, int guiTop, int mouseX, int mouseY, IValueType valueType) {
        if (isRenderTooltip()) {
            // Output type tooltip
            if (!container.hasWriteItemInSlot()) {
                if (gui.isPointInRegion(ContainerLogicProgrammerBase.OUTPUT_X, ContainerLogicProgrammerBase.OUTPUT_Y,
                        ContainerScreenLogicProgrammerBase.BOX_HEIGHT, ContainerScreenLogicProgrammerBase.BOX_HEIGHT, mouseX, mouseY)) {
                    gui.drawTooltip(getValueTypeTooltip(valueType), mouseX - guiLeft, mouseY - guiTop);
                }
            }
        }
    }

}
