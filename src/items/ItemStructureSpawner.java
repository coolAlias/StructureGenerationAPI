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

package coolalias.structuregen.items;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import coolalias.structuregen.LinkedStructureGenerator;
import coolalias.structuregen.StructureGenerator;
import coolalias.structuregen.lib.LogHelper;
import coolalias.structuregen.lib.SGTKeyBindings;
import coolalias.structuregen.util.Structure;

public class ItemStructureSpawner extends BaseModItem
{
	/** Enumerates valid values for increment / decrement offset methods */
	public static enum Offset { OFFSET_X, OFFSET_Y, OFFSET_Z }
	
	/** String identifiers for NBT storage and retrieval */
	private static final String[] data = {"Structure", "Rotations", "OffsetX", "OffsetY", "OffsetZ", "InvertY", "Remove"};

	/** Indices for data variables */
	public static final int STRUCTURE_INDEX = 0, ROTATIONS = 1, OFFSET_X = 2, OFFSET_Y = 3, OFFSET_Z = 4, INVERT_Y = 5, REMOVE = 6;

	public ItemStructureSpawner(int par1)
	{
		super(par1);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	/**
	 * Called when item is crafted/smelted. Not called from Creative Tabs.
	 */
	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		initNBTCompound(itemstack);
	}

	/**
	 * Increments the appropriate Offset and returns the new value for convenience.
	 */
	public static final int incrementOffset(ItemStack itemstack, Offset type)
	{
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);

		int offset;

