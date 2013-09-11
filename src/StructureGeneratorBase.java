/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet25EntityPainting;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumArt;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.FakePlayer;

public abstract class StructureGeneratorBase extends WorldGenerator
{
	/** Use this value to skip setting a block at an x,y,z coordinate for whatever reason. */
	public static final int SET_NO_BLOCK = Integer.MAX_VALUE;
	
	/** The directional values associated with player facing: */
	public static final int SOUTH = 0, WEST = 1, NORTH = 2, EAST = 3;
	
	/** Valid rotation types. Each type is handled like vanilla blocks of this kind. */
	public static enum ROTATION {ANVIL, DOOR, GENERIC, PISTON_CONTAINER, QUARTZ, RAIL, REPEATER,
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
	
	/** Stores the data for current layer. See StructureArray.java for information on how create a blockArray. */
	private int[][][][] blockArray;
	
	/** Stores a list of the structure to build, in 'layers' of up to the limit set by static array initialization. */
	private final List<int[][][][]> blockArrayList = new LinkedList();
	
	/** Stores blocks that need to be set post-generation, such as torches */
	private final List<BlockData> postGenBlocks = new LinkedList();
	
	/**
	 * Basic constructor. Sets generator to notify other blocks of blocks it changes.
     */
	public StructureGeneratorBase() {
		super(true);
	}
	
	/**
	 * Constructs the generator based on the player's facing and blockArray for the structure
	 */
	public StructureGeneratorBase(Entity entity, int[][][][] blocks)
	{
		super(false);
		this.facing = MathHelper.floor_double((double)((entity.rotationYaw * 4F) / 360f) + 0.5D) &3;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			this.addBlockArray(blocks);
	}

	/**
	 * Constructs the generator with the player's facing, the blockArray for the structure
	 * and the structure's facing
	 */
	public StructureGeneratorBase(Entity entity, int[][][][] blocks, int structureFacing)
	{
		super(false);
		this.facing = MathHelper.floor_double((double)((entity.rotationYaw * 4F) / 360f) + 0.5D) &3;
		this.structureFacing = structureFacing;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			this.addBlockArray(blocks);
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
	public StructureGeneratorBase(Entity entity, int[][][][] blocks, int structureFacing, int offX, int offY, int offZ)
	{
		super(false);
		this.facing = MathHelper.floor_double((double)((entity.rotationYaw * 4F) / 360f) + 0.5D) &3;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			this.addBlockArray(blocks);
		this.structureFacing = structureFacing;
		this.offsetX = offX;
		this.offsetY = offY;
		this.offsetZ = offZ;
	}
	
	/**
	 * Allows the use of block ids greater than 4096 as custom 'hooks' to trigger onCustomBlockAdded
	 * @param fakeID ID you use to identify your 'event'. Absolute value must be greater than 4096
	 * @param customData1 Custom data may be used to subtype events for given fakeID
	 * Returns the real id of the block to spawn in the world; must be <= 4096
	 */
	public abstract int getRealBlockID(int fakeID, int customData1);
	
	/**
	 * A custom 'hook' to allow setting of tile entities, spawning entities, etc.
	 * @param fakeID The custom identifier used to distinguish between types
	 * @param customData1 Custom data which can be used to subtype events for given fakeID
	 * @param customData2 Additional custom data
	 */
	public abstract void onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData1, int customData2);
	
	/**
	 * Maps a block id to a specified rotation type. Allows custom blocks to rotate with structure.
	 * @param blockID a valid block id, 0 to 4096
	 * @param rotationType types predefined by enumerated type ROTATION
	 * @return false if a rotation type has already been specified for the given blockID
	 */
	public static final boolean registerCustomBlockRotation(int blockID, ROTATION rotationType)
	{
		return registerCustomBlockRotation(blockID, rotationType, false);
	}
	
