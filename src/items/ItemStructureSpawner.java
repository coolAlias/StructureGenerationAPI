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

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import coolalias.structuregen.StructureArrays;
import coolalias.structuregen.StructureGeneratorBase;
import coolalias.structuregen.WorldGenStructure;
import coolalias.structuregen.lib.LogHelper;
import coolalias.structuregen.util.Structure;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemStructureSpawner extends BaseModItem
{
	/** Enumerates valid values for increment / decrement offset methods */
	public static enum Offset { OFFSET_X, OFFSET_Y, OFFSET_Z }
	
	/** List of all structures that can be generated with this item */
	private static final List<Structure> structures = new LinkedList();
	
	private static final StructureGeneratorBase gen = new WorldGenStructure();
	
	/** String identifiers for NBT storage and retrieval */
	private static final String[] data = {"Structure", "Rotations", "OffsetX", "OffsetY", "OffsetZ", "InvertY"};
	
	/** Indices for data variables */
	private static final int STRUCTURE_INDEX = 0, ROTATIONS = 1, OFFSET_X = 2, OFFSET_Y = 3, OFFSET_Z = 4, INVERT_Y = 5;
	
	public ItemStructureSpawner(int par1)
	{
		super(par1);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
		// constructor should only be called once anyways
		init();
		LogHelper.log(Level.INFO, "ItemStructureSpawner initialized structures.");
	}
	
	/**
     * Called when item is crafted/smelted. Not called from Creative Tabs.
     */
	@Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer player)
    {
		initNBTCompound(itemstack);
    }
	
	/**
	 * Increments the appropriate Offset and returns the new value for convenience.
	 */
	public int incrementOffset(ItemStack itemstack, Offset type)
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
	public int decrementOffset(ItemStack itemstack, Offset type)
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
	public boolean isInverted(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		return itemstack.stackTagCompound.getBoolean(data[INVERT_Y]);
	}
	
	/**
	 * Inverts Y axis for offset adjustments; returns new value for convenience.
	 */
	public boolean invertY(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		boolean invert = !itemstack.stackTagCompound.getBoolean(data[INVERT_Y]);
		itemstack.stackTagCompound.setBoolean(data[INVERT_Y], invert);
		return invert;
	}
	
	/**
	 * Resets all manual offsets to 0.
	 */
	public void resetOffset(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		itemstack.stackTagCompound.setInteger(data[OFFSET_X], 0);
		itemstack.stackTagCompound.setInteger(data[OFFSET_Y], 0);
		itemstack.stackTagCompound.setInteger(data[OFFSET_Z], 0);
	}
	
	/**
	 * Rotates structure's facing by 90 degrees clockwise; returns number of rotations for convenience.
	 */
	public int rotate(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		int rotations = (itemstack.stackTagCompound.getInteger(data[ROTATIONS]) + 1) % 4;
		itemstack.stackTagCompound.setInteger(data[ROTATIONS], rotations);
		return rotations;
	}
	
	/**
	 * Increments the structure index and returns the new value for convenience.
	 */
	public int nextStructure(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		int index = itemstack.stackTagCompound.getInteger(data[STRUCTURE_INDEX]) + 1;
		if (index == structures.size()) index = 0;
		itemstack.stackTagCompound.setInteger(data[STRUCTURE_INDEX], index);
		return index;
	}
	
	/**
	 * Decrements the structure index and returns the new value for convenience.
	 */
	public int prevStructure(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		int index = itemstack.stackTagCompound.getInteger(data[STRUCTURE_INDEX]) - 1;
		if (index < 0) index = this.structures.size() - 1;
		itemstack.stackTagCompound.setInteger(data[STRUCTURE_INDEX], index);
		return index;
	}
	
	/**
	 * Returns the name of the structure at provided index, or "" if index out of bounds
	 */
	public String getStructureName(int index) {
		return (index < structures.size() ? structures.get(index).name : "");
	}
	
	/**
	 * Returns index of currently selected structure
	 */
	public int getCurrentStructureIndex(ItemStack itemstack) {
		if (itemstack.stackTagCompound == null)
			initNBTCompound(itemstack);
		return itemstack.stackTagCompound.getInteger(data[STRUCTURE_INDEX]);
	}
	
	/**
	 * Toggles between generate and remove structure setting. Returns new value for convenience.
	 */
	public boolean toggleRemove() {
		return gen.toggleRemoveStructure();
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 1;
    }
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
		if (!world.isRemote && structures.size() > 0)
		{
			NBTTagCompound tag = itemstack.stackTagCompound;
			gen.setPlayerFacing(player);
			Structure structure = structures.get(tag.getInteger(data[STRUCTURE_INDEX]));
			gen.setBlockArrayList(structure.blockArrayList());
			gen.setStructureFacing(structure.getFacing() + tag.getInteger(data[ROTATIONS]));
			gen.setDefaultOffset(structure.getOffsetX() + tag.getInteger(data[OFFSET_X]), structure.getOffsetY() + tag.getInteger(data[OFFSET_Y]), structure.getOffsetZ() + tag.getInteger(data[OFFSET_Z]));
			gen.generate(world, world.rand, x, y, z);
		}
		
        return true;
    }
	
	/**
	 * Creates a new NBTTagCompound for the itemstack if none exists
	 */
	private final void initNBTCompound(ItemStack itemstack)
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
	 * Adds all structures to the Structure List
	 */
	private final void init()
	{
		Structure structure = new Structure("Hut");
		structure.addBlockArray(StructureArrays.blockArrayNPCHut);
		structure.setFacing(StructureGeneratorBase.EAST);
		// has a buffer layer on the bottom in case no ground; spawn at y-1 for ground level
		structure.setStructureOffset(0, -1, 0);
		structures.add(structure);
		
		structure = new Structure("Blacksmith");
		structure.addBlockArray(StructureArrays.blockArrayNPCBlackSmith);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("Viking Shop");
		structure.addBlockArray(StructureArrays.blockArrayShop);
		structure.setFacing(StructureGeneratorBase.WEST);
		structures.add(structure);
		
		structure = new Structure("Redstone Dungeon");
		structure.addBlockArray(StructureArrays.blockArrayRedstone);
		//structure.setFacing(StructureGeneratorBase.EAST);
		structures.add(structure);
		
		structure = new Structure("Offset Test");
		structure.addBlockArray(StructureArrays.blockArraySpawnTest);
		/*
		structure.addBlockArray(StructureArrays.blockArrayOffsetTest1);
		structure.addBlockArray(StructureArrays.blockArrayOffsetTest2);
		structure.addBlockArray(StructureArrays.blockArrayOffsetTest2);
		structure.addBlockArray(StructureArrays.blockArrayOffsetTest2);
		structure.addBlockArray(StructureArrays.blockArrayOffsetTest1);
		*/
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
	}
}
