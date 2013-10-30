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
    
	This class is derived from MineChess's ChessModDrawBlockHighlightHandler,
	found at https://github.com/MineMaarten/MineChess/blob/master/src/chessMod/client/ChessModDrawBlockHighlightHandler.java
	
	which was derived from Equivalent Exchange 3's DrawBlockHighlightHandler,
	found at https://github.com/pahimar/Equivalent-Exchange-3/blob/master/common/com/pahimar/ee3/core/handlers/DrawBlockHighlightHandler.java
 */

package coolalias.structuregen.mod.handlers;

import java.util.logging.Level;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import coolalias.structuregen.api.util.LogHelper;
import coolalias.structuregen.api.util.Structure;
import coolalias.structuregen.mod.items.ItemStructureSpawnerBase;

public class SGTBlockHighlightHandler
{
	public static final int PULSE_RATE = 25, MAX_PULSE = 2000, MIN_PULSE = MAX_PULSE / 2;
	
	/** No variables should be static or they will do weird things in multiplayer */
	private int pulse = MIN_PULSE, offsetX, offsetZ;
	private boolean doInc = true;

	@ForgeSubscribe
	public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent event)
	{
		if (!event.player.isSneaking()) { return; }
		
		if (event.currentItem != null && event.currentItem.getItem() instanceof ItemStructureSpawnerBase)
		{
			Structure structure = ((ItemStructureSpawnerBase) event.currentItem.getItem()).getCurrentStructure(event.currentItem);
			
			if (structure == null) {
				LogHelper.log(Level.WARNING, "Null structure during block highlight event.");
				return;
			}
			
			float pulseTransparency = getPulseValue() * 0.75F / 3000f;
			int centerX = structure.getWidthX() / 2, centerZ = structure.getWidthZ() / 2;
			int rotations = getRotations(event.currentItem, event.player);
			setDefaultOffset(event.currentItem);
			
			for (int i = 0; i < structure.getWidthX(); i++)
			{
				for (int j = 0; j < structure.getWidthZ(); j++)
				{
					int rotX = event.target.blockX, rotZ = event.target.blockZ;
					
					switch(rotations) {
					case 0: // Player is looking at the front of the default structure
						rotX += i - centerX + offsetX;
						rotZ += j - centerZ + offsetZ;
						break;
					case 1: // Rotate structure 90 degrees clockwise
						rotX += -(j - centerZ + offsetZ);
						rotZ += i - centerX + offsetX;
						break;
					case 2: // Rotate structure 180 degrees
						rotX += -(i - centerX + offsetX);
						rotZ += -(j - centerZ + offsetZ);
						break;
					case 3: // Rotate structure 270 degrees clockwise
						rotX += j - centerZ + offsetZ;
						rotZ += -(i - centerX + offsetX);
						break;
					}
					
					highlightTile(event.player, rotX, event.target.blockY, rotZ, event.partialTicks, pulseTransparency);
				}
			}
		}
	}
	
	/**
	 * Calculates and returns number of rotations to apply for structure orientation
	 */
	private int getRotations(ItemStack itemstack, EntityPlayer player)
	{
		ItemStructureSpawnerBase spawner = (ItemStructureSpawnerBase) itemstack.getItem();
		Structure structure = spawner.getCurrentStructure(itemstack);
		int orientation = MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360f) + 0.5D) &3;
		int facing = (spawner.getData(itemstack, ItemStructureSpawnerBase.ROTATIONS) + structure.getFacing()) % 4;
		return ((facing % 2 != structure.getFacing() % 2 ? facing + 2 : facing) + orientation) % 4;
	}
	
	/**
	 * Updates and returns pulse value for determining transparency
	 */
	private int getPulseValue()
	{
		pulse += doInc ? PULSE_RATE : -PULSE_RATE;
		doInc = pulse > MAX_PULSE ? false : pulse < MIN_PULSE ? true : doInc;

		return pulse;
	}
	
	/**
	 * Highlights the tile located at x/y/z
	 */
	public static final void highlightTile(EntityPlayer player, double x, double y, double z, float partialTicks, float transparency)
	{
		double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
		double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
		double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
		
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPushMatrix();
		GL11.glTranslated(-iPX + x + 0.5D, -iPY + y + 0.51D, -iPZ + z + 0.5D);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glRotatef(90, -1, 0, 0);
		GL11.glTranslated(0, 0, 0.5f);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		drawQuad(transparency);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDepthMask(true);
	}
	
	/**
	 * Helper method for 'highlightTile', not to be used in isolation
	 */
	private static final void drawQuad(float transparency)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(5.0F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(0F, 1F, 0F, transparency);

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertex(-0.5F, 0.5F, 0F);
		tessellator.addVertex(0.5F, 0.5F, 0F);
		tessellator.addVertex(0.5F, -0.5F, 0F);
		tessellator.addVertex(-0.5F, -0.5F, 0F);
		tessellator.draw();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	private final void setDefaultOffset(ItemStack itemstack)
	{
		ItemStructureSpawnerBase spawner = (ItemStructureSpawnerBase) itemstack.getItem();
		Structure structure = spawner.getCurrentStructure(itemstack);
		int offX = structure.getOffsetX() + spawner.getData(itemstack, ItemStructureSpawnerBase.OFFSET_X);
		int offZ = structure.getOffsetZ() + spawner.getData(itemstack, ItemStructureSpawnerBase.OFFSET_Z);
		
		/** flagNS is true if structure's facing is north or south */
		boolean flagNS = structure.getFacing() % 2 == 0;
		int length = flagNS ? structure.getWidthX() : structure.getWidthZ();
		int adj1 = length - (flagNS ? structure.getWidthZ() : structure.getWidthX());
		
		/** Flag1 tags structures of certain dimension specifications for adjustment */
		boolean flag1 = (flagNS ? (structure.getWidthX() % 2 == 0 && adj1 % 2 == 1) || (structure.getWidthX() % 2 == 1 && adj1 % 2 == -1) : (structure.getWidthX() % 2 == 0 && adj1 % 2 == -1) || (structure.getWidthX() % 2 == 1 && adj1 % 2 == 1));
		
		if (flag1 && !flagNS) { adj1 = -adj1; }
		
		int adj2 = (length+1) % 2;
		int adj3 = adj1 % 2;
		int adj4 = adj1 / 2 + adj3;
		int man = spawner.getData(itemstack, ItemStructureSpawnerBase.ROTATIONS);
		
		switch(structure.getFacing()) {
		case 0: // SOUTH
			offsetZ = offX + length / 2 - (man == 0 ? adj1 / 2 + (adj3 == 0 ? 0 : adj1 < 0 && flag1 ? adj3 : adj2) : man == 1 ? (adj3 == 0 ? adj2 : adj1 > 0 && flag1 ? adj3 : 0) : man == 2 ? adj1 / 2 + (adj3 == 0 || flag1 ? adj2 : adj3) : 0);
			offsetX = -offZ + (man == 0 ? adj2 + (adj3 > 0 && !flag1 ? adj4 : 0) : man == 1 ? (adj3 == 0 ? adj2 : flag1 ? (adj3 < 0 ? -adj3 : 0) : adj3) : man == 2 ? (adj3 > 0 && !flag1 ? adj4 : 0) : 0);
			break;
		case 1: // WEST
			offsetX = offX + length / 2 - (man == 0 ? (flag1 ? -adj4 : adj1 / 2) : man == 2 ? (flag1 ? (adj1 > 0 ? -adj1 / 2 : -adj4) : adj1 / 2 + (adj3 == 0 ? adj2 : 0)) : man == 3 ? (adj3 == 0 || flag1 ? adj2 : -adj3) : 0);
			offsetZ = offZ + (man == 1 ? (adj3 < 0 && !flag1 ? adj4 : adj3 > 0 && flag1 ? (adj1 > 1 ? -adj1 / 2 : -adj4) : 0) + (adj3 == 0 ? -adj2 : 0) : man == 2 ? (adj3 == 0 || flag1 ? -adj2 : adj3) : man == 3 ? (adj3 < 0 && !flag1 ? adj4 : 0) : 0);
			break;
		case 2: // NORTH
			offsetZ = -offX - length / 2 + (man == 0 ? adj1 / 2 + (adj3 == 0 || flag1 ? adj2 : adj3) : man == 2 ? (flag1 ? adj4 : adj1 / 2) : man == 3 ? (adj3 == 0 || flag1 ? adj2 : 0) : 0);
			offsetX = offZ - (man == 0 ? (adj3 > 0 ? adj3 - adj2 : 0) : man == 2 ? (adj3 > 0 ? adj3 : adj2) : man == 3 ? (adj3 > 0 ? adj3 - adj2 : adj3 < 0 ? -adj3 : adj2) : 0);
			break;
		case 3: // EAST
			offsetX = -offX - length / 2 + (man == 0 ? adj1 / 2 + (adj3 == 0 ? adj2 : flag1 ? -adj1 + (adj1 > 0 ? adj3 : 0) : 0) : man == 1 ? (adj3 == 0 || flag1 ? adj2 : -adj3) : man == 2 ? (flag1 ? -adj4 : adj1 / 2) : 0);
			offsetZ = -offZ - (man == 0 ? (adj3 == 0 || flag1 ? -adj2 : adj3) : man == 1 ? (adj3 != 0 && !flag1 ? adj4 : 0) : man == 3 ? (adj3 < 0 && !flag1 ? adj4 : adj3 > 0 && flag1 ? -adj4 : 0) + (adj3 == 0 ? -adj2 : flag1 && adj1 > 1 ? adj3 : 0) : 0);
			break;
		}
		
		int j, k;

		for (int i = 0; i < spawner.getData(itemstack, ItemStructureSpawnerBase.ROTATIONS); ++i)
		{
			j = -offsetZ;
			k = offsetX;
			offsetX = j;
			offsetZ = k;
		}
	}
}
