package coolalias.structuregen.mod;

import java.util.LinkedList;
import java.util.List;

import coolalias.structuregen.api.util.Structure;
import coolalias.structuregen.api.util.StructureGeneratorBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class PsionStructureGenerator extends StructureGeneratorBase
{
	/** List storing all structures currently available */
	public static final List<Structure> structures = new LinkedList();
	
	public PsionStructureGenerator() {}

	public PsionStructureGenerator(Entity entity, int[][][][] blocks) {
		super(entity, blocks);
	}

	public PsionStructureGenerator(Entity entity, int[][][][] blocks, int structureFacing) {
		super(entity, blocks, structureFacing);
	}

	public PsionStructureGenerator(Entity entity, int[][][][] blocks, int structureFacing, int offX, int offY, int offZ) {
		super(entity, blocks, structureFacing, offX, offY, offZ);
	}

	@Override
	public int getRealBlockID(int fakeID, int customData1)
	{
		switch(fakeID) {
		case PsionBasicTreeArray.WOOD_R: return PsionBasicTreeArray.WOOD;
		case PsionBasicTreeArray.LEAF_R: return PsionBasicTreeArray.LEAF;
		case PsionBasicTreeArray.LEAF_WOOD: return PsionBasicTreeArray.WOOD;
		}
		return 0;
	}

	@Override
	public void onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData1, int customData2)
	{
		switch(fakeID) {
		case PsionBasicTreeArray.WOOD_R:
			if (world.rand.nextFloat() < 0.25) { world.setBlockToAir(x, y, z); }
			break;
		case PsionBasicTreeArray.LEAF_R:
			if (world.rand.nextFloat() < 0.15) { world.setBlockToAir(x, y, z); }
			break;
		case PsionBasicTreeArray.LEAF_WOOD: // 50% chance of being leaves instead of wood
			if (world.rand.nextFloat() < 0.51) { world.setBlock(x, y, z, PsionBasicTreeArray.LEAF); }
			break;
		}
	}
	
	public static Structure getTreeBase() {
		return structures.get(0);
	}
	
	public static Structure getTreeCanopy() {
		return structures.get(1);
	}
	
	static {
		Structure structure;
		
		structure = new Structure("Psion Tree Base");
		structure.addBlockArray(PsionBasicTreeArray.psionBasicTreeTrunk);
		structures.add(structure);
		
		structure = new Structure("Psion Tree Canopy");
		structure.addBlockArray(PsionBasicTreeArray.psionBasicTreeCanopy);
		structures.add(structure);
	}
}
