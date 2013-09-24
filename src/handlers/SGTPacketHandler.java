package coolalias.structuregen.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import coolalias.structuregen.ModInfo;
import coolalias.structuregen.items.ItemStructureSpawner;
import coolalias.structuregen.lib.LogHelper;
import coolalias.structuregen.lib.SGTKeyBindings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class SGTPacketHandler implements IPacketHandler
{
	/** Packet IDs */
	private static final byte PACKET_KEY_PRESS = 1;
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
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
	
	public static final void sendPacketKeyPress(byte key)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
			outputStream.writeByte(SGTPacketHandler.PACKET_KEY_PRESS);
			outputStream.writeByte(key);
		} catch (Exception ex) {
			LogHelper.log(Level.SEVERE, "Failed to send key press packet.");
			ex.printStackTrace();
		}
		
		PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, bos.toByteArray()));
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
		
		if (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemStructureSpawner)) {
			LogHelper.log(Level.SEVERE, "Held item is not an instance of ItemStructureSpawner - unable to process key press packet");
			return;
		}
		
		ItemStructureSpawner.handleKeyPressPacket(key, player.getHeldItem(), player);
	}
}
