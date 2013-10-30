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

package coolalias.structuregen.mod.gen.structures;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import coolalias.structuregen.mod.lib.CustomHooks;

public class StructureArrays
{
	/**
	This file contains several sample structures as well as a template structure array
	to illustrate how to go about creating your own. Use this in conjunction with the
	provided StructureGen mod to test out your creation before adding it to your own mod.
	*/
	/**
	=====================================================================================
					HOW TO GENERATE YOUR CUSTOM STRUCTURES
	=====================================================================================
	!!! MOVED !!! See 'Instructions.md'
	
	STRUCTURE FACING
	Structure facing determines which 'side' of the array faces the player when generated.
	
	Facing      Array Setup
	NORTH       Front: min z, Back: max z, Left (east): max x, Right (west): x = 0
	SOUTH       Front: max z, Back: min z, Left (west): x = 0, Right (east): max x
	EAST        Front: max x, Back: x = 0, Left (south): max z, Right (north): z = 0
	WEST        Front: x = 0, Back: max x, Left (north): z = 0, Right (south): max z
	*/
	
	/**
	 * For every structure that you want to add, create another block array:
	 * 
	 * public static final int[][][][] blockArrayName
	 * 
	 * Be warned that there is a limit to the total size of static initializers that can
	 * be included in a single file; exceeding this limit will result in a compile time
	 * error. Simply put your arrays in separate files when they get too big.
	 */
	
	/**
	 * Here is an EMPTY block array template. Copy and paste as needed. See below for samples.
	 * 
	public static final int[][][][] blockArrayTemplate =
	{	// Brackets to encompass entire array
		{	// Brackets for y values
			{	// Brackets for x values within a single y bracket
				// Brackets for z values within a single x bracket
				{}, // z = 0,
				{}, // z = 1,
				{}	// z = 2 etc. for as many z values as you need
				// Each z bracket contains up to four integers:
				// {blockID, metadata, customData1, customData2}
			}
		}
	};
	*/
	/**
	 * Here is an EMPTY block array template. Copy and paste as needed.
	 */
	public static final int[][][][] blockArrayTemplate =
	{
		{ // y
			{ // x
				{} // z
			}
		}
	};
	
