package anaso.ReverseVisible;

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
import net.minecraft.util.RegistryNamespaced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ReverseVisibleTick{
	HashMap<String, int[]> Options = new HashMap<String, int[]>();

	String[] WhiteList;
	String[] BlackList;

	private int X = 0, Y = 0;

	RegistryNamespaced blockNamespace = Block.blockRegistry;
	RegistryNamespaced itemNamespace = Item.itemRegistry;

	String[] allBlocks = Arrays.asList(blockNamespace.getKeys().toArray()).toArray(new String[blockNamespace.getKeys().toArray().length]);
	List<Object> enableBlockAndItem = new ArrayList<Object>();

	public ReverseVisibleTick(HashMap Options){
		this.Options = Options;

		WhiteList = (String[]) Options.get("WhiteList");
		BlackList = (String[]) Options.get("BlackList");
		enableBlockAndItem = createEnableObjects(WhiteList, BlackList);
		System.out.println("Reverse Visible enabled " + enableBlockAndItem.size() + " Blocks");
	}

	List<Object> createEnableObjects(String[] WhiteList, String[] BlackList){
		List<Object> enableObject = new ArrayList<Object>();

		for(String whiteName : WhiteList){
			whiteName.trim();
			for(String checkString : allBlocks){
				checkString.trim();
				if(checkString.contains(whiteName)){
					Object blockObject = blockNamespace.getObject(checkString);
					if(blockObject != null){
						// ブロックじゃなかったらアイテムを試す
						blockObject = itemNamespace.getObject(checkString);
					}

					if(!enableObject.contains(blockObject)){
						for(String blackName : BlackList){
							blackName.trim();
							if(checkString.equals(blackName)){
								blockObject = null;
							}
						}
						if(blockObject != null){
							enableObject.add(blockObject);
						}
					}
				}
			}
		}

		return enableObject;
	}

	public void renderReverseVisible(Minecraft MC){
		if(MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY > 0.5D && MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY != 1){
			ScaledResolution XY = new ScaledResolution(MC.gameSettings, MC.displayWidth, MC.displayHeight);
			X = (XY.getScaledHeight() / 2) - 6 - MC.fontRenderer.FONT_HEIGHT;
			Y = (XY.getScaledWidth() / 2);
			MC.fontRenderer.drawStringWithShadow("Reverse", Y, X, 16777215);
		} else if((MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY < 0.5D && MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY != -1 && MC.objectMouseOver.hitVec.yCoord - MC.objectMouseOver.blockY != 0D)){
			ScaledResolution XY = new ScaledResolution(MC.gameSettings, MC.displayWidth, MC.displayHeight);
			X = (XY.getScaledHeight() / 2) + 9;
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
		boolean reBoolean = false;

		if(enableBlockAndItem.contains(haveItem)){
			reBoolean = true;
		} else{
			if(enableBlockAndItem.contains(Block.getBlockFromItem(haveItem))){
				reBoolean = true;
			}
		}

		return reBoolean;
	}
}