	/**
	 * Maps a block id to a specified rotation type. Allows custom blocks to rotate with structure.
	 * @param blockID a valid block id, 0 to 4096
	 * @param rotationType types predefined by enumerated type ROTATION
	 * @param override if true, will override the previously set rotation data for specified blockID
	 * @return false if a rotation type has already been specified for the given blockID
	 */
	public static final boolean registerCustomBlockRotation(int blockID, ROTATION rotationType, boolean override)
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
	 * Use this method to add an ItemStack to the first available slot in a TileEntity that
	 * implements IInventory (and thus, by extension, ISidedInventory)
	 * @return true if entire itemstack was added
	 */
	public final boolean addItemToTileInventory(World world, ItemStack itemstack, int x, int y, int z)
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile == null || !(tile instanceof IInventory)) {
			System.out.println("[GEN STRUCTURE][WARNING] Tile Entity at " + x + "/" + y + "/" + z + " is " + (tile != null ? "not an IInventory" : "null"));
			return false;
		}
		if (itemstack.stackSize < 1) {
			System.out.println("[GEN STRUCTURE][WARNING] Trying to add ItemStack of size 0 to Tile Inventory");
			return false;
		}
		
		IInventory inventory = (IInventory) tile;
		int remaining = itemstack.stackSize;
		
		for (int i = 0; i < inventory.getSizeInventory() && remaining > 0; ++i)
		{
			ItemStack slotstack = inventory.getStackInSlot(i);
			
			if (slotstack == null && inventory.isItemValidForSlot(i, itemstack))
			{
				remaining -= inventory.getInventoryStackLimit();
				itemstack.stackSize = (remaining > 0 ? inventory.getInventoryStackLimit() : itemstack.stackSize);
				inventory.setInventorySlotContents(i, itemstack);
				inventory.onInventoryChanged();
			}
			else if (slotstack != null && itemstack.isStackable() && inventory.isItemValidForSlot(i, itemstack))
			{
				if (slotstack.itemID == itemstack.itemID  && (!itemstack.getHasSubtypes() || 
					itemstack.getItemDamage() == slotstack.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemstack, slotstack))
				{
					int l = slotstack.stackSize + remaining;

                    if (l <= itemstack.getMaxStackSize() && l <= inventory.getInventoryStackLimit())
                    {
                    	remaining = 0;
                        slotstack.stackSize = l;
                        inventory.onInventoryChanged();
                    }
                    else if (slotstack.stackSize < itemstack.getMaxStackSize() && itemstack.getMaxStackSize() <= inventory.getInventoryStackLimit())
                    {
                        remaining -= itemstack.getMaxStackSize() - slotstack.stackSize;
                        slotstack.stackSize = itemstack.getMaxStackSize();
                        inventory.onInventoryChanged();
                    }
				}
			}
		}
		
