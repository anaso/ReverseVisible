package anaso.ReverseVisible;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;

public class AddBlockID extends CommandBase
{
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
			//var1.sendChatToPlayer("TEST:" + var2[0]);
			ModLoader.getMinecraftInstance().thePlayer.addChatMessage("TEST : " + var2[0]);
		}
	}

}
