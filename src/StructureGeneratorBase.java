/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class StructureGeneratorBase extends WorldGenerator
{
	/** Use this value to skip setting a block at an x,y,z coordinate for whatever reason. */
	public static final int SET_NO_BLOCK = Integer.MAX_VALUE;
	
	/** The directional values associated with player facing: */
	public static final int SOUTH = 0, WEST = 1, NORTH = 2, EAST = 3;
	
	/** Valid rotation types. Each type is handled like vanilla blocks of this kind. */
	public static enum ROTATION {ANVIL, DOOR, GENERIC, PISTON_CONTAINER, RAIL, REPEATER,
		SIGNPOST, SKULL, STAIRS, TRAPDOOR, VINE, WALL_MOUNTED, WOOD};
	
	/**
	 * Used to distinguish blocks with metadata value of 0 versus no metadata at all. 
	 * Really more of an internal check. Users don't really need this.
	 */
	private static final int NO_METADATA = Integer.MIN_VALUE;
	
	/** Stores the direction this structure faces. Default is EAST.*/
	private int structureFacing = EAST, manualRotations = 0;
	
	/** Stores the player's facing for structure generation */
	private int facing;
	
	/** Stores amount to offset structure's location in the world, if any. */
	private int offsetX = 0, offsetY = 0, offsetZ = 0;
	
	/** When true all blocks will be set to air within the structure's area. */
	private boolean removeStructure = false;
	
	/** A mapping of block ids to rotation type for handling rotation. Allows custom blocks to be added. */
	private static final Map<Integer, ROTATION> blockRotationData = new HashMap<Integer, ROTATION>();
	
	/** Stores the structure's data. See StructureArray.java for information on how create a blockArray. */
	private int[][][][] blockArray;
	
	/**
	 * Constructs the generator with the player's facing and blockArray for the structure
	 */
	public StructureGeneratorBase(int facing, int[][][][] blocks)
	{
		super(false);
		this.facing = facing;
		this.blockArray = blocks;
	}

	/**
	 * Constructs the generator with the player's facing, the structure's front facing
	 * and blockArray for the structure
	 */
	public StructureGeneratorBase(int facing, int[][][][] blocks, int structureFacing)
	{
		super(false);
		this.facing = facing;
		this.structureFacing = structureFacing;
		this.blockArray = blocks;
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
	public StructureGeneratorBase(int facing, int[][][][] blocks, int structureFacing, int offX, int offY, int offZ)
	{
		super(false);
		this.facing = facing;
		this.blockArray = blocks;
		this.structureFacing = structureFacing;
		this.offsetX = offX;
		this.offsetY = offY;
		this.offsetZ = offZ;
	}

	/**
	 * Super constructor. par1 sets whether or not the generator should notify blocks of blocks it
	 * changes. When the world is first generated, this is false, when saplings grow, this is true.
     */
	public StructureGeneratorBase(boolean par1) {
		super(par1);
	}
	
	/**
	 * Sets the direction in which the player is facing. The structure will be generated
	 * opposite of player view (so player will be looking at front when finished)
	 */
	public final void setFacing(int facing) {
		this.facing = facing;
	}
	
	/**
	 * Sets the default direction the structure is facing. This side will always face the player
	 * unless you manually rotate the structure with the rotateStructureFacing() method.
	 */
	public final void setStructureFacing(int facing) {
		this.structureFacing = facing;
	}
	
	/**
	 * Sets the block array to generate
	 */
	public final void setBlockArray(int blocks[][][][]) {
		this.blockArray = blocks;
	}
	
	/**
	 * Returns structure's width along the x axis or 0 if no structure has been set
	 */
	public final int getWidthX() {
		return this.blockArray != null ? this.blockArray[0].length : 0;
	}
	
	/**
	 * Returns structure's width along the z axis or 0 if no structure has been set
	 */
	public final int getWidthZ() {
		return this.blockArray != null ? this.blockArray[0][0].length : 0;
	}
	
	/**
	 * Returns structure's height or 0 if no structure has been set
	 */
	public final int getHeight() {
		return this.blockArray != null ? this.blockArray.length : 0;
	}
	
	/**
	 * Sets the amount by which to offset the structure's generated location in the world.
	 * -x values will move the structure away from the player, +x towards the player
	 * +/-z will move the structure to the left/right of the player
	 * These will auto-adjust based on rotation, so x is always towards or away from player
	 * @param offX Amount to offset the structure's location towards/away from player
	 * @param offY Amount to offset the structure's location along the vertical axis
	 * @param offZ Amount to offset the structure's location left/right of the player
	 */
	public final void setOffset(int offX, int offY, int offZ) {
		this.offsetX = offX;
		this.offsetY = offY;
		this.offsetZ = offZ;
	}
	
	/**
	 * Call this only after setting the blockArray. Set a default offset amount
	 * that will keep the entire structure's boundaries from overlapping with
	 * the position spawned at, so it will never spawn on the player.
	 */
	public final void setDefaultOffset() {
		this.offsetX = -1;
		this.offsetY = 0;
		this.offsetZ = 0;
	}
	
	/**
	 * This will rotate the structure's default facing 90 degrees clockwise.
	 * Note that a different side will now face the player when generated.
	 */
	public final void rotateStructureFacing() {
		this.structureFacing = (this.structureFacing == EAST ? SOUTH : this.structureFacing + 1);
		this.manualRotations = this.manualRotations == 3 ? 0 : this.manualRotations + 1;
	}
	
	/**
	 * Returns a string describing current facing of structure
	 */
	public final String currentStructureFacing()
	{
		return (this.structureFacing == EAST ? "East" : this.structureFacing == WEST ? "West" : this.structureFacing == NORTH ? "North" : "South");
	}
	
	/**
	 * Toggles between generate and remove structure setting.
	 * Returns value for ease of reference.
	 */
	public final boolean toggleRemoveStructure()
	{
		this.removeStructure = !this.removeStructure;
		return this.removeStructure;
	}
	
	/**
	 * Returns true if the generator has enough information to generate a structure
	 */
	public final boolean canGenerate()
	{
		return this.blockArray != null;
	}

	/**
	 * IMPORTANT!!! Before calling WorldGenStructure generate method, you MUST set the player
	 * facing with setFacing(int) and setBlockArray(int[][][][]) either with the constructor
	 * or individual methods. If you want the structure's location offset by an amount, be
	 * sure to setOffset as well.
	 */
	@Override
	public final boolean generate(World world, Random random, int posX, int posY, int posZ)
	{
		// We only want to generate server side and if blockArray has been set
		if (world.isRemote) { return false; }
		if (blockArray == null) { System.out.println("[GEN STRUCTURE][WARNING] No structure array has been set."); return false; }
		
		int centerX = blockArray[0].length / 2, centerZ = blockArray[0][0].length / 2;
		int rotation = (((this.structureFacing == NORTH || this.structureFacing == SOUTH) ? this.structureFacing + 2 : this.structureFacing) + this.facing) % 4;
		
		setOffsetFromRotation();
		
		// need to check total array size here and split it into smaller chunks if too big
		
		for (int y = 0; y < blockArray.length; ++y)
		{
			for (int x = 0; x < blockArray[y].length; ++x)
			{
				for (int z = 0; z < blockArray[y][x].length; ++z)
				{
					/*
					// Threw an NPE once, so...
					if (blockArray[y][x][z].length == 0) {
						if (z < blockArray[y][x].length) {
							System.out.println("[GEN STRUCTURE][WARNING] No data set in blockArray[" + y + "][" + x + "][" + z + "]");
							continue;
						}
						else {
							System.out.println("[GEN STRUCTURE][WARNING] End of array reached with null data.");
							return true;
						}	
					}
					*/
					// If no block data or user decides not to set this block, so be it...
					if (blockArray[y][x][z].length == 0 || blockArray[y][x][z][0] == SET_NO_BLOCK) continue;
					
					int meta = (blockArray[y][x][z].length > 1 ? blockArray[y][x][z][1] : NO_METADATA);
					int flag = (blockArray[y][x][z].length > 2 ? blockArray[y][x][z][2] : 2);
					int customData = (blockArray[y][x][z].length > 3 ? blockArray[y][x][z][3] : 0);
					int rotX = posX, rotZ = posZ, rotY = posY + y + offsetY;
					int fakeID = blockArray[y][x][z][0];
					int realID = (Math.abs(fakeID) > 4096 ? getRealBlockID(fakeID) : fakeID);
					
					if (Math.abs(realID) > 4096) {
						System.out.println("[GEN STRUCTURE][WARNING] Invalid block ID. Initial ID: " + fakeID + ", returned id from getRealID: " + realID);
						continue;
					}

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
					
					if (this.removeStructure) {
						world.setBlockToAir(rotX, rotY, rotZ);
					}
					else
					{
						// Allows 'soft-spawning' blocks to be spawned only in air or on blocks that allow movement, such as air or grass
						if (realID >= 0 || world.isAirBlock(rotX, rotY, rotZ) || 
								(Block.blocksList[world.getBlockId(rotX, rotY, rotZ)] != null
								&& !Block.blocksList[world.getBlockId(rotX, rotY, rotZ)].blockMaterial.blocksMovement()))
						{
							if (meta != NO_METADATA && blockRotationData.containsKey(realID))
								meta = getMetadata(Math.abs(realID), meta, facing);
							else
								meta = 0;
							
							flag = flag == 0 ? 2 : flag;

							world.setBlock(rotX, rotY, rotZ, Math.abs(realID), meta, flag);
							// Fixes things like rails that automatically update onBlockAdded from world.setBlock
							if (blockRotationData.containsKey(realID))
								setMetadata(world, rotX, rotY, rotZ, meta, facing);
							
							if (Math.abs(fakeID) > 4096) {
								onCustomBlockAdded(world, rotX, rotY, rotZ, fakeID, customData);
							}
						}
					}
				}
			}
		}

		return true;
	}
	
	/**
	 * Maps a block id to a specified rotation type. Allows custom blocks to rotate with structure.
	 * @param blockID a valid block id, 0 to 4096
	 * @param rotationType types predefined by enumerated type ROTATION
	 * @return false if a rotation type has already been specified for the given blockID
	 */
	public static final boolean addCustomBlockRotation(int blockID, ROTATION rotationType)
	{
		return addCustomBlockRotation(blockID, rotationType, false);
	}
	
	/**
	 * Maps a block id to a specified rotation type. Allows custom blocks to rotate with structure.
	 * @param blockID a valid block id, 0 to 4096
	 * @param rotationType types predefined by enumerated type ROTATION
	 * @param override if true, will override the previously set rotation data for specified blockID
	 * @return false if a rotation type has already been specified for the given blockID
	 */
	public static final boolean addCustomBlockRotation(int blockID, ROTATION rotationType, boolean override)
	{
		if (blockID < 0 || blockID > 4096) {
			throw new IllegalArgumentException("[GEN STRUCTURE] Error setting custom block rotation. Provided id: " + blockID + ". Valid ids are (0-4095)");
		}
		if (blockRotationData.containsKey(blockID)) {
			System.out.println("[GEN STRUCTURE][WARNING] Block " + blockID + " already has a rotation type." + (override ? " Overriding previous data." : ""));
			if (override) blockRotationData.put(blockID, rotationType);
			else return false;
		}
		blockRotationData.put(blockID, rotationType);
		return true;
	}
	
	/**
	 * Allows the use of block ids greater than 4096 as custom 'hooks' to trigger onCustomBlockAdded
	 * @param fakeID ID you use to identify your 'event'. Absolute value must be greater than 4096
	 * @return Returns the id of the real block to spawn in the world. Absolute value must be greater than 4096
	 */
	public abstract int getRealBlockID(int fakeID);
	
	/**
	 * A custom 'hook' to allow setting of tile entities, spawning entities, etc.
	 * @param fakeID The custom identifier used to distinguish between types
	 * @param customData Custom data may be used to subtype events for given fakeID
	 */
	public abstract void onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData);
	
	/**
	 * Fixes blocks metadata after they've been placed in the world, specifically for blocks
	 * such as rails, furnaces, etc. whose orientation is automatically determined by the block
	 * when placed via the onBlockAdded method.
	 */
	private final void setMetadata(World world, int x, int y, int z, int origMeta, int facing)
	{
		int meta = world.getBlockMetadata(x, y, z), id = world.getBlockId(x, y, z);
		
		if (blockRotationData.get(id) == null) return;
		switch(blockRotationData.get(id)) {
		case PISTON_CONTAINER:
			world.setBlockMetadataWithNotify(x, y, z, origMeta, 2);
			break;
		case RAIL:
			world.setBlockMetadataWithNotify(x, y, z, origMeta, 2);
			break;
		default:
			break;
		}
	}
	
	/**
	 * This method will return the correct metadata value for the block type based on
	 * how it was rotated in the world, IF and ONLY IF you used the correct metadata
	 * value to set the block's default orientation for your structure's default facing.
	 * 
	 * If your structure's front faces EAST by default, for example, and you want a wall
	 * sign out front greeting all your guests, you'd better use '5' as its metadata value
	 * in your blockArray so it faces EAST as well.
	 * 
	 * Please read the blockArray notes very carefully and test out your structure to make
	 * sure everything is oriented how you thought it was.
	 */
	private final int getMetadata(int id, int origMeta, int facing)
	{
		// No rotational metadata value associated with this block, return
		if (blockRotationData.get(id) == null) return 0;
		
		int rotation = (((this.structureFacing == NORTH || this.structureFacing == SOUTH) ? this.structureFacing + 2 : this.structureFacing) + this.facing) % 4;
		
		int meta = origMeta, bitface, tickDelay = meta >> 2, bit9 = meta >> 3,
			bit4 = meta & 4, bit8 = meta & 8, extra = meta & ~3;
		// meta & ~3 gets all that extra information for doors and possible other stuff
		
		for (int i = 0; i < rotation; ++i)
		{
			bitface = meta % 4;
			
			switch(blockRotationData.get(id)) {
			case ANVIL:
				meta ^= 1;
				break;
			case DOOR:
				if (bit8 != 0) return meta;
				meta = (bitface == 3 ? 0 : bitface + 1);
				meta |= extra;
				break;
			case GENERIC:
				meta = (bitface == 3 ? 0 : bitface + 1) | bit4 | bit8;
				break;
			case PISTON_CONTAINER:
				meta -= meta > 7 ? 8 : 0;
				//if (meta < 2) meta ^= 1; // don't want to invert the block!
				if (meta > 1) meta = meta == 2 ? 5 : meta == 5 ? 3 : meta == 3 ? 4 : 2;
				meta |= bit8 | bit9 << 3;
				break;
			case RAIL:
				if (meta < 2) meta ^= 1;
				else if (meta < 6) meta = meta == 2 ? 5 : meta == 5 ? 3 : meta == 3 ? 4 : 2;
				else meta = meta == 9 ? 6 : meta + 1;
				break;
			case REPEATER:
				meta = (bitface == 3 ? 0 : bitface + 1) | (tickDelay << 2);
				break;
			case SIGNPOST:
				meta = meta < 12 ? meta + 4 : meta - 12;
				break;
			case SKULL:
				meta = meta == 1 ? 1 : meta == 4 ? 2 : meta == 2 ? 5 : meta == 5 ? 3 : 4;
				break;
			case STAIRS:
				meta = (bitface == 0 ? 2 : bitface == 2 ? 1 : bitface == 1 ? 3 : 0) | bit4;
				break;
			case TRAPDOOR:
				meta = (bitface == 0 ? 3 : bitface == 3 ? 1 : bitface == 1 ? 2 : 0) | bit4 | bit8;
				break;
			case VINE:
				meta = meta == 1 ? 2 : meta == 2 ? 4 : meta == 4 ? 8 : 1;
				break;
			case WALL_MOUNTED:
				meta -= meta > 7 ? 8 : 0; // remove on/off status for setting direction
				if (meta > 0 && meta < 5) meta = meta == 4 ? 1 : meta == 1 ? 3 : meta == 3 ? 2 : 4;
				else if (meta == 5 || meta == 6) meta = meta == 5 ? 6 : 5;
				else meta = meta == 7 ? 0 : 7;
				meta |= bit8; // re-apply on/off status
				break;
			case WOOD:
				if (meta > 4 && meta < 12) meta = meta < 8 ? meta + 4 : meta - 4;
				break;
			default:
				break;
			}
		}
		
		return meta;
	}
	
	/**
	 * Adjusts offsetX and offsetZ amounts to compensate for manual rotation
	 */
	private final void setOffsetFromRotation()
	{
		int x, z;
		
		for (int i = 0; i < this.manualRotations; ++i)
		{
			x = -this.offsetZ;
			z = this.offsetX;
			this.offsetX = x;
			this.offsetZ = z;
		}
	}
	
	/** Set rotation data for vanilla blocks */
	static {
		blockRotationData.put(Block.anvil.blockID, ROTATION.ANVIL);
		
		blockRotationData.put(Block.doorIron.blockID, ROTATION.DOOR);
		blockRotationData.put(Block.doorWood.blockID, ROTATION.DOOR);
		
		blockRotationData.put(Block.bed.blockID, ROTATION.GENERIC);
		blockRotationData.put(Block.cocoaPlant.blockID, ROTATION.GENERIC);
		blockRotationData.put(Block.fenceGate.blockID, ROTATION.GENERIC);
		blockRotationData.put(Block.pumpkin.blockID, ROTATION.GENERIC);
		blockRotationData.put(Block.pumpkinLantern.blockID, ROTATION.GENERIC);
		blockRotationData.put(Block.endPortalFrame.blockID, ROTATION.GENERIC);
		blockRotationData.put(Block.tripWireSource.blockID, ROTATION.GENERIC);
		
		blockRotationData.put(Block.chest.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.chestTrapped.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.dispenser.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.dropper.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.enderChest.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.furnaceBurning.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.furnaceIdle.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.hopperBlock.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.ladder.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.signWall.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.pistonBase.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.pistonExtension.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.pistonMoving.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.pistonStickyBase.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.railActivator.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.railDetector.blockID, ROTATION.PISTON_CONTAINER);
		blockRotationData.put(Block.railPowered.blockID, ROTATION.PISTON_CONTAINER);
		
		blockRotationData.put(Block.rail.blockID, ROTATION.RAIL);
		
		blockRotationData.put(Block.redstoneComparatorActive.blockID, ROTATION.REPEATER);
		blockRotationData.put(Block.redstoneComparatorIdle.blockID, ROTATION.REPEATER);
		blockRotationData.put(Block.redstoneRepeaterActive.blockID, ROTATION.REPEATER);
		blockRotationData.put(Block.redstoneRepeaterIdle.blockID, ROTATION.REPEATER);
		
		blockRotationData.put(Block.signPost.blockID, ROTATION.SIGNPOST);
		
		blockRotationData.put(Block.skull.blockID, ROTATION.SKULL);
		
		blockRotationData.put(Block.stairsBrick.blockID, ROTATION.STAIRS);
		blockRotationData.put(Block.stairsCobblestone.blockID, ROTATION.STAIRS);
		blockRotationData.put(Block.stairsNetherBrick.blockID, ROTATION.STAIRS);
		blockRotationData.put(Block.stairsNetherQuartz.blockID, ROTATION.STAIRS);
		blockRotationData.put(Block.stairsSandStone.blockID, ROTATION.STAIRS);
		blockRotationData.put(Block.stairsStoneBrick.blockID, ROTATION.STAIRS);
		blockRotationData.put(Block.stairsWoodBirch.blockID, ROTATION.STAIRS);
		blockRotationData.put(Block.stairsWoodJungle.blockID, ROTATION.STAIRS);
		blockRotationData.put(Block.stairsWoodOak.blockID, ROTATION.STAIRS);
		blockRotationData.put(Block.stairsWoodSpruce.blockID, ROTATION.STAIRS);
		
		blockRotationData.put(Block.trapdoor.blockID, ROTATION.TRAPDOOR);
		
		blockRotationData.put(Block.vine.blockID, ROTATION.VINE);
		
		blockRotationData.put(Block.lever.blockID, ROTATION.WALL_MOUNTED);
		blockRotationData.put(Block.stoneButton.blockID, ROTATION.WALL_MOUNTED);
		blockRotationData.put(Block.woodenButton.blockID, ROTATION.WALL_MOUNTED);
		blockRotationData.put(Block.torchRedstoneActive.blockID, ROTATION.WALL_MOUNTED);
		blockRotationData.put(Block.torchRedstoneIdle.blockID, ROTATION.WALL_MOUNTED);
		blockRotationData.put(Block.torchWood.blockID, ROTATION.WALL_MOUNTED);
		
		blockRotationData.put(Block.wood.blockID, ROTATION.WOOD);
	}
}
