package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.track.TrackHandler;

public class PacketTrackFabricatorUpdate extends AbstractPacket {

    public PacketTrackFabricatorUpdate() {
    }

    private int x, y, z;
    private int amount, current_track;

    public PacketTrackFabricatorUpdate(int x, int y, int z, int amount, int current_track) {
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

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        World world = player.worldObj;
        TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
        int cost = TrackHandler.tracks.get(current_track).crafting_cost * amount;
        if(te.contents[0] != null) {
        	if(te.contents[0].getItem() == Items.iron_ingot) {
		        if(te.contents[0].stackSize >= cost) {
		        	if(te.contents[1] == null) {
		        		te.contents[0].stackSize -= cost;
		        		if(te.contents[0].stackSize <= 0) {
		        			te.contents[0] = null;
		        		}
		        		
		        		te.contents[1] = new ItemStack(TrackHandler.tracks.get(current_track).block, amount);
		        	}
		        	else if(te.contents[1].getItem() == Item.getItemFromBlock(TrackHandler.tracks.get(current_track).block) && te.contents[1].stackSize + amount <= 64) {
		        		te.contents[0].stackSize -= cost;
		        		if(te.contents[0].stackSize <= 0) {
		        			te.contents[0] = null;
		        		}
		        		
		        		te.contents[1].stackSize += amount;
		        	}
		        }
        	}
        }
    }

}