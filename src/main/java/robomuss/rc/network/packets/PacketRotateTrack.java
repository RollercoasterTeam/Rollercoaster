package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import robomuss.rc.block.BlockPath;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.track.TrackManager;

public class PacketRotateTrack extends AbstractPacket {
	private boolean settingDirection;
	private boolean rotateClockwise;
	private BlockPos trackPos;

	public PacketRotateTrack() {}

	public PacketRotateTrack(BlockPos pos, boolean settingDirection, boolean rotateClockwise) {
		this.trackPos = pos;
		this.settingDirection = settingDirection;
		this.rotateClockwise = rotateClockwise;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeLong(trackPos.toLong());
		buffer.writeBoolean(this.settingDirection);
		buffer.writeBoolean(this.rotateClockwise);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.trackPos = BlockPos.fromLong(buffer.readLong());
		this.settingDirection = buffer.readBoolean();
		this.rotateClockwise = buffer.readBoolean();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {}

	@Override
	public void handleServerSide(EntityPlayer player) {
		BlockTrackBase track = TrackManager.getTrackAtCoords(player.getEntityWorld(), trackPos);
        TileEntityTrackBase tile = TrackManager.getTrackTileAtCoords(player.getEntityWorld(), trackPos);

		if (this.settingDirection) {
			IBlockState state = player.getEntityWorld().getBlockState(trackPos);
			EnumFacing facing = (EnumFacing) state.getValue(BlockTrackBase.FACING);
			player.getEntityWorld().setBlockState(trackPos, state.withProperty(BlockTrackBase.FACING, facing), 2);
		} else {
			IBlockState state = player.getEntityWorld().getBlockState(trackPos);
			EnumFacing facing = (EnumFacing) state.getValue(BlockTrackBase.FACING);
			player.getEntityWorld().setBlockState(trackPos, state.withProperty(BlockTrackBase.FACING, rotateClockwise ? facing.rotateY() : facing.rotateYCCW()), 2);
		}
	}
}
