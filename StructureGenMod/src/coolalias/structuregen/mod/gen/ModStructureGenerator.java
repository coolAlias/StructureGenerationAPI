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

package coolalias.structuregen.mod.gen;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import coolalias.structuregen.api.util.LogHelper;
import coolalias.structuregen.api.util.Structure;
import coolalias.structuregen.api.util.StructureGeneratorBase;
import coolalias.structuregen.mod.PsionBasicTreeArray;
import coolalias.structuregen.mod.gen.structures.PortcullisArray;
import coolalias.structuregen.mod.gen.structures.StructureArrays;
import coolalias.structuregen.mod.gen.structures.WaterMillArray1;
import coolalias.structuregen.mod.gen.structures.WaterMillArray2;
import coolalias.structuregen.mod.gen.structures.WaterMillArray3;
import coolalias.structuregen.mod.lib.CustomHooks;

public class ModStructureGenerator extends StructureGeneratorBase
{
	/** List storing all structures currently available */
	public static final List<Structure> structures = new LinkedList();

	int random_hole;
	// a better way would be to pass World in to the constructors and set the random_hole
	// value there, but I'm feeling lazy
	boolean value_set = false;

	public ModStructureGenerator(Entity entity, int[][][][] blocks){
		super(entity, blocks);
	}

	public ModStructureGenerator(Entity entity, int[][][][] blocks, int structureFacing) {
		super(entity, blocks, structureFacing);
	}

	public ModStructureGenerator(Entity entity, int[][][][] blocks, int structureFacing, int offX, int offY, int offZ) {
		super(entity, blocks, structureFacing, offX, offY, offZ);
	}

	public ModStructureGenerator() {
		super();
	}

