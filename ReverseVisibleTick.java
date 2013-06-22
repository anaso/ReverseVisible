package anaso.ReverseVisible;

import java.util.HashMap;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.util.EnumMovingObjectType;
import cpw.mods.fml.common.*;

public class ReverseVisibleTick implements ITickHandler
{
	private GuiReverseVisible GuiRV;

	HashMap <String, int[]> Options = new HashMap<String, int[]>();

	private final EnumSet<TickType> tickSet = EnumSet.of(TickType.RENDER);

	int[] BlockIDs;

	private int X = 0, Y = 0;

	public ReverseVisibleTick(HashMap Options)
	{
		this.Options = Options;

		BlockIDs = (int[]) Options.get("BlockIDs");

		//GuiRV = new GuiReverseVisible(ModLoader.getMinecraftInstance());
		//System.out.println("In ReverseVisibleTick");
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
	}

	public void renderReverseVisible()
	{
		Minecraft MC = ModLoader.getMinecraftInstance();

		if(MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY > 0.5D && MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY != 1)
		{
			ScaledResolution XY = new ScaledResolution(MC.gameSettings, MC.displayWidth, MC.displayHeight);
			X = (XY.getScaledHeight() / 2) - (XY.getScaledHeight() / 20);
			Y = (XY.getScaledWidth() / 2);
			//System.out.println("X:" + X + "  Y:" + Y);
			MC.fontRenderer.drawStringWithShadow("Reverse", Y, X, 16777215);
		}
		else if((MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY < 0.5D && MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY != -1))
		{
			ScaledResolution XY = new ScaledResolution(MC.gameSettings, MC.displayWidth, MC.displayHeight);
			X = (XY.getScaledHeight() / 2) + (XY.getScaledHeight() / 20);
			Y = (XY.getScaledWidth() / 2);
			//System.out.println("X:" + X + "  Y:" + Y);
			MC.fontRenderer.drawStringWithShadow("Normal", Y, X, 16777215);
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		Minecraft MC = ModLoader.getMinecraftInstance();

		if(MC.theWorld != null)
		{
			int invSlot = MC.thePlayer.inventory.currentItem;
			ItemStack invItem =  MC.thePlayer.inventory.mainInventory[invSlot];

			if(invItem != null && MC.currentScreen == null)
			{
				if(MC.objectMouseOver != null && ItemCheck(BlockIDs, MC.thePlayer.inventory.mainInventory[invSlot].itemID))
				{
					if(MC.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
					{
						//GuiRV.renderGameOverlay(0, false, 0, 0);
						renderReverseVisible();
					}
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return tickSet;
	}

	@Override
	public String getLabel() { return null; }


	/*
	public void onRenderTick()
	{
		//System.out.println("onRenderTick");
	}

	public void onTickInGUI(GuiScreen guiscreen)
	{
		//System.out.println("onTickInGUI");
	}

	public void onTickInGame()
	{
		Minecraft MC = ModLoader.getMinecraftInstance();
		int invSlot = MC.thePlayer.inventory.currentItem;
		ItemStack invItem =  MC.thePlayer.inventory.mainInventory[invSlot];

		if(invItem != null && MC.currentScreen == null)
		{
			if(MC.objectMouseOver != null && ItemCheck(BlockIDs, invItem.getItem().shiftedIndex))
			{
				if(MC.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
				{
					GuiRV.renderGameOverlay(0, false, 0, 0);
				}
			}
		}
	}
	*/

	public boolean ItemCheck(int[] IDs, int ICHaveItem)
	{
		boolean re = false;


		for(int i=0; i<IDs.length; i++)
		{
			if(IDs[i] == ICHaveItem)
			{
				re = true;
				break;
			}
		}

		return re;
	}
}