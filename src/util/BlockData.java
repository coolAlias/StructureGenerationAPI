package coolalias.structuregen.util;

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
