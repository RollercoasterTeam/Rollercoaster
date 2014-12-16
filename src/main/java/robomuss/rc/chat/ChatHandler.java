package robomuss.rc.chat;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ChatHandler {
	public ChatHandler() {}

	private static void iCommandSenderReply(ICommandSender player, String message) {
		sendChatToPlayer((EntityPlayer) player, message);
	}

	private static IChatComponent createChatComponent(String message) {
		return new ChatComponentText(message);
	}

	public static void sendChatToPlayer(EntityPlayer player, String message) {
		player.addChatComponentMessage(createChatComponent(message));
	}

	public static void broadcastChatMessageToPlayers(String message) {
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(createChatComponent(message));
	}
}
