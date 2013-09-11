/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen.util;

/**
 * Stores all data needed for post-gen processing, specifically for custom 'hooks'
 */
public class BlockData
{
	private final int x, y, z, id, meta, customData1, customData2;
	
	public BlockData(int x, int y, int z, int id, int meta, int customData1, int customData2)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
		this.meta = meta;
		this.customData1 = customData1;
		this.customData2 = customData2;
	}
	
	public final int getPosX() {
		return this.x;
	}
	
	public final int getPosY() {
		return this.y;
	}
	
	public final int getPosZ() {
		return this.z;
	}
	
	public final int getBlockID() {
		return this.id;
	}
	
	public final int getMetaData() {
		return this.meta;
	}
	
	public final int getCustomData1() {
		return this.customData1;
	}
	
	public final int getCustomData2() {
		return this.customData2;
	}
}
