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

package coolalias.structuregen.mod.lib;

public class CustomHooks {
	/**
	 * Some predefined hook types for custom hooks that I use in the demo structure
	 * These values are used in place of BlockId and require you to define what the
	 * real block Id is when you define the abstract method 'getRealBlockID'
	 */
	public static final int
		CUSTOM_CHEST = 4096,
		CUSTOM_DISPENSER = 4097,
		ITEM_FRAME = 4098,
		PAINTING = 4099,
		SPAWN_VILLAGER = 4100,
		CUSTOM_SKULL = 4101,
		CUSTOM_SIGNWALL = 4102,
		CUSTOM_SIGNPOST = 4103,
		RANDOM_HOLE = 4104;
	
	/*
	The following values are all used in the CustomData slots of the array and provide
	further information about the custom 'block', such as number of chest contents.
	Note that values can overlap as long as they are for a different hook id
	 */
	
	/** Custom Chest 'id'; used to set a specific chest's contents
	  * I use negative values for these to avoid potential conflicts with ItemIDs */
	public static final int CUSTOM_CHEST_1 = -1;
	
	/** Custom sign 'id', used to set a specific sign's text */
	public static final int CUSTOM_SIGN_1 = 1;
}
