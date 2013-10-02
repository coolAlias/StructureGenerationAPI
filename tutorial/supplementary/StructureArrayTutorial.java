package coolalias.structuregen;

import coolalias.structuregen.lib.CustomHooks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class StructureArrayTutorial
{
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
