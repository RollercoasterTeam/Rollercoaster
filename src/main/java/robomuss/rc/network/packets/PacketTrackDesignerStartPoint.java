package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.network.AbstractPacket;

public class PacketTrackDesignerStartPoint extends AbstractPacket {

    public PacketTrackDesignerStartPoint() {
    	
    }

    private int x, y, z;
    private int crosshairX, crosshairY, crosshairZ;

    public PacketTrackDesignerStartPoint(int x, int y, int z, int crosshairX, int crosshairY, int crosshairZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.crosshairX = crosshairX;
        this.crosshairY = crosshairY;
        this.crosshairZ = crosshairZ;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        
        buffer.writeInt(this.crosshairX);
        buffer.writeInt(this.crosshairY);
        buffer.writeInt(this.crosshairZ);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        
        this.crosshairX = buffer.readInt();
        this.crosshairY = buffer.readInt();
        this.crosshairZ = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    	TileEntityTrackDesigner te = (TileEntityTrackDesigner) player.worldObj.getTileEntity(x, y, z);
    	te.currentPosX = this.crosshairX;
    	te.currentPosY = this.crosshairY + 1;
    	te.currentPosZ = this.crosshairZ;
    	player.worldObj.setBlock(crosshairX, crosshairY + 1, crosshairZ, RCBlocks.path);
    }
}