package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.network.AbstractPacket;

public class PacketTrackDesignerStartPoint extends AbstractPacket {
    private BlockPos startPos;
    private BlockPos crossHairPos;

    public PacketTrackDesignerStartPoint() {}

    public PacketTrackDesignerStartPoint(BlockPos pos, BlockPos crossHairPos) {
        this.startPos = pos;
        this.crossHairPos = crossHairPos;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeLong(startPos.toLong());
        buffer.writeLong(crossHairPos.toLong());
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.startPos = BlockPos.fromLong(buffer.readLong());
        this.crossHairPos = BlockPos.fromLong(buffer.readLong());
    }

    @Override
    public void handleClientSide(EntityPlayer player) {}

    @Override
    public void handleServerSide(EntityPlayer player) {
        player.getEntityWorld().setBlockState(crossHairPos.up(), RCBlocks.path.getDefaultState());
    }
}