package coolalias.structuregen.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import coolalias.structuregen.items.ItemStructureSpawner;
import coolalias.structuregen.lib.LogHelper;
import coolalias.structuregen.lib.SGTKeyBindings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class SGTPacketHandler implements IPacketHandler
{
	/** Packet IDs */
	public static final byte PACKET_KEY_PRESS = 1;
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		LogHelper.log(Level.INFO, "[SERVER] Received client packet.");
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		byte packetType;
		
		try {
			packetType = inputStream.readByte();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		switch (packetType) {
		case PACKET_KEY_PRESS: handlePacketKeyPress(packet, (EntityPlayer) player, inputStream); break;
		default: LogHelper.log(Level.SEVERE, "Unhandled packet exception for packet id " + packetType);
		}
	}
	
	private void handlePacketKeyPress(Packet250CustomPayload packet, EntityPlayer player, DataInputStream inputStream)
	{
		byte key;
		
		try {
			key = inputStream.readByte();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		if (player.getHeldItem() == null) {
			LogHelper.log(Level.SEVERE, "Player held item is null - unable to process key press packet");
			return;
		} else if (!(player.getHeldItem().getItem() instanceof ItemStructureSpawner)) {
			LogHelper.log(Level.SEVERE, "Held item is not an instance of ItemStructureSpawner - unable to process key press packet");
			return;
		}
		
		ItemStructureSpawner spawner = (ItemStructureSpawner) player.getHeldItem().getItem();
		
		switch (key) {
		case SGTKeyBindings.PLUS_X: spawner.incrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_X); break;
		case SGTKeyBindings.MINUS_X: spawner.decrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_X); break;
		case SGTKeyBindings.PLUS_Z: spawner.incrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_Z); break;
		case SGTKeyBindings.MINUS_Z: spawner.decrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_Z); break;
		case SGTKeyBindings.OFFSET_Y:
			if (spawner.isInverted(player.getHeldItem())) spawner.decrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_Y);
			else spawner.incrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_Y);
			break;
		case SGTKeyBindings.INVERT_Y: spawner.invertY(player.getHeldItem()); break;
		case SGTKeyBindings.RESET_OFFSET: spawner.resetOffset(player.getHeldItem()); break;
		case SGTKeyBindings.ROTATE: spawner.rotate(player.getHeldItem()); break;
		case SGTKeyBindings.PREV_STRUCT: spawner.prevStructure(player.getHeldItem()); break;
		case SGTKeyBindings.NEXT_STRUCT: spawner.nextStructure(player.getHeldItem()); break;
		case SGTKeyBindings.TOGGLE_REMOVE: spawner.toggleRemove(); break;
		default: LogHelper.log(Level.SEVERE, "Unhandled key ID " + key + " from packet.");
		}
	}
}
