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

package coolalias.structuregen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import coolalias.structuregen.lib.LogHelper;
import coolalias.structuregen.util.Structure;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class StructureGenerator extends StructureGeneratorBase
{
	/** List storing all structures currently available */
	public static final List<Structure> structures = new LinkedList();
	
	int random_hole;
	// a better way would be to pass World in to the constructors and set the random_hole
	// value there, but I'm feeling lazy
	boolean value_set = false;
	
	public StructureGenerator(Entity entity, int[][][][] blocks){
		super(entity, blocks);
	}

	public StructureGenerator(Entity entity, int[][][][] blocks, int structureFacing) {
		super(entity, blocks, structureFacing);
	}

	public StructureGenerator(Entity entity, int[][][][] blocks,
			int structureFacing, int offX, int offY, int offZ) {
		super(entity, blocks, structureFacing, offX, offY, offZ);
	}

	public StructureGenerator() {
		super();
	}

	/**
	 * Allows the use of block ids greater than 4096 as custom 'hooks' to trigger onCustomBlockAdded
	 * @param fakeID Identifier for your 'event'. Absolute value must be greater than 4096
	 * @param customData Custom data may be used to subtype events for given fakeID
	 * @return Returns the real id of the block to spawn in the world; must be <= 4096
	 */
	@Override
	public int getRealBlockID(int fakeID, int customData1) {
		LogHelper.log(Level.FINE, "Getting real id from fake id: " + fakeID);
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
		case StructureArrays.CUSTOM_SKULL:
			return Block.skull.blockID;
		case StructureArrays.CUSTOM_SIGNWALL:
			return Block.signWall.blockID;
		case StructureArrays.RANDOM_HOLE: // Used customData1 to store the real block id
			return customData1;
		default:
			// note that SPAWN_VILLAGER would return 0 by default if we didn't set a custom id above,
			// which is what we would want for 'air' if we didn't care about post-gen spawning
			return 0;
		}
	}

	/**
	 * A custom 'hook' to allow setting of tile entities, spawning entities, etc.
	 * @param fakeID The custom identifier used to distinguish between types
	 * @param customData1 Custom data used to subtype events for given fakeID
	 * @param customData2 Additional custom data
	 */
	@Override
	public void onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData1, int customData2)
	{
		if (!value_set) {
			// if using this method, this should only be done once per structure, preferably with a better method
			// sets one value of RANDOM_HOLE to remove from the structure, allowing for patterns
			random_hole = world.rand.nextInt(5);
			value_set = true;
		}
		int meta = world.getBlockMetadata(x, y, z);
		LogHelper.log(Level.FINE, "Setting custom block info for fake id " + fakeID + " and customData1 " + customData1);
		LogHelper.log(Level.FINEST, "Custom block metadata from world = " + meta);
		
		switch(fakeID) {
		case StructureArrays.CUSTOM_CHEST:
			// Using the pre-made method addItemToTileInventory adds items to the first slot available

			// Here we use customData to subtype custom_chest:
			if (customData1 == StructureArrays.CUSTOM_CHEST_1)
			{
				// Let's load it with goodies; don't worry about over-filling, the method will take care of it
				for (int i = 0; i < 30; ++i) {
					addItemToTileInventory(world, new ItemStack(Item.diamond, 64), x, y, z);
					addItemToTileInventory(world, new ItemStack(Item.emerald, 64), x, y, z);
				}
			}
			// Not our specific chest, so we'll do some generic stuff
			else
			{
				// Here we're using customData for stack size to add
				addItemToTileInventory(world, new ItemStack(Item.diamond, customData1), x, y, z);

				// Here we use customData to add a metadata block to the chest
				addItemToTileInventory(world, new ItemStack(Block.cloth.blockID, 1, customData1), x, y, z);
				
				// Adding potions
				addItemToTileInventory(world, new ItemStack(Item.potion,1,8206), x, y, z);
				addItemToTileInventory(world, new ItemStack(Item.potion,1,8270), x, y, z);
				addItemToTileInventory(world, new ItemStack(Item.potion,1,8193), x, y, z);
				addItemToTileInventory(world, new ItemStack(Item.potion,1,16385), x, y, z);
			}
			break;
		case StructureArrays.CUSTOM_DISPENSER:
			// We're going to take advantage of addItemToTileInventory's return value to fill
			// the container to the brim; note that this way is better than the for loop from
			// above because it doesn't waste processing time - it stops as soon as it is full
			boolean addmore = true;
			while (addmore)
			{
				// Here we use customData as the itemID to place
				addmore = addItemToTileInventory(world, new ItemStack(customData1, 64, 0), x, y, z);
			}
			break;
		case StructureArrays.CUSTOM_SIGNWALL:
			// An array that stores up to 4 Strings, the max capacity of a sign
			String[] text = new String[5];
			// Set different text for each custom sign, using different colors
			if (customData1 == StructureArrays.CUSTOM_SIGN_1)
			{
				text[0] = EnumChatFormatting.DARK_RED + "   BEWARE";
				text[1] = EnumChatFormatting.DARK_RED + "  NO ENTRY";
				// the following string is too long and will automatically be truncated to the correct length
				text[2] = EnumChatFormatting.DARK_BLUE + "Enter at your abcdefghijklm";
				text[3] = EnumChatFormatting.DARK_GRAY + "  own risk.";
				text[4] = EnumChatFormatting.DARK_GRAY + "CRASH TEST";
			}
			// Use this easy method to add text to the sign's tile entity:
			setSignText(world, text, x, y, z);
			break;
		case StructureArrays.CUSTOM_SKULL:
			// Easily set the skull type or player name if you know it:
			setSkullData(world, "", customData1, x, y, z);
			break;
		case StructureArrays.ITEM_FRAME:
			ItemStack frame = new ItemStack(Item.itemFrame);
			// To save you lots of trouble, there are ready-made methods to handle placing
			// hanging entities and set ItemFrame items (with or without rotation)
			
			// You need to store the returned facing from setHangingEntity to use later methods
			int facing = setHangingEntity(world, frame, x, y, z);
			
			// Use this method for default rotation:
			setItemFrameStack(world, new ItemStack(customData1,1,0), x, y, z, facing);

			// or this one if you want to specify rotation:
			// setItemFrameStack(world, x, y, z, facing, new ItemStack(customData,1,0),2);
			break;
		case StructureArrays.PAINTING:
			ItemStack painting = new ItemStack(Item.painting);
			facing = setHangingEntity(world, painting, x, y, z);
			// choose painting you want based on custom data; look at EnumArt for painting names
			String custom = (customData1 == 1 ? "Aztec" : "Bomb");
			// use following method to set painting and update client automatically
			setPaintingArt(world, custom, x, y, z, facing);
			break;
		case StructureArrays.RANDOM_HOLE:
			// One way to generate holes would be to set a random int once per structure,
			// then remove only hole blocks with that value, allowing for custom patterns
			//if (random_hole == customData2)
				//world.setBlockToAir(x, y, z);
			
			// another way that doesn't use customData2 would be to use world.rand.nextFloat()
			// use whatever value you want to check against, I used 0.25F so 25% will become holes
			// this way is nice because we don't need to set customData2 for all these blocks
			if (world.rand.nextFloat() < 0.25F)
				world.setBlockToAir(x, y, z);
			break;
		case StructureArrays.SPAWN_VILLAGER:
			// here I'm using customData as the villagerID
			Entity bob = new EntityVillager(world, customData1);
			//Entity X = new EntityHorse(world);
			//((EntityHorse) X).func_110235_q(1026);
			
			// Now use the preset method to avoid spawning in walls
			spawnEntityInStructure(world, bob, x, y, z);
			break;
		default:
			LogHelper.log(Level.WARNING, "No custom method defined for id " + fakeID);
		}
	}
	
	/**
	 * Add all structures to the Structure List
	 */
	static
	{
		Structure structure = new Structure("Hut");
		structure.addBlockArray(StructureArrays.blockArrayNPCHut);
		structure.setFacing(StructureGeneratorBase.EAST);
		// has a buffer layer on the bottom in case no ground; spawn at y-1 for ground level
		structure.setStructureOffset(0, -1, 0);
		structures.add(structure);
		
		structure = new Structure("Blacksmith");
		structure.addBlockArray(StructureArrays.blockArrayNPCBlackSmith);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("Viking Shop");
		structure.addBlockArray(StructureArrays.blockArrayShop);
		structure.setFacing(StructureGeneratorBase.WEST);
		structures.add(structure);
		
		structure = new Structure("Redstone Dungeon");
		structure.addBlockArray(StructureArrays.blockArrayRedstone);
		//structure.setFacing(StructureGeneratorBase.EAST);
		structures.add(structure);
		
		structure = new Structure("Offset Test");
		structure.addBlockArray(StructureArrays.blockArraySpawnTest);
		/*
		structure.addBlockArray(StructureArrays.blockArrayOffsetTest1);
		structure.addBlockArray(StructureArrays.blockArrayOffsetTest2);
		structure.addBlockArray(StructureArrays.blockArrayOffsetTest2);
		structure.addBlockArray(StructureArrays.blockArrayOffsetTest2);
		structure.addBlockArray(StructureArrays.blockArrayOffsetTest1);
		*/
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
	}
}
