package anaso.ReverseVisible;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.TickRegistry;
import net.minecraft.src.*;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.relauncher.Side;
import java.util.HashMap;
import java.util.logging.Level;
import net.minecraft.src.*;

@Mod
(
	modid = "ReverseVisible",
	name = "Reverse Visible",
	version = "1.5.2"
)
@NetworkMod
(
	clientSideRequired = true
)

public class ReverseVisible
{
	@SidedProxy(clientSide = "anaso_ReverseVisible.ClientProxy", serverSide = "anaso_ReverseVisible.CommonProxy")
	public static CommonProxy proxy;

	public static int[] DefaultIDs = {44,53,67,96,108,109,114,126,128,134,135,136,156};

	private int[] BlockIDs;

	HashMap <String, int[]> Options = new HashMap<String, int[]>();

	private GuiReverseVisible GuiRV;

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			Property PropIDs  = cfg.get(cfg.CATEGORY_BLOCK, "Use ID", DefaultIDs);
			BlockIDs = PropIDs.getIntList();

			Options.put("BlockIDs", BlockIDs);

		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Error Message");
		}
		finally
		{
			cfg.save();
		}
	}

	@Mod.Init
	public void Init(FMLInitializationEvent event)
	{
		proxy.RegisterTicking(Options);
		//System.out.println("RV Init");
	}

	/*
	@Mod.PostInit
	public void PostInit(FMLPostInitializationEvent event)
	{
		KeyBinding[] myBinding = {new KeyBinding("WheatHarvest", MouseRightButton)};

		boolean[] myBindingRepeat = {false};

		WheatHarvestKey myKeyHandler = new WheatHarvestKey(myBinding, myBindingRepeat, Options);

		KeyBindingRegistry.registerKeyBinding(myKeyHandler);
	}
	*/
}