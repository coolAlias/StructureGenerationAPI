/**
    Copyright (C) <2013> <coolAlias>

    This file is part of coolAlias' Structure Generation Tool; as such,
    you can redistribute it and/or modify it under the terms of the GNU
    General Public License as published by the Free Software Foundation,
    either version 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package coolalias.structuregen.handlers;

import java.util.EnumSet;

import coolalias.structuregen.ModInfo;
import coolalias.structuregen.items.ItemStructureSpawner;
import coolalias.structuregen.lib.SGTKeyBindings;
import coolalias.structuregen.util.SGTPacketKeyPress;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SGTKeyHandler extends KeyHandler
{
	public static final String label = ModInfo.MOD_NAME + " Key";
	
	private EnumSet tickTypes = EnumSet.of(TickType.PLAYER);
			
	public SGTKeyHandler(KeyBinding[] keyBindings, boolean[] repeatings)
	{
		super(keyBindings, repeatings);
	}

	public SGTKeyHandler(KeyBinding[] keyBindings)
	{
		super(keyBindings);
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		if (tickEnd && SGTKeyBindings.SGTKeyMap.containsKey(kb.keyCode) && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;
			
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemStructureSpawner)
			{
				PacketDispatcher.sendPacketToServer(SGTPacketKeyPress.getPacket((byte) SGTKeyBindings.SGTKeyMap.get(kb.keyCode)));
				
				ItemStructureSpawner spawner = (ItemStructureSpawner) player.getHeldItem().getItem();
				
				switch (SGTKeyBindings.SGTKeyMap.get(kb.keyCode)) {
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
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return tickTypes;
	}

}
