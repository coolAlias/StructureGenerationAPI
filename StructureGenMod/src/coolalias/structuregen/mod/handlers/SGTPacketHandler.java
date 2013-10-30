package coolalias.structuregen.mod.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import coolalias.structuregen.api.util.LogHelper;
import coolalias.structuregen.mod.items.ItemStructureSpawnerBase;
import coolalias.structuregen.mod.lib.ModInfo;
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
		try {
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			byte packetType;

			try {
				packetType = inputStream.readByte();

				switch (packetType) {
				case PACKET_KEY_PRESS: handlePacketKeyPress(packet, (EntityPlayer) player, inputStream); break;
				default: LogHelper.log(Level.SEVERE, "Unhandled packet exception for packet id " + packetType);
				}
			} finally {
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final void sendPacketKeyPress(byte key)
	{
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream outputStream = new DataOutputStream(bos);

			try {
				outputStream.writeByte(SGTPacketHandler.PACKET_KEY_PRESS);
				outputStream.writeByte(key);
			} finally {
				outputStream.close();
			}

			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, bos.toByteArray()));

		} catch (Exception ex) {
			LogHelper.log(Level.SEVERE, "Failed to send key press packet.");
			ex.printStackTrace();
		}
	}

	private void handlePacketKeyPress(Packet250CustomPayload packet, EntityPlayer player, DataInputStream inputStream)
	{
		byte key;

		try {
			key = inputStream.readByte();

			if (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemStructureSpawnerBase))
				LogHelper.log(Level.SEVERE, "Held item is not an instance of ItemStructureSpawnerBase - unable to process key press packet");
			else
				ItemStructureSpawnerBase.handleKeyPressPacket(key, player.getHeldItem(), player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
