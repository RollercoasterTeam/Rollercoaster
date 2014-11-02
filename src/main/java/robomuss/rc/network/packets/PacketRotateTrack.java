package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.RCMod;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.track.TrackManager;

public class PacketRotateTrack extends AbstractPacket {
	public PacketRotateTrack() {}

	private int[][] rotations = {{5, 4, 2, 3}, {4, 5, 3, 2}};
	private boolean settingDirection;
	private boolean rotateClockwise;
	private int trackX, trackY, trackZ;
//	private ForgeDirection direction;
	private int facing;

	public PacketRotateTrack(int trackX, int trackY, int trackZ, int facing, boolean settingDirection, boolean rotateClockwise) {
		this.trackX = trackX;
		this.trackY = trackY;
		this.trackZ = trackZ;

		this.settingDirection = settingDirection;
		this.rotateClockwise = rotateClockwise;

		this.facing = facing;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(this.trackX);
		buffer.writeInt(this.trackY);
		buffer.writeInt(this.trackZ);
		buffer.writeInt(this.facing);

		buffer.writeBoolean(this.settingDirection);
		buffer.writeBoolean(this.rotateClockwise);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.trackX = buffer.readInt();
		this.trackY = buffer.readInt();
		this.trackZ = buffer.readInt();
		this.facing = buffer.readInt();

		this.settingDirection = buffer.readBoolean();
		this.rotateClockwise = buffer.readBoolean();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		BlockTrackBase track = TrackManager.getTrackAtCoords(player.getEntityWorld(), trackX, trackY, trackZ);
        TileEntityTrackBase tile = TrackManager.getTrackTileAtCoords(player.getEntityWorld(), trackX, trackY, trackZ);
		if (this.settingDirection) {
//			track.setDirection(this.direction, tile);
			player.getEntityWorld().setBlockMetadataWithNotify(trackX, trackY, trackZ, facing, 2);
		} else if (this.rotateClockwise) {
//			tile.rotate(player.getEntityWorld(), tile, rotateClockwise);
			player.getEntityWorld().setBlockMetadataWithNotify(trackX, trackY, trackZ, this.rotations[0][facing - 2], 2);
		} else {
			player.getEntityWorld().setBlockMetadataWithNotify(trackX, trackY, trackZ, this.rotations[1][facing - 2], 2);
		}
//		track.rotate(player.getEntityWorld(), track);
	}
}
