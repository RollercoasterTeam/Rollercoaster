package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.RCMod;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.track.TrackManager;

public class PacketRotateTrack extends AbstractPacket {
	public PacketRotateTrack() {}

	private boolean settingDirection;
	private boolean rotateClockwise;
	private int trackX, trackY, trackZ;
	private ForgeDirection direction;

	public PacketRotateTrack(int trackX, int trackY, int trackZ, ForgeDirection direction, boolean settingDirection, boolean rotateClockwise) {
		this.trackX = trackX;
		this.trackY = trackY;
		this.trackZ = trackZ;

		this.settingDirection = settingDirection;
		this.rotateClockwise = rotateClockwise;

		this.direction = direction;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(this.trackX);
		buffer.writeInt(this.trackY);
		buffer.writeInt(this.trackZ);
		buffer.writeInt(this.direction.ordinal() - 2);

		buffer.writeBoolean(this.settingDirection);
		buffer.writeBoolean(this.rotateClockwise);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.trackX = buffer.readInt();
		this.trackY = buffer.readInt();
		this.trackZ = buffer.readInt();
		this.direction = ForgeDirection.getOrientation(buffer.readInt() + 2);

		this.settingDirection = buffer.readBoolean();
		this.rotateClockwise = buffer.readBoolean();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		BlockTrackBase track = TrackManager.getTrackAtCoords(trackX, trackY, trackZ);
		if (this.settingDirection) {
			track.setDirection(this.direction);
		} else if (!this.settingDirection && this.rotateClockwise) {
			track.rotate(player.getEntityWorld(), track, rotateClockwise);
		}
//		track.rotate(player.getEntityWorld(), track);
	}
}
