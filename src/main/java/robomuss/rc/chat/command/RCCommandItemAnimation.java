package robomuss.rc.chat.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import robomuss.rc.chat.ChatHandler;
import robomuss.rc.event.RCTickHandler;

public class RCCommandItemAnimation implements ICommand {
	private List aliases;
	public RCCommandItemAnimation() {
		this.aliases = new ArrayList();
		this.aliases.add("tia");
	}

	@Override
	public String getCommandName() {
		return "trackitemanimation";
	}

	@Override
	public String getCommandUsage(ICommandSender commandSender) {
		return "commands.track.item.animation.usage";
	}

	@Override
	public List getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender commandSender, String[] input) {
		String enabled = "ItemBlockTrack animation is now enabled";
		String disabled = "ItemBlockTrack animation is now disabled";

		if (input.length > 1) {
			if (input[1].equals("1") || input[1].equals("true")) {
				RCTickHandler.shouldReflect = true;
				commandSender.addChatMessage(new ChatComponentText(disabled));
			} else if (input[1].equals("0") || input[1].equals("false")) {
				RCTickHandler.shouldReflect = false;
				commandSender.addChatMessage(new ChatComponentText(enabled));
			}
		} else {
			RCTickHandler.shouldReflect = !RCTickHandler.shouldReflect;
			commandSender.addChatMessage(new ChatComponentText(RCTickHandler.shouldReflect ? disabled : enabled));
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
		ChatHandler.broadcastChatMessageToPlayers(commandSender.getCommandSenderName());
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender commandSender, String[] options) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] strings, int i) {
		return false;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
