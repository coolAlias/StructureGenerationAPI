package coolalias.structuregen;

import java.util.Random;
import java.util.logging.Level;

import coolalias.structuregen.lib.LogHelper;
import coolalias.structuregen.util.Structure;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldStructureGenerator implements IWorldGenerator
{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		switch(world.provider.dimensionId)
		{
		case -1:
			// not currently generating anything in the nether
			// generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			// 50% chance of a single structure per chunk; could make a weighted list
			// Recall that a chunk is only 16x16 blocks in area, so this is quite a lot of structures
			if (random.nextFloat() < 0.5F)
				generateStructure(world, random, chunkX * 16, chunkZ * 16);
			break;
		default:
			break;
		}
	}

	private final void generateStructure(World world, Random rand, int chunkX, int chunkZ)
	{
		// Need to create a new instance each time or the generate() methods may overlap themselves and cause a crash
		StructureGenerator gen = new StructureGenerator();
		int struct; // This will store a random index of the structure to generate
		
		struct = rand.nextInt(gen.structures.size());
		LogHelper.log(Level.INFO, "[GEN] Generating " + gen.structures.get(struct).name);
		int x = chunkX + rand.nextInt(16);
		int y = 128; // start high and we'll work down
		int z = chunkZ + rand.nextInt(16);

		// find ground level, ignoring blocks such as grass but may build on pre-existing structures such as villages
		while (!world.doesBlockHaveSolidTopSurface(x, y, z) && y > 62)
		{
			--y;
		}
		// Not biome-specific, only dirt for now.
		if (!world.doesBlockHaveSolidTopSurface(x, y, z))
		{
			LogHelper.log(Level.INFO, "Failed to find suitable surface. Not generating structure.");
			return;
		}
		int widthX = gen.structures.get(struct).getWidthX();
		int widthZ = gen.structures.get(struct).getWidthZ();
		int height = gen.structures.get(struct).getHeight();
		
		LogHelper.log(Level.INFO, "Y/X/Z dimensions of structure: " + height + "/" + widthX + "/" + widthZ);
		
		// check if structure will collide with any others in area
		// might be able to use built-in StructureBoundBox
		/*
		for (int i = x - (widthX / 2); i < x + (widthX / 2) ; ++i)
		{
			for (int j = z - (widthZ / 2); j < z + (widthZ / 2); ++j)
			{
				for (int k = y; k < y + height; ++k)
				{
					// check for collisions somehow - no generated structures map to reference
				}
			}
		}
		*/
		// Set structure and random facing, then generate; no offset needed here
		gen.setStructure(gen.structures.get(struct));
		gen.setStructureFacing(rand.nextInt(4));
		gen.generate(world, rand, x, y, z);
	}
}
