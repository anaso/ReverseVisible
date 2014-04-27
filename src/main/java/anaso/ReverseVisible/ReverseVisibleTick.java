package anaso.ReverseVisible;

import com.sun.deploy.util.BlackList;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

import java.util.HashMap;

@SideOnly(Side.CLIENT)
public class ReverseVisibleTick{
	HashMap<String, int[]> Options = new HashMap<String, int[]>();

	String[] WhiteList;
	String[] BlackList;

	private int X = 0, Y = 0;

	public ReverseVisibleTick(HashMap Options){
		this.Options = Options;

		WhiteList = (String[]) Options.get("WhiteList");
		BlackList = (String[]) Options.get("BlackList");
	}

	public void renderReverseVisible(Minecraft MC){
		if(MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY > 0.5D && MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY != 1){
			ScaledResolution XY = new ScaledResolution(MC.gameSettings, MC.displayWidth, MC.displayHeight);
			X = (XY.getScaledHeight() / 2) - (XY.getScaledHeight() / 20);
			Y = (XY.getScaledWidth() / 2);
			MC.fontRenderer.drawStringWithShadow("Reverse", Y, X, 16777215);
		} else if((MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY < 0.5D && MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY != -1)){
			ScaledResolution XY = new ScaledResolution(MC.gameSettings, MC.displayWidth, MC.displayHeight);
			X = (XY.getScaledHeight() / 2) + (XY.getScaledHeight() / 20);
			Y = (XY.getScaledWidth() / 2);
			MC.fontRenderer.drawStringWithShadow("Normal", Y, X, 16777215);
		}
	}

	@SubscribeEvent
	public void RenderTickEvent(TickEvent.RenderTickEvent event){
		Minecraft MC = FMLClientHandler.instance().getClient();

		if(MC.theWorld != null){
			int invSlot = MC.thePlayer.inventory.currentItem;
			ItemStack invItem = MC.thePlayer.inventory.mainInventory[invSlot];

			if(invItem != null && MC.currentScreen == null){
				if(MC.objectMouseOver != null && blockCheck(MC.thePlayer.inventory.mainInventory[invSlot].getItem())){
					if(MC.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
						renderReverseVisible(MC);
					}
				}
			}
		}
	}

	public boolean blockCheck(Item haveItem){
		boolean re = false;
		System.out.println("have item unlocalized name" + haveItem.getUnlocalizedName());

		if(BlackList.length > 0){
			for(int i = 0; i < BlackList.length; i++){
				if(BlackList[i].equals(haveItem.getUnlocalizedName())){
					re = false;
					break;
				}
			}
		}

		return re;
	}
}
