package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.tracks.TrackHandler;

public class PacketTrackDesignerUpdate extends AbstractPacket {

    public PacketTrackDesignerUpdate() {
    	
    }

    private int x, y, z;
    private int crosshairX, crosshairY, crosshairZ;
    private int id;

    public PacketTrackDesignerUpdate(int x, int y, int z, int crosshairX, int crosshairY, int crosshairZ, int id) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.crosshairX = crosshairX;
        this.crosshairY = crosshairY;
        this.crosshairZ = crosshairZ;
        
        this.id = id;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        
        buffer.writeInt(this.crosshairX);
        buffer.writeInt(this.crosshairY);
        buffer.writeInt(this.crosshairZ);
        
        buffer.writeInt(id);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        
        this.crosshairX = buffer.readInt();
        this.crosshairY = buffer.readInt();
        this.crosshairZ = buffer.readInt();
        
        this.id = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    	/*if(id == 0) {
    		placeTrackPiece(player, "horizontal");
    		TileEntityTrackDesigner te = (TileEntityTrackDesigner) player.worldObj.getTileEntity(x, y, z);
    		te.currentPosX = crosshairX;
    		te.currentPosY = crosshairY;
    		te.currentPosZ = crosshairZ;
    	}
    	if(id == 1) {
    		placeTrackPiece(player, "curve");
    	}
    	if(id == 2) {
    		placeTrackPiece(player, "horizontal");
    	}
    	if(id == 3) {
    		placeTrackPiece(player, "curve");
    	}*/
    }

    
    private void placeTrackPiece(EntityPlayer player, String track) {
    	player.worldObj.setBlock(crosshairX, crosshairY + 1, crosshairZ, TrackHandler.findTrackType(track).block);
	}
}