		return remaining < 1;
	}
	
	/**
	 * Sets an entity's location so that it doesn't spawn inside of walls.
	 * Automatically removes placeholder block at coordinates x/y/z.
	 * @return false if no suitable location found
	 */
	public final boolean setEntityInStructure(World world, Entity entity, int x, int y, int z)
	{
		if (entity == null) { return false; }
		int i = 0, iMax = (entity.width > 1.0F ? 16 : 4), factor = 1;
		
		// remove place-holder block
		world.setBlockToAir(x, y, z);
		
		// entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
		entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
		
		while (entity.isEntityInsideOpaqueBlock() && i < iMax)
		{
			if (i == 4 && entity.isEntityInsideOpaqueBlock() && entity.width > 1.0F) {
				entity.setLocationAndAngles(x, y, z, 90.0F, 0.0F);
				System.out.println("[GEN STRUCTURE][SPAWN] Large entity; rotating 90 degrees");
			}
			else if (i == 8 && entity.isEntityInsideOpaqueBlock() && entity.width > 1.0F) {
				entity.setLocationAndAngles(x, y, z, 180.0F, 0.0F);
				System.out.println("[GEN STRUCTURE][SPAWN] Large entity; rotating 180 degrees");
			}
			else if (i == 12 && entity.isEntityInsideOpaqueBlock() && entity.width > 1.0F) {
				entity.setLocationAndAngles(x, y, z, 270.0F, 0.0F);
				System.out.println("[GEN STRUCTURE][SPAWN] Large entity; rotating 270 degrees");
			}
			
			System.out.println("[GEN STRUCTURE][SPAWN] Entity inside opaque block at " + entity.posX + "/" + entity.posY + "/" + entity.posZ);
			
			switch(i % 4) {
			case 0: entity.setPosition(entity.posX + 0.5D, entity.posY, entity.posZ + 0.5D); break;
			case 1: entity.setPosition(entity.posX, entity.posY, entity.posZ - 1.0D); break;
			case 2: entity.setPosition(entity.posX - 1.0D, entity.posY, entity.posZ); break;
			case 3: entity.setPosition(entity.posX, entity.posY, entity.posZ + 1.0D); break;
			}
			
			++i;
			/*
			if (i == 12 && factor == 1 && entity.isEntityInsideOpaqueBlock() && entity.width > 1.0F) {
				System.out.println("[GEN STRUCTURE][SPAWN] Large entity still inside opaque block; resetting with factor of 2");
				factor = 2;
				i = 0;
			}
			*/
		}
		if (entity.isEntityInsideOpaqueBlock()) {
			System.out.println("[GEN STRUCTURE][SPAWN] Failed to set entity in open space. Returning to default position.");
			entity.setPosition(entity.posX + 0.5D, entity.posY, entity.posZ + 0.5D);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Spawns an entity in the structure by using setEntityInStructure.
	 * @return true if entity spawned without collision (entity will still spawn if false, but may be in a wall)
	 */
	public final boolean spawnEntityInStructure(World world, Entity entity, int x, int y, int z)
	{
		if (world.isRemote || entity == null) { return false; }
		
		boolean collided = setEntityInStructure(world, entity, x, y, z);
		
		world.spawnEntityInWorld(entity);
		
		System.out.println("[GEN STRUCTURE] Spawned entity at " + entity.posX + "/" + entity.posY + "/" + entity.posZ);
		
		return collided;
	}
	
	/**
	 * Places a hanging item entity in the world at the correct location and facing.
	 * Note that you MUST use a WALL_MOUNTED type block id (such as torch) for your custom
	 * block id's getRealBlockID return value in order for orientation to be correct.
	 * Coordinates x,y,z are the location of the block used to spawn the entity
	 * NOTE: Automatically removes the dummy block at x/y/z before placing the entity, so the
	 * metadata stored in the block will no longer be available, but will be returned by this
	 * method so it can be stored in a local variable for later use.
	 * @param hanging Must be an instance of ItemHangingEntity, such as Item.painting
	 * @return Returns direction for further processing such as for ItemFrames, or -1 if no entity set
	 */
	public final int setHangingEntity(World world, ItemStack hanging, int x, int y, int z)
	{
		if (hanging.getItem() == null || !(hanging.getItem() instanceof ItemHangingEntity)) {
			return -1;
		}
		if (world.getBlockMetadata(x, y, z) < 1 || world.getBlockMetadata(x, y, z) > 5) {
			System.out.println("[GEN STRUCTURE][WARNING] Hanging entity has invalid metadata of " + world.getBlockMetadata(x, y, z) + ". Valid values are 1,2,3,4");
			return - 1;
		}
		
		int[] metaToFacing = {5, 4, 3, 2};
		int direction = metaToFacing[world.getBlockMetadata(x, y, z) - 1];
		FakePlayer player = new FakePlayer(world,"fake");
		
		// remove placeholder block:
		world.setBlockToAir(x, y, z);
		
		switch(direction) {
		case 2: // frame facing NORTH
			++z;
			break;
		case 3: // frame facing SOUTH
			--z;
			break;
		case 4: // frame facing WEST
			++x;
			break;
		case 5: // frame facing EAST
			--x;
			break;
		}
		
		((ItemHangingEntity) hanging.getItem()).onItemUse(hanging, player, world, x, y, z, direction, 0, 0, 0);
		
		return direction;
	}
	
	/**
	 * Set's the itemstack contained in ItemFrame at x/y/z with default rotation.
	 * @param direction Use the value returned from the setHangingEntity method
	 */
	public final void setItemFrameStack(World world, ItemStack itemstack, int x, int y, int z, int direction)
	{
		setItemFrameStack(world, itemstack, x, y, z, direction, 0);
	}
	
	/**
	 * Set's the itemstack contained in ItemFrame at x/y/z with specified rotation.
	 * @param direction Use the value returned from the setHangingEntity method
	 * @param itemRotation 0,1,2,3 starting at default and rotating 90 degrees clockwise
	 */
	public final void setItemFrameStack(World world, ItemStack itemstack, int x, int y, int z, int direction, int itemRotation)
	{
		List<EntityItemFrame> frames = world.getEntitiesWithinAABB(EntityItemFrame.class, getHangingEntityAxisAligned(x, y, z, direction));
		if (frames != null && !frames.isEmpty())
		{
			Iterator<EntityItemFrame> iterator = frames.iterator();

			while (iterator.hasNext())
			{
				EntityItemFrame frame1 = iterator.next();
				frame1.setDisplayedItem(itemstack);
				frame1.setItemRotation(itemRotation);
			}
		}
	}
	
	/**
	 * Sets the art for a painting at location x/y/z and sends a packet to update players.
	 * @param direction Use the value returned from the setHangingEntity method
	 * @return false if 'name' didn't match any EnumArt values.
	 */
	public final boolean setPaintingArt(World world, String name, int x, int y, int z, int direction)
	{
		List<EntityPainting> paintings = world.getEntitiesWithinAABB(EntityPainting.class, getHangingEntityAxisAligned(x, y, z, direction));
		
		if (paintings != null && !paintings.isEmpty() && name.length() > 0)
		{
			Iterator<EntityPainting> iterator = paintings.iterator();

			while (iterator.hasNext())
			{
				EntityPainting toEdit = iterator.next();
				EnumArt[] aenumart = EnumArt.values();
		        int i1 = aenumart.length;

		        for (int j1 = 0; j1 < i1; ++j1)
		        {
		            EnumArt enumart = aenumart[j1];

		            if (enumart.title.equals(name))
		            {
		                toEdit.art = enumart;
		                PacketDispatcher.sendPacketToAllAround(x, y, z, 64, world.provider.dimensionId, new Packet25EntityPainting(toEdit));
		                return true;
		            }
		        }
			}
		}
		return false;
	}
	
	/**
	 * Adds text to a sign in the world. Use EnumChatFormatting to set colors. Text of more
	 * than 15 characters per line will be truncated automatically.
	 * @param text A String array of no more than 4 elements; additional elements will be ignored
	 * @return false if no sign tile entity was found at x/y/z
	 */
	public final boolean setSignText(World world, String[] text, int x, int y, int z)
	{
		TileEntitySign sign = (world.getBlockTileEntity(x, y, z) instanceof TileEntitySign ? (TileEntitySign) world.getBlockTileEntity(x, y, z) : null);
		
		if (sign != null)
		{
			for (int i = 0; i < sign.signText.length && i < text.length; ++i) {
				if (text[i].length() > 15)
					sign.signText[i] = text[i].substring(0, 15);
				else
					sign.signText[i] = text[i];
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method to set skulls not requiring extra rotation data (i.e. wall-mounted skulls whose rotation is determined by metadata)
	 */
	public final boolean setSkullData(World world, String name, int type, int x, int y, int z)
	{
		return setSkullData(world, name, type, -1, x, y, z);
	}
	
	/**
	 * Sets skull type and name for a TileEntitySkull at x/y/z
	 * @param name Must be a valid player username
	 * @param type Type of skull: 0 Skeleton, 1 Wither Skeleton, 2 Zombie, 3 Human, 4 Creeper
	 * @param rot Sets the rotation for the skull if positive value is used
	 * @return false if errors were encountered (i.e. incorrect tile entity at x/y/z)
	 */
	public final boolean setSkullData(World world, String name, int type, int rot, int x, int y, int z)
	{
		TileEntitySkull skull = (world.getBlockTileEntity(x, y, z) instanceof TileEntitySkull ? (TileEntitySkull) world.getBlockTileEntity(x, y, z) : null);
		if (skull != null)
		{
			if (type > 4 || type < 0)
			{
				System.out.println("[GEN STRUCTURE][WARNING] Custom data value " + type + " not valid for skulls. Valid values are 0 to 4.");
				type = 0;
			}
			skull.setSkullType(type, name);
			if (rot > -1)
				skull.setSkullRotation(rot);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sets the direction in which the player is facing. The structure will be generated
	 * opposite of player view (so player will be looking at front when finished)
	 */
	public final void setPlayerFacing(Entity entity) {
		this.facing = MathHelper.floor_double((double)((entity.rotationYaw * 4F) / 360f) + 0.5D) &3;
	}
	
	/**
	 * Sets the default direction the structure is facing. This side will always face the player
	 * unless you manually rotate the structure with the rotateStructureFacing() method.
	 */
	public final void setStructureFacing(int facing) {
		this.structureFacing = facing;
	}
	
	/**
	 * Adds all elements contained in the parameter list to the structure
	 */
	public final void addBlockArrayList(List<int[][][][]> list)
	{
		this.blockArrayList.addAll(list);
		if (this.blockArray == null && list.size() > 0)
			this.blockArray = list.get(0);
	}
	
	/**
	 * Overwrites current blockArrayList with list provided
	 */
	public final void setBlockArrayList(List<int[][][][]> list)
	{
		this.blockArrayList.clear();
		this.blockArrayList.addAll(list);
		this.blockArray = (list.size() > 0 ? list.get(0) : null);
	}
	
	/**
	 * Adds a block array 'layer' to the list to be generated
	 */
	public final void addBlockArray(int blocks[][][][]) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			this.blockArrayList.add(blocks);
			if (this.blockArray == null)
				this.blockArray = blocks;
		}
	}
	
	/**
	 * Returns lowest structure layer's width along the x axis or 0 if no structure has been added
	 */
	public final int getWidthX() {
		return this.blockArray != null ? this.blockArray[0].length : 0;
	}
	
	/**
	 * Returns lowest structure layer's width along the z axis or 0 if no structure has been set
	 */
	public final int getWidthZ() {
		return this.blockArray != null ? this.blockArray[0][0].length : 0;
	}
	
	/**
	 * Returns current structure layer's height or 0 if no structure has been set
	 */
	// do we need this?
	public final int getHeight() {
		return this.blockArray != null ? this.blockArray.length : 0;
	}
	
	/**
	 * Returns the original facing for the structure
	 */
	public final int getOriginalFacing() {
		return (this.structureFacing + (4 - this.manualRotations)) % 4;
	}
	
	/**
	 * Returns true if the structure has been rotated onto the opposite axis from original
	 */
	public final boolean isOppositeAxis() {
		return getOriginalFacing() % 2 != this.structureFacing % 2;
	}
	
	/**
	 * Sets the amount by which to offset the structure's generated location in the world.
	 * For advanced users only. Recommended to use setDefaultOffset() methods instead.
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
		setDefaultOffset(0,0,0);
	}
	
	/**
	 * Sets offsets such that the structure always generates in front of the player,
	 * regardless of structure facing, offset by parameters x/y/z.
	 * NOTE: If your structures y=0 layer has an area smaller than another part of the structure,
	 * setting default offset will not work correctly.
	 * @param x Positive value spawns structure further away from player, negative closer to or behind
	 * @param z Positive value spawns structure more to the right, negative to the left
	 */
	public final void setDefaultOffset(int x, int y, int z) {
		switch(this.getOriginalFacing()) {
		case SOUTH:
			this.offsetZ = (getWidthZ() / 2) + x;
			this.offsetX = -z;
			break;
		case WEST:
			this.offsetX = (getWidthX() / 2) + x;
			this.offsetZ = z;
			break;
		case NORTH:
			this.offsetZ = -(getWidthZ() / 2) - x;
			this.offsetX = z;
			break;
		case EAST:
			this.offsetX = -(getWidthX() / 2) - x;
			this.offsetZ = -z;
			break;
		}
		this.offsetY = 1 + y;
	}
	
	/**
	 * This will manually rotate the structure's default facing 90 degrees clockwise.
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
		return this.blockArrayList.size() > 0 || this.blockArray != null;
	}
	
	/**
	 * IMPORTANT!!! Before calling WorldGenStructure generate method, you MUST set the player
	 * facing with setFacing(Entity) and addBlockArray(int[][][][]) either with the constructor
	 * or individual methods. If you want the structure's location offset by an amount, be
	 * sure to setOffset as well.
	 */
	@Override
	public final boolean generate(World world, Random random, int posX, int posY, int posZ)
	{
		if (world.isRemote || !canGenerate()) { return false; }
		
		int rotations = (((this.structureFacing == NORTH || this.structureFacing == SOUTH) ? this.structureFacing + 2 : this.structureFacing) + this.facing) % 4;
		
		setOffsetFromRotation();
		
		Iterator iterator = blockArrayList.iterator();
		while (iterator.hasNext())
		{
			this.blockArray = (int[][][][]) iterator.next();
			generateLayer(world, random, posX, posY, posZ, rotations);
			this.offsetY += this.blockArray.length;
		}
		
		doPostGenProcessing(world);
		
		this.blockArrayList.clear();
		this.blockArray = null;
		
		return true;
	}
	
	/**
	 * Custom 'generate' method that generates a single 'layer' from the list of blockArrays
	 */
	private final boolean generateLayer(World world, Random random, int posX, int posY, int posZ, int rotations)
	{
		// already checked in 'generate' method
		// if (blockArray == null) { System.out.println("[GEN STRUCTURE][WARNING] No structure array has been set."); return false; }

		// does center need to be calculated each time? What if first index isn't true size of structure?
		int centerX = blockArray[0].length / 2, centerZ = blockArray[0][0].length / 2;
		// It seems to work both ways, so for now, use the one with fewest computations
		// int centerX, centerZ;

		for (int y = (this.removeStructure ? blockArray.length - 1 : 0); (this.removeStructure ? y >= 0 : y < blockArray.length); y = (this.removeStructure ? --y : ++y))
		{
			for (int x = 0; x < blockArray[y].length; ++x)
			{
				for (int z = 0; z < blockArray[y][x].length; ++z)
				{
					if (blockArray[y][x][z].length == 0 || blockArray[y][x][z][0] == SET_NO_BLOCK)
						continue; // && !this.removeStructure) <- but we only want to remove blocks set by array

					int rotX = posX, rotZ = posZ, rotY = posY + y + offsetY;
					
					switch(rotations) {
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
					
					int customData1 = (blockArray[y][x][z].length > 2 ? blockArray[y][x][z][2] : 0);
					int fakeID = blockArray[y][x][z][0];
					int realID = (Math.abs(fakeID) > 4096 ? getRealBlockID(fakeID, customData1) : fakeID);

					if (this.removeStructure)
					{
						if (world.isAirBlock(rotX, rotY, rotZ) || (realID < 0 && Math.abs(realID) != world.getBlockId(rotX, rotY, rotZ)))
							continue;
						else if (Math.abs(realID) == world.getBlockId(rotX, rotY, rotZ) || 
								(Block.blocksList[world.getBlockId(rotX, rotY, rotZ)].blockMaterial.isLiquid() &&
								(Block.blocksList[Math.abs(realID)].blockMaterial.isLiquid() || realID == 0)))
							world.setBlockToAir(rotX, rotY, rotZ);
						else {
							System.out.println("[GEN STRUCTURE][WARNING] Incorrect location for structure removal, aborting.");
							return false;
						}
					}
					else
					{
						if (Math.abs(realID) > 4096) {
							System.out.println("[GEN STRUCTURE][WARNING] Invalid block ID. Initial ID: " + fakeID + ", returned id from getRealID: " + realID);
							continue;
						}
						
						int customData2 = (blockArray[y][x][z].length > 3 ? blockArray[y][x][z][3] : 0);
						int meta = (blockArray[y][x][z].length > 1 ? blockArray[y][x][z][1] : blockRotationData.containsKey(Math.abs(realID)) ? 0 : NO_METADATA);
						
						// Allows 'soft-spawning' blocks to be spawned only in air or on blocks that allow movement, such as air or grass
						if (realID >= 0 || world.isAirBlock(rotX, rotY, rotZ) || 
								(Block.blocksList[world.getBlockId(rotX, rotY, rotZ)] != null
								&& !Block.blocksList[world.getBlockId(rotX, rotY, rotZ)].blockMaterial.blocksMovement()))
						{
							if (meta == NO_METADATA)
								meta = 0;
							else if (blockRotationData.containsKey(realID))
								meta = getMetadata(Math.abs(realID), meta, facing);
							//else {} // leave metadata alone

							// Get color data for wool blocks
							int flag = (Math.abs(realID) == Block.cloth.blockID ? customData1 : 2);
							
							// add torches and such to a list for after-generation setBlock calls
							if (blockRotationData.get(realID) != null && blockRotationData.get(realID) == ROTATION.WALL_MOUNTED)
							{
								System.out.println("[GEN STRUCTURE] Block " + realID + " requires post-processing. Adding to list. Meta = " + meta);
								this.postGenBlocks.add(new BlockData(rotX, rotY, rotZ, fakeID, meta, customData1, customData2));
							}
							// set all other blocks here
							else
							{
								world.setBlock(rotX, rotY, rotZ, Math.abs(realID), meta, flag);
								
								// Fix things like rails that automatically update onBlockAdded from world.setBlock
								if (blockRotationData.containsKey(realID))
									setMetadata(world, rotX, rotY, rotZ, meta, facing);

								// Call to custom block hooks
								if (Math.abs(fakeID) > 4096) {
									onCustomBlockAdded(world, rotX, rotY, rotZ, fakeID, customData1, customData2);
								}
							}
						}
					}
				}
			}
		}

		return true;
	}
	
	private final void doPostGenProcessing(World world)
	{
		int fakeID, realID;
		BlockData block;
		Iterator iterator = this.postGenBlocks.iterator();

		while (iterator.hasNext())
		{
			block = (BlockData) iterator.next();
			fakeID = block.getBlockID();
			realID = (Math.abs(fakeID) > 4096 ? getRealBlockID(fakeID, block.getCustomData1()) : fakeID);

			if (Math.abs(realID) > 4096) {
				System.out.println("[GEN STRUCTURE][WARNING] Invalid block ID. Initial ID: " + fakeID + ", returned id from getRealID: " + realID);
				continue;
			}

			System.out.println("[GEN STRUCTURE] Post-gen processing for initial ID: " + fakeID + ", returned id from getRealID: " + realID);
			if (realID >= 0 || world.isAirBlock(block.getPosX(), block.getPosY(), block.getPosZ()) || 
					(Block.blocksList[world.getBlockId(block.getPosX(), block.getPosY(), block.getPosZ())] != null
					&& !Block.blocksList[world.getBlockId(block.getPosX(), block.getPosY(), block.getPosZ())].blockMaterial.blocksMovement()))
			{
				// occasionally doesn't set metadata correctly, such as for certain ItemFrames - notification flag 3 allows redstone torches to update circuits when placed
				world.setBlock(block.getPosX(), block.getPosY(), block.getPosZ(), Math.abs(realID), block.getMetaData(), 3);
				// print warning for mismatched metadata
				if (world.getBlockMetadata(block.getPosX(), block.getPosY(), block.getPosZ()) != block.getMetaData()) {
					System.out.println("[GEN STRUCTURE][WARNING] Mismatched metadata. Meta from world: " + world.getBlockMetadata(block.getPosX(), block.getPosY(), block.getPosZ()) + ", original: " + block.getMetaData());
				}
				
				if (Math.abs(fakeID) > 4096) {
					onCustomBlockAdded(world, block.getPosX(), block.getPosY(), block.getPosZ(), fakeID, block.getCustomData1(), block.getCustomData2());
				}
			}
		}
		
		this.postGenBlocks.clear();
	}
	
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
		
		// original:
		// int rotation = (((this.structureFacing == NORTH || this.structureFacing == SOUTH) ? this.structureFacing + 2 : this.structureFacing) + this.facing) % 4;
		// should use manualRotation
		int rotation = (((this.manualRotations == 1 || this.manualRotations == 3) ? this.structureFacing + 2 : this.structureFacing) + this.facing) % 4;
		
		int meta = origMeta, bitface, tickDelay = meta >> 2, bit9 = meta >> 3,
			bit4 = meta & 4, bit8 = meta & 8, extra = meta & ~3;
		// meta & ~3 gets all that extra information for doors and possibly other stuff
		
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
			case QUARTZ:
				meta = meta == 3 ? 4 : meta == 4 ? 3 : meta;
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
		// adjust for generating on opposite axis
		if (this.isOppositeAxis()) {
			switch(this.getOriginalFacing()) {
			case SOUTH: this.offsetZ += (this.getWidthX() - this.getWidthZ()); break;
			case WEST: this.offsetX += (this.getWidthZ() - this.getWidthX()); break;
			case NORTH: this.offsetZ -= (this.getWidthX() - this.getWidthZ()); break;
			case EAST: this.offsetX -= (this.getWidthZ() - this.getWidthX()); break;
			}
		}
		
		// adjust by one block for opposite axis
		this.offsetX -= (this.isOppositeAxis() ? 1 : 0);
		
		for (int i = 0; i < this.manualRotations; ++i)
		{
			x = -this.offsetZ;
			z = this.offsetX;
			this.offsetX = x;
			this.offsetZ = z;
		}
	}
	
	/**
	 * Returns an AxisAlignedBB suitable for a hanging entity at x/y/z facing direction
	 */
	private final AxisAlignedBB getHangingEntityAxisAligned(int x, int y, int z, int direction)
	{
		double minX = (double) x, minZ = (double) z, maxX = minX, maxZ =  minZ;
		
		switch(direction) {
		case 2: // frame facing NORTH
			minX += 0.25D;
			maxX += 0.75D;
			minZ += 0.5D;
			maxZ += 1.5D;
			break;
		case 3: // frame facing SOUTH
			minX += 0.25D;
			maxX += 0.75D;
			minZ -= 0.5D;
			maxZ += 0.5D;
			break;
		case 4: // frame facing WEST
			minX += 0.5D;
			maxX += 1.5D;
			minZ += 0.25D;
			maxZ += 0.75D;
			break;
		case 5: // frame facing EAST
			minX -= 0.5D;
			maxX += 0.5D;
			minZ += 0.25D;
			maxZ += 0.75D;
			break;
		}
		
		return AxisAlignedBB.getBoundingBox(minX, (double) y, minZ, maxX, (double) y + 1, maxZ);
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
		
		blockRotationData.put(Block.blockNetherQuartz.blockID, ROTATION.QUARTZ);
		
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

class BlockData
{
	private final int x, y, z, id, meta, customData1, customData2;
	
	public BlockData(int x, int y, int z, int id, int meta, int customData1, int customData2)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
		this.meta = meta;
		this.customData1 = customData1;
		this.customData2 = customData2;
	}
	
	public final int getPosX() {
		return this.x;
	}
	
	public final int getPosY() {
		return this.y;
	}
	
	public final int getPosZ() {
		return this.z;
	}
	
	public final int getBlockID() {
		return this.id;
	}
	
	public final int getMetaData() {
		return this.meta;
	}
	
	public final int getCustomData1() {
		return this.customData1;
	}
	
	public final int getCustomData2() {
		return this.customData2;
	}
}
