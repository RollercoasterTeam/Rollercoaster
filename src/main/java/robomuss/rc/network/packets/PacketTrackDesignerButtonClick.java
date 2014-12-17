package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.track.TrackHandler;

public class PacketTrackDesignerButtonClick extends AbstractPacket {
    private BlockPos pos;

    private int id;
    private MovingObjectPosition movingObjectPosition;
    private int selectedSlot;

    public PacketTrackDesignerButtonClick() {}

    public PacketTrackDesignerButtonClick(BlockPos pos, int id, MovingObjectPosition xHair, int selection) {
        this.pos = pos;

        this.id = id;
        this.movingObjectPosition = xHair;

        this.selectedSlot = selection;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeLong(pos.toLong());
        buffer.writeLong(movingObjectPosition.getBlockPos().toLong());
        buffer.writeFloat((float) movingObjectPosition.hitVec.xCoord);
        buffer.writeFloat((float) movingObjectPosition.hitVec.yCoord);
        buffer.writeFloat((float) movingObjectPosition.hitVec.zCoord);
        buffer.writeInt(this.id);
        buffer.writeInt(selectedSlot);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.pos = BlockPos.fromLong(buffer.readLong());
        BlockPos tempPos = BlockPos.fromLong(buffer.readLong());
        EnumFacing tempFacing = EnumFacing.getFacingFromVector(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        this.movingObjectPosition = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.BLOCK, new Vec3(tempPos.getX(), tempPos.getY(), tempPos.getZ()), tempFacing, tempPos);
        this.id = buffer.readInt();
        this.selectedSlot = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        if (player.getEntityWorld().isAirBlock(movingObjectPosition.getBlockPos().up())) {
            IBlockState state = TrackHandler.pieces.get(selectedSlot).block.getDefaultState();
            player.getEntityWorld().setBlockState(movingObjectPosition.getBlockPos().up(), state.withProperty(BlockTrackBase.FACING, EnumFacing.NORTH));
        }
    }
}