package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.network.AbstractPacket;

public class PacketTrackDesignerButtonClick extends AbstractPacket {

    public PacketTrackDesignerButtonClick() {
    	
    }

    private int x, y, z;
    private int id;
    private MovingObjectPosition movingObjectPosition;
    private int Xx, Xy, Xz;

    public PacketTrackDesignerButtonClick(int x, int y, int z, int id, MovingObjectPosition xHair) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.id = id;
        this.movingObjectPosition = xHair;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        
        buffer.writeInt(this.id);

        buffer.writeInt(movingObjectPosition.blockX);
        buffer.writeInt(movingObjectPosition.blockY);
        buffer.writeInt(movingObjectPosition.blockZ);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        
        this.id = buffer.readInt();

        this.Xx = buffer.readInt();
        this.Xy = buffer.readInt();
        this.Xz = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    	player.worldObj.setBlock(Xx, Xy + 1, Xz, RCBlocks.path, 0 , 2);
    }
}