package coolalias.structuregen.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import coolalias.structuregen.ModInfo;
import coolalias.structuregen.handlers.SGTPacketHandler;

public class SGTPacketKeyPress
{
	/**
	 * Returns a packet containing data for the key pressed
	 */
	public static Packet250CustomPayload getPacket(byte key)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(2);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
			outputStream.writeByte(SGTPacketHandler.PACKET_KEY_PRESS);
			outputStream.writeByte(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return new Packet250CustomPayload(ModInfo.CHANNEL, bos.toByteArray());
	}
}
