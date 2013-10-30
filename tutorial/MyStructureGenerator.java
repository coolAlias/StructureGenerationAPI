package coolalias.structuregen.tutorial;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import coolalias.structuregen.api.util.Structure;
import coolalias.structuregen.api.util.StructureGeneratorBase;

public class MyStructureGenerator extends StructureGeneratorBase
{
	/** List storing all structures currently available */
	public static final List<Structure> structures = new LinkedList();

	public MyStructureGenerator() {
		// TODO Auto-generated constructor stub
	}

	public MyStructureGenerator(Entity entity, int[][][][] blocks) {
		super(entity, blocks);
		// TODO Auto-generated constructor stub
	}

	public MyStructureGenerator(Entity entity, int[][][][] blocks, int structureFacing) {
		super(entity, blocks, structureFacing);
		// TODO Auto-generated constructor stub
	}

	public MyStructureGenerator(Entity entity, int[][][][] blocks, int structureFacing, int offX, int offY, int offZ) {
		super(entity, blocks, structureFacing, offX, offY, offZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRealBlockID(int fakeID, int customData1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData1, int customData2) {
		// TODO Auto-generated method stub
	
	}
	
	static {
		// A temporary object to store a Structure before adding it to the List
		Structure structure;
		
		// For each structure you need to set 'structure' to a new instance of Structure
		structure = new Structure("Tutorial Home");
		
		// Add all your structure's block arrays to the structure, starting from the bottom
		// and working up; our home, however, only has one array
		structure.addBlockArray(StructureArrayTutorial.blockArrayTutorial);
		
		// Remember to set the structure facing
		structure.setFacing(StructureGeneratorBase.WEST);
		
		// Finally, add the structure to the List
		structures.add(structure);
	}
}
