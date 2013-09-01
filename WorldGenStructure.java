package coolalias.structuregen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWireSource;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockWood;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenStructure extends WorldGenerator
{
	/** Used to distinguish blocks with metadata value of 0 versus no metadata at all. */
	public static final int NO_METADATA = 10000;
	
	/** Use this value to skip setting a block at an x,y,z coordinate for whatever reason. */
	public static final int SET_NO_BLOCK = -1;
	
	/** The directional values associated with player facing: */
	public static final int SOUTH = 0, WEST = 1, NORTH = 2, EAST = 3;
	
	/** Stores the direction this structure faces. Default is EAST.*/
	private int structureFacing = EAST, manualRotations = 0;
	
	/** Stores the player's facing for structure generation */
	private int facing;
	
	/** Stores amount to offset structure's location in the world, if any. */
	private int offsetX = 0, offsetY = 0, offsetZ = 0;
	
	/**
	 * This stores all the blocks for a structure in an array.
	 * 
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
	 * =====================================================================================
	 * 							IMPORTANT: NOTES ON SETTING METADATA
	 * =====================================================================================
	 * Set metadata value in relation to player facing opposite your structure's facing.
	 * Default structure facing is EAST, so I set all my metadata based on a player looking
	 * WEST, e.g. if I want stairs leading away from the player, I set the metadata to 1.
	 * 
	 * Anvils:
	 * 0 sets the anvil's length along the north-south axis, 1 along east-west
	 * Add 4 for slightly damaged, add 8 for very damaged. DON'T add 12!!!
	 * 
	 * Beds:
	 * 0,1,2,3 head is pointing south, west, north, east.
	 * Add 8 if the block is the head, and be sure to put it on the correct side of the base ;)
	 * 
	 * Doors:
	 * Bottom block stores facing: 0,1,2,3 for west, north, east, south
	 * (so player looking east, south, west, north)
	 * Add 4 to bottom block to set it sideways, at which point the side it is rendered
	 * on is determined by the top block:
	 * Top door will always have a value of 8 (hinge left) or 9 (hinge right).
	 * 
	 * Stairs:
	 * 0,1,2,3 ascending east, west, south, north, +4 for descending
	 * 
	 * Wood:
	 * 0,1,2,3 Oak/Spruce/Birch/Jungle placed vertically
	 * Add 4 will face east/west, add 8 will face north/south, add 12 bark only
	 * 
	 * Ladders, furnaces, chests, wall signs:
	 * 2,3,4,5 facing north, south, west, east
	 * (ladders and signs placed on 'facing' side of the block: 2 will place on north side of block to the south)
	 * RE: Chests - if you place two chests adjacent to each other but with different facings,
	 * be prepared for weird things to happen as the code will try to enforce your chosen
	 * facing. Don't say I didn't warn you.
	 * 
	 * Sign Posts:
	 * 16 directions, 0 being due south and working clockwise towards south-southeast at 15
	 * Keep in mind that this is the direction in which the writing will show, but the player
	 * will be looking at it from the opposite direction which may at first seem counter-intuitive.
	 * I know I set mine backwards a few times.
	 * See http://www.minecraftwiki.net/wiki/Data_values#Sign_Posts for exact details
	 * 
	 * Buttons and Torches (normal and redstone):
	 * 1,2,3,4 pointing east, west, south, north.
	 * Torches (both) only: 5,6 standing on floor / in ground (what's the difference?)
	 * 
	 * Levers:
	 * As buttons with the following: 5,6 ground lever south or east when off;
	 * 7,0 ceiling lever, south or east when off, +8 for switched on (might want
	 * to set the flag value to 3 if switched on to notify neighboring blocks)
	 * 
	 * Redstone Repeater:
	 * 0,1,2,3 facing north, east, south, west. Add 4 per tick delay beyond the first.
	 * Example: Repeater facing south with 3 ticks = 2 + 4 + 4 = 10.
	 * 
	 * Dispensers, Droppers and Hoppers:
	 * As ladder with the following: 0, 1 facing down or up (for Hopper, 1 is unattached
	 * to container). Probably unnecessary, but add (1 >> 3)? for powered and updated (i.e. DON'T set flag to 3).
	 * 
	 * Pistons and Piston Extensions:
	 * 0,1 down/up; 2,3,4,5 piston head is pointing north, south, west, east
	 * Add 8 if the piston is pushed out (for base) or sticky (for extension)
	 * Note that if you're not providing power to the piston base, it will retract
	 * automatically and vice-versa if you are providing power, so it's not really
	 * necessary to add the piston extension separately
	 * 
	 * Trapdoors:
	 * 0,1,2,3 attached to the wall on the south, north, east or west. For example, if you
	 * are facing west and want the trapdoor to open away from you, attach it to the wall
	 * to the west (3), NOT east.
	 * Add 4 if you want the trapdoor opened, add 8 if you want it attached to the top
	 * half of the block (these are additive, so east wall open on upper half is 2+4+8 = 14)
	 * 
	 * Fence Gates, Pumpkins, Jack'o Lanterns, Tripwire Hooks, End Portal Frames:
	 * 0,1,2,3 facing south, west, north, east.
	 * Add 4 for opened (gates), no face (pumpkins & jack'o lanterns), connected (tripwire hooks)
	 * or inserted eye (end portal frame)
	 * 
	 * Rails:
	 * 0,1 flat track along north-south/east-west axis
	 * 2,3,4,5 track ascending to the east, west, north, south
	 * 6,7,8,9 corner track: NW, NE, SE, SW corner
	 * Corner pieces connect the opposite directions, so 6 (NW corner) connects to the south and east
	 * 
	 * Powered Rails:
	 * Same as Rails for values 0-5. No corner pieces.
	 * Add 8 if powered (should be set automatically, but maybe not)
	 * 
	 * Cocoas:
	 * 0,1,2,3 cocoa is on north, east, south, west side of adjacent block.
	 * Add 4 for medium size, add 8 for large size.
	 * 
	 * Vines:
	 * 1,2,4,8 anchored to the south, west, north or east side of the vine block, NOT
	 * neighboring block (so the position may be opposite what you think)
	 * 
	 * Skull Blocks:
	 * 1 is on the floor, 2,3,4,5 on a wall and facing south, north, west, east.
	 * Note that these are opposite from what the wiki claims, but it's what I saw in my
	 * tests. For example, while looking WEST at a structure of facing EAST, no rotations
	 * would be applied to metadata. A value of 4, which the wiki claims is facing east,
	 * in fact faces west, AWAY from the player.
	 * Associated tile entity will determine skull type, as well as rotation if on floor.
	 */
	private int[][][][] blockArray;

	/**
	 * Constructs the structure generator with player's facing
	 * Don't use, as values SOUTH and WEST might be confused with boolean values,
	 * calling the super constructor instead
	 */
	/*
	public WorldGenStructure(int facing) {
		this.facing = facing;
	}
	*/
	
	/**
	 * Constructs the generator with the player's facing and blockArray for the structure
	 */
	public WorldGenStructure(int facing, int[][][][] blocks) {
		this.facing = facing;
		this.blockArray = blocks;
		this.structureFacing = EAST;
		this.manualRotations = 0;
	}

	/**
	 * Constructs the generator with the player's facing, the structure's front facing
	 * and blockArray for the structure
	 */
	public WorldGenStructure(int facing, int[][][][] blocks, int structureFacing) {
		this.facing = facing;
		this.structureFacing = structureFacing;
		this.blockArray = blocks;
		this.manualRotations = 0;
	}
	
	/**
	 * Constructor for one line setting of all variables necessary to generate structure
	 * @param facing Direction player is facing
	 * @param blocks The structure's blockArray
	 * @param structureFacing The direction in which the structure faces
	 * @param offX Amount to offset the structure's location along the east-west axis
	 * @param offY Amount to offset the structure's location along the vertical axis
	 * @param offZ Amount to offset the structure's location along the north-south axis
	 */
	public WorldGenStructure(int facing, int[][][][] blocks, int structureFacing, int offX, int offY, int offZ) {
		this.facing = facing;
		this.blockArray = blocks;
		this.structureFacing = structureFacing;
		this.manualRotations = 0;
		this.offsetX = offX;
		this.offsetY = offY;
		this.offsetZ = offZ;
	}

	/**
	 * Super constructor. Will probably never use.
	 */
	public WorldGenStructure(boolean par1) {
		super(par1);
		this.structureFacing = EAST;
		this.manualRotations = 0;
		this.offsetX = 0;
		this.offsetY = 0;
		this.offsetZ = 0;
	}
	
	/**
	 * Sets the direction in which the player is facing. The structure will be generated
	 * opposite of player view (so player will be looking at front when finished)
	 */
	public final void setFacing(int facing) {
		this.facing = facing;
	}
	
	/**
	 * Sets the block array to generate
	 */
	public final void setBlockArray(int blocks[][][][]) {
		this.blockArray = blocks;
	}
	
	/**
	 * Sets the amount by which to offset the structure's generated location in the world.
	 * -x values will move the structure away from the player, +x towards the player
	 * +/-z will move the structure to the left/right of the player
	 * @param offX Amount to offset the structure's location along the east-west axis
	 * @param offY Amount to offset the structure's location along the vertical axis
	 * @param offZ Amount to offset the structure's location along the north-south axis
	 */
	public final void setOffset(int offX, int offY, int offZ) {
		this.offsetX = offX;
		this.offsetY = offY;
		this.offsetZ = offZ;
	}
	
	/**
	 * This will rotate the structure's default facing 90 degrees clockwise.
	 * Note that a different side will now face the player when generated.
	 */
	public final void rotateStructureFacing() {
		this.structureFacing = (this.structureFacing == EAST ? SOUTH : this.structureFacing + 1);
		this.manualRotations = this.manualRotations == 3 ? 0 : this.manualRotations + 1;
		System.out.println("[GEN STRUCTURE] Manual rotations: " + this.manualRotations);
	}
	
	/**
	 * Returns a string describing current facing of structure
	 */
	public final String currentStructureFacing()
	{
		return (this.structureFacing == EAST ? "East" : this.structureFacing == WEST ? "West" : this.structureFacing == NORTH ? "North" : "South");
	}

	/**
	 * IMPORTANT!!! Before calling WorldGenStructure generate method, you MUST set the player
	 * facing with setFacing(int) and setBlockArray(int[][][][]) either with the constructor
	 * or individual methods. If you want the structure's location offset by an amount, be
	 * sure to setOffset as well.
	 */
	@Override
	public boolean generate(World world, Random random, int posX, int posY, int posZ)
	{
		int centerX = blockArray[0].length / 2, centerZ = blockArray[0][0].length / 2;
		// The number of 90 degree rotations to perform based on default structure facing and current player facing
		int rotation = (((this.structureFacing == NORTH || this.structureFacing == SOUTH) ? this.structureFacing + 2 : this.structureFacing) + this.facing) % 4;

		for (int y = 0; y < blockArray.length; ++y)
		{
			for (int x = 0; x < blockArray[0].length; ++x)
			{
				for (int z = 0; z < blockArray[0][0].length; ++z)
				{
					// Threw an NPE once, so...
					if (blockArray[y][x][z].length == 0) continue;
					// If user decides not to set this block, so be it...
					if (blockArray[y][x][z][0] == SET_NO_BLOCK) continue;
					
					int meta = (blockArray[y][x][z].length > 1 ? blockArray[y][x][z][1] : NO_METADATA);
					int flag = (blockArray[y][x][z].length > 2 ? blockArray[y][x][z][2] : 0);
					int rotX = posX, rotZ = posZ;

					switch(rotation) {
					case 0: // Player is looking at the front of the default structure
						rotX += x - centerX + offsetX;
						rotZ += z - centerZ + offsetZ;
						break;
					case 1: // Rotate structure 90 degrees clockwise
						rotX += -(z - centerZ + offsetZ);
						rotZ += x - centerX + offsetX;
						break;
					case 2: // Rotate structure 180 degrees
						rotX += -(x - centerX + offsetX);
						rotZ += -(z - centerZ + offsetZ);
						break;
					case 3: // Rotate structure 270 degrees clockwise
						rotX += z - centerZ + offsetZ;
						rotZ += -(x - centerX + offsetX);
						break;
					default:
						System.out.println("[GEN STRUCTURE] Error computing number of rotations.");
						break;
					}
					
					if (meta != NO_METADATA)
					{
						meta = getMetadata(blockArray[y][x][z][0], meta, facing);
						if (flag == 0) flag = 2;
					}

					world.setBlock(rotX, posY + y + offsetY, rotZ, blockArray[y][x][z][0], meta, flag);

					setMetadata(world, rotX, posY + y + offsetY, rotZ, meta, facing);
				}
			}
		}

		return true;
	}
	
	/**
	 * Sets metadata value for blocks after they've been placed in the world
	 * Used for blocks such as chests that rely on other blocks in the world
	 * to determine metadata or furnace whose meta is auto-set in Block's onAdded method
	 */
	private final void setMetadata(World world, int x, int y, int z, int origMeta, int facing)
	{
		int meta = world.getBlockMetadata(x, y, z), id = world.getBlockId(x, y, z);
		
		// chests may form large chests - happens automatically onBlockAdded
		// also, chest facing is changed automatically, so re-set it here:
		if (Block.blocksList[id] instanceof BlockChest) {
			world.setBlockMetadataWithNotify(x, y, z, origMeta, 3);
			setChestMetadata(world, x, y, z);
		}
		// dispenser and dropper both have direction set onBlockAdded, need to reset
		if (Block.blocksList[id] instanceof BlockDispenser || Block.blocksList[id] instanceof BlockDropper)
			world.setBlockMetadataWithNotify(x, y, z, origMeta, 3);
		// door needs to set both upper and lower block metadata correctly
		else if (Block.blocksList[id] instanceof BlockDoor)
			setDoorMetadata(world, x, y, z, facing);
		// furnace metadata also gets changed when onAdded method called - set after block placed instead
		else if (Block.blocksList[id] instanceof BlockFurnace)
			world.setBlockMetadataWithNotify(x, y, z, origMeta, 2);
		// rails, specifically corners, need to be fixed after onBlockAdded
		else if (Block.blocksList[id] instanceof BlockRailBase)
			world.setBlockMetadataWithNotify(x, y, z, origMeta, 2);
	}
	
	/**
	 * This method will return the correct metadata value for the block type based on
	 * how it was rotated in the world, IF and ONLY IF you used the correct metadata
	 * value to set the block's default orientation for your structure's default facing.
	 * 
	 * If your structure's front faces EAST by default, for example, and you want a wall
	 * sign out front greeting all your guests, you'd better use '4' as its metadata value
	 * in your blockArray so it faces WEST, the direction from which player's will be
	 * viewing your beautiful abode.
	 * 
	 * Please read the blockArray notes very carefully and test out your structure to make
	 * sure everything is oriented how you thought it was.
	 */
	private final int getMetadata(int id, int origMeta, int facing)
	{
		// No metadata value associated with this block, return
		if (origMeta == NO_METADATA) return 0;
		// The number of 90 degree rotations to perform based on default structure facing and current player facing
		int rotation = (((this.structureFacing == NORTH || this.structureFacing == SOUTH) ? this.structureFacing + 2 : this.structureFacing) + this.facing) % 4;
		
		int meta = origMeta, tickDelay = meta >> 2, bit9 = meta >> 3,
			bitface, bit4, bit8, bit16;
		
		for (int i = 0; i < rotation; ++i)
		{
			// Need to reset each iteration
			bitface = meta % 4; // usually used for block's facing, such as for stairs
			bit4 = meta & 4; // often stores an on/off value; for stairs, determines whether upside-down
			bit8 = meta & 8; // often stores an on/off value, such as for a lever
			bit16 = meta & 16;

			if (Block.blocksList[id] instanceof BlockAnvil)
				meta ^= 1;
			else if (Block.blocksList[id] instanceof BlockBed)
				meta = (bitface == 3 ? 0 : bitface + 1) | bit8;
			// Don't change the top half of the door, so hinge always on same side
			//else if (Block.blocksList[id] instanceof BlockDoor)
				//meta = (bitface == 3 ? 0 : bitface + 1) | bit4 | bit8 | bit16;
			else if (Block.blocksList[id] instanceof BlockButton || ((Block.blocksList[id] instanceof BlockTorch
					|| Block.blocksList[id] instanceof BlockRedstoneTorch) && meta < 5))
				meta = meta == 4 ? 1 : meta == 1 ? 3 : meta == 3 ? 2 : 4;
			else if (Block.blocksList[id] instanceof BlockCocoa)
				meta = (bitface == 3 ? 0 : bitface + 1) | bit4 | bit8;
			else if (Block.blocksList[id] instanceof BlockFenceGate || Block.blocksList[id] instanceof BlockPumpkin
					|| Block.blocksList[id] instanceof BlockEndPortalFrame || Block.blocksList[id] instanceof BlockTripWireSource)
				meta = (bitface == 3 ? 0 : bitface + 1) | bit4;
			else if (Block.blocksList[id] instanceof BlockChest || Block.blocksList[id] instanceof BlockFurnace
					|| Block.blocksList[id] instanceof BlockLadder || id == Block.signWall.blockID)
					meta = meta == 2 ? 5 : meta == 5 ? 3 : meta == 3 ? 4 : 2;
			// Need to differentiate the two BlockSign blocks, signWall and signPost, due to different rotation formula
			else if (id == Block.signPost.blockID)
				meta = meta < 12 ? meta + 4 : meta - 12;
			else if (Block.blocksList[id] instanceof BlockDispenser || Block.blocksList[id] instanceof BlockDropper
					|| Block.blocksList[id] instanceof BlockHopper) {
				if ((meta & 7) < 2) continue; // don't rotate up or down
				meta &= 7; // get facing
				meta = meta == 2 ? 5 : meta == 5 ? 3 : meta == 3 ? 4 : 2;
				meta |= bit9 << 3; // restore power if was powered
			}
			else if (Block.blocksList[id] instanceof BlockPistonBase || Block.blocksList[id] instanceof BlockPistonExtension) {
				meta -= meta > 7 ? 8 : 0;
				if (meta < 2) meta ^= 1;
				else meta = meta == 2 ? 5 : meta == 5 ? 3 : meta == 3 ? 4 : 2;
				meta |= bit8;
			}
			else if (Block.blocksList[id] instanceof BlockRail) {
				if (meta < 2) meta ^= 1;
				else if (meta < 6) meta = meta == 2 ? 5 : meta == 5 ? 3 : meta == 3 ? 4 : 2;
				else meta = meta == 9 ? 6 : meta + 1;
			}
			else if (Block.blocksList[id] instanceof BlockRailPowered) {
				meta -= meta > 7 ? 8 : 0; // get rid of power variable
				if (meta < 2) meta ^= 1;
				else meta = meta == 2 ? 5 : meta == 5 ? 3 : meta == 3 ? 4 : 2;
				meta |= bit8; // re-add power
			}
			else if (Block.blocksList[id] instanceof BlockRedstoneRepeater)
				meta = (bitface == 3 ? 0 : bitface + 1) | (tickDelay << 2);
			else if (Block.blocksList[id] instanceof BlockLever) {
				meta -= meta > 7 ? 8 : 0; // remove on/off status for setting direction
				if (meta > 0 && meta < 5) meta = meta == 4 ? 1 : meta == 1 ? 3 : meta == 3 ? 2 : 4;
				else if (meta == 5 || meta == 6) meta = meta == 5 ? 6 : 5;
				else meta = meta == 7 ? 0 : 7;
				meta |= bit8; // re-apply on/off status
			}
			else if (Block.blocksList[id] instanceof BlockStairs)
				meta = (bitface == 0 ? 2 : bitface == 2 ? 1 : bitface == 1 ? 3 : 0) | bit4;
			else if (Block.blocksList[id] instanceof BlockTrapDoor)
				meta = (bitface == 0 ? 3 : bitface == 3 ? 1 : bitface == 1 ? 2 : 0) | bit4 | bit8;
			else if (Block.blocksList[id] instanceof BlockVine)
				meta = meta == 1 ? 2 : meta == 2 ? 4 : meta == 4 ? 8 : 1;
			else if (Block.blocksList[id] instanceof BlockSkull)
				meta = meta == 1 ? 1 : meta == 4 ? 2 : meta == 2 ? 5 : meta == 5 ? 3 : 4;
			else if (Block.blocksList[id] instanceof BlockWood) {
				if (meta > 4 && meta < 12) meta = meta < 8 ? meta + 4 : meta - 4;
			}
		}
		
		return meta;
	}
	
	/**
	 * Updates nearby chests for the recently placed and rotated chest.
	 */
	private final void setChestMetadata(World world, int x, int y, int z)
	{
		int l = world.getBlockId(x, y, z - 1);
        int i1 = world.getBlockId(x, y, z + 1);
        int j1 = world.getBlockId(x - 1, y, z);
        int k1 = world.getBlockId(x + 1, y, z);
        int b0 = world.getBlockMetadata(x, y, z);
        
        if (l != Block.chest.blockID && i1 != Block.chest.blockID && j1 != Block.chest.blockID && k1 != Block.chest.blockID)
        {
        	world.setBlockMetadataWithNotify(x, y, z, b0, 3);
        }
        else
        {
            if ((l == Block.chest.blockID || i1 == Block.chest.blockID) && (b0 == 4 || b0 == 5))
            {
                if (l == Block.chest.blockID)
                {
                	world.setBlockMetadataWithNotify(x, y, z - 1, b0, 3);
                }
                else
                {
                	world.setBlockMetadataWithNotify(x, y, z + 1, b0, 3);
                }

                world.setBlockMetadataWithNotify(x, y, z, b0, 3);
            }

            if ((j1 == Block.chest.blockID || k1 == Block.chest.blockID) && (b0 == 2 || b0 == 3))
            {
                if (j1 == Block.chest.blockID)
                {
                	world.setBlockMetadataWithNotify(x - 1, y, z, b0, 3);
                }
                else
                {
                	world.setBlockMetadataWithNotify(x + 1, y, z, b0, 3);
                }

                world.setBlockMetadataWithNotify(x, y, z, b0, 3);
            }
        }
	}
	
	/**
	 * Sets a placed door's metadata based on rotation from player's facing
	 * For the blockArray structure, the original door's metadata should be as follows:
	 * Bottom block: determines orientation: 0,1,2,3 are hinge left, 16,17,18,19 hinge right
	 * in order of East/South/West/North
	 * Tob block: Value is bottom value + 8
	 * NOTE: After rotation, N/S doors' hinge will be on opposite side from E/W doors. Currently don't know how to fix this.
	 */
	private final void setDoorMetadata(World world, int x, int y, int z, int facing)
	{
		int meta1 = world.getBlockMetadata(x, y, z);
		// if flag is true, it is the top door block
		boolean isTop = (meta1 & 8) != 0;
		// Since we're building from the bottom up, we do this from the top door block only so
		// we are also able to modify the block below
		if (!isTop) return;
		
		int meta2 = world.getBlockMetadata(x, y - 1, z);
		
		// Check if metadata values are mismatched and reset based off top block:
		if (meta2 - 8 != meta1) meta2 = meta1 - 8;
		// get door block's original facing in the structure (from the lower door block):
		int origfacing = (meta2 < 16 ? meta2 : meta2 - 16);
		// adjust facing to match metadata direction values:
		facing = (facing + 1) % 4;
		// Number of 90 degree rotations required:
		int rotations = (Math.abs(origfacing - facing) + this.manualRotations) % 4;
		
		for (int i = 0; i < rotations; ++i)
		{
			meta1 = meta1 == 11 ? 8 : meta1 == 27 ? 24 : ++meta1;
			meta2 = meta2 == 3 ? 0 : meta2 == 19 ? 16 : ++meta2;
		}
		
		world.setBlockMetadataWithNotify(x, y, z, meta1, 2);
		world.setBlockMetadataWithNotify(x, y - 1, z, meta2, 2);
	}
}
