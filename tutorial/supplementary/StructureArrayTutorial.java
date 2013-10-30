package coolalias.structuregen.tutorial;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class StructureArrayTutorial
{
	/**
	 * These values are used in place of BlockId and require you to define what the
	 * real block Id is when you define the abstract method 'getRealBlockID'
	 * 
	 * See StructureGenMod's CustomHooks class (in mod/lib) for further examples.
	 */
	public static final int CUSTOM_CHEST = 4096;
	
	/*
	The following values are all used in the CustomData slots of the array and provide
	further information about the custom 'block', such as number of chest contents.
	Note that values can overlap as long as they are for a different hook id
	 */
	
	/** Custom Chest 'id'; used to set a specific chest's contents
	  * I use negative values for these to avoid potential conflicts with ItemIDs */
	public static final int CUSTOM_CHEST_1 = -1;
	
	/** Custom sign 'id', used to set a specific sign's text */
	public static final int CUSTOM_SIGN_1 = 1;

	public static final int[][][][] blockArrayTutorial =
	{
	    { // y = 1
	        { // x = 1
	            {Block.wood.blockID},{Block.wood.blockID},{Block.doorWood.blockID,0},{Block.wood.blockID},{Block.wood.blockID}
	        },
	        { // x = 2
	            {Block.wood.blockID},{0},{0},{CustomHooks.CUSTOM_CHEST,2,Item.appleGold.itemID,16},{Block.wood.blockID}
	        },
	        { // x = 3
	            {Block.wood.blockID},{0},{0},{CustomHooks.CUSTOM_CHEST,2,CustomHooks.CUSTOM_CHEST_1},{Block.wood.blockID}
	        },
	        { // x = 4
	            {Block.wood.blockID},{Block.bed.blockID,10},{Block.bed.blockID,2},{0},{Block.wood.blockID}
	        },
	        { // x = 5
	            {Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID}
	        }
	    },
	    { // y = 2
	        { // x = 1
	            {Block.wood.blockID},{Block.wood.blockID},{Block.doorWood.blockID,8},{Block.wood.blockID},{Block.wood.blockID}
	        },
	        { // x = 2
	            {Block.wood.blockID},{Block.torchWood.blockID,1},{0},{0},{Block.wood.blockID}
	        },
	        { // x = 3
	            {Block.glass.blockID},{0},{0},{0},{Block.glass.blockID}
	        },
	        { // x = 4
	            {Block.wood.blockID},{0},{0},{0},{Block.wood.blockID}
	        },
	        { // x = 5
	            {Block.wood.blockID},{Block.wood.blockID},{Block.glass.blockID},{Block.wood.blockID},{Block.wood.blockID}
	        }
	    },
	    { // y = 3
	        { // x = 1
	            {Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID}
	        },
	        { // x = 2
	            {Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID}
	        },
	        { // x = 3
	            {Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID}
	        },
	        { // x = 4
	            {Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID}
	        },
	        { // x = 5
	            {Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID}
	        }
	    }
	};
}
