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

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.Configuration;

import org.lwjgl.input.Keyboard;

import coolalias.structuregen.mod.handlers.SGTKeyHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SGTKeyBindings
{
	/** Key index for easy handling */
	public static final byte PLUS_X = 0, MINUS_X = 1, PLUS_Z = 2, MINUS_Z = 3, OFFSET_Y = 4,
			INVERT_Y = 5, RESET_OFFSET = 6, ROTATE = 7, NEXT_STRUCT = 8, PREV_STRUCT = 9,
			TOGGLE_REMOVE = 10;
	
	/** Key descriptions */
	private static final String[] desc = {"Move structure forward", "Move structure back",
		"Move structure right", "Move structure left", "Raise/lower structure","Invert raise/lower",
		"Reset offsets", "Rotate 90 degrees", "Next Structure", "Previous Structure", "Toggle Remove"
	};
	
	/** Default key values */
	private static final int[] keyValues = {Keyboard.KEY_UP, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT,
		Keyboard.KEY_LEFT, Keyboard.KEY_Y, Keyboard.KEY_I, Keyboard.KEY_U, Keyboard.KEY_O,
		Keyboard.KEY_RBRACKET, Keyboard.KEY_LBRACKET, Keyboard.KEY_V
	};
	
	/** Maps Keyboard values to SGT KeyBinding values */
	public static final Map<Integer, Byte> SGTKeyMap = new HashMap<Integer, Byte>();
	
	public static void init(Configuration config)
	{
		KeyBinding[] key = new KeyBinding[desc.length];
		boolean[] repeat = new boolean[desc.length];
		
		for (int i = 0; i < desc.length; ++i)
		{
			key[i] = new KeyBinding(desc[i], config.get(SGTKeyHandler.label, desc[i], keyValues[i]).getInt());
			repeat[i] = false;
			SGTKeyMap.put(key[i].keyCode, (byte) i);
		}
		
        KeyBindingRegistry.registerKeyBinding(new SGTKeyHandler(key, repeat));
	}
}
