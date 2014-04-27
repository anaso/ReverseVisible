package anaso.ReverseVisible;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.HashMap;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	public void RegisterTicking(HashMap Options)
	{
		FMLCommonHandler.instance().bus().register(new ReverseVisibleTick(Options));
	}
}