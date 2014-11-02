package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.BlockSnapshot;
import robomuss.rc.network.AbstractPacket;

public class PacketTrackPlacement extends AbstractPacket {
	public PacketTrackPlacement() {}

	private BlockSnapshot snapshot;
	private int trackX, trackY, trackZ;
	private int trackMeta;
	private int facing;

	public PacketTrackPlacement(int trackX, int trackY, int trackZ, int trackMeta, int facing) {
		this.trackX = trackX;
		this.trackY = trackY;
		this.trackZ = trackZ;
		this.trackMeta = trackMeta;
		this.facing = facing;
	}
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {

	}
}
