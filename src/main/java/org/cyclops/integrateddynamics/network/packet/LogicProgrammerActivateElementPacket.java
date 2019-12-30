package org.cyclops.integrateddynamics.network.packet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.cyclops.cyclopscore.network.CodecField;
import org.cyclops.cyclopscore.network.PacketCodec;
import org.cyclops.integrateddynamics.inventory.container.ContainerLogicProgrammerBase;

/**
 * Packet for sending a button packet for the exalted crafting.
 * @author rubensworks
 *
 */
public class LogicProgrammerActivateElementPacket extends PacketCodec {

	@CodecField
	private String typeId;
    @CodecField
    private String elementId;

    public LogicProgrammerActivateElementPacket() {

    }

    public LogicProgrammerActivateElementPacket(String typeId, String elementId) {
		this.typeId = typeId;
        this.elementId = elementId;
    }

	@Override
	public boolean isAsync() {
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void actionClient(World world, PlayerEntity player) {
		
	}

	@Override
	public void actionServer(World world, ServerPlayerEntity player) {
		if(player.openContainer instanceof ContainerLogicProgrammerBase) {
			((ContainerLogicProgrammerBase) player.openContainer).setActiveElementById(typeId, elementId);
		}
	}
	
}