/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen;

import net.minecraft.block.Block;

public class StructureArrays
{
	public static final int CUSTOM_CHEST = 4097, SPAWN_VILLAGER = 4098;
	/**
	 * This file contains a demo and a template structure to illustrate how to go about
	 * creating your own. Use this in conjunction with the provided StructureGen mod to
	 * test out your creation before adding it to your own mod.
	 * 
	 * =====================================================================================
	 * 						HOW TO GENERATE YOUR CUSTOM STRUCTURES
	 * =====================================================================================
	 * Once you have your blockArray(s) set up correctly (see below), you will need to add
	 * them to the WorldGenStructure generator's list of arrays to generate.
	 * 
	 * This is done with the method "addBlockArray(int[][][][])".
	 * 
	 * If you have more than one layer, they should be added from the bottom up, so first
	 * the base, then the layer on top of the base, and so on until the final topmost layer
	 * is added.
	 * 
	 * Next, set your structure's facing and possibly offset with the following methods:
	 * 
	 * "setStructureFacing(StructureGeneratorBase.DIRECTION)"
	 *  where DIRECTION is NORTH, SOUTH, EAST, WEST
	 * 
	 * "setOffset(int offX, int offY, int offZ)"
	 * -offX places the structure further away from the player, +offX will move toward the
	 * player. Thinking about inverting this.
	 * 
	 * =====================================================================================
	 * 						SETTING UP A STRUCTURE'S BLOCK ARRAY
	 * =====================================================================================
	 * You can choose any direction to be the default facing of your structure. It is the
	 * side that will always face the player when generated, which doesn't necessarily have
	 * to be the front door. The structure's facing is set from the WorldGenStructure constructor.
	 * 
	 * Keep your structure's facing in mind when creating the blockArray. For example, with
	 * the default structure facing of EAST, all blocks that I want to be right in front
	 * of the player will be placed in the east-most bounds of my array. Since x is the
	 * east-west axis and east is positive, that means the maximum x value possible will
	 * store the face of my structure, while x=0 will be the back side.
	 * 
	 * The first array [] stores y values, so we're building a structure one flat layer at
	 * a time because I personally feel it's easier to visualize that way.
	 * 
	 * The next two array [][] store x and z values, in any order, but you must maintain
	 * whichever order you choose throughout your structure or you will probably misplace
	 * blocks. If x is first, you will be building your structure as a flat layer, adding
	 * one line of blocks starting at north (z=0) and west (x=0), building towards the south.
	 * The next line will be in the north (z=0) and one block east of the first line.
	 * 
	 * If it's easier for you to visualize adding lines along the east-west axis starting
	 * in the northwest corner, adding blocks toward the east and lines toward the south,
	 * put your z values first and x values second.
	 * 
	 * The final array stores the following variables:
	 * {blockID, metadata, flag, customData}
	 * 
	 * Note that you only need to fill this array up to the point you need, so if your block
	 * doesn't use metadata, you could simply use {blockID} for the array, instead of {blockID, 0, 0, 0}
	 * 
	 * {blockID}
	 * If left blank, the structure generator will skip the coordinate of the missing block.
	 * 
	 * If you want any part of your structure to 'soft spawn', use the negative value of the
	 * block ID. This will prevent your block from spawning in the world if another block
	 * already exists at that location, allowing your structure to more naturally fit in with
	 * the environment. To set an air block that doesn't overwrite pre-existing blocks, use
	 * the value SET_NO_BLOCK instead of a block ID.
	 * 
	 * A quick example: Block.cobblestone.blockID will only spawn a cobblestone block if its
	 * spawn location is air or occupied by a block such as grass that doesn't block movement.
	 * 
	 * Values above 4096 are used to trigger a hook that allows custom manipulation of the block
	 * via the method onCustomBlockAdded in WorldGenStructure. See that class for details.
	 * 
	 * {metadata}
	 * Stores the block's metadata. If metadata determines orientation, see below for details on
	 * setting the value correctly.
	 * 
	 * {flag}
	 * Will automatically be set to 2 to notify the client if you don't set it yourself.
	 * See 'World.setBlock' method for more information on setting your own flag.
	 * 
	 * {customData}
	 * A value passed to the onCustomBlockAdded method. One could use this to subtype a block
	 * ID, such as CUSTOM_CHEST with subtypes VILLAGE_BLACKSMITH, VILLAGE_LIBRARY, etc., to
	 * set the number of random items to generate, to set villager type to spawn... you get
	 * the idea.
	 * 
	 * =====================================================================================
	 * 					GENERATING LARGE STRUCTURES: USING MULTIPLE ARRAYS
	 * =====================================================================================
	 * If you receive the error message "The code for the static initializer is exceeding
	 * the 65535 bytes limit", then you will need to break your structure up into multiple
	 * array 'layers'.
	 * 
	 * A 'layer' is defined here as a set of one or more complete 'y' arrays, such that all
	 * blocks contained in the horizontal plane to be generated are defined. In other words,
	 * you cannot end a 'layer' partway through generating blocks for a given 'y' without
	 * achieving undesirable results.
	 * 
	 * Thus, if your structure is 40x40 blocks wide, its total planar area is 1600 blocks.
	 * An array containing this 40x40 block area to a height of 3 would contain 4800 blocks;
	 * trying to add another 'y' element at this point would exceed the static initializer
	 * limit, and you wouldn't want to add 200 more blocks to fit under the limit as these
	 * blocks do not form a complete 'y' layer.
	 * 
	 * If you wanted to generate a structure using this base that is 20 blocks tall, you'd
	 * need to make 7 'layers' of height 3 each, except for the final layer which would only
	 * have a height of 2.
	 * =====================================================================================
	 * 							IMPORTANT: NOTES ON SETTING METADATA
	 * =====================================================================================
	 * Set metadata value in relation to player facing opposite your structure's facing.
	 * Default structure facing is EAST, so I set all my metadata based on a player looking
	 * WEST, e.g. if I want stairs leading away from the player, I set the metadata to 1.
	 * 
	 * The ROTATION type is in parentheses ( ), followed by metadata explanations for all blocks
	 * that use that type. Use this as a guide for setting custom block rotation type.
	 * 
	 * (ANVIL) Anvils:
	 * 0 sets the anvil's length along the north-south axis, 1 along east-west
	 * Add 4 for slightly damaged, add 8 for very damaged. DON'T add 12!!!
	 * 
	 * (DOOR) Doors:
	 * Bottom block should have a value of 0,1,2,3 facing west, north, east, or south
	 * Add 16 for hinges on right side, but this doesn't seem to work correctly - check
	 * the wiki for more details: http://www.minecraftwiki.net/wiki/Data_values#Door
	 * Top block's value should be bottom + 8
	 * 
	 * (GENERIC) Beds, Cocoas, Fence Gates, Pumpkins, Jack'o Lanterns, Tripwire Hooks, End Portal Frames:
	 * 0,1,2,3 facing south, west, north, east. For cocoa, it is placed on the block to that side.
	 * Add 4 for opened (gates), no face (pumpkins & jack'o lanterns), connected (tripwire hooks)
	 * medium size (cocoa) or inserted eye (end portal frame)
	 * Add 8 for large size (cocoa) or to set as head (bed) - be sure to put the head on the correct side
	 * of the base ;)
	 * 
	 * (STAIRS) Stairs:
	 * 0,1,2,3 ascending east, west, south, north, +4 for descending
	 * 
	 * (PISTON_CONTAINER)
	 * Chests, Furnaces, Ladders and Wall Signs:
	 * 2,3,4,5 facing north, south, west, east
	 * (ladders and signs placed on 'facing' side of the block: 2 will place on north side of block to the south)
	 * RE: Chests - if you place two chests adjacent to each other but with different facings,
	 * be prepared for weird things to happen as the code will try to enforce your chosen
	 * facing. Don't say I didn't warn you.
	 * 
	 * Pistons, Piston Extensions, Dispensers, Droppers and Hoppers:
	 * Same as above; 0,1 down/up
	 * 
	 * Activated/Detector/Powered Rails: (yes, they use PISTON_CONTAINER, not RAIL)
	 * Same as Rails for values 0-5. No corner pieces.
	 * Add 8 if powered (should be set automatically, but maybe not)
	 *
	 * (RAIL) Rails:
	 * 0,1 flat track along north-south/east-west axis
	 * 2,3,4,5 track ascending to the east, west, north, south
	 * 6,7,8,9 corner track: NW, NE, SE, SW corner
	 * Corner pieces connect the opposite directions, so 6 (NW corner) connects to the south and east
	 * 
	 * (REPEATER) Redstone Repeater and Comparator:
	 * 0,1,2,3 facing north, east, south, west. Add 4 per tick delay beyond the first.
	 * Example: Repeater facing south with 3 ticks = 2 + 4 + 4 = 10.
	 * 
	 * (SIGNPOST) Sign Posts:
	 * 16 directions, 0 being due south and working clockwise towards south-southeast at 15
	 * Keep in mind that this is the direction in which the writing will show, but the player
	 * will be looking at it from the opposite direction which may at first seem counter-intuitive.
	 * I know I set mine backwards a few times.
	 * See http://www.minecraftwiki.net/wiki/Data_values#Sign_Posts for exact details
	 * 
	 * (TRAPDOOR) Trapdoors:
	 * 0,1,2,3 attached to the wall on the south, north, east or west. For example, if you
	 * are facing west and want the trapdoor to open away from you, attach it to the wall
	 * to the west (3), NOT east.
	 * Add 4 if you want the trapdoor opened, add 8 if you want it attached to the top
	 * half of the block (these are additive, so east wall open on upper half is 2+4+8 = 14)
	 * 
	 * (VINE) Vines:
	 * 1,2,4,8 anchored to the south, west, north or east side of the vine block, NOT
	 * neighboring block (so the position may be opposite what you think)
	 * 
	 * (WALL_MOUNTED)
	 * Buttons and Torches (normal and redstone):
	 * 1,2,3,4 pointing east, west, south, north.
	 * Torches (both) only: 5,6 standing on floor / in ground (what's the difference?)
	 * 
	 * Levers:
	 * As buttons with the following: 5,6 ground lever south or east when off;
	 * 7,0 ceiling lever, south or east when off, +8 for switched on (might want
	 * to set the flag value to 3 if switched on to notify neighboring blocks)
	 * 
	 * (WOOD) Wood:
	 * 0,1,2,3 Oak/Spruce/Birch/Jungle placed vertically
	 * Add 4 will face east/west, add 8 will face north/south, add 12 bark only
	 * 
	 * (SKULL) Skull Blocks:
	 * 1 is on the floor, 2,3,4,5 on a wall and facing south, north, west, east.
	 * Note that these are opposite from what the wiki claims, but it's what I saw in my
	 * tests. For example, while looking WEST at a structure of facing EAST, no rotations
	 * would be applied to metadata. A value of 4, which the wiki claims is facing east,
	 * in fact faces west, AWAY from the player.
	 * Associated tile entity will determine skull type, as well as rotation if on floor.
	 */
	
