/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenStructure extends StructureGeneratorBase
{
	public WorldGenStructure(Entity entity, int[][][][] blocks){
		super(entity, blocks);
	}

	public WorldGenStructure(Entity entity, int[][][][] blocks, int structureFacing) {
		super(entity, blocks, structureFacing);
	}

	public WorldGenStructure(Entity entity, int[][][][] blocks,
			int structureFacing, int offX, int offY, int offZ) {
		super(entity, blocks, structureFacing, offX, offY, offZ);
	}

	public WorldGenStructure() {
		super();
	}

	/**
	 * Allows the use of block ids greater than 4096 as custom 'hooks' to trigger onCustomBlockAdded
	 * @param fakeID Identifier for your 'event'. Absolute value must be greater than 4096
	 * @param customData Custom data may be used to subtype events for given fakeID
	 * @return Returns the real id of the block to spawn in the world; must be <= 4096
	 */
	@Override
	public int getRealBlockID(int fakeID, int customData) {
		System.out.println("[GEN STRUCTURE] Getting real id from fake id: " + fakeID);
		switch(fakeID) {
		case StructureArrays.CUSTOM_CHEST:
			return Block.chest.blockID;
		case StructureArrays.CUSTOM_DISPENSER:
			return Block.dispenser.blockID;
		case StructureArrays.ITEM_FRAME: // same as PAINTING
			return Block.torchWood.blockID;
		case StructureArrays.PAINTING:
			return Block.torchWood.blockID; // need to do post-generation setting of this entity
		case StructureArrays.SPAWN_VILLAGER:
			return Block.torchWood.blockID; // using this, the villager will be spawned post-generation
		default:
			// note that SPAWN_VILLAGER would return 0 by default if we didn't set a custom id above,
			// which is what we would want for 'air' if we didn't care about post-gen spawning
			return 0;
		}
	}

	/**
	 * A custom 'hook' to allow setting of tile entities, spawning entities, etc.
	 * @param fakeID The custom identifier used to distinguish between types
	 * @param customData Custom data may be used to subtype events for given fakeID
	 */
	@Override
	public void onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData)
	{
		int meta = world.getBlockMetadata(x, y, z);
		System.out.println("[CUSTOM BLOCK ADDED] metadata = " + meta);
		System.out.println("[GEN STRUCTURE] Setting custom block info for fake id " + fakeID + " and customData " + customData);
		switch(fakeID) {
		case StructureArrays.CUSTOM_CHEST:
			// Using the pre-made method addItemToTileInventory adds items to the first slot available

			// Here we're using customData for stack size to add
			addItemToTileInventory(world, new ItemStack(Item.diamond, customData), x, y, z);
			
			// Here we use customData to add a metadata block to the chest
			addItemToTileInventory(world, new ItemStack(Block.cloth.blockID, 1, customData), x, y, z);
			break;
		case StructureArrays.CUSTOM_DISPENSER:
			// We're going to take advantage of addItemToTileInventory's return value to fill
			// the container to the brim
			boolean addmore = true;
			while (addmore)
			{
				// Here we use customData as the itemID to place
				addmore = addItemToTileInventory(world, new ItemStack(customData, 64, 0), x, y, z);
			}
			break;
		case StructureArrays.ITEM_FRAME:
			ItemStack frame = new ItemStack(Item.itemFrame);
			// To save you lots of trouble, there are ready-made methods to handle placing
			// hanging entities and set ItemFrame items (with or without rotation)
			
			// You need to store the returned facing from setHangingEntity to use later methods
			int facing = setHangingEntity(world, frame, x, y, z);
			
			// Use this method for default rotation:
			setItemFrameStack(world, new ItemStack(customData,1,0), x, y, z, facing);

			// or this one if you want to specify rotation:
			// setItemFrameStack(world, x, y, z, facing, new ItemStack(customData,1,0),2);
			break;
		case StructureArrays.PAINTING:
			ItemStack painting = new ItemStack(Item.painting);
			setHangingEntity(world, painting, x, y, z);
			// choose painting you want based on custom data; look at EnumArt for painting names
			/*
			String custom = (customData == 1 ? "Aztec" : "Bomb");
			
			List paintings = world.getEntitiesWithinAABB(EntityPainting.class, AxisAlignedBB.getBoundingBox((double) x - 1, (double) y - 1, (double) z - 1, (double) x + 1, (double) y + 1, (double) z + 1));
			
			if (paintings != null && !paintings.isEmpty())
			{
				Iterator iterator = paintings.iterator();

				while (iterator.hasNext())
				{
					EntityPainting toEdit = (EntityPainting) iterator.next();
					System.out.println("[PAINTING] setting custom painting for " + toEdit.getEntityData().getString("Motive"));
					toEdit.getEntityData().setString("Motive", custom);
					System.out.println("[PAINTING] new painting is " + toEdit.getEntityData().getString("Motive"));
					//EntityPainting newPainting = new EntityPainting(world, (int) toEdit.posX, (int) toEdit.posY, (int) toEdit.posZ, 2, custom);
					//toEdit.setDead();
					//world.spawnEntityInWorld(newPainting);
				}
			}
			*/
			break;
		case StructureArrays.SPAWN_VILLAGER:
			// Again, it would be cleaner to call a custom method from this point
			
			// first remove the torch used as a place-marker
			world.setBlockToAir(x, y, z);
			
			// here I'm using customData as the villagerID
			EntityVillager bob = new EntityVillager(world, customData);
			
			// set the entity's location; note that these are doubles so you could perform more
			// sophisticated calculations to prevent spawning halfway in a wall
			bob.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
			
			// spawn the entity
			world.spawnEntityInWorld(bob);
			
			// hooray! Bob is here :)
			System.out.println("[GEN STRUCTURE] Spawned villager at " + x + "/" + y + "/" + z);
			break;
		default:
			System.out.println("[GEN STRUCTURE] No custom method defined for this id.");
		}
	}

}
