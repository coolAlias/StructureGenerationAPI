/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen.items;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.lwjgl.input.Keyboard;

import coolalias.structuregen.StructureArrays;
import coolalias.structuregen.StructureGeneratorBase;
import coolalias.structuregen.WorldGenStructure;
import coolalias.structuregen.handlers.KeyHandlerSGT;
import coolalias.structuregen.lib.KeyBindSGT;
import coolalias.structuregen.lib.LogHelper;
import coolalias.structuregen.util.Structure;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStructureSpawner extends BaseModItem
{
	/** Enumerates valid values for increment / decrement offset methods */
	public static enum Offset { OFFSET_X, OFFSET_Y, OFFSET_Z }
	
	/** List of all structures that can be generated with this item */
	private static final List<Structure> structures = new LinkedList();
	
	private static final StructureGeneratorBase gen = new WorldGenStructure();
	
	private int index = 0, rotations = 0, offsetX = 0, offsetY = 0, offsetZ = 0;
	
	private boolean invert_y = false;

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
	 * Increments the appropriate Offset and returns the new value for convenience.
	 */
	public int incrementOffset(Offset type) {
		switch(type) {
		case OFFSET_X: return ++offsetX;
		case OFFSET_Y: return ++offsetY;
		case OFFSET_Z: return ++offsetZ;
		default: return 0;
		}
	}
	
	/**
	 * Decrements the appropriate Offset and returns the new value for convenience.
	 */
	public int decrementOffset(Offset type) {
		switch(type) {
		case OFFSET_X: return --offsetX;
		case OFFSET_Y: return --offsetY;
		case OFFSET_Z: return --offsetZ;
		default: return 0;
		}
	}
	
	/**
	 * Returns true if y offset is inverted (i.e. y will decrement)
	 */
	public boolean isInverted() {
		return this.invert_y;
	}
	
	/**
	 * Inverts Y axis for offset adjustments; returns new value for convenience.
	 */
	public boolean invertY() {
		this.invert_y = !this.invert_y;
		return this.invert_y;
	}
	
	/**
	 * Resets all manual offsets to 0.
	 */
	public void resetOffset() {
		offsetX = offsetY = offsetZ = 0;
	}
	
	/**
	 * Rotates structure's facing by 90 degrees clockwise; returns number of rotations for convenience.
	 */
	public int rotate() {
		this.rotations = (this.rotations == 3 ? 0 : ++this.rotations);
		return this.rotations;
	}
	
	/**
	 * Increments the structure index and returns the new value for convenience.
	 */
	public int nextStructure() {
		this.index = (this.index + 1 == structures.size() ? 0 : this.index + 1);
		return this.index;
	}
	
	/**
	 * Decrements the structure index and returns the new value for convenience.
	 */
	public int prevStructure() {
		this.index = (this.index > 0 ? this.index - 1 : this.structures.size() - 1);
		return this.index;
	}
	
	/**
	 * Returns the name of the structure at provided index, or null if index out of bounds
	 */
	public String getStructureName(int index) {
		return structures.get(index).name;
	}
	
	/**
	 * Returns index of currently selected structure
	 */
	public int getCurrentStructure() {
		return this.index;
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
		// NOTE: It isn't absolutely necessary to check if the world is not remote here,
		// but I recommend it as the client will be notified automatically anyway.
		if (!world.isRemote && structures.size() > 0)
		{
			// LogHelper.log(Level.INFO, "Preparing to generate structure");
			gen.setPlayerFacing(player);
			//gen.addBlockArray(StructureArrays.blockArrayNPCBlackSmith);
			Structure structure = structures.get(this.index);
			// LogHelper.log(Level.INFO, "Structure size: " + structure.blockArrayList().size());
			gen.setBlockArrayList(structure.blockArrayList());
			gen.setStructureFacing(structure.getFacing() + this.rotations);
			// LogHelper.log(Level.INFO, "Default offsets: " + structure.getOffsetX() + "/" + structure.getOffsetZ());
			//structure.setDefaultOffset(this.offsetX, this.offsetY, this.offsetZ);
			gen.setDefaultOffset(structure.getOffsetX() + this.offsetX, structure.getOffsetY() + this.offsetY, structure.getOffsetZ() + this.offsetZ);
			// adjust for structure generating centered on player's position (including height)
			//gen.setDefaultOffset(structure.getOffsetX() + this.offsetX, structure.getOffsetY() + this.offsetY, structure.getOffsetZ() + this.offsetZ); // adjust down one for the buffer layer
			gen.generate(world, world.rand, x, y, z);
		}
		
        return true;
    }
	
	private final void init()
	{
		Structure structure = new Structure("Hut");
		structure.addBlockArray(StructureArrays.blockArrayNPCHut);
		structure.setFacing(StructureGeneratorBase.EAST);
		// has a buffer layer on the bottom in case no ground; spawn at y-1 for ground level
		structure.setStructureOffset(0, -1, 0);
		structures.add(structure);
		LogHelper.log(Level.FINE, "Added " + structure.name + ". Structure list size is now " + this.structures.size());
		LogHelper.log(Level.FINEST, structure.name + " size: " + this.structures.get(0).blockArrayList().size());
		
		structure = new Structure("Blacksmith");
		structure.addBlockArray(StructureArrays.blockArrayNPCBlackSmith);
		structure.setFacing(StructureGeneratorBase.NORTH);
		//structure.setDefaultOffset();
		structures.add(structure);
		LogHelper.log(Level.FINE, "Added " + structure.name + ". Structure list size is now " + this.structures.size());
		LogHelper.log(Level.FINEST, structure.name + " size: " + this.structures.get(1).blockArrayList().size());
		
		structure = new Structure("Viking Shop");
		structure.addBlockArray(StructureArrays.blockArrayShop);
		structure.setFacing(StructureGeneratorBase.WEST);
		//structure.setDefaultOffset();
		structures.add(structure);
		LogHelper.log(Level.FINE, "Added " + structure.name + ". Structure list size is now " + this.structures.size());
		LogHelper.log(Level.FINEST, structure.name + " size: " + this.structures.get(2).blockArrayList().size());
		
		structure = new Structure("Redstone Dungeon");
		structure.addBlockArray(StructureArrays.blockArrayRedstone);
		//structure.setFacing(StructureGeneratorBase.EAST);
		structures.add(structure);
		LogHelper.log(Level.FINE, "Added " + structure.name + ". Structure list size is now " + this.structures.size());
		LogHelper.log(Level.FINEST, structure.name + " size: " + this.structures.get(3).blockArrayList().size());
		
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
		//structure.setDefaultOffset();
		structures.add(structure);
	}
}