	public static final int[][][][] blockArraySpawnTest =
	{
		{ // y = 0
			{ // x = 0
				{Block.cloth.blockID,4},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.stainedClay.blockID,5},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cloth.blockID,6},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.stainedClay.blockID,7},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			}
		},
		{ // y = 1
			{ // x = 0
				{Block.stainedClay.blockID,0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.cloth.blockID,1},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.stainedClay.blockID,2},{CustomHooks.SPAWN_VILLAGER},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.cloth.blockID,3},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			}
		},
		{ // y = 2
			{ // x = 0
				{Block.cloth.blockID,8},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.stainedClay.blockID,9},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cloth.blockID,10},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.stainedClay.blockID,11},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			}
		}
	};
	
	/**
	 * Here is my demo structure, a showcase NPC Hut. Dimensions: 7x6 base, 8 blocks high.
	 * Uses lots of different kinds of blocks and metadata to demonstrate them in use.
	 */
	public static final int[][][][] blockArrayNPCHut =
	{
		{ // y = 0 demonstrating a layer of 'soft' spawning cobblestone
			{ // x = 0 (since this structure faces EAST, this is the back side)
				// below is the z axis; blocks run from north to south for our default facing
				{-Block.cobblestone.blockID}, // z = 0 (this is the NORTH side, which is on the right)
				{-Block.cobblestone.blockID}, // z = 1
				{-Block.cobblestone.blockID}, // z = 2
				{-Block.cobblestone.blockID}, // z = 3
				{-Block.cobblestone.blockID}, // z = 4
				{-Block.cobblestone.blockID}, // z = 5 (this is the SOUTH side, which is on the left)
			},
			{ // x = 1
				{-Block.cobblestone.blockID}, // z = 0
				{-Block.cobblestone.blockID}, // z = 1
				{-Block.cobblestone.blockID}, // z = 2
				{-Block.cobblestone.blockID}, // z = 3
				{-Block.cobblestone.blockID}, // z = 4
				{-Block.cobblestone.blockID}, // z = 5
			},
			{ // x = 2
				{-Block.cobblestone.blockID}, // z = 0
				{-Block.cobblestone.blockID}, // z = 1
				{-Block.cobblestone.blockID}, // z = 2
				{-Block.cobblestone.blockID}, // z = 3
				{-Block.cobblestone.blockID}, // z = 4
				{-Block.cobblestone.blockID}, // z = 5
			},
			{ // x = 3
				{-Block.cobblestone.blockID}, // z = 0
				{-Block.cobblestone.blockID}, // z = 1
				{-Block.cobblestone.blockID}, // z = 2
				{-Block.cobblestone.blockID}, // z = 3
				{-Block.cobblestone.blockID}, // z = 4
				{-Block.cobblestone.blockID}, // z = 5
			},
			{ // x = 4
				{-Block.cobblestone.blockID}, // z = 0
				{-Block.cobblestone.blockID}, // z = 1
				{-Block.cobblestone.blockID}, // z = 2
				{-Block.cobblestone.blockID}, // z = 3
				{-Block.cobblestone.blockID}, // z = 4
				{-Block.cobblestone.blockID}, // z = 5
			},
			{ // x = 5 (this is the EAST side, which for this structure is the front)
				{-Block.cobblestone.blockID}, // z = 0
				{-Block.cobblestone.blockID}, // z = 1
				{-Block.cobblestone.blockID}, // z = 2
				{-Block.cobblestone.blockID}, // z = 3
				{-Block.cobblestone.blockID}, // z = 4
				{-Block.cobblestone.blockID}, // z = 5
			},
			{ // x = 6 (this is the EAST side, which for this structure is the front)
				{-Block.cobblestone.blockID}, // z = 0
				{-Block.cobblestone.blockID}, // z = 1
				{-Block.cobblestone.blockID}, // z = 2
				{-Block.cobblestone.blockID}, // z = 3
				{-Block.cobblestone.blockID}, // z = 4
				{-Block.cobblestone.blockID}, // z = 5
			}
		},
		{ // y = 1
			{ // x = 0
				{Block.wood.blockID,8},	// z = 0
				{Block.cobblestone.blockID}, // z = 1
				{Block.cobblestone.blockID}, // z = 2
				{Block.cobblestone.blockID}, // z = 3
				{Block.cobblestone.blockID}, // z = 4
				{Block.lever.blockID,11} // z = 5
			},
			{ // x = 1 z values:
				{Block.tripWireSource.blockID,7},
				{Block.cobblestone.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.torchRedstoneIdle.blockID,1},
				{Block.glass.blockID}
			},
			{ // x = 2 z values:
				{Block.tripWire.blockID},
				{Block.cobblestone.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.redstoneWire.blockID},
				{Block.glass.blockID}
			},
			{ // x = 3 z values:
				{Block.tripWireSource.blockID,5},
				{Block.cobblestone.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.redstoneRepeaterIdle.blockID,13},
				{Block.glass.blockID}
			},
			{ // x = 4 z values:
				{Block.wood.blockID,6},
				{Block.cobblestone.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.pistonBase.blockID,13},
				{Block.fence.blockID}
			},
			{ // x = 5 z values:
				{Block.fence.blockID},
				{CustomHooks.CUSTOM_SIGNPOST,14},
				{Block.railPowered.blockID,11},
				{Block.stairsCobblestone.blockID,1}, // ascending to west
				{CustomHooks.SPAWN_VILLAGER,1,2},
				// we'll spawn him outside just so there's more room inside for stuff
				// however, this way the villager will not move inside at night
				// you should ALWAYS spawn villagers inside a valid 'home' if possible
				{Block.fence.blockID}
			},
			{ // x = 6
				{Block.fence.blockID},
				{Block.fence.blockID},
				{Block.fence.blockID},
				{Block.fenceGate.blockID,1}, // could also be 3
				{Block.fence.blockID},
				{Block.fence.blockID},
			}
		},
		{ // y = 2
			{ // x = 0 z values:
				{CustomHooks.PAINTING,2}, // faces west, but because it has no block to attach to, it doesn't get placed
				{Block.wood.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.cloth.blockID,14},
				{CustomHooks.PAINTING,3} // facing south (since default structure faces EAST, this is the left-hand side)
			},
			{ // x = 1 z values:
				{CustomHooks.PAINTING,4}, // facing north (since default structure faces EAST, this is the right-hand side)
				// if you change the above block to wood, the painting at y=2,x=0,z=0 will work fine
				{Block.planks.blockID},
				{Block.bed.blockID,10},
				// head, facing west (i.e. to the right), so we must place the head to the north of the base (i.e. z - 1)
				{Block.bed.blockID,2}, // base, facing west, at z=3, so head is at z=2
				{Block.planks.blockID},
				{0}
			},
			{ // x = 2 z values:
				{0},
				{Block.planks.blockID},
				{Block.cobblestone.blockID},
				{CustomHooks.CUSTOM_DISPENSER,5,Item.arrow.itemID}, // Using custom data to define item id
				{Block.planks.blockID},
				{0}
			},
			{ // x = 3 z values:
				{0},
				{Block.planks.blockID},
				{CustomHooks.CUSTOM_CHEST,3,4}, // a custom chest, facing south, with custom data of 4
				{0},
				{Block.planks.blockID},
				{0}
			},
			{ // x = 4 z values:
				{Block.ladder.blockID,2}, // attached to block to south
				{Block.wood.blockID},
				{Block.planks.blockID},
				{Block.doorWood.blockID,2}, // faces east, bottom block of door
				{Block.wood.blockID},
				{0}
			},
			{ // x = 5 z values:
				{0},{CustomHooks.PAINTING,1},{CustomHooks.PAINTING,1},{0},{CustomHooks.CUSTOM_SIGNWALL,5,CustomHooks.CUSTOM_SIGN_1},{0}
			}
			// note that since we don't spawn anything at x = 6 from here on, we don't need to include it
			// excluding x=0, however, would cause this entire layer to be out of place
		},
		{ // y = 3
			{ // x = 0 z values:
				{0},{Block.wood.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.wood.blockID},{0}
			},
			{ // x = 1 z values:
				{0},
				{Block.planks.blockID},
				{Block.cobblestone.blockID}, // block just to north of torch below
				{Block.torchRedstoneIdle.blockID,3}, // facing south, so on block to north
				{Block.planks.blockID},
				{0}
			},
			{ // x = 2 z values:
				{Block.trapdoor.blockID,4}, // placing a trapdoor on glass is normally not possible
				{Block.thinGlass.blockID}, // changing this to a non-glass block would prevent the trapdoor from falling off
				{Block.redstoneWire.blockID}, // so this wire here will cause the above trapdoor to detach
				{0},
				{Block.thinGlass.blockID},
				{Block.trapdoor.blockID,5} // this one, however, will stay (unless you connect redstone wire to it and activate it)
			},
			{ // x = 3 z values:
				{0},
				{Block.planks.blockID},
				{Block.torchRedstoneActive.blockID,2}, // faces west
				{0},
				{Block.planks.blockID},
				{0}
			},
			{ // x = 4 z values:
				{Block.vine.blockID,1}, // attached to block to south
				{Block.wood.blockID},
				{Block.planks.blockID},
				{Block.doorWood.blockID,10}, // faces east + 8 because it's the top
				{Block.pumpkinLantern.blockID,3}, // faces east
				{0}
			},
			{ // x = 5 z values:
				{0},
				{0},
				{Block.woodenButton.blockID,1}, // faces east
				{0},
				{Block.trapdoor.blockID,7},
				{0}
			}
		},
		// From here on, I will condense the format further
		{ // y = 4
			{ // x = 0 z values:
				{0},{Block.wood.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.wood.blockID},{0}
			},
			{ // x = 1 z values:
				{0},{Block.planks.blockID},{0},{0},{Block.planks.blockID},{0}
			},
			{ // x = 2 z values:
				{0},{Block.planks.blockID},{Block.lever.blockID,0},{0},{Block.planks.blockID},{0}
			},
			{ // x = 3 z values:
				{0},{Block.planks.blockID},{0},{0},{Block.planks.blockID},{0}
			},
			{ // x = 4 z values:
				{Block.cocoaPlant.blockID,8},{Block.wood.blockID,3},{Block.planks.blockID},{Block.planks.blockID},{Block.wood.blockID},{0}
			},
			{ // x = 5 z values:
				{0},{Block.torchWood.blockID,1},{0},{CustomHooks.CUSTOM_SKULL,5,3},{Block.torchWood.blockID,1},{0}
			}
		},
		{ // y = 5
			{ // x = 0 z values:
				{0},{CustomHooks.ITEM_FRAME,4,Item.diamond.itemID},{Block.wood.blockID},{Block.wood.blockID},{CustomHooks.ITEM_FRAME,3,Item.diamond.itemID},{0}
			},
			{ // x = 1 z values:
				{CustomHooks.ITEM_FRAME,4,Item.emerald.itemID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{CustomHooks.ITEM_FRAME,3,Item.emerald.itemID}
			},
			{ // x = 2 z values:
				{CustomHooks.ITEM_FRAME,4,Item.diamond.itemID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{CustomHooks.ITEM_FRAME,3,Item.diamond.itemID}
			},
			{ // x = 3 z values:
				{CustomHooks.ITEM_FRAME,4,Item.emerald.itemID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{CustomHooks.ITEM_FRAME,3,Item.emerald.itemID}
			},
			{ // x = 4 z values:
				{0},{CustomHooks.ITEM_FRAME,4,Item.diamond.itemID},{Block.wood.blockID},{Block.wood.blockID},{CustomHooks.ITEM_FRAME,3,Item.diamond.itemID},{0}
			},
			{ // x = 5 z values:
				{0},{0},{CustomHooks.ITEM_FRAME,1,Item.pickaxeDiamond.itemID},{CustomHooks.ITEM_FRAME,1,Item.swordDiamond.itemID},{0},{0}
			}
		},
		{ // y = 6
			{ // x = 0 z values:
				{0},{0},{0},{0},{0},{0}
			},
			{ // x = 1 z values:
				{0},{0},{Block.rail.blockID,6},{Block.rail.blockID,9},{0},{0}
			},
			{ // x = 2 z values:
				{0},{0},{Block.rail.blockID,1},{Block.rail.blockID,1},{0},{0}
			},
			{ // x = 3 z values:
				{0},{0},{Block.rail.blockID,7},{Block.rail.blockID,8},{0},{0}
			},
			{ // x = 4 z values:
				{0},{0},{0},{0},{0},{0}
			},
			{ // x = 5 z values:
				{0},{0},{0},{0},{0},{0}
			}
		}
	};
	
	public static final int[][][][] blockArrayNPCBlackSmith =
	{
		{// y = 0
			{// x = 0
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 1
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 2
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 3
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 4
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 5
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 6
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 7
				{},{},{},{},{},{},{Block.stairsCobblestone.blockID,1},{Block.stairsCobblestone.blockID,1},{Block.stairsCobblestone.blockID,1},{}
			}
		},
		{//y = 1
			{//x = 0
				{Block.wood.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 1
				{Block.planks.blockID},{Block.planks.blockID},{Block.stairsWoodOak.blockID, 1},{},{},{Block.chest.blockID, 5},{Block.cobblestone.blockID},{Block.lavaStill.blockID},{Block.lavaStill.blockID},{Block.cobblestone.blockID}
			},
			{//x = 2
				{Block.planks.blockID},{Block.stairsWoodOak.blockID, 3},{Block.fence.blockID},{},{},{},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 3
				{Block.planks.blockID},{0},{0},{0},{Block.planks.blockID},{Block.planks.blockID},{Block.cobblestone.blockID},{0},{0},{0}
			},
			{//x = 4
				{Block.planks.blockID},{0},{0},{Block.planks.blockID},{0},{0},{0},{0},{0},{0}
			},
			{//x = 5
				{Block.planks.blockID},{0},{0},{0},{0},{0},{0},{0},{Block.anvil.blockID},{0}
			},
			{//x = 6
				{Block.wood.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.wood.blockID},{0},{Block.fence.blockID},{0},{0},{0},{Block.fence.blockID}
			},
			{//x = 7
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			}
		},
		{//y = 2
			{//x = 0
				{Block.wood.blockID},{Block.planks.blockID},{Block.thinGlass.blockID},{Block.planks.blockID},{Block.thinGlass.blockID},{Block.planks.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 1
				{Block.planks.blockID},{0},{0},{0},{0},{0},{Block.cobblestone.blockID},{0},{0},{Block.fenceIron.blockID}
			},
			{//x = 2
				{Block.thinGlass.blockID},{0},{Block.pressurePlatePlanks.blockID},{0},{0},{0},{Block.cobblestone.blockID},{0},{0},{Block.fenceIron.blockID}
			},
			{//x = 3
				{Block.planks.blockID},{0},{0},{0},{Block.planks.blockID},{Block.planks.blockID},{Block.furnaceIdle.blockID, 5},{0},{0},{0}
			},
			{//x = 4
				{Block.thinGlass.blockID},{0},{0},{Block.planks.blockID},{0},{0},{0},{0},{0},{0}
			},
			{//x = 5
				{Block.planks.blockID},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 6
				{Block.wood.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.wood.blockID},{0},{Block.fence.blockID},{0},{0},{0},{Block.fence.blockID}
			},
			{//x = 7
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			}
		},
		{//y = 3
			{//x 0
				{Block.wood.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 1
				{Block.planks.blockID},{0},{0},{0},{0},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 2
				{Block.planks.blockID},{0},{0},{0},{0},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 3
				{Block.planks.blockID},{0},{0},{0},{Block.planks.blockID},{Block.planks.blockID},{Block.furnaceIdle.blockID, 5},{0},{0},{0}
			},
			{//x = 4
				{Block.planks.blockID},{0},{0},{Block.planks.blockID},{0},{0},{0},{0},{0},{0}
			},
			{//x - 5
				{Block.planks.blockID},{0},{0},{Block.planks.blockID},{0},{0},{0},{0},{0},{0}
			},
			{//x = 6
				{Block.wood.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.wood.blockID},{0},{Block.fence.blockID},{0},{0},{0},{Block.fence.blockID}
			},
			{//x = 7
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			}
		},
		{//y = 4
			{//x = 0
				{Block.wood.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 1
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 2
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 3
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 4
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 5
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 6
				{Block.wood.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.wood.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{//x = 7
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			}
		},
		{//y = 5
			{//x = 0
				{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID}
			},
			{//x = 1
				{0},{0},{0},{0},{0},{0},{0},{0},{Block.woodSingleSlab.blockID}
			},
			{//x = 2
				{Block.woodSingleSlab.blockID},{0},{0},{0},{0},{0},{0},{0},{0},{Block.woodSingleSlab.blockID}
			},
			{//x = 3
				{Block.woodSingleSlab.blockID},{0},{0},{0},{0},{0},{0},{0},{0},{Block.woodSingleSlab.blockID}
			},
			{//x = 4
				{Block.woodSingleSlab.blockID},{0},{0},{0},{0},{0},{0},{0},{0},{Block.woodSingleSlab.blockID}
			},
			{//x = 5
				{Block.woodSingleSlab.blockID},{0},{0},{0},{0},{0},{0},{0},{0},{Block.woodSingleSlab.blockID}
			},
			{//x = 6
				{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID},{Block.woodSingleSlab.blockID}
			},
			{//x = 7
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			}
		}
	};
	
	/*
	public static final int[][][][] blockArrayMaxBase =
		{
			{ // y = 0
				{ // x = 0
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 1
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 2
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 3
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 4
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 5
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 6
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 7
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 8
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 9
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 10
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 1
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 2
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 3
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 4
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 5
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 6
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 7
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 8
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 9
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 20
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 1
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 2
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 3
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 4
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 5
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 6
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 7
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 8
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 9
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 30
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 1
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 2
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 3
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 4
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 5
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 6
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 7
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 8
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 9
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 40
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 1
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 2
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 3
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 4
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 5
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 6
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 7
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 8
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 9
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 50
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 1
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 2
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 3
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 4
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 5
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 6
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 7
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 8
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 9
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 60
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 1
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 2
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 3
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 4
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 5
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 6
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 7
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 8
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				}
			}
		};
		*/
	
	public static final int[][][][] blockArrayShop =
	{
		{//y = 0
			{ //x = 0
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID}
			},
			{ //x = 1
				{Block.grass.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.wood.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.grass.blockID}
			},
			{ //x = 2
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID}
			},
			{ //x = 3
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID},
				{Block.dirt.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,2},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,4},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,1},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{Block.dirt.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID}
			},
			{ //x = 4
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID},
				{Block.dirt.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,2},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,1},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,4},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.cobblestone.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID}
			},
			{ //x = 5
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID},
				{Block.dirt.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,4},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,2},
				{CustomHooks.RANDOM_HOLE,0,Block.planks.blockID,1},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{Block.dirt.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID}
			},
			{ //x = 6
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID}
			},
			{ //x = 7
				{Block.grass.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.grass.blockID}
			},
			{ //x = 8
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID}
			}
		},
		{ //y = 1
			{ //x = 0
				{0},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,4},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,2},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,1},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,1},
				{0},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,4},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,2},
				{0}
			},
			{//x = 1
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,4},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,2},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,4},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,1},
				{Block.doorWood.blockID, 2},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,2},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,1},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,2}
			},
			{//x = 2
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID},
				{Block.planks.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.planks.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID}
			},
			{//x = 3
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{0}
			},
			{//x = 4
				{0},
				{CustomHooks.RANDOM_HOLE,0,Block.cobblestone.blockID,3},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,0,Block.cobblestone.blockID,3},
				{0}
			},
			{//x = 5
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{0}
			},
			{//x = 6
				{Block.wood.blockID},
				{Block.planks.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.planks.blockID},
				{Block.wood.blockID}
			},
			{//x = 7
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,1},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,4},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,2},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,2},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,1},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.stoneBrick.blockID,4},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0}
			},
			{//x = 8
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{0}
			}
		},
		{//y = 2
			{//x = 0
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 1
				{0},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,0},
				{Block.fence.blockID},
				{0},
				{Block.fence.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,0},
				{Block.doorWood.blockID, 10},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,0},
				{0}
			},
			{//x = 2
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,00}
			},
			{//x = 3
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID},
				{Block.torchWood.blockID, 3},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.torchWood.blockID, 4},
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID}
			},
			{//x = 4
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{0}
			},
			{//x = 5
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID},
				{Block.torchWood.blockID, 3},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.torchWood.blockID, 4},
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID}
			},
			{//x = 6
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
			},
			{//x = 7
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{0}
			},
			{//x = 8
				{0},
				{0},
				{0},
				{Block.torchWood.blockID, 1},
				{0},
				{0},
				{0},
				{Block.torchWood.blockID, 1},
				{0},
				{0},
				{0}
			}
		},
		{//y = 3
			{//x = 0
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 1
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{0},
				{0},
				{0}
			},
			{//x = 2
				{0},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,2},
				{CustomHooks.RANDOM_HOLE,0,Block.cobblestone.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.cobblestone.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.cobblestone.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,3},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,3},
				{Block.stairsCobblestone.blockID,0},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,2},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID,2},
				{0}
			},
			{//x = 3
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID}
			},
			{//x = 4
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID},
				{Block.planks.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.planks.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.wood.blockID}
			},
			{//x = 5
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,2},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
			},
			{//x = 6
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,1},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,3},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsCobblestone.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,3},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,1},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsCobblestone.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,4},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,4},
				{0}
			},
			{//x = 7
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{0},
				{0},
				{0}
			},
			{//x = 8
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{0},
				{0},
				{0},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{0},
				{0},
				{0}
			}
		},
		{//y = 4
			{//x = 0
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 1
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 2
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 3
				{0},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsCobblestone.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsCobblestone.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsCobblestone.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsCobblestone.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID},
				{CustomHooks.RANDOM_HOLE,0,Block.stairsWoodSpruce.blockID},
				{0}
			},
			{//x = 4
				{CustomHooks.RANDOM_HOLE,2,Block.stairsCobblestone.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.wood.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stairsCobblestone.blockID}
			},
			{//x = 5
				{0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,2},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,1},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsCobblestone.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,4},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,0},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsCobblestone.blockID},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,3},
				{CustomHooks.RANDOM_HOLE,1,Block.stairsWoodSpruce.blockID,1},
				{0}
			}
		},
		{//y = 5
			{//x = 0
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 1
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 2
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 3
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 4
				{CustomHooks.RANDOM_HOLE,2,Block.stairsCobblestone.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stoneSingleSlab.blockID},
				{CustomHooks.RANDOM_HOLE,3,Block.stairsCobblestone.blockID}
			}
		}
	};
	
	public static final int[][][][] blockArrayRedstone =
	{
		{ // y = 0 (buffer layer)
			{ // x = 0
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 1
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 2
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 3
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 4
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 5
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 6
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 7
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 8
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 9
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 10
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			},
			{ // x = 11
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},
				{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID},{-Block.cobblestone.blockID}
			}
		},
		{ // y = 1
			{ // x = 0
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 4
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 5
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 6
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 7
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.lavaStill.blockID},
				{Block.lavaStill.blockID},{Block.lavaStill.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 8
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.lavaStill.blockID},
				{Block.lavaStill.blockID},{Block.lavaStill.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 9
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.lavaStill.blockID},
				{Block.lavaStill.blockID},{Block.lavaStill.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 10
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 11
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			}
		},
		{ // y = 2
			{ // x = 0
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.cobblestone.blockID},{0},{0},{Block.cobblestone.blockID},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cobblestone.blockID},{0},{0},{Block.redstoneWire.blockID},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.cobblestone.blockID},{0},{0},{Block.redstoneWire.blockID},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 4
				{Block.cobblestone.blockID},{0},{0},{Block.torchRedstoneActive.blockID,2},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 5
				{Block.cobblestone.blockID},{0},{0},{Block.cobblestone.blockID},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 6
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.redstoneWire.blockID},
				{0},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 7
				{Block.cobblestone.blockID},{0},{0},{0},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 8
				{Block.cobblestone.blockID},{0},{0},{0},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 9
				{Block.cobblestone.blockID},{0},{0},{0},{0},{0},{0},{Block.stairsCobblestone.blockID,3}
			},
			{ // x = 10
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.stairsCobblestone.blockID,3}
			},
			{ // x = 11
				{0},{0},{0},{0},{0},{0},{Block.torchWood.blockID,1},{0}
			}
		},
		{ // y = 3
			{ // x = 0
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.cobblestone.blockID},{0},{0},{Block.redstoneWire.blockID},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cobblestone.blockID},{0},{0},{0},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.cobblestone.blockID},{0},{0},{0},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 4
				{Block.cobblestone.blockID},{0},{0},{0},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 5
				{Block.cobblestone.blockID},{0},{0},{Block.redstoneWire.blockID},{0},{0},{0},{Block.cobblestone.blockID}
			},
			{ // x = 6
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.redstoneWire.blockID},{0},
				{0},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 7
				{Block.cobblestone.blockID},{0},{0},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 8
				{Block.cobblestone.blockID},{0},{0},{Block.cobblestone.blockID},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 9
				{Block.cobblestone.blockID},{0},{0},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.stairsCobblestone.blockID,3},{0}
			},
			{ // x = 10
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.stairsCobblestone.blockID,3},{0}
			},
			{ // x = 11
				{0},{0},{0},{0},{0},{Block.torchWood.blockID,1},{0},{0}
			}
		},
		{ // y = 4
			{ // x = 0
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.redstoneWire.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{0},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 4
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 5
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 6
				{Block.cobblestone.blockID},{Block.redstoneWire.blockID},{0},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 7
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 8
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 9
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.stairsCobblestone.blockID,3},{0},{0}
			},
			{ // x = 10
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.stairsCobblestone.blockID,3},{0},{0}
			},
			{ // x = 11
				{0},{0},{0},{0},{Block.torchWood.blockID,1},{0},{0},{0}
			}
		},
		{ // y = 5
			{ // x = 0
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{0},
				{Block.redstoneWire.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 4
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.pistonStickyBase.blockID,5},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 5
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 6
				{Block.cobblestone.blockID},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.pistonStickyBase.blockID,5},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 7
				{Block.redstoneWire.blockID},{Block.redstoneWire.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{0},{0},{CustomHooks.CUSTOM_CHEST,2,CustomHooks.CUSTOM_CHEST_1},{Block.cobblestone.blockID}
			},
			{ // x = 8
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 9
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{0},{0},{0},{Block.torchWood.blockID,1}
			},
			{ // x = 10
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{0},{0},{0},{0}
			},
			{ // x = 11
				{0},{0},{0},{Block.torchWood.blockID,1},{0},{0},{0},{0}
			}
		},
		{ // y = 6
			{ // x = 0
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.redstoneWire.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.redstoneWire.blockID},{Block.redstoneWire.blockID},
				{Block.redstoneWire.blockID},{Block.redstoneWire.blockID},{Block.redstoneWire.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.redstoneWire.blockID},{Block.cobblestone.blockID},
				{Block.redstoneWire.blockID},{Block.cobblestone.blockID},{Block.redstoneRepeaterActive.blockID,9},{Block.cobblestone.blockID}
			},
			{ // x = 4
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.redstoneWire.blockID},{Block.cobblestone.blockID},
				{Block.pistonStickyBase.blockID,5},{Block.redstoneRepeaterActive.blockID,0},{Block.redstoneWire.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 5
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.redstoneWire.blockID},{Block.cobblestone.blockID},
				{0},{Block.redstoneRepeaterActive.blockID,12},{Block.redstoneWire.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 6
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.redstoneWire.blockID},{Block.redstoneRepeaterActive.blockID,6},
				{Block.pistonStickyBase.blockID,5},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 7
				{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{0},{Block.torchWood.blockID,2},{0},{Block.cobblestone.blockID}
			},
			{ // x = 8
				{Block.redstoneWire.blockID},{Block.redstoneWire.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 9
				{Block.cobblestone.blockID},{Block.redstoneWire.blockID},{Block.redstoneWire.blockID},{Block.cobblestone.blockID},
				{Block.lever.blockID,3},{0},{0},{0}
			},
			{ // x = 10
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{0},{0},{0},{0}
			},
			{ // x = 11
				{0},{0},{Block.torchWood.blockID,1},{0},{0},{0},{0},{0}
			}
		},
		{ // y = 7
			{ // x = 0
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 4
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 5
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 6
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 7
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 8
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 9
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 10
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 11
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			}
		}
	};
	
	public static final int[][][][] blockArrayOffsetTest1 = 
	{
		{ // y = 0
			{ // x = 0
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 3
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 4
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			}
		}
	};
	
	public static final int[][][][] blockArrayOffsetTest2 = 
	{
		{ // y = 0
			{ // x = 0
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 1
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			},
			{ // x = 2
				{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
			}
		}
	};
	
	public static final int[][][][] threeByFour =
	{
		{
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
		}
	};
	
	public static final int[][][][] fourByThree =
	{
		{
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}}
		}
	};
	
	public static final int[][][][] twoBySix =
	{
		{
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}}
		}
	};
	
	public static final int[][][][] sixByTwo =
	{
		{
			{{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID}}
		}
	};
	
	public static final int[][][][] threeBySix =
	{
		{
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}}
		}
	};
	
	public static final int[][][][] sixByThree =
	{
		{
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}}
		}
	};
	
	public static final int[][][][] threeBySeven =
	{
		{
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}}
		}
	};
	
	public static final int[][][][] sevenByThree =
	{
		{
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}}
		}
	};
	
	public static final int[][][][] eightBySeven =
	{
		{
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}}
		}
	};
	
	public static final int[][][][] sevenByEight =
	{
		{
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}}
		}
	};
	
	// 9 x 10
	public static final int[][][][] dimensionTest =
	{
		{
			/*
			// DOESN'T WORK!!!
			// 3 x 6
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}}
			*/
			/*
			// 10 x 9
	    	{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}}
	    	*/
			/*
			// DOESN'T WORK!!!
			// 10 x 7
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}}
	    	*/
			/*
			// 11 x 8
	    	{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}}
	    	*/
			
	    	// 9 x 10
			{{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{},{},{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}},
	    	{{Block.stone.blockID}}
	    	
	    }
	};
}
