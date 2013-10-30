package coolalias.structuregen.mod.gen;

import java.util.Random;
import java.util.logging.Level;

import coolalias.structuregen.api.util.LogHelper;
import coolalias.structuregen.api.util.Structure;
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
			// 25% chance of a single structure per chunk; could make a weighted list
			// Recall that a chunk is only 16x16 blocks in area, so this is quite a lot of structures
			if (random.nextFloat() < 0.25F)
				generateStructure(world, random, chunkX * 16, chunkZ * 16);
			break;
		default:
			break;
		}
	}

	private final void generateStructure(World world, Random rand, int chunkX, int chunkZ)
	{
		// Need to create a new instance each time or the generate() methods may overlap themselves and cause a crash
		ModStructureGenerator gen = new ModStructureGenerator();
		int struct; // This will store a random index of the structure to generate

		struct = rand.nextInt(gen.structures.size());
		LogHelper.log(Level.INFO, "[GEN] Generating " + gen.structures.get(struct).name);
		int x = chunkX + rand.nextInt(16);
		int z = chunkZ + rand.nextInt(16);

		// nice way of getting a height to work from; it returns the topmost
		// non-air block at an x/z position, such as tall grass, dirt or leaves
		int y = world.getHeightValue(x, z);

		// find ground level, ignoring blocks such as grass and water
		while (!world.doesBlockHaveSolidTopSurface(x, y, z) && y > world.provider.getAverageGroundLevel())
		{
			--y;
		}

		if (!world.doesBlockHaveSolidTopSurface(x, y, z))
		{
			LogHelper.log(Level.INFO, "Failed to find suitable surface. Not generating structure. Block id " + world.getBlockId(x, y, z));
			return;
		}
		int widthX = gen.structures.get(struct).getWidthX();
		int widthZ = gen.structures.get(struct).getWidthZ();
		int height = gen.structures.get(struct).getHeight();

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
