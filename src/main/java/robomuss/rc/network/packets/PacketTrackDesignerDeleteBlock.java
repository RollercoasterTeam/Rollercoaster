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

public class PacketTrackDesignerDeleteBlock extends AbstractPacket {

    public PacketTrackDesignerDeleteBlock() {
    	
    }

    private int x, y, z;

    public PacketTrackDesignerDeleteBlock(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    	player.worldObj.setBlockToAir(x, y, z);
    }
}