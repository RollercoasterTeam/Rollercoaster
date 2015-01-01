package robomuss.rc.chat.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import robomuss.rc.entity.EntityTrain;

import java.util.ArrayList;
import java.util.List;

public class EntityTrainDefaultTestCommandAddSpeed implements ICommand {
	private List aliases;

	public EntityTrainDefaultTestCommandAddSpeed() {
		this.aliases = new ArrayList();
		this.aliases.add("as");
	}

	@Override
	public String getCommandName() {
		return "addspeed";
	}

	@Override
	public String getCommandUsage(ICommandSender commandSender) {
		return "commands.train.add.speed.usage";
	}

	@Override
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender commandSender, String[] input) {

	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return false;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
