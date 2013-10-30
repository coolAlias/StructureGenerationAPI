package coolalias.structuregen.mod;

import net.minecraft.block.Block;

public class PsionBasicTreeArray
{
	/** Randomized placement for wood, leaves and wood or leaves */
	public static final int WOOD_R = 4096, LEAF_R = 4097, LEAF_WOOD = 4098;
	
	/** Real block IDs; change to Psion wood / leaf IDs */
	public static final int WOOD = Block.wood.blockID, LEAF = Block.leaves.blockID;
	
	public static final int[][][][] psionBasicTreeTrunk =
	{
		{ // y = 0
			{{},{},{},{},{},{},{},{}},
			{{},{},{},{-WOOD},{-WOOD},{-WOOD},{},{}},
			{{},{-WOOD},{-WOOD},{-WOOD},{-WOOD},{-WOOD},{-WOOD},{}},
			{{-WOOD},{-WOOD},{WOOD_R},{WOOD_R},{WOOD_R},{-WOOD},{-WOOD},{-WOOD}},
			{{-WOOD},{-WOOD},{WOOD_R},{WOOD_R},{WOOD_R},{-WOOD},{-WOOD},{-WOOD}},
			{{},{-WOOD},{WOOD_R},{WOOD_R},{WOOD_R},{-WOOD},{-WOOD}},
			{{},{},{-WOOD},{-WOOD},{-WOOD},{-WOOD}},
			{{},{},{},{-WOOD},{-WOOD},{-WOOD}}
		},
		{ // y = 1
			{{},{},{},{},{-WOOD}},
			{{},{},{-WOOD},{-WOOD},{-WOOD},{-WOOD}},
			{{},{-WOOD},{WOOD_R},{WOOD_R},{-WOOD},{-WOOD},{-WOOD}},
			{{-WOOD},{WOOD_R},{WOOD_R},{WOOD_R},{WOOD_R},{-WOOD},{-WOOD},{-WOOD}},
			{{},{-WOOD},{WOOD_R},{WOOD_R},{WOOD_R},{WOOD_R},{-WOOD},{-WOOD}},
			{{},{},{-WOOD},{},{WOOD_R},{WOOD_R},{-WOOD}},
			{{},{},{},{-WOOD},{-WOOD},{-WOOD}}
		},
		{ // y = 2
			{{},{},{},{},{-WOOD}},
			{{},{},{},{-WOOD},{-WOOD},{-WOOD}},
			{{},{},{-WOOD},{WOOD_R},{WOOD_R},{-WOOD},{-WOOD}},
			{{},{-WOOD},{-WOOD},{WOOD_R},{WOOD_R},{WOOD_R},{-WOOD},{-WOOD}},
			{{},{},{},{},{},{-WOOD},{-WOOD}},
			{{},{},{},{},{-WOOD},{-WOOD}}
		},
		{ // y = 3
			{},
			{{},{},{},{},{WOOD}},
			{{},{},{},{WOOD},{WOOD},{WOOD}},
			{{},{},{WOOD},{WOOD},{WOOD},{WOOD},{WOOD}},
			{{},{},{},{},{WOOD},{WOOD}},
			{{},{},{},{},{WOOD}}
		},
		{ // y = 4
			{},
			{{},{},{},{},{WOOD}},
			{{},{},{},{WOOD},{WOOD},{WOOD}},
			{{},{},{WOOD},{WOOD},{WOOD},{WOOD},{WOOD_R}},
			{{},{},{},{},{WOOD},{WOOD}},
			{{},{},{},{},{WOOD_R}}
		},
		// y = 5
		{{},{{},{},{},{},{WOOD}},{{},{},{},{WOOD_R},{WOOD},{WOOD}},{{},{},{WOOD_R},{WOOD},{WOOD},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD},{WOOD}},{{},{},{},{},{WOOD}}},
		{{},{},{{},{},{},{WOOD_R},{WOOD},{WOOD}},{{},{},{},{WOOD},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD},{WOOD}}},
		{{},{},{{},{},{},{WOOD},{WOOD},{WOOD}},{{},{},{},{WOOD},{WOOD},{WOOD}},{{},{},{},{},{WOOD},{WOOD}}},
		{{},{},{{},{},{},{WOOD},{WOOD},{WOOD}},{{},{},{},{WOOD},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD},{WOOD}}},
		{{},{},{{},{},{},{WOOD},{WOOD_R},{WOOD}},{{},{},{},{WOOD},{WOOD},{WOOD}},{{},{},{},{},{WOOD},{WOOD_R}}},
		// y = 10
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		{{},{},{{},{},{},{WOOD_R},{WOOD}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		// y = 15
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		{{},{},{},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}}},
		{{},{},{},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}}},
		{{},{},{},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}}},
		// y = 20
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		{{},{},{{},{},{},{},{WOOD_R}},{{},{},{},{WOOD_R},{WOOD},{WOOD_R}},{{},{},{},{},{WOOD_R}}},
		// y = 25
		{{},{},{},{{},{},{},{},{WOOD}},{{},{},{},{},{WOOD}}},
	};
	
	public static final int[][][][] psionBasicTreeCanopy =
	{
		{ // y = 26
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}},
			{},{},{},{},{},{},{},{},{},{},{},{},
			{{},{},{},{},{},{},{},{},{},{},{WOOD},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{},{},{},{},{},{},{},{},{}
		},
		{ // y = 27
			{},{},{},{},{},{},{},{},{},{},{},{},{},
			{{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{WOOD}}
		},
		{ // y = 28
			{},{},{},{},{},{},{},{},{},{},{},{},
			{{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{WOOD}}
		},
		{ // y = 29
			{},{},{},{},{},{},{},{},{},
			{{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{WOOD},{WOOD}},{},
			{{},{},{},{},{},{},{},{WOOD},{},{},{},{WOOD}}
		},
		{ // y = 30
			{},{},{},{},{},{},{},{},
			{{},{},{},{},{},{},{},{},{},{},{},{WOOD}},{},{},
			{{},{},{},{},{},{},{},{},{WOOD}},{},
			{{},{},{},{},{},{},{},{},{},{WOOD},{WOOD},{WOOD}},{},
			{{},{},{},{},{},{},{WOOD},{},{},{},{},{},{WOOD}}
		},
		{ // y = 31
			{},{},{},{},{},
			// x = 5
			{},{{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{WOOD}},{},{},
			// x = 10
			{{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{WOOD},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{WOOD},{},{WOOD},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			// x = 15
			{},{{},{},{},{},{},{WOOD},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{WOOD}}
		},
		{ // y = 32
			{},{},{},{},{},
			// x = 5
			{{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{},
			{},
			{},
			{{},{},{},{},{},{},{WOOD}},
			// x = 10
			{{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{WOOD},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{WOOD},{},{WOOD},{},{WOOD},{WOOD},{WOOD}},
			{},
			// x = 15
			{{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{WOOD},{},{},{},{},{},{},{WOOD},{},{},{WOOD}},
			{},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{WOOD}}
		},
		{ // y = 33
			{},{},{},{},{{},{},{},{},{},{},{},{},{},{},{LEAF_WOOD},{WOOD}},
			// x = 5
			{},
			{{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			{{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 10
			{{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{LEAF_R}},
			{{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF}},
			{{},{},{},{},{},{LEAF_R},{LEAF},{},{LEAF},{LEAF},{LEAF},{LEAF},{},{},{LEAF_R}},
			{{},{},{},{LEAF_R},{LEAF},{LEAF}},
			// x = 15
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{},{},{},{LEAF},{},{},{},{},{LEAF},{LEAF_R}},
			{{},{},{},{LEAF},{LEAF},{LEAF}},
			{{},{},{},{LEAF_R},{LEAF_R},{},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF_R}},
			// x = 20
			{{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}}
		},
		{ // y = 34
			{},{},{},
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{WOOD}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			// x = 5
			{{},{},{},{},{},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{LEAF_R},{LEAF},{LEAF_R},{},{},{},{},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{},{LEAF_R},{LEAF_WOOD},{LEAF},{LEAF},{},{},{},{LEAF_WOOD},{LEAF},{LEAF},{LEAF_WOOD},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{WOOD},{LEAF_R},{LEAF},{LEAF_WOOD},{LEAF},{},{},{},{},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R},{},{WOOD},{},{},{},{WOOD}},
			// x = 10
			{{},{},{},{},{},{LEAF},{LEAF_R},{},{LEAF},{WOOD},{LEAF_R}},
			{{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF}},
			{{},{},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF}},
			{{},{},{},{WOOD},{},{WOOD},{},{WOOD},{},{WOOD},{},{WOOD},{},{},{},{WOOD},{WOOD}},
			{{},{},{},{LEAF_R},{LEAF},{},{},{},{},{},{LEAF_R},{WOOD},{LEAF},{LEAF},{LEAF}},
			// x = 15
			{{},{},{},{LEAF_R},{LEAF},{LEAF},{},{},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{},{LEAF},{LEAF},{LEAF},{LEAF_WOOD},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{LEAF},{LEAF},{LEAF_WOOD},{LEAF},{LEAF},{LEAF_WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_WOOD},{LEAF},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{},{},{LEAF},{LEAF_R},{},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF_WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{LEAF_R},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{LEAF},{},{LEAF},{LEAF},{LEAF_R}},
			// x = 20
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_WOOD},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}}
		},
		{ // y = 35
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_WOOD},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			// x = 5
			{{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			{{},{},{},{WOOD},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{WOOD},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_WOOD},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{WOOD},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{WOOD},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 10
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			{{},{LEAF_R},{LEAF},{LEAF_R}},
			{{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{LEAF_R},{LEAF},{WOOD},{LEAF},{WOOD},{LEAF_R},{WOOD},{LEAF_R},{WOOD},{WOOD},{},{},{},{},{LEAF_R},{WOOD}},
			{{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{},{},{},{},{LEAF_R},{LEAF}},
			// x = 15
			{{},{LEAF_R},{LEAF},{LEAF},{LEAF_R},{},{},{},{},{},{WOOD},{WOOD},{LEAF},{LEAF},{LEAF_R},{},{LEAF}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{},{LEAF},{LEAF_R},{LEAF},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{},{LEAF},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R},{LEAF_R},{LEAF},{},{LEAF},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{},{},{LEAF},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			// x = 20
			{{},{},{},{},{},{},{},{},{},{LEAF},{},{},{},{LEAF_R},{LEAF},{LEAF_R},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R},{LEAF}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{WOOD},{LEAF},{LEAF_R}}
		},
		{ // y = 36
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{LEAF_R},{LEAF},{LEAF},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF_R}},
			{{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{},{},{},{},{LEAF},{LEAF_R}},
			// x = 5
			{{LEAF_R},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF_R},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			{{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			{{},{LEAF_R},{LEAF},{LEAF},{},{},{},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{LEAF_R},{LEAF_R},{},{},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 10
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF}},
			{{},{},{LEAF},{LEAF},{LEAF},{LEAF},{},{},{},{},{},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF}},
			{{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{},{},{},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF}},
			{{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{WOOD},{LEAF},{WOOD},{},{WOOD},{},{WOOD},{},{},{},{},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF}},
			{{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{},{},{},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF}},
			// x = 15
			{{},{},{LEAF},{LEAF},{LEAF},{LEAF},{},{},{},{WOOD},{},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{WOOD}},
			{},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			// x = 20
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}}
		},
		{ // y = 37
			{},{},{},{{},{},{LEAF}},{{},{LEAF_R},{LEAF},{LEAF_R}},
			// x = 5
			{{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			{{},{LEAF_R},{LEAF},{LEAF},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF_R},{},{},{},{LEAF_R},{LEAF},{WOOD},{LEAF},{LEAF},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{LEAF_R},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{},{},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF_R}},
			// x = 10
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{LEAF_R},{LEAF_R},{},{},{},{},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R},{},{WOOD},{LEAF},{LEAF},{},{},{},{},{},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF},{WOOD},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF_R},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{LEAF_R},{LEAF},{LEAF},{},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF_R},{},{},{},{},{LEAF},{LEAF},{LEAF_R}},
			// x = 15
			{{},{},{},{},{LEAF_R},{},{},{LEAF_R},{WOOD},{LEAF},{LEAF},{LEAF},{},{},{},{},{},{},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{WOOD}}
		},
		{ // y = 38
			{},{},{},{},{},
			// x = 5
			{{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R},{},{},{},{},{},{LEAF_R}},
			{{},{},{},{LEAF_R},{LEAF},{LEAF},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{},{LEAF},{LEAF},{LEAF_R},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF},{WOOD},{LEAF},{LEAF},{},{},{LEAF},{LEAF_R},{},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 10
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{},{},{},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF_R},{LEAF},{},{},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{LEAF_R},{LEAF},{WOOD},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{},{LEAF},{LEAF},{LEAF_R},{},{},{LEAF_R}},
			// x = 15
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF}},
			{{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{WOOD}}
		},
		{ // y = 39
			{},{},{},{},{},
			// x = 5
			{},
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
			{{},{},{},{},{LEAF_R},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{LEAF_R},{LEAF},{LEAF},{},{},{},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 10
			{{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R},{},{LEAF_R}},
			{{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{},{},{},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{},{},{LEAF},{WOOD},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 15
			{{},{},{},{LEAF_R},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{},{},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{},{},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF_R},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R}},
		},
		{ // y = 40
			{},{},{},{},{},
			// x = 5
			{},
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF_R},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF_R},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 10
			{{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 15
			{{},{},{},{},{},{},{},{LEAF_R},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF_R},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R}},
		},
		{ // y = 41
			{},{},{},{},{},
			// x = 5
			{},{},
			{{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF_R},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 10
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{WOOD},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 15
			{{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF_R}}
		},
		{ // y =  42
			{},{},{},{},{},
			// x = 5
			{},{},{},
			{{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF_R},{},{LEAF_R},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			// x = 10
			{{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{},{},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF_R},{},{},{LEAF},{LEAF},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF},{LEAF},{LEAF_R}},
			{{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{LEAF_R},{LEAF_R}},
		}
	};
}
