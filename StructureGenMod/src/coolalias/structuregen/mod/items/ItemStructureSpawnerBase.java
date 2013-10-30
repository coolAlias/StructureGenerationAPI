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

package coolalias.structuregen.mod.items;

import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import coolalias.structuregen.api.util.LogHelper;
import coolalias.structuregen.api.util.Structure;
import coolalias.structuregen.mod.lib.SGTKeyBindings;

public abstract class ItemStructureSpawnerBase extends BaseModItem
{
	/** Enumerates valid values for increment / decrement offset methods */
	public static enum Offset { OFFSET_X, OFFSET_Y, OFFSET_Z }
	
	/** String identifiers for NBT storage and retrieval */
	private static final String[] data = {"Structure", "Rotations", "OffsetX", "OffsetY", "OffsetZ", "InvertY", "Remove"};

	/** Indices for data variables */
	public static final int STRUCTURE_INDEX = 0, ROTATIONS = 1, OFFSET_X = 2, OFFSET_Y = 3, OFFSET_Z = 4, INVERT_Y = 5, REMOVE = 6;

	/**
	 * StructureGeneratorBase parameter supplies the appropriate List<Structure> to use 
	 */
	public ItemStructureSpawnerBase(int par1)
	{
		super(par1);
		setMaxDamage(0);
		setMaxStackSize(1);
	}

