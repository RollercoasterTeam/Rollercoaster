package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.network.AbstractPacket;

public class PacketTrackDesignerButtonClick extends AbstractPacket {

    public PacketTrackDesignerButtonClick() {
    	
    }

    private int x, y, z;
    private int id;

    public PacketTrackDesignerButtonClick(int x, int y, int z, int id) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.id = id;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        
        buffer.writeInt(this.id);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        
        this.id = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    	TileEntityTrackDesigner te = (TileEntityTrackDesigner) player.worldObj.getTileEntity(x, y, z);
    	//player.worldObj.setBlock(te.currentPosX, te.currentPosY, te.currentPosZ, RCBlocks.path);
    }
}