package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.network.AbstractPacket;

public class PacketTrackFabricatorCraft extends AbstractPacket {

    public PacketTrackFabricatorCraft() {
    }

    private int x, y, z;
    private int amount, current_track;

    public PacketTrackFabricatorCraft(int x, int y, int z, int amount, int current_track) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.amount = amount;
        this.current_track = current_track;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        buffer.writeInt(amount);
        buffer.writeInt(current_track);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.amount = buffer.readInt();
        this.current_track = buffer.readInt();

    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        World worldObj = player.worldObj;

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        int dimensionID = player.dimension;
        World worldObj = player.worldObj;
        System.out.println(x + ":" + y + ":" + z + ":" + current_track + ":" + amount);
        worldObj.setBlock(x, y + 1, z, RCBlocks.ride_fence);
    }

}