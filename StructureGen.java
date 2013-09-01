package coolalias.structuregen;

import java.io.File;

import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class StructureGen
{
	@Instance(ModInfo.MOD_ID)
	public static StructureGen instance = new StructureGen();
	
	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
	public static CommonProxy proxy;
	
	public static final int MOD_ITEM_INDEX_DEFAULT = 8888;
	private int modItemIndex;
	
	public static Item structureSpawner;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + "/StructureGenMod.cfg"));
        config.load();
        modItemIndex = config.getItem("modItemIndex", MOD_ITEM_INDEX_DEFAULT).getInt() - 256;
        config.save();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		structureSpawner = new ItemStructureSpawner(modItemIndex++).setUnlocalizedName("structureSpawner");
		LanguageRegistry.addName(structureSpawner, "Structure Spawner");
		proxy.registerRenderers();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
