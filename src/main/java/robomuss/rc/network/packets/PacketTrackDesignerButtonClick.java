package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.track.TrackHandler;

public class PacketTrackDesignerButtonClick extends AbstractPacket {

    public PacketTrackDesignerButtonClick() {
    	
    }

    private int x, y, z;
    private int id;
    private MovingObjectPosition movingObjectPosition;
    private int Xx, Xy, Xz;
    private int selectedSlot;

    public PacketTrackDesignerButtonClick(int x, int y, int z, int id, MovingObjectPosition xHair, int selection) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.id = id;
        this.movingObjectPosition = xHair;

        this.selectedSlot = selection;
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

        buffer.writeInt(selectedSlot);
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
        this.selectedSlot = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        if(player.worldObj.getBlock(Xx, Xy + 1, Xz) == Blocks.air)
    	player.worldObj.setBlock(Xx, Xy + 1, Xz, TrackHandler.pieces.get(selectedSlot).block, 0 , 2);
    }
}