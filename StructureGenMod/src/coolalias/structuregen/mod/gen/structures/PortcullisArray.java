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
    
    Original design by PearSquirrel: http://www.youtube.com/watch?feature=player_embedded&v=9pG91IfoyAw
 */

package coolalias.structuregen.mod.gen.structures;

import net.minecraft.block.Block;

public class PortcullisArray
{
	private static final int
		BASE = Block.stoneBrick.blockID,
		GATE = Block.netherFence.blockID,
		SAND = Block.sand.blockID,
		STAIRS = Block.stairsStoneBrick.blockID,
		PISTON = Block.pistonBase.blockID,
		STICKY = Block.pistonStickyBase.blockID,
		TORCH = Block.torchRedstoneActive.blockID,
		REPEATER = Block.redstoneRepeaterIdle.blockID,
		WIRE = Block.redstoneWire.blockID,
		LEVER = Block.lever.blockID;
	
	/**
	 * Facing WEST: Front: min x, Back: max x, Left (north): min z, Right (south): max z
	 * REMEMBER that structure will generate opposite player, so north (array left) will
	 * appear on RIGHT side when viewed by player
	 * 
	 * NOTE: When generating the portcullis along the NORTH / SOUTH axis, you must replace
	 * all of the redstone torches sticking in the ground for the gate to work correctly.
	 */
	public static final int[][][][] blockArrayPortcullis =
	{
		{ // y = 1
			{ // x = 1
				// z (11 blocks)
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{	// x = 2
				{0},{0},{TORCH,2},{0},{0},{0},{0},{0},{BASE},{BASE},{LEVER,3}
			},
			{	// x = 3
				{0},{0},{BASE},{0},{0},{0},{0},{0},{REPEATER,7},{0},{0}
			},
			{	// x = 4
				{0},{0},{REPEATER,15},{STICKY,1},{STICKY,1},{STICKY,1},{STICKY,1},{STICKY,1},{WIRE},{WIRE},{0}
			},
			{	// x = 5
				{0},{0},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{REPEATER,1},{REPEATER,9},{0}
			},
			{	// x = 6
				{0},{0},{REPEATER,3},{0},{0},{0},{REPEATER,3},{REPEATER,15},{WIRE},{WIRE},{0}
			},
			{	// x = 7
				{},{0},{BASE},{REPEATER,0},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{0},{0}
			}
		},
		{ // y = 2
			{ // x = 1
				{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{	// x = 2
				{0},{0},{BASE},{TORCH,3},{0},{0},{0},{0},{TORCH,5},{TORCH,5},{0}
			},
			{	// x = 3
				{0},{0},{REPEATER,7},{BASE},{BASE},{BASE},{BASE},{BASE},{0},{0},{0}
			},
			{	// x = 4
				{0},{0},{BASE},{STICKY,1},{STICKY,1},{STICKY,1},{STICKY,1},{STICKY,1},{0},{TORCH,4},{BASE}
			},
			{	// x = 5
				{0},{0},{REPEATER,3},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{0},{0},{0}
			},
			{	// x = 6
				{0},{0},{BASE},{0},{0},{0},{0},{0},{0},{0},{0}
			},
			{	// x = 7
				{},{0},{WIRE},{0},{},{},{},{},{},{},{}
			}
		},
		{ // y = 3
			{ // x = 1
				{},{},{},{},{},{},{},{},{0},{0},{0}
			},
			{	// x = 2
				{},{},{0},{BASE},{0},{0},{0},{0},{BASE},{BASE},{0}
			},
			{	// x = 3
				{},{},{0},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{0},{0},{0}
			},
			{	// x = 4
				{},{0},{WIRE},{PISTON,1},{PISTON,1},{PISTON,1},{PISTON,1},{PISTON,1},{0},{0},{WIRE}
			},
			{	// x = 5
				{},{0},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{0},{0},{BASE}
			},
			{	// x = 6
				{},{},{},{},{},{},{},{},{0},{0},{BASE}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 4
			{ // x = 1
				{},{},{},{},{},{},{},{0},{0},{0},{0}
			},
			{	// x = 2
				{},{},{},{BASE},{BASE},{BASE},{BASE},{BASE},{TORCH,5},{TORCH,5},{0}
			},
			{	// x = 3
				{},{},{},{BASE},{BASE},{BASE},{BASE},{BASE},{0},{BASE},{0}
			},
			{	// x = 4
				{},{},{0},{SAND},{SAND},{SAND},{SAND},{SAND},{0},{0},{0}
			},
			{	// x = 5
				{},{0},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{0},{BASE},{WIRE}
			},
			{	// x = 6
				{},{},{},{},{},{},{},{},{},{BASE},{REPEATER,15}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{BASE}
			}
		},
		{ // y = 5
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{},{},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{BASE},{BASE},{LEVER,3}
			},
			{	// x = 3
				{},{},{},{REPEATER,1},{REPEATER,1},{REPEATER,1},{REPEATER,1},{REPEATER,1},{0},{WIRE},{0}
			},
			{	// x = 4
				{},{},{0},{SAND},{SAND},{SAND},{SAND},{SAND},{0},{BASE},{0}
			},
			{	// x = 5
				{},{},{},{},{},{},{},{},{},{REPEATER,13},{TORCH,2}
			},
			{	// x = 6
				{},{},{},{},{},{},{},{},{},{REPEATER,13},{BASE}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{BASE},{WIRE}
			}
		},
		{ // y = 6
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{WIRE},{0}
			},
			{	// x = 3
				{},{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{0},{0}
			},
			{	// x = 4
				{},{},{BASE},{SAND},{SAND},{SAND},{SAND},{SAND},{BASE},{TORCH,5},{0}
			},
			{	// x = 5
				{},{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{0},{0}
			},
			{	// x = 6
				{},{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{0},{0}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 7
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{LEVER,2},{}
			},
			{	// x = 2
				{},{BASE},{},{},{},{},{},{},{},{BASE},{}
			},
			{	// x = 3
				{BASE},{},{BASE},{},{},{},{},{},{BASE},{},{BASE}
			},
			{	// x = 4
				{BASE},{},{STAIRS,2},{GATE},{GATE},{GATE},{GATE},{GATE},{STAIRS,3},{BASE},{BASE}
			},
			{	// x = 5
				{BASE},{},{BASE},{},{},{},{},{},{BASE},{},{BASE}
			},
			{	// x = 6
				{},{BASE},{},{},{},{},{},{},{},{BASE},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 8
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{BASE},{},{},{},{},{},{},{},{BASE},{}
			},
			{	// x = 3
				{BASE},{},{BASE},{},{},{},{},{},{BASE},{},{BASE}
			},
			{	// x = 4
				{BASE},{},{STAIRS,2},{GATE},{GATE},{GATE},{GATE},{GATE},{STAIRS,3},{TORCH,5},{BASE}
			},
			{	// x = 5
				{BASE},{},{BASE},{},{},{},{},{},{BASE},{},{BASE}
			},
			{	// x = 6
				{},{BASE},{},{},{},{},{},{},{},{BASE},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 9
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{BASE},{},{},{},{},{},{},{},{BASE},{}
			},
			{	// x = 3
				{BASE},{},{BASE},{},{},{},{},{},{BASE},{},{BASE}
			},
			{	// x = 4
				{BASE},{},{STAIRS,2},{GATE},{GATE},{GATE},{GATE},{GATE},{STAIRS,3},{BASE},{BASE}
			},
			{	// x = 5
				{BASE},{},{BASE},{},{},{},{},{},{BASE},{},{BASE}
			},
			{	// x = 6
				{},{BASE},{},{},{},{},{},{},{},{BASE},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 10
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{BASE},{},{},{},{},{},{},{},{BASE},{}
			},
			{	// x = 3
				{BASE},{},{BASE},{},{},{},{},{},{BASE},{},{BASE}
			},
			{	// x = 4
				{BASE},{},{STAIRS,2},{GATE},{GATE},{GATE},{GATE},{GATE},{STAIRS,3},{TORCH,5},{BASE}
			},
			{	// x = 5
				{BASE},{},{BASE},{},{},{},{},{},{BASE},{},{BASE}
			},
			{	// x = 6
				{},{BASE},{},{},{},{},{},{},{},{BASE},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 11
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{BASE},{},{},{},{},{},{},{},{BASE},{}
			},
			{	// x = 3
				{BASE},{},{BASE},{STAIRS,4},{STAIRS,4},{STAIRS,4},{STAIRS,4},{STAIRS,4},{BASE},{},{BASE}
			},
			{	// x = 4
				{BASE},{},{STAIRS,2},{GATE},{GATE},{GATE},{GATE},{GATE},{STAIRS,3},{BASE},{BASE}
			},
			{	// x = 5
				{BASE},{},{BASE},{STAIRS,5},{STAIRS,5},{STAIRS,5},{STAIRS,5},{STAIRS,5},{BASE},{},{BASE}
			},
			{	// x = 6
				{},{BASE},{},{},{},{},{},{},{},{BASE},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 12
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{}
			},
			{	// x = 3
				{BASE},{},{BASE},{STAIRS,4},{STAIRS,4},{STAIRS,4},{STAIRS,4},{STAIRS,4},{BASE},{},{BASE}
			},
			{	// x = 4
				{BASE},{},{STAIRS,2},{BASE},{BASE},{BASE},{BASE},{BASE},{STAIRS,3},{TORCH,5},{BASE}
			},
			{	// x = 5
				{BASE},{},{BASE},{STAIRS,5},{STAIRS,5},{STAIRS,5},{STAIRS,5},{STAIRS,5},{BASE},{},{BASE}
			},
			{	// x = 6
				{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 13
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{}
			},
			{	// x = 3
				{},{BASE},{BASE},{STAIRS,4},{STAIRS,4},{STAIRS,4},{STAIRS,4},{STAIRS,4},{BASE},{BASE},{}
			},
			{	// x = 4
				{},{BASE},{STAIRS,2},{0},{0},{0},{0},{0},{STAIRS,3},{BASE},{BASE}
			},
			{	// x = 5
				{},{BASE},{BASE},{STAIRS,5},{STAIRS,5},{STAIRS,5},{STAIRS,5},{STAIRS,5},{BASE},{BASE},{}
			},
			{	// x = 6
				{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 14
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 3
				{},{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{}
			},
			{	// x = 4
				{},{},{BASE},{0},{0},{0},{0},{0},{},{TORCH,5},{}
			},
			{	// x = 5
				{},{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{}
			},
			{	// x = 6
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 15
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 3
				{},{},{},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{},{}
			},
			{	// x = 4
				{},{},{},{0},{0},{0},{0},{0},{BASE},{BASE},{}
			},
			{	// x = 5
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 6
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 16
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 3
				{},{},{},{},{},{},{},{},{BASE},{},{}
			},
			{	// x = 4
				{},{},{},{PISTON},{PISTON},{PISTON},{PISTON},{PISTON},{REPEATER,3},{TORCH,5},{}
			},
			{	// x = 5
				{},{},{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{},{}
			},
			{	// x = 6
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 17
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 3
				{},{},{},{},{},{},{},{},{},{TORCH,2},{}
			},
			{	// x = 4
				{},{},{},{PISTON},{PISTON},{PISTON},{PISTON},{PISTON},{BASE},{BASE},{}
			},
			{	// x = 5
				{},{},{},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{},{}
			},
			{	// x = 6
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 18
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 3
				{},{},{},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{BASE},{}
			},
			{	// x = 4
				{},{},{},{PISTON},{PISTON},{PISTON},{PISTON},{PISTON},{REPEATER,5},{},{}
			},
			{	// x = 5
				{},{},{},{},{},{},{},{},{BASE},{},{}
			},
			{	// x = 6
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		},
		{ // y = 19
			{ // x = 1
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 2
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 3
				{},{},{},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{WIRE},{}
			},
			{	// x = 4
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 5
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 6
				{},{},{},{},{},{},{},{},{},{},{}
			},
			{	// x = 7
				{},{},{},{},{},{},{},{},{},{},{}
			}
		}
	};
}
