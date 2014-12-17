package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.util.BlockSnapshot;
import robomuss.rc.network.AbstractPacket;

public class PacketTrackPlacement extends AbstractPacket {
	private BlockSnapshot snapshot;
	private BlockPos pos;
	private int trackMeta;
	private int facing;

	public PacketTrackPlacement() {}

	public PacketTrackPlacement(BlockPos pos, int trackMeta, int facing) {
		this.pos = pos;
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
