/**
 * @author coolAlias
 * @license This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package coolalias.structuregen;

import coolalias.structuregen.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class BaseModItem extends Item {

	public BaseModItem(int par1)
	{
		super(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(ModInfo.MOD_ID + ":" + this.getUnlocalizedName().substring(5));
	}

}
