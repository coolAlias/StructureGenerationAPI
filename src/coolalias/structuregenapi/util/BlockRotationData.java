/**
    Copyright (C) <2014> <coolAlias>

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

package coolalias.structuregenapi.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.block.Block;

public class BlockRotationData
{
	/** Valid rotation types. Each type is handled like vanilla blocks of this kind. */
	public static enum Rotation {ANVIL, DOOR, GENERIC, PISTON_CONTAINER, QUARTZ, RAIL, REPEATER,
		SIGNPOST, SKULL, STAIRS, TRAPDOOR, VINE, WALL_MOUNTED, LEVER, WOOD};
	
	/** A mapping of block ids to rotation type for handling rotation. Allows custom blocks to be added. */
	private static final Map<Integer, Rotation> blockRotationData = new HashMap<Integer, Rotation>();
	
	/**
	 * Returns the rotation type for the block id given, or null if no type is registered
	 */
	public static final Rotation getBlockRotationType(int blockId) {
		return blockRotationData.get(blockId);
	}
	
	/**
	 * Maps a block id to a specified rotation type. Allows custom blocks to rotate with structure.
	 * @param blockID a valid block id, 0 to 4095 (4096 total)
	 * @param rotationType types predefined by enumerated type ROTATION
	 * @return false if a rotation type has already been specified for the given blockID
	 */
	public static final boolean registerCustomBlockRotation(int blockID, Rotation rotationType) {
		return registerCustomBlockRotation(blockID, rotationType, false);
	}
	
	/**
	 * Maps a block id to a specified rotation type. Allows custom blocks to rotate with structure.
	 * @param blockID a valid block id, 0 to 4095 (4096 total)
	 * @param rotationType types predefined by enumerated type ROTATION
	 * @param override if true, will override the previously set rotation data for specified blockID
	 * @return false if a rotation type has already been specified for the given blockID
	 */
	public static final boolean registerCustomBlockRotation(int blockID, Rotation rotationType, boolean override)
	{
		if (Block.blocksList[blockID] == null || blockID < 0 || blockID > 4095) {
			throw new IllegalArgumentException("[STRUCTURE GEN API] Error setting custom block rotation for block ID " + blockID + (Block.blocksList[blockID] == null ? "; block was not found in Block.blocksList. Please register your block." : "Valid ids are (0-4095)"));
		}
		
		if (blockRotationData.containsKey(blockID)) {
			LogHelper.log(Level.WARNING, "Block " + blockID + " already has a rotation type." + (override ? " Overriding previous data." : ""));
			if (override) blockRotationData.remove(blockID);
			else return false;
		}
		
		blockRotationData.put(blockID, rotationType);
		
		return true;
	}
	
	/** Set rotation data for vanilla blocks */
	static
	{
		blockRotationData.put(Block.anvil.blockID, Rotation.ANVIL);
		
		blockRotationData.put(Block.doorIron.blockID, Rotation.DOOR);
		blockRotationData.put(Block.doorWood.blockID, Rotation.DOOR);
		
		blockRotationData.put(Block.bed.blockID, Rotation.GENERIC);
		blockRotationData.put(Block.cocoaPlant.blockID, Rotation.GENERIC);
		blockRotationData.put(Block.fenceGate.blockID, Rotation.GENERIC);
		blockRotationData.put(Block.pumpkin.blockID, Rotation.GENERIC);
		blockRotationData.put(Block.pumpkinLantern.blockID, Rotation.GENERIC);
		blockRotationData.put(Block.endPortalFrame.blockID, Rotation.GENERIC);
		blockRotationData.put(Block.tripWireSource.blockID, Rotation.GENERIC);
		
		blockRotationData.put(Block.chest.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.chestTrapped.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.dispenser.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.dropper.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.enderChest.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.furnaceBurning.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.furnaceIdle.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.hopperBlock.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.ladder.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.signWall.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.pistonBase.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.pistonExtension.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.pistonMoving.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.pistonStickyBase.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.railActivator.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.railDetector.blockID, Rotation.PISTON_CONTAINER);
		blockRotationData.put(Block.railPowered.blockID, Rotation.PISTON_CONTAINER);
		
		blockRotationData.put(Block.blockNetherQuartz.blockID, Rotation.QUARTZ);
		
		blockRotationData.put(Block.rail.blockID, Rotation.RAIL);
		
		blockRotationData.put(Block.redstoneComparatorActive.blockID, Rotation.REPEATER);
		blockRotationData.put(Block.redstoneComparatorIdle.blockID, Rotation.REPEATER);
		blockRotationData.put(Block.redstoneRepeaterActive.blockID, Rotation.REPEATER);
		blockRotationData.put(Block.redstoneRepeaterIdle.blockID, Rotation.REPEATER);
		
		blockRotationData.put(Block.signPost.blockID, Rotation.SIGNPOST);
		
		blockRotationData.put(Block.skull.blockID, Rotation.SKULL);
		
		blockRotationData.put(Block.stairsBrick.blockID, Rotation.STAIRS);
		blockRotationData.put(Block.stairsCobblestone.blockID, Rotation.STAIRS);
		blockRotationData.put(Block.stairsNetherBrick.blockID, Rotation.STAIRS);
		blockRotationData.put(Block.stairsNetherQuartz.blockID, Rotation.STAIRS);
		blockRotationData.put(Block.stairsSandStone.blockID, Rotation.STAIRS);
		blockRotationData.put(Block.stairsStoneBrick.blockID, Rotation.STAIRS);
		blockRotationData.put(Block.stairsWoodBirch.blockID, Rotation.STAIRS);
		blockRotationData.put(Block.stairsWoodJungle.blockID, Rotation.STAIRS);
		blockRotationData.put(Block.stairsWoodOak.blockID, Rotation.STAIRS);
		blockRotationData.put(Block.stairsWoodSpruce.blockID, Rotation.STAIRS);
		
		blockRotationData.put(Block.trapdoor.blockID, Rotation.TRAPDOOR);
		
		blockRotationData.put(Block.vine.blockID, Rotation.VINE);
		
		blockRotationData.put(Block.lever.blockID, Rotation.LEVER);
		
		blockRotationData.put(Block.stoneButton.blockID, Rotation.WALL_MOUNTED);
		blockRotationData.put(Block.woodenButton.blockID, Rotation.WALL_MOUNTED);
		blockRotationData.put(Block.torchRedstoneActive.blockID, Rotation.WALL_MOUNTED);
		blockRotationData.put(Block.torchRedstoneIdle.blockID, Rotation.WALL_MOUNTED);
		blockRotationData.put(Block.torchWood.blockID, Rotation.WALL_MOUNTED);
		
		blockRotationData.put(Block.wood.blockID, Rotation.WOOD);
	}
}