	/**
	 * Called when item is crafted/smelted. Not called from Creative Tabs.
	 */
	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		initNBTCompound(itemstack);
	}
	
	/**
	 * ABSTRACT methods needed to use separate List<Structure> objects for different
	 * types of StructureGeneratorBase classes.
	 * 
	 * Use the suggested implementations by replacing 'StructureGenerator' with the
	 * class containing the appropriate List<Structure>
	 */
	
	/**
	 * Increments the structure index and returns the new value for convenience.
	 */
	public abstract int nextStructure(ItemStack itemstack);
	/*
	{
		int index = getData(itemstack, STRUCTURE_INDEX) + 1;
		if (index >= StructureGenerator.structures.size()) index = 0;
		setData(itemstack, STRUCTURE_INDEX, index);
		return index;
	}
	*/

	/**
	 * Decrements the structure index and returns the new value for convenience.
	 */
	public abstract int prevStructure(ItemStack itemstack);
	/*
	{
		int index = getData(itemstack, STRUCTURE_INDEX) - 1;
		if (index < 0) index = StructureGenerator.structures.size() - 1;
		setData(itemstack, STRUCTURE_INDEX, index);
		return index;
	}
	*/

	/**
	 * Returns the name of the structure at provided index, or "" if index out of bounds
	 */
	public abstract String getStructureName(ItemStack itemstack, int index);
	/*
	{
		return (index < StructureGenerator.structures.size() ? StructureGenerator.structures.get(index).name : "");
	}
	*/
	
	/**
	 * Returns index of currently selected structure
	 */
	public abstract int getCurrentStructureIndex(ItemStack itemstack);
	/*
	{
		return getData(itemstack, STRUCTURE_INDEX) >= StructureGenerator.structures.size() ? 0 : getData(itemstack, STRUCTURE_INDEX);
	}
	*/
	
	/**
	 * Returns currently selected structure
	 */
	public abstract Structure getCurrentStructure(ItemStack itemstack);
	/*
	{
		return StructureGenerator.structures.get(getCurrentStructureIndex(itemstack));
	}
	*/

	/**
	 * Increments the appropriate Offset and returns the new value for convenience.
	 */
	public static final int incrementOffset(ItemStack itemstack, Offset type)
	{
		int offset;

		switch(type) {
		case OFFSET_X:
			offset = getData(itemstack, OFFSET_X) + 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_X], offset);
			return offset;
		case OFFSET_Y:
			offset = getData(itemstack, OFFSET_Y) + 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_Y], offset);
			return offset;
		case OFFSET_Z:
			offset = getData(itemstack, OFFSET_Z) + 1;
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
		int offset;
		
		switch(type) {
		case OFFSET_X:
			offset = getData(itemstack, OFFSET_X) - 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_X], offset);
			return offset;
		case OFFSET_Y:
			offset = getData(itemstack, OFFSET_Y) - 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_Y], offset);
			return offset;
		case OFFSET_Z:
			offset = getData(itemstack, OFFSET_Z) - 1;
			itemstack.stackTagCompound.setInteger(data[OFFSET_Z], offset);
			return offset;
		default: return 0;
		}
	}

	/**
	 * Returns true if offset y is inverted (i.e. y will decrement)
	 */
	public static final boolean isInverted(ItemStack itemstack) {
		return getData(itemstack, INVERT_Y) == 1;
	}

	/**
	 * Inverts Y axis for offset adjustments; returns new value for convenience.
	 */
	public static final boolean invertY(ItemStack itemstack) {
		setData(itemstack, INVERT_Y, (getData(itemstack, INVERT_Y) + 1) % 2);
		return itemstack.stackTagCompound.getInteger(data[INVERT_Y]) == 1;
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
		int rotations = (getData(itemstack, ROTATIONS) + 1) % 4;
		itemstack.stackTagCompound.setInteger(data[ROTATIONS], rotations);
		return rotations;
	}
	
	/**
	 * Returns data field at index
	 */
	public static final int getData(ItemStack itemstack, int index) {
		if (itemstack.stackTagCompound == null) initNBTCompound(itemstack);
		if (index < data.length) return itemstack.stackTagCompound.getInteger(data[index]);
		else {
			LogHelper.log(Level.WARNING, "Index " + index + " out of bounds while trying to get data for ItemStructureSpawnerBase");
			return 0;
		}
	}
	
	/**
	 * Returns data field at index
	 */
	public static final void setData(ItemStack itemstack, int index, int value) {
		if (itemstack.stackTagCompound == null) initNBTCompound(itemstack);
		if (index < data.length) itemstack.stackTagCompound.setInteger(data[index], value);
		else LogHelper.log(Level.WARNING, "Index " + index + " out of bounds while trying to set data for ItemStructureSpawnerBase");
	}

	/**
	 * Returns true if structure will be removed
	 */
	public static final boolean getRemove(ItemStack itemstack) {
		return itemstack.stackTagCompound.getInteger(data[REMOVE]) == 1;
	}
	
	/**
	 * Toggles between generate and remove structure setting. Returns new value for convenience.
	 */
	public static final boolean toggleRemove(ItemStack itemstack) {
		setData(itemstack, REMOVE, (getData(itemstack, REMOVE) + 1) % 2);
		return itemstack.stackTagCompound.getInteger(data[REMOVE]) == 1;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 1;
	}

	/**
	 * Creates a new NBTTagCompound for the itemstack if none exists
	 */
	protected static final void initNBTCompound(ItemStack itemstack)
	{
		if (itemstack.stackTagCompound == null)
			itemstack.stackTagCompound = new NBTTagCompound();

		for (int i = 0; i < data.length; ++i) {
			itemstack.stackTagCompound.setInteger(data[i], 0);
		}
	}

	/**
	 * Updates spawners's data when key pressed and adds chat message for player
	 */
	public static final void handleKeyPressPacket(byte key, ItemStack itemstack, EntityPlayer player)
	{
		ItemStructureSpawnerBase spawner = (ItemStructureSpawnerBase) itemstack.getItem();

		switch (key) {
		case SGTKeyBindings.PLUS_X: player.addChatMessage("Incremented offset x: " + incrementOffset(itemstack, Offset.OFFSET_X)); break;
		case SGTKeyBindings.MINUS_X: player.addChatMessage("Decremented offset x: " + decrementOffset(itemstack, Offset.OFFSET_X)); break;
		case SGTKeyBindings.PLUS_Z: player.addChatMessage("Incremented offset z: " + incrementOffset(itemstack, Offset.OFFSET_Z)); break;
		case SGTKeyBindings.MINUS_Z: player.addChatMessage("Decremented offset z: " + decrementOffset(itemstack, Offset.OFFSET_Z)); break;
		case SGTKeyBindings.OFFSET_Y:
			if (spawner.isInverted(itemstack)) player.addChatMessage("Decremented offset y: " + decrementOffset(itemstack, Offset.OFFSET_Y));
			else player.addChatMessage("Incremented offset y: " + incrementOffset(itemstack, Offset.OFFSET_Y));
			break;
		case SGTKeyBindings.INVERT_Y: player.addChatMessage("Offset y will now " + (invertY(itemstack) ? "decrement." : "increment.")); break;
		case SGTKeyBindings.RESET_OFFSET:
			spawner.resetOffset(itemstack);
			player.addChatMessage("Offsets x/y/z reset to 0.");
			break;
		case SGTKeyBindings.ROTATE: player.addChatMessage("Structure orientation rotated by " + (rotate(itemstack) * 90) + " degrees."); break;
		case SGTKeyBindings.PREV_STRUCT: player.addChatMessage("Selected structure: " + spawner.getStructureName(itemstack, spawner.prevStructure(itemstack)) + " at index " + (spawner.getCurrentStructureIndex(itemstack) + 1)); break;
		case SGTKeyBindings.NEXT_STRUCT: player.addChatMessage("Selected structure: " + spawner.getStructureName(itemstack, spawner.nextStructure(itemstack)) + " at index " + (spawner.getCurrentStructureIndex(itemstack) + 1)); break;
		case SGTKeyBindings.TOGGLE_REMOVE: player.addChatMessage("Structure will " + (toggleRemove(itemstack) ? "be removed" : "generate") + " on right click."); break;
		default: LogHelper.log(Level.WARNING, "Structure Spawner received an invalid key id, unable to process.");
		}
	}
}
