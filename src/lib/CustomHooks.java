package coolalias.structuregen.lib;

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