	/**
	 * Allows the use of block ids greater than 4096 as custom 'hooks' to trigger onCustomBlockAdded
	 * @param fakeID Identifier for your 'event'. Absolute value must be greater than 4096
	 * @param customData Custom data may be used to subtype events for given fakeID
	 * @return Returns the real id of the block to spawn in the world; must be <= 4096
	 */
	@Override
	public int getRealBlockID(int fakeID, int customData1)
	{
		LogHelper.log(Level.FINE, "Getting real id from fake id: " + fakeID);
		switch(fakeID) {
		case CustomHooks.CUSTOM_CHEST: return Block.chest.blockID;
		case CustomHooks.CUSTOM_DISPENSER: return Block.dispenser.blockID;
		// Since item frames and paintings both have rotational metadata, we need to
		// return a block id that uses the same rotation algorithm. Torches will work fine.
		case CustomHooks.ITEM_FRAME: return Block.torchWood.blockID;
		case CustomHooks.PAINTING: return Block.torchWood.blockID;
		// using torch block id will spawn the villager post-generation
		case CustomHooks.SPAWN_VILLAGER: return Block.torchWood.blockID;
		case CustomHooks.CUSTOM_SKULL: return Block.skull.blockID;
		case CustomHooks.CUSTOM_SIGNWALL: return Block.signWall.blockID;
		case CustomHooks.CUSTOM_SIGNPOST: return Block.signPost.blockID;
		// For RANDOM_HOLE, we used customData1 to store the real block id
		case CustomHooks.RANDOM_HOLE: return customData1;
		default: return 0;
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
		case CustomHooks.CUSTOM_CHEST:
			// Using the pre-made method addItemToTileInventory adds items to the first slot available

			// Here we use customData1 to subtype our custom chest hook:
			if (customData1 == CustomHooks.CUSTOM_CHEST_1)
			{
				boolean canAdd;
				// This takes advantage of the method's return value to determine when it's full
				do {
					canAdd = addItemToTileInventory(world, new ItemStack(Item.diamond, 64), x, y, z);
					if (canAdd) canAdd = addItemToTileInventory(world, new ItemStack(Item.emerald, 64), x, y, z);
				} while (canAdd);
			}
			// Not our specific chest, so we'll do some generic stuff
			else
			{
				// Here we're using customData1 for stack size to add
				addItemToTileInventory(world, new ItemStack(Item.diamond, customData1), x, y, z);

				// Here we use customData1 to add a metadata block to the chest
				addItemToTileInventory(world, new ItemStack(Block.cloth.blockID, 4, customData1), x, y, z);

				// Here we use both customData1 and customData2 to determine item id and stack size
				addItemToTileInventory(world, new ItemStack(customData1, customData2, 0), x, y, z);

				// Adding potions; check the wiki 'data values' for potion ids
				addItemToTileInventory(world, new ItemStack(Item.potion,1,8206), x, y, z);
				addItemToTileInventory(world, new ItemStack(Item.potion,1,8270), x, y, z);
				addItemToTileInventory(world, new ItemStack(Item.potion,1,8193), x, y, z);
				addItemToTileInventory(world, new ItemStack(Item.potion,1,16385), x, y, z);
			}
			break;
		case CustomHooks.CUSTOM_DISPENSER:
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
		case CustomHooks.CUSTOM_SIGNWALL:
			// SignWall and SignPost are handled in the same way, but we need two custom
			// hook ids to return the different real block ids
			// no 'break' and no 'return', so it gets handled in the next case
		case CustomHooks.CUSTOM_SIGNPOST:
			// An array that stores up to 4 Strings, the max capacity of a sign
			// Best to allocate the array to the size you need
			String[] text;
			// Set different text for each custom sign, using different colors
			if (customData1 == CustomHooks.CUSTOM_SIGN_1)
			{
				// max number of lines is 4; any more than that will be ignored
				text = new String[5];
				text[0] = EnumChatFormatting.DARK_RED + "   BEWARE";
				text[1] = EnumChatFormatting.DARK_RED + "  NO ENTRY";
				// the following string is too long and will automatically be truncated to the correct length
				text[2] = EnumChatFormatting.DARK_BLUE + "Enter at your abcdefghijklm";
				text[3] = EnumChatFormatting.DARK_GRAY + "  own risk.";
				text[4] = "This never prints because its index is greater than signs can handle!";
			}
			else
			{
				// best to allocate only what is needed, here just one line
				text = new String[1];
				text[0] = EnumChatFormatting.BLACK + "A Sign Post";
			}
			// Use this easy method to add text to the sign's tile entity:
			setSignText(world, text, x, y, z);
			break;
		case CustomHooks.CUSTOM_SKULL:
			// Easily set the skull type or player name if you know it:
			setSkullData(world, "", customData1, x, y, z);
			break;
		case CustomHooks.ITEM_FRAME:
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
		case CustomHooks.PAINTING:
			ItemStack painting = new ItemStack(Item.painting);
			facing = setHangingEntity(world, painting, x, y, z);
			// choose painting you want based on custom data; look at EnumArt for painting names
			String custom = (customData1 == 1 ? "Aztec" : "Bomb");
			// use following method to set painting and update client automatically
			setPaintingArt(world, custom, x, y, z, facing);
			break;
		case CustomHooks.RANDOM_HOLE:
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
		case CustomHooks.SPAWN_VILLAGER:
			// here I'm using customData as the villagerID
			Entity bob = new EntityVillager(world, customData1);

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
		/*
		structure = new Structure("Tutorial Home");
		structure.addBlockArray(StructureArrayTutorial.blockArrayTutorial);
		structure.setFacing(StructureGeneratorBase.WEST);
		structures.add(structure);
		 */
		
		structure = new Structure("Blacksmith");
		structure.addBlockArray(StructureArrays.blockArrayNPCBlackSmith);
		structure.setFacing(StructureGeneratorBase.EAST);
		structures.add(structure);

		structure = new Structure("Viking Shop");
		structure.addBlockArray(StructureArrays.blockArrayShop);
		structure.setFacing(StructureGeneratorBase.WEST);
		structures.add(structure);

		structure = new Structure("Redstone Dungeon");
		structure.addBlockArray(StructureArrays.blockArrayRedstone);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structure.setStructureOffset(0, -2, -2);
		structures.add(structure);
		
		structure = new Structure("Spawn Test");
		structure.addBlockArray(StructureArrays.blockArraySpawnTest);
		structure.setFacing(StructureGeneratorBase.EAST);
		structures.add(structure);

		structure = new Structure("WaterMill");
		structure.addBlockArray(WaterMillArray1.blockArrayWaterMill);
		structure.addBlockArray(WaterMillArray2.blockArrayWaterMill);
		structure.addBlockArray(WaterMillArray3.blockArrayWaterMill);
		structure.setFacing(StructureGeneratorBase.EAST);
		structures.add(structure);
		
		structure = new Structure("Redstone Portcullis");
		structure.addBlockArray(PortcullisArray.blockArrayPortcullis);
		structure.setFacing(StructureGeneratorBase.WEST);
		structure.setStructureOffset(0, -6, 0);
		structures.add(structure);
		
		structure = new Structure("Psion Tree Base");
		structure.addBlockArray(PsionBasicTreeArray.psionBasicTreeTrunk);
		structures.add(structure);
		
		structure = new Structure("Psion Tree Canopy");
		structure.addBlockArray(PsionBasicTreeArray.psionBasicTreeCanopy);
		structures.add(structure);
		
		/** DEBUG STRUCTURES for setDefaultOffset */
		/*
		structure = new Structure("3 x 4");
		structure.addBlockArray(StructureArrays.threeByFour);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("4 x 3");
		structure.addBlockArray(StructureArrays.fourByThree);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("2 x 6");
		structure.addBlockArray(StructureArrays.twoBySix);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("6 x 2");
		structure.addBlockArray(StructureArrays.sixByTwo);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("6 x 3");
		structure.addBlockArray(StructureArrays.sixByThree);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("3 x 6");
		structure.addBlockArray(StructureArrays.threeBySix);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("7 x 3");
		structure.addBlockArray(StructureArrays.sevenByThree);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("3 x 7");
		structure.addBlockArray(StructureArrays.threeBySeven);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("7 x 8");
		structure.addBlockArray(StructureArrays.sevenByEight);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("8 x 7");
		structure.addBlockArray(StructureArrays.eightBySeven);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		
		structure = new Structure("Dimension Test");
		structure.addBlockArray(StructureArrays.dimensionTest);
		structure.setFacing(StructureGeneratorBase.NORTH);
		structures.add(structure);
		*/
	}
}
