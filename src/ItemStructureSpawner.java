/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStructureSpawner extends BaseModItem
{
	private static final StructureGeneratorBase gen = new WorldGenStructure(true);
	
	private int offsetX = 0, offsetY = 0, offsetZ = 0;
	
	private boolean increment = true;

	public ItemStructureSpawner(int par1)
	{
		super(par1);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean isCurrentItem)
	{
		if (!world.isRemote && entity instanceof EntityPlayer && 
			isCurrentItem && FMLClientHandler.instance().getClient().inGameHasFocus)
		{
			EntityPlayer player = (EntityPlayer) entity;
			if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
				gen.rotateStructureFacing();
				player.addChatMessage("[STRUCTURE GEN] Structure orientation rotated to face " + gen.currentStructureFacing());
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
				if (increment) {
					++this.offsetX;
					player.addChatMessage("[STRUCTURE GEN] Incremented x offset: " + this.offsetX);
				} else {
					--this.offsetX;
					player.addChatMessage("[STRUCTURE GEN] Decremented x offset: " + this.offsetX);
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
				if (increment) {
					++this.offsetY;
					player.addChatMessage("[STRUCTURE GEN] Incremented y offset: " + this.offsetY);
				} else {
					--this.offsetY;
					player.addChatMessage("[STRUCTURE GEN] Decremented y offset: " + this.offsetY);
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
				if (increment) {
					++this.offsetZ;
					player.addChatMessage("[STRUCTURE GEN] Incremented z offset: " + this.offsetZ);
				} else {
					--this.offsetZ;
					player.addChatMessage("[STRUCTURE GEN] Decremented z offset: " + this.offsetZ);
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
				this.offsetX = this.offsetY = this.offsetZ = 0;
				player.addChatMessage("[STRUCTURE GEN] Offset x y z reset to 0.");
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
				player.addChatMessage("[STRUCTURE GEN] Structure will " + (gen.toggleRemoveStructure() ? "be removed" : "generate") + " on right click.");
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
				this.increment = !this.increment;
				player.addChatMessage("[STRUCTURE GEN] Offsets will now " + (this.increment ? "increment." : "decrement."));
			}
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
		// NOTE: It isn't absolutely necessary to check if the world is not remote here,
		// but I recommend it as the client will be notified automatically anyway.
		
		if (!world.isRemote)
		{
			gen.setPlayerFacing(player);
			gen.addBlockArray(StructureArrays.blockArrayNPCHut);

			// adjust for structure generating centered on player's position (including height)
			//gen.setOffset(this.offsetX, this.offsetY, this.offsetZ);
			gen.setDefaultOffset(0, -1, 0); // adjust down one for the buffer layer
			gen.generate(world, world.rand, x, y, z);
		}
		
        return true;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 1;
    }

}
