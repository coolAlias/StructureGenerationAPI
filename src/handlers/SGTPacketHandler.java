package coolalias.structuregen.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import coolalias.structuregen.items.ItemStructureSpawner;
import coolalias.structuregen.lib.LogHelper;
import coolalias.structuregen.lib.SGTKeyBindings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
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
		case SGTKeyBindings.PLUS_X: player.addChatMessage("[STRUCTURE GEN] Incremented x offset: " + spawner.incrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_X)); break;
		case SGTKeyBindings.MINUS_X: player.addChatMessage("[STRUCTURE GEN] Decremented x offset: " + spawner.decrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_X)); break;
		case SGTKeyBindings.PLUS_Z: player.addChatMessage("[STRUCTURE GEN] Incremented z offset: " + spawner.incrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_Z)); break;
		case SGTKeyBindings.MINUS_Z: player.addChatMessage("[STRUCTURE GEN] Decremented z offset: " + spawner.decrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_Z)); break;
		case SGTKeyBindings.OFFSET_Y:
			if (spawner.isInverted(player.getHeldItem()))
				player.addChatMessage("[STRUCTURE GEN] Decremented y offset: " + spawner.decrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_Y));
			else
				player.addChatMessage("[STRUCTURE GEN] Incremented y offset: " + spawner.incrementOffset(player.getHeldItem(), ItemStructureSpawner.Offset.OFFSET_Y));
			break;
		case SGTKeyBindings.INVERT_Y: player.addChatMessage("[STRUCTURE GEN] y offset will now " + (spawner.invertY(player.getHeldItem()) ? "decrement." : "increment.")); break;
		case SGTKeyBindings.RESET_OFFSET:
			spawner.resetOffset(player.getHeldItem());
			player.addChatMessage("[STRUCTURE GEN] Offsets x/y/z reset to 0.");
			break;
		case SGTKeyBindings.ROTATE: player.addChatMessage("[STRUCTURE GEN] Structure orientation rotated by " + (spawner.rotate(player.getHeldItem()) * 90) + " degrees."); break;
		case SGTKeyBindings.PREV_STRUCT: player.addChatMessage("[STRUCTURE GEN] Selected structure: " + spawner.getStructureName(spawner.prevStructure(player.getHeldItem())) + " at index " + (spawner.getCurrentStructureIndex(player.getHeldItem()) + 1)); break;
		case SGTKeyBindings.NEXT_STRUCT: player.addChatMessage("[STRUCTURE GEN] Selected structure: " + spawner.getStructureName(spawner.nextStructure(player.getHeldItem())) + " at index " + (spawner.getCurrentStructureIndex(player.getHeldItem()) + 1)); break;
		case SGTKeyBindings.TOGGLE_REMOVE: player.addChatMessage("[STRUCTURE GEN] Structure will " + (spawner.toggleRemove() ? "be removed" : "generate") + " on right click."); break;
		}
	}
}
