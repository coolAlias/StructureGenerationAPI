package coolalias.structuregen.handlers;

import java.util.EnumSet;

import coolalias.structuregen.ModInfo;
import coolalias.structuregen.items.ItemStructureSpawner;
import coolalias.structuregen.lib.KeyBindSGT;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;

public class KeyHandlerSGT extends KeyHandler
{
	private EnumSet tickTypes = EnumSet.of(TickType.PLAYER);
	public static final String label = ModInfo.MOD_NAME + " Key";
			
	public KeyHandlerSGT(KeyBinding[] keyBindings, boolean[] repeatings)
	{
		super(keyBindings, repeatings);
	}

	public KeyHandlerSGT(KeyBinding[] keyBindings)
	{
		super(keyBindings);
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		if (tickEnd && KeyBindSGT.SGTKeyMap.containsKey(kb.keyCode) && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;
			
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemStructureSpawner)
			{
				ItemStructureSpawner spawner = (ItemStructureSpawner) player.getHeldItem().getItem();
				
				switch(KeyBindSGT.SGTKeyMap.get(kb.keyCode)) {
				case KeyBindSGT.PLUS_X: player.addChatMessage("[STRUCTURE GEN] Incremented x offset: " + spawner.incrementOffset(ItemStructureSpawner.Offset.OFFSET_X)); break;
				case KeyBindSGT.MINUS_X: player.addChatMessage("[STRUCTURE GEN] Decremented x offset: " + spawner.decrementOffset(ItemStructureSpawner.Offset.OFFSET_X)); break;
				case KeyBindSGT.PLUS_Z: player.addChatMessage("[STRUCTURE GEN] Incremented z offset: " + spawner.incrementOffset(ItemStructureSpawner.Offset.OFFSET_Z)); break;
				case KeyBindSGT.MINUS_Z: player.addChatMessage("[STRUCTURE GEN] Decremented z offset: " + spawner.decrementOffset(ItemStructureSpawner.Offset.OFFSET_Z)); break;
				case KeyBindSGT.OFFSET_Y:
					if (spawner.isInverted())
						player.addChatMessage("[STRUCTURE GEN] Decremented y offset: " + spawner.decrementOffset(ItemStructureSpawner.Offset.OFFSET_Y));
					else
						player.addChatMessage("[STRUCTURE GEN] Incremented y offset: " + spawner.incrementOffset(ItemStructureSpawner.Offset.OFFSET_Y));
					break;
				case KeyBindSGT.INVERT_Y: player.addChatMessage("[STRUCTURE GEN] y offset will now " + (spawner.invertY() ? "decrement." : "increment.")); break;
				case KeyBindSGT.RESET_OFFSET:
					spawner.resetOffset();
					player.addChatMessage("[STRUCTURE GEN] Offsets x/y/z reset to 0.");
					break;
				case KeyBindSGT.ROTATE: player.addChatMessage("[STRUCTURE GEN] Structure orientation rotated by " + (spawner.rotate() * 90) + " degrees."); break;
				case KeyBindSGT.PREV_STRUCT: player.addChatMessage("[STRUCTURE GEN] Selected structure " + spawner.getStructureName(spawner.prevStructure()) + " at index " + (spawner.getCurrentStructure() + 1)); break;
				case KeyBindSGT.NEXT_STRUCT: player.addChatMessage("[STRUCTURE GEN] Selected structure " + spawner.getStructureName(spawner.nextStructure()) + " at index " + (spawner.getCurrentStructure() + 1)); break;
				case KeyBindSGT.TOGGLE_REMOVE: player.addChatMessage("[STRUCTURE GEN] Structure will " + (spawner.toggleRemove() ? "be removed" : "generate") + " on right click."); break;
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return tickTypes;
	}

}
