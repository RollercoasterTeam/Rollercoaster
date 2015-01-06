package robomuss.rc.chat.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import robomuss.rc.chat.ChatHandler;
import robomuss.rc.client.gui.GuiHammerOverlay;

import java.util.ArrayList;
import java.util.List;

public class ToggleHammerOverlay implements ICommand {
	private List aliases;

	public ToggleHammerOverlay() {
		this.aliases = new ArrayList();
		this.aliases.add("tho");
	}

	@Override
	public String getCommandName() {
		return "togglehammeroverlay";
	}

	@Override
	public String getCommandUsage(ICommandSender commandSender) {
		return "commands.toggle.hammer.overlay";
	}

	@Override
	public List getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender commandSender, String[] input) {
		String enabled = "Hammer overlay text is now enabled";
		String disabled = "Hammer overlay text is now disabled";

		if (input.length > 1) {
			if (input[1].equals("1") || input[1].equals("true") || input[1].equals("on")) {
				GuiHammerOverlay.showText = true;
				commandSender.addChatMessage(new ChatComponentText(enabled));
			} else if (input[1].equals("0") || input[1].equals("false") || input[1].equals("off")) {
				GuiHammerOverlay.showText = false;
				commandSender.addChatMessage(new ChatComponentText(disabled));
			}
		} else {
			GuiHammerOverlay.showText = !GuiHammerOverlay.showText;
			commandSender.addChatMessage(new ChatComponentText(GuiHammerOverlay.showText ? enabled : disabled));
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
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
