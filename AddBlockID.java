package anaso.ReverseVisible;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;

public class AddBlockID extends CommandBase
{
	Configuration cfg;

	AddBlockID(Configuration cfg)
	{
		this.cfg = cfg;
	}

	@Override
	public String getCommandName() {
		return "ReverseVisible_AddBlockID";
	}

	@Override
	public List getCommandAliases()
	{
		ArrayList Aliases = new ArrayList();
		Aliases.add("RVAddID");

		return Aliases;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "ReverseVisible_AddBlockID <Number(AddBlockID)>";
	}

	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
	{
		return MinecraftServer.getServer().isSinglePlayer();
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2)
	{
		if(var2.length == 0)
		{
			//var1.sendChatToPlayer("Not arguments <Number(AddBlockID)>");
			ModLoader.getMinecraftInstance().thePlayer.addChatMessage("No arguments");
		}
		else
		{
			Minecraft MC = ModLoader.getMinecraftInstance();

			if(Integer.parseInt(var2[0]) <= 4096)
			{
				MC.thePlayer.addChatMessage("TEST : " + var2[0]);
			}
			else
			{
				MC.thePlayer.addChatMessage("You Input ItemID! You must input BlockID.");
			}
		}
	}

}
