/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import coolalias.structuregen.lib.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class Structure
{
	/** The name of this structure */
	public final String name;
	
	/** The List of all blockArray layers necessary to complete the structure */
	private final List<int[][][][]> blockArrayList = new LinkedList();
	
	/** Stores the direction this structure faces. Default is EAST.*/
	private int facing;
	
	/** Stores default amount to offset structure's location in the world. */
	private int offsetX = 0, offsetY = 0, offsetZ = 0;

	public Structure(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the blockArray List for this structure
	 */
	public final List<int[][][][]> blockArrayList() {
		return this.blockArrayList;
	}
	
	/**
	 * Returns the structure's default facing
	 */
	public final int getFacing() {
		return this.facing;
	}
	
	/**
	 * Sets the default direction the structure is facing. This side will always face the player
	 * unless you manually rotate the structure with the rotateStructureFacing() method.
	 */
	public final void setFacing(int facing) {
		this.facing = facing;
	}
	
	/**
	 * Adds a block array 'layer' to the list to be generated
	 */
	public final void addBlockArray(int blocks[][][][]) {
		//if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			this.blockArrayList.add(blocks);
			//if (this.blockArray == null)
				//this.blockArray = blocks;
		//}
	}
	
	/**
	 * Adds all elements contained in the parameter list to the structure
	 */
	public final void addBlockArrayList(List<int[][][][]> list)
	{
		this.blockArrayList.addAll(list);
	}
	
	/**
	 * Returns lowest structure layer's width along the x axis or 0 if no structure has been added
	 */
	public final int getWidthX() {
		return blockArrayList.size() > 0 ? blockArrayList.get(0)[0].length : 0;
	}
	
	/**
	 * Returns lowest structure layer's width along the z axis or 0 if no structure has been set
	 */
	public final int getWidthZ() {
		return blockArrayList != null ? blockArrayList.get(0)[0][0].length : 0;
	}
	
	/**
	 * Returns structure's total height
	 */
	public final int getHeight() {
		int sum = 0;
		Iterator<int[][][][]> iterator = this.blockArrayList.iterator();
		
		while (iterator.hasNext())
		{
			int[][][][] blockArray = iterator.next();
			sum += blockArray.length;
		}
		
		return sum;
	}
	
	/**
	 * Returns the structure's offset for the x axis
	 */
	public final int getOffsetX() {
		return this.offsetX;
	}
	
	/**
	 * Returns the structure's offset for the y axis
	 */
	public final int getOffsetY() {
		return this.offsetY;
	}
	
	/**
	 * Returns the structure's offset for the z axis
	 */
	public final int getOffsetZ() {
		return this.offsetZ;
	}
	
	/**
	 * This is how much the structure should be offset from default; i.e. sets the values
	 * that should be passed in to StructureGeneratorBase.setDefaultOffset. Used, for
	 * example, if your structure's front door is not in the center, but a few blocks
	 * to the left and you want the door to spawn in front of the player, or if your
	 * structure should always be spawned in the air, etc.
	 */
	public final void setStructureOffset(int x, int y, int z) {
		this.offsetX = x;
		this.offsetY = y;
		this.offsetZ = z;
	}
}