		switch(type) {
		case OFFSET_X:
			offset = itemstack.stackTagCompound.getInteger(data[OFFSET_X]) + 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_X], offset);
			return offset;
		case OFFSET_Y:
			offset = itemstack.stackTagCompound.getInteger(data[OFFSET_Y]) + 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_Y], offset);
			return offset;
		case OFFSET_Z:
			offset = itemstack.stackTagCompound.getInteger(data[OFFSET_Z]) + 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_Z], offset);
			return offset;
		default: return 0;
		}
	}

	/**
	 * Decrements the appropriate Offset and returns the new value for convenience.
	 */
	public static final int decrementOffset(ItemStack itemstack, Offset type)
	{
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);

		int offset;

		switch(type) {
		case OFFSET_X:
			offset = itemstack.stackTagCompound.getInteger(data[OFFSET_X]) - 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_X], offset);
			return offset;
		case OFFSET_Y:
			offset = itemstack.stackTagCompound.getInteger(data[OFFSET_Y]) - 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_Y], offset);
			return offset;
		case OFFSET_Z:
			offset = itemstack.stackTagCompound.getInteger(data[OFFSET_Z]) - 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_Z], offset);
			return offset;
		default: return 0;
		}
	}

	/**
	 * Returns true if y offset is inverted (i.e. y will decrement)
	 */
	public static final boolean isInverted(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		return itemstack.stackTagCompound.getBoolean(data[INVERT_Y]);
	}

	/**
	 * Inverts Y axis for offset adjustments; returns new value for convenience.
	 */
	public static final boolean invertY(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		boolean invert = !itemstack.stackTagCompound.getBoolean(data[INVERT_Y]);
		itemstack.stackTagCompound.setBoolean(data[INVERT_Y], invert);
		return invert;
	}

	/**
	 * Resets all manual offsets to 0.
	 */
	public static final void resetOffset(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		itemstack.stackTagCompound.setInteger(data[OFFSET_X], 0);
		itemstack.stackTagCompound.setInteger(data[OFFSET_Y], 0);
		itemstack.stackTagCompound.setInteger(data[OFFSET_Z], 0);
	}

	/**
	 * Rotates structure's facing by 90 degrees clockwise; returns number of rotations for convenience.
	 */
	public static final int rotate(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		int rotations = (itemstack.stackTagCompound.getInteger(data[ROTATIONS]) + 1) % 4;
		itemstack.stackTagCompound.setInteger(data[ROTATIONS], rotations);
		return rotations;
	}

	/**
	 * Increments the structure index and returns the new value for convenience.
	 */
	public static final int nextStructure(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		int index = itemstack.stackTagCompound.getInteger(data[STRUCTURE_INDEX]) + 1;
		if (index == StructureGenerator.structures.size()) index = 0;
		itemstack.stackTagCompound.setInteger(data[STRUCTURE_INDEX], index);
		return index;
	}

	/**
	 * Decrements the structure index and returns the new value for convenience.
	 */
	public static final int prevStructure(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		int index = itemstack.stackTagCompound.getInteger(data[STRUCTURE_INDEX]) - 1;
		if (index < 0) index = StructureGenerator.structures.size() - 1;
		itemstack.stackTagCompound.setInteger(data[STRUCTURE_INDEX], index);
		return index;
	}

	/**
	 * Returns the name of the structure at provided index, or "" if index out of bounds
	 */
	public static final String getStructureName(int index) {
		return (index < StructureGenerator.structures.size() ? StructureGenerator.structures.get(index).name : "");
	}

	/**
	 * Returns index of currently selected structure
	 */
	public static final int getCurrentStructureIndex(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		return itemstack.stackTagCompound.getInteger(data[STRUCTURE_INDEX]);
	}
	
	/**
	 * Returns currently selected structure
	 */
	public static final Structure getCurrentStructure(ItemStack itemstack) {
		return StructureGenerator.structures.get(getCurrentStructureIndex(itemstack));
	}
	
	/**
	 * Returns data field at index
	 */
	public static final int getData(ItemStack itemstack, int index) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		return itemstack.stackTagCompound.getInteger(data[index]);
	}

	/**
	 * Toggles between generate and remove structure setting. Returns new value for convenience.
	 */
	public static final boolean toggleRemove(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		itemstack.stackTagCompound.setInteger(data[REMOVE], (itemstack.stackTagCompound.getInteger(data[REMOVE]) + 1) % 2);
		return itemstack.stackTagCompound.getInteger(data[REMOVE]) == 1;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 1;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	{
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);

		if (!world.isRemote && StructureGenerator.structures.size() > 0)
		{
			/*
			NBTTagCompound tag = itemstack.stackTagCompound;
			
			LinkedStructureGenerator link = new LinkedStructureGenerator();
			Structure hut = StructureGenerator.structures.get(0);
			
			link.setRotation(tag.getInteger(data[ROTATIONS]));
			
			for (int i = 0; i < 6; ++i) {
				link.addStructureWithOffset(hut,(i/2) * hut.getWidthZ() - i % 2,0,(i % 2 == 0 ? -1 : 1) * (hut.getWidthX() / 2 + 1));
				link.setLastRotation(i % 2 == 0 ? 3 : 1);
			}
			for (int i = 0; i < 6; ++i) {
				link.addStructureWithOffset(hut,(i % 2 == 0 ? -1 : 1) * (hut.getWidthX() / 2) - (3 * hut.getWidthX() / 2),0,(i/2 + 2) * hut.getWidthZ() + i % 2 - 2);
				link.setLastRotation(i % 2 == 0 ? 2 : 0);
			}
			
			link.generateLinkedStructures(player, world, world.rand, x, y+1, z);
			*/
			
			// Necessary for SMP compatibility, as using static variables will fail
			StructureGenerator gen = new StructureGenerator();
			NBTTagCompound tag = itemstack.stackTagCompound;
			Structure structure = getCurrentStructure(itemstack);
			
			if (structure == null) {
				LogHelper.log(Level.WARNING, "Current structure is null.");
				return false;
			}
			
			if (world.getBlockId(x,y,z) == Block.snow.blockID) { --y; }
			
			gen.setPlayerFacing(player);
			gen.setRemoveStructure(tag.getInteger(data[REMOVE]) == 1);
			gen.setStructureWithRotation(structure, tag.getInteger(data[ROTATIONS]));
			gen.setDefaultOffset(structure.getOffsetX() + tag.getInteger(data[OFFSET_X]), structure.getOffsetY() + tag.getInteger(data[OFFSET_Y]), structure.getOffsetZ() + tag.getInteger(data[OFFSET_Z]));
			gen.generate(world, world.rand, x, y, z);
			
		}

		return true;
	}

	/**
	 * Creates a new NBTTagCompound for the itemstack if none exists
	 */
	private static final void initNBTCompound(ItemStack itemstack)
	{
		if (itemstack.stackTagCompound == null)
			itemstack.stackTagCompound = new NBTTagCompound();

		for (int i = 0; i < INVERT_Y; ++i) {
			itemstack.stackTagCompound.setInteger(data[i], 0);
		}

		itemstack.stackTagCompound.setBoolean(data[INVERT_Y], false);

		LogHelper.log(Level.INFO, "NBT Tag initialized for ItemStructureSpawner");
	}

	/**
	 * Updates spawners's data when key pressed and adds chat message for player
	 */
	public static final void handleKeyPressPacket(byte key, ItemStack itemstack, EntityPlayer player)
	{
		ItemStructureSpawner spawner = (ItemStructureSpawner) itemstack.getItem();

		switch (key) {
		case SGTKeyBindings.PLUS_X: player.addChatMessage("[STRUCTURE GEN] Incremented x offset: " + spawner.incrementOffset(itemstack, ItemStructureSpawner.Offset.OFFSET_X)); break;
		case SGTKeyBindings.MINUS_X: player.addChatMessage("[STRUCTURE GEN] Decremented x offset: " + spawner.decrementOffset(itemstack, ItemStructureSpawner.Offset.OFFSET_X)); break;
		case SGTKeyBindings.PLUS_Z: player.addChatMessage("[STRUCTURE GEN] Incremented z offset: " + spawner.incrementOffset(itemstack, ItemStructureSpawner.Offset.OFFSET_Z)); break;
		case SGTKeyBindings.MINUS_Z: player.addChatMessage("[STRUCTURE GEN] Decremented z offset: " + spawner.decrementOffset(itemstack, ItemStructureSpawner.Offset.OFFSET_Z)); break;
		case SGTKeyBindings.OFFSET_Y:
			if (spawner.isInverted(itemstack))
				player.addChatMessage("[STRUCTURE GEN] Decremented y offset: " + spawner.decrementOffset(itemstack, ItemStructureSpawner.Offset.OFFSET_Y));
			else
				player.addChatMessage("[STRUCTURE GEN] Incremented y offset: " + spawner.incrementOffset(itemstack, ItemStructureSpawner.Offset.OFFSET_Y));
			break;
		case SGTKeyBindings.INVERT_Y: player.addChatMessage("[STRUCTURE GEN] y offset will now " + (spawner.invertY(itemstack) ? "decrement." : "increment.")); break;
		case SGTKeyBindings.RESET_OFFSET:
			spawner.resetOffset(itemstack);
			player.addChatMessage("[STRUCTURE GEN] Offsets x/y/z reset to 0.");
			break;
		case SGTKeyBindings.ROTATE: player.addChatMessage("[STRUCTURE GEN] Structure orientation rotated by " + (spawner.rotate(itemstack) * 90) + " degrees."); break;
		case SGTKeyBindings.PREV_STRUCT: player.addChatMessage("[STRUCTURE GEN] Selected structure: " + spawner.getStructureName(spawner.prevStructure(itemstack)) + " at index " + (spawner.getCurrentStructureIndex(itemstack) + 1)); break;
		case SGTKeyBindings.NEXT_STRUCT: player.addChatMessage("[STRUCTURE GEN] Selected structure: " + spawner.getStructureName(spawner.nextStructure(itemstack)) + " at index " + (spawner.getCurrentStructureIndex(itemstack) + 1)); break;
		case SGTKeyBindings.TOGGLE_REMOVE: player.addChatMessage("[STRUCTURE GEN] Structure will " + (spawner.toggleRemove(itemstack) ? "be removed" : "generate") + " on right click."); break;
		default: LogHelper.log(Level.WARNING, "ItemStructureSpawner received an invalid key id, unable to process.");
		}
	}
}
