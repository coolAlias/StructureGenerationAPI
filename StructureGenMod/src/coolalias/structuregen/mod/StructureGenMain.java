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

package coolalias.structuregen.mod;

import java.io.File;

import coolalias.structuregen.api.util.LogHelper;
import coolalias.structuregen.mod.handlers.SGTPacketHandler;
import coolalias.structuregen.mod.items.ItemStructureSpawner;
import coolalias.structuregen.mod.lib.ModInfo;
import coolalias.structuregen.mod.lib.SGTKeyBindings;
import coolalias.structuregen.mod.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
@NetworkMod(clientSideRequired=true, serverSideRequired=false,
channels = {ModInfo.CHANNEL}, packetHandler = SGTPacketHandler.class)

public class StructureGenMain
{
	@Instance(ModInfo.MOD_ID)
	public static StructureGenMain instance = new StructureGenMain();
	
	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
	public static CommonProxy proxy;
	
	public static final int MOD_ITEM_INDEX_DEFAULT = 8888;
	private int modItemIndex;
	
	public static Item structureSpawner;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		LogHelper.init();
		
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + "/StructureGenMod.cfg"));
        config.load();
        
        modItemIndex = config.getItem("modItemIndex", MOD_ITEM_INDEX_DEFAULT).getInt() - 256;
        
        if (FMLCommonHandler.instance().getSide().isClient())
        	SGTKeyBindings.init(config);
        
        config.save();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		structureSpawner = new ItemStructureSpawner(modItemIndex++).setUnlocalizedName("structureSpawner");
		GameRegistry.addShapelessRecipe(new ItemStack(structureSpawner), Item.stick, Block.dirt);
		LanguageRegistry.addName(structureSpawner, "Structure Spawner");
		proxy.registerRenderers();
		
		/** WORLD GENERATION: Uncomment out the following line to have structures randomly generate */
		// GameRegistry.registerWorldGenerator(new WorldStructureGenerator());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
}
