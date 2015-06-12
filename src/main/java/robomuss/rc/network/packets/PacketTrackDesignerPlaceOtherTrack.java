package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.track.TrackHandler;

public class PacketTrackDesignerPlaceOtherTrack extends AbstractPacket {

    public PacketTrackDesignerPlaceOtherTrack() {
    	
    }

    private int x, y, z;
    private int crosshairX, crosshairY, crosshairZ;
    private int direction, colour;
    private int trackType; // 0 is curve 1 is slope up 2 is slope 3 is slope down

    public PacketTrackDesignerPlaceOtherTrack(int x, int y, int z, int crosshairX, int crosshairY, int crosshairZ, int direction, int colour, int trackType) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.crosshairX = crosshairX;
        this.crosshairY = crosshairY;
        this.crosshairZ = crosshairZ;
        
        this.direction = direction;
        this.colour = colour;
        
        this.trackType = trackType;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        
        buffer.writeInt(this.crosshairX);
        buffer.writeInt(this.crosshairY);
        buffer.writeInt(this.crosshairZ);
        
        buffer.writeInt(this.direction);
        buffer.writeInt(this.colour);
        
        buffer.writeInt(this.trackType);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        
        this.crosshairX = buffer.readInt();
        this.crosshairY = buffer.readInt();
        this.crosshairZ = buffer.readInt();
        
        this.direction = buffer.readInt();
        this.colour = buffer.readInt();
        
        this.trackType = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    	
    	
		player.worldObj.setBlock(crosshairX, crosshairY, crosshairZ, trackType == 0 ? TrackHandler.tracks.get(4).block : trackType == 1 ? TrackHandler.tracks.get(1).block : trackType == 2 ? TrackHandler.tracks.get(2).block : TrackHandler.tracks.get(3).block);
	
		TileEntityTrack tet = new TileEntityTrack();
		tet.xCoord = crosshairX;
		tet.yCoord = crosshairY;
		tet.zCoord = crosshairZ;
		
		tet.direction = direction;
		tet.colour = colour;
		
		player.worldObj.setTileEntity(crosshairX, crosshairY, crosshairZ, tet);
    	player.worldObj.markBlockForUpdate(crosshairX, crosshairY, crosshairZ);
    }
}