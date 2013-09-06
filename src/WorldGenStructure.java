/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.FakePlayer;

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

	public WorldGenStructure(boolean par1) {
		super(par1);
	}

	/**
	 * A custom 'hook' to allow setting of tile entities, spawning entities, etc.
	 * @param fakeID The custom identifier used to distinguish between types
	 * @param customData Custom data may be used to subtype events for given fakeID
	 */
	@Override
	public int getRealBlockID(int fakeID) {
		System.out.println("[GEN STRUCTURE] Getting real id from fake id: " + fakeID);
		switch(fakeID) {
		case StructureArrays.CUSTOM_CHEST:
			return Block.chest.blockID;
		case StructureArrays.CUSTOM_DISPENSER:
			return Block.dispenser.blockID;
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
		TileEntity T;
		System.out.println("[GEN STRUCTURE] Setting custom block info for fake id " + fakeID + " and customData " + customData);
		switch(fakeID) {
		case StructureArrays.CUSTOM_CHEST:
			// just for demonstration; better to call a custom method
			T = (TileEntityChest) world.getBlockTileEntity(x, y, z);
			
			// here I'm using customData as the size of the stack, but it could also be used as the
			// item id to spawn, number of random items to place inside or to distinguish between
			// custom chest types such as CHEST_BLACKSMITH, CHEST_LIBRARY, etc.
			((TileEntityChest) T).setInventorySlotContents(0, new ItemStack(Item.diamond, customData));
			break;
		case StructureArrays.CUSTOM_DISPENSER:
			T = (TileEntityDispenser) world.getBlockTileEntity(x, y, z);
			// here we use the customData as the itemID to add to the inventory
			((TileEntityDispenser) T).setInventorySlotContents(0, new ItemStack(customData, 64, 0));
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
