package anaso.ReverseVisible;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import net.minecraft.src.*;

@Mod
(
	modid = "ReverseVisible",
	name = "Reverse Visible",
	version = "1.6"
)
@NetworkMod
(
		clientSideRequired = false,
		serverSideRequired = false
)

public class ReverseVisible
{
	@SidedProxy(clientSide = "anaso.ReverseVisible.ClientProxy", serverSide = "anaso.ReverseVisible.CommonProxy")
	public static CommonProxy proxy;

	int[] DefaultIDs = {44,53,67,96,108,109,114,126,128,134,135,136,156};

	private boolean EnableDefaultIDs = true;

	private int[] BlockIDs;

	HashMap <String, Object> Options = new HashMap<String, Object>();

	Configuration cfg;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		cfg = new Configuration(event.getSuggestedConfigurationFile());

		int[] empty = {};

		try
		{
			cfg.load();
			Property PropEnableDefaultIDs = cfg.get(cfg.CATEGORY_GENERAL, "Enable Default IDs", true, "true = Enable Default Blocks");
			Property PropIDs  = cfg.get(cfg.CATEGORY_BLOCK, "Use ID", empty);

			EnableDefaultIDs = PropEnableDefaultIDs.getBoolean(true);
			BlockIDs = PropIDs.getIntList();

			Options.put("EnableDefaultIDs", Boolean.valueOf(EnableDefaultIDs));
			Options.put("BlockIDs", BlockIDs);

			if(EnableDefaultIDs)
			{
				int[] marge = new int[DefaultIDs.length + BlockIDs.length];

				System.arraycopy(DefaultIDs, 0, marge, 0, DefaultIDs.length);
				System.arraycopy(BlockIDs, 0, marge, DefaultIDs.length, BlockIDs.length);

				Options.put("BlockIDs", marge);
			}
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

	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		proxy.RegisterTicking(Options);
	}
}