	/**
	 * Here is our first structure, a showcase NPC Hut. Uses lots of different kinds of
	 * blocks and metadata to demonstrate them in use.
	 */
	public static final int[][][][] blockArrayNPCHut =
		{
			{ // NEW y = 0 that demonstrates a layer of 'soft' spawning cobblestone
				{ // x = 0
					{-Block.cobblestone.blockID}, // z = 0
					{-Block.cobblestone.blockID}, // z = 1
					{-Block.cobblestone.blockID}, // z = 2
					{-Block.cobblestone.blockID}, // z = 3
					{-Block.cobblestone.blockID}, // z = 4
					{-Block.cobblestone.blockID}, // z = 5
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
				{ // x = 5
					{-Block.cobblestone.blockID}, // z = 0
					{-Block.cobblestone.blockID}, // z = 1
					{-Block.cobblestone.blockID}, // z = 2
					{-Block.cobblestone.blockID}, // z = 3
					{-Block.cobblestone.blockID}, // z = 4
					{-Block.cobblestone.blockID}, // z = 5
				}
			},
			{ // y = 0
				{ // x = 0 this is the western-most position in our structure
					// z axis, so these are all the blocks in a north-south line at x = 0
					{Block.wood.blockID,8},	// z = 0, the northern-most position
					{Block.cobblestone.blockID}, // z = 1
					{Block.cobblestone.blockID}, // z = 2
					{Block.cobblestone.blockID}, // z = 3
					{Block.cobblestone.blockID}, // z = 4
					{Block.lever.blockID,11,3} // z = 5, the southern-most position
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
					{0}
				},
				{ // x = 5 z values:
					{0},
					{Block.signPost.blockID,14},
					{Block.railPowered.blockID,11},
					{Block.stairsCobblestone.blockID, 1},
					{0},
					{0}
				}
			},
			{ // y = 1
				{ // x = 0 z values:
					{0},
					{Block.wood.blockID},
					{Block.planks.blockID},
					{Block.planks.blockID},
					{Block.wood.blockID},
					{0}
				},
				{ // x = 1 z values:
					{0},
					{Block.planks.blockID},
					{Block.bed.blockID,10},
					{Block.bed.blockID,2},
					{Block.planks.blockID},
					{0}
				},
				{ // x = 2 z values:
					{0},
					{Block.planks.blockID},
					{0},
					{0},
					{Block.planks.blockID},
					{0}
				},
				{ // x = 3 z values:
					{0},
					{Block.planks.blockID},
					{CUSTOM_CHEST,3,2,1},
					{Block.dispenser.blockID,5},
					{Block.planks.blockID},
					{0}
				},
				{ // x = 4 z values:
					{Block.cocoaPlant.blockID,8},
					{Block.wood.blockID},
					{Block.planks.blockID},
					{Block.doorWood.blockID,2},
					{Block.wood.blockID},
					{0}
				},
				{ // x = 5 z values:
					// Note since these are all empty, you could leave this portion of the array out
					// unless you want to force air to be spawned at these locations
					{Block.fenceGate.blockID,2},{0},{0},{0},{Block.signWall.blockID,5},{0}
				}
			},
			{ // y = 2
				{ // x = 0 z values:
					{0},{Block.wood.blockID},
					{Block.planks.blockID},
					{Block.planks.blockID},
					{Block.wood.blockID},{0}
				},
				{ // x = 1 z values:
					{0},{Block.planks.blockID},
					{0},{0},
					{Block.planks.blockID},{0}
				},
				{ // x = 2 z values:
					{Block.trapdoor.blockID,4},
					{Block.thinGlass.blockID},
					{0},{0},
					{Block.thinGlass.blockID},
					{Block.trapdoor.blockID,5}
				},
				{ // x = 3 z values:
					{0},{Block.planks.blockID},
					{Block.woodenButton.blockID,2},{Block.dispenser.blockID,1},
					{Block.planks.blockID},{0}
				},
				{ // x = 4 z values:
					{Block.vine.blockID,1},
					{Block.wood.blockID},
					{Block.planks.blockID},
					{Block.doorWood.blockID,10},
					{Block.pumpkinLantern.blockID,3},{0}
				},
				{ // x = 5 z values:
					{0},{0},{Block.woodenButton.blockID,1},
					{0},{Block.trapdoor.blockID,7},{0}
				}
			},
			// From here on, I will condense the format further
			{ // y = 3
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
					{Block.ladder.blockID,2},{Block.wood.blockID},{Block.planks.blockID},{Block.planks.blockID},{Block.wood.blockID},{0}
				},
				{ // x = 5 z values:
					{0},{Block.torchWood.blockID,1},{0},{Block.skull.blockID,5},{Block.torchWood.blockID,1},{0}
				}
			},
			{ // y = 4
				{ // x = 0 z values:
					{0},{0},{Block.wood.blockID},{Block.wood.blockID},{0},{0}
				},
				{ // x = 1 z values:
					{0},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{0}
				},
				{ // x = 2 z values:
					{0},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{0}
				},
				{ // x = 3 z values:
					{0},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{0}
				},
				{ // x = 4 z values:
					{0},{0},{Block.wood.blockID},{Block.wood.blockID},{0},{0}
				},
				{ // x = 5 z values:
					{0},{0},{0},{0},{0},{0}
				}
			},
			{ // y = 5
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
	
	/**
	 * For every structure that you want to add, place another
	 * 
	 * public static final int[][][][] blockArrayName
	 * 
	 * here in this file.
	 */
	
	/** Here is an EMPTY template. Copy and paste as many as you need below. */
	public static final int[][][][] blockArrayTemplate =
		{
			{ // y = 0
				{ // x or z = 0
					{} // values for an entire row of blocks in either north/south or east/west direction
				}
			}
		};
}
