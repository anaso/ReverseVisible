package anaso.ReverseVisible;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.HashMap;

@Mod
(
	modid = "anaso.ReverseVisible",
	version = "1.0"
)
public class ReverseVisible
{
	@SidedProxy(clientSide = "anaso.ReverseVisible.ClientProxy", serverSide = "anaso.ReverseVisible.CommonProxy")
	public static CommonProxy proxy;

	private String[] WhiteList = {"slab", "stairs"};

	private String[] BlackList;

	HashMap <String, Object> Options = new HashMap<String, Object>();

	Configuration cfg;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		cfg = new Configuration(event.getSuggestedConfigurationFile());

		String[] empty = {};

		try
		{
			cfg.load();
			Property PropWhiteList = cfg.get("Block name", "WhiteList", WhiteList);
			Property PropBlackList  = cfg.get("Block name", "BlackList", empty);

			WhiteList = PropWhiteList.getStringList();
			BlackList = PropBlackList.getStringList();

			Options.put("WhiteList", WhiteList);
			Options.put("BlackList", BlackList);

		}
		catch (Exception e)
		{
			System.err.println(e);
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