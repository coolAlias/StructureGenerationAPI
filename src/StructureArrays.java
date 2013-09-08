/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StructureArrays
{
	/** Some predefined values for custom hooks that I use in the demo structure */
	public static final int CUSTOM_CHEST = 4097, CUSTOM_DISPENSER = 4098, ITEM_FRAME = 4099, PAINTING = 4100, SPAWN_VILLAGER = 4101;
	
	/** Start of specific chests; I'll use negative values so as not to conflict with item types and such */
	public static final int CUSTOM_CHEST_1 = -1;
	
	/**
	This file contains a demo and a template structure to illustrate how to go about
	creating your own. Use this in conjunction with the provided StructureGen mod to
	test out your creation before adding it to your own mod.
	
	=====================================================================================
					HOW TO GENERATE YOUR CUSTOM STRUCTURES
	=====================================================================================
	Here I explain in detail how to generate and manipulate the position for your custom
	structure. Only steps 1 and 5 are absolutely required, and step 3 is pretty much also
	absolutely required, but you could skip it. Steps 2 and 4 just give you further
	control over exactly where and how the structure is generated.
	
	Step 1: ABSOLUTELY REQUIRED! Add your blockArray to the generator's list
	
	Once you have your blockArray(s) set up correctly (see below), you will need to add
	them to the WorldGenStructure generator's list of arrays to generate.
	
	This is done with the method "addBlockArray(int[][][][])"
	
	If you have more than one layer, they should be added from the bottom up, so first
	the base, then the layer on top of the base, and so on until the final topmost layer
	is added.
	
	Step 2: Set your structure's facing (defaults to EAST if not specifically set)
	
	"setStructureFacing(StructureGeneratorBase.DIRECTION)"
	 where DIRECTION is NORTH, SOUTH, EAST, WEST
	
	Step 3: REQUIRED! Set how much to offset the structure's position when generated
	
	This can be done in one of three ways:
	
	- Automatically so structure always generates in front of player:
				"setDefaultOffset()" [recommended method]
	
	- Automatically, but with custom adjustments relative to default:
				"setDefaultOffset(int x, int y, int z)" [also recommended]
	
	- Manually: [not recommended for most users]
				"setOffset(int offX, int offY, int offZ)"
	
	- Do nothing:
				Your structure will probably generate on top of you. You were warned.
	
	Negative offX values place the structure further away from the player, +offX will move
	toward the player. Thinking about inverting this.
	
	Step 4: Set the player's facing (optional but generally recommended)
	
	"setPlayerFacing(Entity)"
	
	This will ensure the structure always orients itself toward the player; important if
	you put the front door on the structureFacing side.
	
	Step 5: ABSOLUTELY REQUIRED! Generate your structure
	
	The final step is to call the generate method. This is a vanilla method signature used
	by all classes extending WorldGenerator:
	
	"generate(World world, Random random, int x, int y, int z)"
	
	=====================================================================================
					SETTING UP A STRUCTURE'S BLOCK ARRAY
	=====================================================================================
	TIP: You can use MCEdit first and convert the 'generate' methods from that to a block
	array much more easily than building one from scratch. This has the added benefit of
	providing all the correct metadata values for block facing - I highly recommend you
	use MCEdit first and use it as a template to manually convert the array. 
	
	You can choose any direction to be the default facing of your structure. It is the
	side that will always face the player when generated, which doesn't necessarily have
	to be the front door. The structure's facing is set from the WorldGenStructure constructor.
	
	Keep your structure's facing in mind when creating the blockArray. For example, with
	the default structure facing of EAST, all blocks that I want to be right in front
	of the player will be placed in the east-most bounds of my array. Since x is the
	east-west axis and east is positive, that means the maximum x value possible will
	store the face of my structure, while x=0 will be the back side.
	
	The first array [] stores y values, so we're building a structure one flat layer at
	a time because I personally feel it's easier to visualize that way.
	
	The next two array [][] store x and z values, in any order, but you must maintain
	whichever order you choose throughout your structure or you will probably misplace
	blocks. If x is first, you will be building your structure as a flat layer, adding
	one line of blocks starting at north (z=0) and west (x=0), building towards the south.
	The next line will be in the north (z=0) and one block east of the first line.
	
	If it's easier for you to visualize adding lines along the east-west axis starting
	in the northwest corner, adding blocks toward the east and lines toward the south,
	put your z values first and x values second.
	
	The final array stores the following variables:
	{blockID, metadata, flag, customData}
	
	Note that you only need to fill this array up to the point you need, so if your block
	doesn't use metadata, you could simply use {blockID} for the array, instead of {blockID, 0, 0, 0}
	
	{blockID}
	If left blank, the structure generator will skip the coordinate of the missing block.
	
	If you want any part of your structure to 'soft spawn', use the negative value of the
	block ID. This will prevent your block from spawning in the world if another block
	already exists at that location, allowing your structure to more naturally fit in with
	the environment. To set an air block that doesn't overwrite pre-existing blocks, use
	the value SET_NO_BLOCK instead of a block ID.
	
	A quick example: Block.cobblestone.blockID will only spawn a cobblestone block if its
	spawn location is air or occupied by a block such as grass that doesn't block movement.
	
	Values above 4096 are used to trigger a hook that allows custom manipulation of the block
	via the method onCustomBlockAdded in WorldGenStructure. See below for details.
	
	NOTE: If your mod has custom blocks, you can use YourMod.yourBlock.blockID to spawn it
	in the structure. If your custom block is directional, you MUST register it with the
	method "addCustomBlockRotation(int blockID, ROTATION type)", where ROTATION is an
	enumerated type defining vanilla rotation styles. See the method for further details.
	
	{metadata}
	Stores the block's metadata. If metadata determines orientation, see below for details on
	setting the value correctly.
	
	{flag}
	Will automatically be set to 2 to notify the client if you don't set it yourself.
	See 'World.setBlock' method for more information on setting your own flag.
	
	{customData}
	A value passed to the onCustomBlockAdded method. One could use this to subtype a block
	ID, such as CUSTOM_CHEST with subtypes VILLAGE_BLACKSMITH, VILLAGE_LIBRARY, etc., to
	set the number of random items to generate, to set villager type to spawn... you get
	the idea. See below for more information.
	
	!!!IMPORTANT!!!
	The first element [0] of each y / x array MUST contain an array that
	defines the maximum area of your structure, such that blockArray[0] contains the max
	number of x arrays at any given point and blockArray[0][0] contains the max number of
	z arrays at any given point. After this, you can have fewer as needed.
	
	This is because I use the first index to determine the structure's center, saving myself
	the trouble and processing time of iterating through the entire structure array to find
	the max length and width.
	
	As an example, consider a tower whose base is 6x6, but the roof is 8x8. The first array
	stored at blockArray[0] must contain 8 elements (i.e. 8 arrays for the x axis) and the
	first array stored at blockArray[0][0] must also contain 8 elements (i.e 8 arrays for the
	z axis). blockArray[1]-[maxHeight] can all contain as few as 0 elements, but no more than
	8; same goes for blockArray[n][1]-[n][maxWidth].
	
	=====================================================================================
				GENERATING LARGE STRUCTURES: USING MULTIPLE ARRAYS
	=====================================================================================
	If you receive the error message "The code for the static initializer is exceeding
	the 65535 bytes limit", then you will need to break your structure up into multiple
	array 'layers'.
	
	A 'layer' is defined here as a set of one or more complete 'y' arrays, such that all
	blocks contained in the horizontal plane to be generated are defined. In other words,
	you cannot end a 'layer' partway through generating blocks for a given 'y' without
	achieving undesirable results.
	
	Thus, if your structure is 25x40 blocks wide, its total planar area is 1000 blocks.
	An array containing this 40x40 block area to a height of 3 would contain 3000 blocks;
	trying to add another 'y' element at this point would exceed the static initializer
	limit, and you wouldn't want to add only a partial 'y' layer as the next layer up
	wouldn't finish generating at the same vertical coordinate.
	
	If you wanted to generate a structure using this base that is 20 blocks tall, you'd
	need to make 7 'layers' of height 3 each, except for the final layer which would only
	have a height of 2.
	
	TIP: If your structure has many layers that are the same, just create one array for
	it and add it multiple times to the generator. For instance, if you're making a wizard
	tower with 10 identical floor layouts, you only need to create an array for a single
	floor and then addBlockArray(floorWizardTower) 10 times.
	
	=====================================================================================
		 				IMPORTANT: NOTES ON SETTING METADATA
			Read this or your directional blocks WILL face the wrong direction!
	=====================================================================================
	
	!!!VERY IMPORTANT!!! If the block uses metadata to determine direction, you MUST include
	a metadata value in the array, EVEN if the value for that block happens to be 0 (zero).
	
	{Block.cobblestoneStairs.blockID} will NOT get rotated, but
	{Block.cobblestoneStairs.blockID,0} will.
	
	That goes for all blocks that have direction, not just stairs.
	
	Set metadata value in relation to player facing opposite your structure's facing.
	Default structure facing is EAST, so I set all my metadata based on a player looking
	WEST, e.g. if I want stairs leading away from the player, I set the metadata to 1.
	
	(ROTATION) type is in parentheses, followed by metadata explanations for all blocks
	that use that type. Use this as a guide for setting custom block rotation type.
	
	[POST] Indicates that these block types are set post-generation, so can be used for custom
	events you want to do after the structure has finished generating. Currently only the
	type WALL_MOUNTED uses this.
	
	TIP: It's worth stating one more time: using MCEdit will vastly reduce the headache of
	trying to figure out metadata information for directional blocks. Please use it first,
	then it's a simple (but still tedious) task to manually convert to an array format from
	the code generated by MCEdit. The code it generates WILL give you the correct metadata
	value based on how you placed the block in the structure, whereas calculating it yourself
	tends to involve many backwards blocks.
	
	A NOTE ABOUT REDSTONE: Redstone wires will set their direction automatically. Set your
	redstone torches, repeaters, etc. to the correct on/off state and direction. When you
	generate the structure, it will take a moment for the redstone device to update, at which
	point it should work as expected. If you try to use it before it updates, the redstone
	wire will not have power; manually setting redstone wire's power level can lead to strange
	effects, so is not generally recommended.
	
	===============================
	ROTATION TYPES AND DESCRIPTIONS
	===============================
	
	(ANVIL) Anvils:
	0 sets the anvil's length along the north-south axis, 1 along east-west
	Add 4 for slightly damaged, add 8 for very damaged. DON'T add 12!!!
	
	(DOOR) Doors:
	Bottom block should have a value of 0,1,2,3 facing west, north, east, or south
	Add 16 for hinges on right side, but this doesn't seem to work correctly - check
	the wiki for more details: http://www.minecraftwiki.net/wiki/Data_values#Door
	Top block's value should be bottom + 8
	
	(GENERIC) Beds, Cocoas, Fence Gates, Pumpkins, Jack'o Lanterns, Tripwire Hooks, End Portal Frames:
	0,1,2,3 facing south, west, north, east. For cocoa, it is placed on the block to that side.
	Add 4 for opened (gates), no face (pumpkins & jack'o lanterns), connected (tripwire hooks)
	medium size (cocoa) or inserted eye (end portal frame)
	Add 8 for large size (cocoa) or to set as head (bed) - be sure to put the head on the correct side
	of the base ;)
	
	(STAIRS) Stairs:
	0,1,2,3 ascending east, west, south, north, +4 for descending
	
	(PISTON_CONTAINER)
	Chests, Furnaces, Ladders and Wall Signs:
	2,3,4,5 facing north, south, west, east
	(ladders and signs placed on 'facing' side of the block: 2 will place on north side of block to the south)
	RE: Chests - if you place two chests adjacent to each other but with different facings,
	be prepared for weird things to happen as the code will try to enforce your chosen
	facing. Don't say I didn't warn you.
	
	Pistons, Piston Extensions, Dispensers, Droppers and Hoppers:
	Same as above; 0,1 down/up
	
	Activated/Detector/Powered Rails: (yes, they use PISTON_CONTAINER, not RAIL)
	Same as Rails for values 0-5. No corner pieces.
	Add 8 if powered (should be set automatically, but maybe not)
	
	(RAIL) Rails:
	0,1 flat track along north-south/east-west axis
	2,3,4,5 track ascending to the east, west, north, south
	6,7,8,9 corner track: NW, NE, SE, SW corner
	Corner pieces connect the opposite directions, so 6 (NW corner) connects to the south and east
	
	(REPEATER) Redstone Repeater and Comparator:
	0,1,2,3 facing north, east, south, west. Add 4 per tick delay beyond the first.
	Example: Repeater facing south with 3 ticks = 2 + 4 + 4 = 10.
	
	(SIGNPOST) Sign Posts:
	16 directions, 0 being due south and working clockwise towards south-southeast at 15
	Keep in mind that this is the direction in which the writing will show, but the player
	will be looking at it from the opposite direction which may at first seem counter-intuitive.
	I know I set mine backwards a few times.
	See http://www.minecraftwiki.net/wiki/Data_values#Sign_Posts for exact details
	
	(TRAPDOOR)
	Trapdoors:
	0,1,2,3 attached to the wall on the south, north, east or west. For example, if you
	are facing west and want the trapdoor to open away from you, attach it to the wall
	to the west (3), NOT east.
	Add 4 if you want the trapdoor opened, add 8 if you want it attached to the top
	half of the block (these are additive, so east wall open on upper half is 2+4+8 = 14)
	
	Note that placing active redstone signals near trapdoors will cause them to detach IF
	in a location not otherwise allowed (e.g. attached to glass panes instead of blocks)
	
	(VINE) Vines:
	1,2,4,8 anchored to the south, west, north or east side of the vine block, NOT
	neighboring block (so the position may be opposite what you think)
	
	(WALL_MOUNTED) [POST]
	Buttons and Torches (normal and redstone):
	1,2,3,4 pointing east, west, south, north.
	Torches (both) only: 5,6 standing on floor / in ground (what's the difference?)
	
	Levers:
	As buttons with the following: 5,6 ground lever south or east when off;
	7,0 ceiling lever, south or east when off, +8 for switched on (might want
	to set the flag value to 3 if switched on to notify neighboring blocks)
	
	(WOOD) Wood:
	0,1,2,3 Oak/Spruce/Birch/Jungle placed vertically
	Add 4 will face east/west, add 8 will face north/south, add 12 bark only
	
	(SKULL) Skull Blocks:
	1 is on the floor, 2,3,4,5 on a wall and facing south, north, west, east.
	Note that these are opposite from what the wiki claims, but it's what I saw in my
	tests. For example, while looking WEST at a structure of facing EAST, no rotations
	would be applied to metadata. A value of 4, which the wiki claims is facing east,
	in fact faces west, AWAY from the player.
	Associated tile entity will determine skull type, as well as rotation if on floor.
	
	=====================================================================================
						HOW TO USE CUSTOM HOOK METHOD:
	"onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData)"
	=====================================================================================
	Step 1: Choose a block ID
	Custom block hooks require block ids greater than 4096. If you want to your block to
	soft-spawn, you can also use the negative value of your defined block id.
	
	This value is what will be used by you to determine how to handle the case in the
	onCustomBlockAdded method. It is the parameter 'fakeID'.
	
	Best practice is to define it as "public static final int CUSTOM_BLOCK_NAME = value"
	
	Step 2: Set the real block id
	Since values > 4096 are not real blocks, you must use the special method
	
	"getRealBlockID(int fakeID, int customData)"
	
	to define which block will be set in the world when the generator encounters your custom
	value. The parameter customData could also allow you to subtype a single fakeID.
	
	For example, if you wanted to generate random holes in your structure, you could use a
	single id RANDOM_HOLE, each subtype of which would be a different block id in the structure.
	It would then be simple to randomly remove blocks of this type from within the case
	'RANDOM_HOLE' in onCustomBlockAdded, even if the blocks were in fact different kinds.
	
	If you forget this step, no block will be set and the hook will not trigger.
	
	Step 3: Define your custom case in 'onCustomBlockAdded'
	This is where you handle the custom id, at which point you can do pretty much anything.
	
	See WorldGenStructure's demo implementation of this method for concrete examples.
	
	SOME USEFUL METHODS
	The following are methods designed to make handling onCustomBlockAdded cases easier:
	
	1. addItemToTileInventory(World world, ItemStack itemstack, int x, int y, int z)
	
		Use this method to conveniently add items to any TileEntity that has an inventory
		(i.e. implements either IInventory or ISidedInventory).
		
		Items are added to the first slot available and the method returns false if the
		stack was not able to be added entirely or if there was an error.
	
	2.1 spawnEntityInStructure(World world, Entity entity, int x, int y, int z)
	
		Spawns the passed in entity within the structure such that it doesn't spawn inside of
		walls. If no valid location was found, the entity will still spawn but the method will
		return false.
		
	2.2. setEntityInStructure(World world, Entity entity, int x, int y, int z)
	
		 Sets an entity's location so that it doesn't spawn inside of walls, but doesn't spawn
		 the entity. Automatically removes placeholder block at coordinates x/y/z.
		 Returns false if no suitable location found so user can decide whether to spawn or not.
	
	3. setHangingEntity(World world, ItemStack hanging, int x, int y, int z)
	
		Places a hanging entity in the world based on the arguments provided; orientation
		will be determined automatically based on the dummy blocks data, so a WALL_MOUNTED
		block id (such as Block.torchWood.blockID) must be returned from getRealBlockID().
		
		This method returns the direction in which the entity faces for use with the following.
	
	4. setItemFrameStack(World world, ItemStack itemstack, int x, int y, int z, int direction,
		int itemRotation)
	
		Finds the correct ItemFrame in the world for the coordinates and direction given and
		places the itemstack inside with the rotation provided, or default if no itemRotation
		value is given.
	
	=====================================================================================
				CONGRATULATIONS! YOU'VE REACHED THE END!
	=====================================================================================
	*/
	
	/**
	 * For every structure that you want to add, place another
	 * 
	 * public static final int[][][][] blockArrayName
	 * 
	 * here in this file. Be warned that there is a limit to the total size of static
	 * initializers that can be included in a single file; exceeding this limit will
	 * result in a compile time error. Simply put your arrays in separate files.
	 */
	
	/**
	 * Here is an EMPTY template. Copy and paste as needed. See below for samples.
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
				// {blockID, metadata, flag, customData}
			}
		}
	};
	*/
	public static final int[][][][] blockArrayTemplate =
	{
		{ // y
			{ // x
				{} // z
			}
		}
	};
	/*
	public static final int[][][][] blockArraySpawnTest =
		{
			{ // y = 0
				{ // x = 0
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 1
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 2
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 3
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				}
			},
			{ // y = 1
				{ // x = 0
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 1
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 2
					{Block.cobblestone.blockID},{SPAWN_VILLAGER},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 3
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				}
			},
			{ // y = 2
				{ // x = 0
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 1
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 2
					{Block.cobblestone.blockID},{0},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				},
				{ // x = 3
					{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID},{Block.cobblestone.blockID}
				}
			}
		};
	*/
	/**
	 * Here is my demo structure, a showcase NPC Hut. Dimensions: 7x6 base, 8 blocks high.
	 * Uses lots of different kinds of blocks and metadata to demonstrate them in use.
	 */
	public static final int[][][][] blockArrayNPCHut =
	{
		{ // y = 0 demonstrating a layer of 'soft' spawning cobblestone
			{ // x = 0 (since this structure faces EAST, this is the back side)
				// below is the z axis; blocks run from north to south for our default facing
				{-Block.cobblestone.blockID}, // z = 0 (this is the NORTH side)
				{-Block.cobblestone.blockID}, // z = 1
				{-Block.cobblestone.blockID}, // z = 2
				{-Block.cobblestone.blockID}, // z = 3
				{-Block.cobblestone.blockID}, // z = 4
				{-Block.cobblestone.blockID}, // z = 5 (this is the SOUTH side)
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
				{Block.lever.blockID,11,3} // z = 5
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
				{Block.signPost.blockID,14},
				{Block.railPowered.blockID,11},
				{Block.stairsCobblestone.blockID, 1}, // ascending to west
				{SPAWN_VILLAGER,1,0,2},
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
				{PAINTING,2}, // faces west, but because it has no block to attach to, it doesn't get placed
				{Block.wood.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.cloth.blockID,1,14}, // cloth is odd in that the color value is stored in the flag
				{PAINTING,3} // facing south (since default structure faces EAST, this is the left-hand side)
			},
			{ // x = 1 z values:
				{PAINTING,4}, // facing north (since default structure faces EAST, this is the right-hand side)
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
				{CUSTOM_DISPENSER,5,2,Item.arrow.itemID}, // Using custom data to define the item id
				{Block.planks.blockID},
				{0}
			},
			{ // x = 3 z values:
				{0},
				{Block.planks.blockID},
				{CUSTOM_CHEST,3,2,4}, // a custom chest, facing south, with custom data of 4
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
				{0},{PAINTING,1},{0},{0},{Block.signWall.blockID,5},{0}
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
				{0},{Block.torchWood.blockID,1},{0},{Block.skull.blockID,5},{Block.torchWood.blockID,1},{0}
			}
		},
		{ // y = 5
			{ // x = 0 z values:
				{0},{ITEM_FRAME,4,0,Item.diamond.itemID},{Block.wood.blockID},{Block.wood.blockID},{ITEM_FRAME,3,0,Item.diamond.itemID},{0}
			},
			{ // x = 1 z values:
				{ITEM_FRAME,4,0,Item.emerald.itemID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{ITEM_FRAME,3,0,Item.emerald.itemID}
			},
			{ // x = 2 z values:
				{ITEM_FRAME,4,0,Item.diamond.itemID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{ITEM_FRAME,3,0,Item.diamond.itemID}
			},
			{ // x = 3 z values:
				{ITEM_FRAME,4,0,Item.emerald.itemID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{Block.wood.blockID},{ITEM_FRAME,3,0,Item.emerald.itemID}
			},
			{ // x = 4 z values:
				{0},{ITEM_FRAME,4,0,Item.diamond.itemID},{Block.wood.blockID},{Block.wood.blockID},{ITEM_FRAME,3,0,Item.diamond.itemID},{0}
			},
			{ // x = 5 z values:
				{0},{0},{ITEM_FRAME,1,0,Item.pickaxeDiamond.itemID},{ITEM_FRAME,1,0,Item.swordDiamond.itemID},{0},{0}
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
				{},{},{},{},{},{},{Block.stairsCobblestone.blockID, 1},{Block.stairsCobblestone.blockID, 1},{Block.stairsCobblestone.blockID, 1},{}
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
				{Block.planks.blockID},{0},{0},{0},{Block.planks.blockID},{Block.planks.blockID},{Block.furnaceIdle.blockID, 5, 0},{0},{0},{0}
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
	/*
	public static final int[][][][] blockArrayShop =
	{
		{//y = 0
			{//x = 0
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.wood.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID},
				{Block.grass.blockID}
			},
			{//x = 1
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
			{//x = 2
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.dirt.blockID},
				{Block.dirt.blockID}
			},
			{//x = 3
				{Block.wood.blockID},
				{Block.dirt.blockID},
				{Block.wood.blockID, 1},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.wood.blockID, 1},
				{Block.dirt.blockID},
				{Block.wood.blockID}
			},
			{//x = 4
				{Block.wood.blockID},
				{Block.dirt.blockID},
				{Block.wood.blockID, 1},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.wood.blockID, 1},
				{Block.cobblestone.blockID},
				{Block.wood.blockID}
			},
			{//x = 5
				{Block.wood.blockID},
				{Block.dirt.blockID},
				{Block.wood.blockID, 1},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.planks.blockID},
				{Block.wood.blockID, 1},
				{Block.dirt.blockID},
				{Block.wood.blockID}
			},
			{//x = 6
				{Block.dirt.blockID},
				{Block.dirt.blockID},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.dirt.blockID},
				{Block.dirt.blockID}
			},
			{//x = 7
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
			{//x = 8
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
		{//y = 1
			{//x = 0
				{0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{0}
			},
			{//x = 1
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.doorWood.blockID, 2},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stairsWoodSpruce.blockID,0}
			},
			{//x = 2
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
			{//x = 3
				{0},
				{Block.wood.blockID, 1},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.wood.blockID, 1},
				{0}
			},
			{//x = 4
				{0},
				{Block.cobblestone.blockID},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.cobblestone.blockID},
				{0}
			},
			{//x = 5
				{0},
				{Block.wood.blockID, 1},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.wood.blockID, 1},
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
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stoneBrick.blockID},
				{Block.stairsWoodSpruce.blockID, 1}
			},
			{//x = 8
				{0},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{0}
			}
		},
		{//y = 2
			{//x = 0
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{//x = 1
				{0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.fence.blockID},
				{0},
				{Block.fence.blockID},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.doorWood.blockID, 10},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{0}
			},
			{//x = 2
				{Block.stairsWoodSpruce.blockID,0},
				{Block.wood.blockID, 1},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.wood.blockID, 1},
				{Block.stairsWoodSpruce.blockID,0}
			},
			{//x = 3
				{Block.wood.blockID},
				{Block.torchWood.blockID, 3},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.torchWood.blockID, 4},
				{Block.wood.blockID}
			},
			{//x = 4
				{0},
				{Block.wood.blockID, 1},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.wood.blockID, 1},
				{0}
			},
			{//x = 5
				{Block.wood.blockID},
				{Block.torchWood.blockID, 3},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.torchWood.blockID, 4},
				{Block.wood.blockID}
			},
			{//x = 6
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.wood.blockID, 1},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.wood.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
			},
			{//x = 7
				{0},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
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
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stoneSingleSlab.blockID, 3},
				{0},
				{0},
				{Block.stoneSingleSlab.blockID, 3},
				{0},
				{0},
				{0}
			},
			{//x = 2
				{0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},
				{Block.cobblestone.blockID},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsCobblestone.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{0}
			},
			{//x = 3
				{Block.stairsWoodSpruce.blockID,0},
				{Block.wood.blockID, 1},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.wood.blockID, 1},
				{Block.stairsWoodSpruce.blockID,0}
			},
			{//x = 4
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
			{//x = 5
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.wood.blockID, 1},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{0},
				{Block.wood.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
			},
			{//x = 6
				{0},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsCobblestone.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsCobblestone.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{0}
			},
			{//x = 7
				{0},
				{0},
				{0},
				{Block.stoneSingleSlab.blockID, 3},
				{0},
				{0},
				{0},
				{Block.stoneSingleSlab.blockID, 3},
				{0},
				{0},
				{0}
			},
			{//x = 8
				{0},
				{0},
				{0},
				{Block.stoneSingleSlab.blockID, 3},
				{0},
				{0},
				{0},
				{Block.stoneSingleSlab.blockID, 3},
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
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsCobblestone.blockID,0},
				{Block.stairsCobblestone.blockID,0},
				{Block.stairsCobblestone.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsCobblestone.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{Block.stairsWoodSpruce.blockID,0},
				{0}
			},
			{//x = 4
				{Block.stairsCobblestone.blockID, 2},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.wood.blockID, 1},
				{Block.stairsCobblestone.blockID, 3}
			},
			{//x = 5
				{0},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsCobblestone.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsCobblestone.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
				{Block.stairsWoodSpruce.blockID, 1},
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
				{Block.stairsCobblestone.blockID, 2},
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stoneSingleSlab.blockID, 3},
				{Block.stairsCobblestone.blockID, 3}
			}
		}
	};
	*/
	public static final int[][][][] blockArrayRedstone =
		{
			{ // y = 0
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
			{ // y = 1
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
			{ // y = 2
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
			{ // y = 3
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
			{ // y = 4
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
					{0},{0},{CUSTOM_CHEST,2,0,CUSTOM_CHEST_1},{Block.cobblestone.blockID}
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
			{ // y = 5
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
			{ // y = 6
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
}
