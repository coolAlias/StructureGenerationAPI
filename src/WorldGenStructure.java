package coolalias.structuregen;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenStructure extends StructureGeneratorBase
{

	public WorldGenStructure(int facing, int[][][][] blocks){
		super(facing, blocks);
	}

	public WorldGenStructure(int facing, int[][][][] blocks, int structureFacing) {
		super(facing, blocks, structureFacing);
	}

	public WorldGenStructure(int facing, int[][][][] blocks,
			int structureFacing, int offX, int offY, int offZ) {
		super(facing, blocks, structureFacing, offX, offY, offZ);
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
		default:
			// note that SPAWN_VILLAGER will return 0 by default, which is what we want for 'air'
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
			// number of random items to place inside or to distinguish between custom chest types
			// such as CHEST_BLACKSMITH, CHEST_LIBRARY, etc.
			((TileEntityChest) T).setInventorySlotContents(0, new ItemStack(Item.diamond, customData));
			break;
		case StructureArrays.SPAWN_VILLAGER:
			// here I'm using customData as the villagerID
			EntityVillager bob = new EntityVillager(world, customData);
			bob.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
			world.spawnEntityInWorld(bob);
			break;
		default:
			System.out.println("[GEN STRUCTURE] No custom method defined for this id.");
		}
	}

}
