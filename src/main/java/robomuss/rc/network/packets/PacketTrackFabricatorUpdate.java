package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.track.TrackHandler;

public class PacketTrackFabricatorUpdate extends AbstractPacket {
    private BlockPos pos;
    private int amount, current_track;

    public PacketTrackFabricatorUpdate() {}

    public PacketTrackFabricatorUpdate(BlockPos pos, int amount, int current_track) {
        this.pos = pos;
        this.amount = amount;
        this.current_track = current_track;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeLong(pos.toLong());
        buffer.writeInt(amount);
        buffer.writeInt(current_track);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.pos = BlockPos.fromLong(buffer.readLong());
        this.amount = buffer.readInt();
        this.current_track = buffer.readInt();

    }

    @Override
    public void handleClientSide(EntityPlayer player) {}

    @Override
    public void handleServerSide(EntityPlayer player) {
        TileEntityTrackFabricator te = (TileEntityTrackFabricator) player.getEntityWorld().getTileEntity(pos);
        int cost = TrackHandler.pieces.get(current_track).crafting_cost * amount;
        if(te.contents[0] != null) {
        	if(te.contents[0].getItem() == Items.iron_ingot) {
		        if(te.contents[0].stackSize >= cost) {
		        	if(te.contents[1] == null) {
		        		te.contents[0].stackSize -= cost;
		        		if(te.contents[0].stackSize <= 0) {
		        			te.contents[0] = null;
		        		}
		        		
		        		te.contents[1] = new ItemStack(TrackHandler.pieces.get(current_track).block, amount);
		        	}
		        	else if(te.contents[1].getItem() == Item.getItemFromBlock(TrackHandler.pieces.get(current_track).block) && te.contents[1].stackSize + amount <= 64) {
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