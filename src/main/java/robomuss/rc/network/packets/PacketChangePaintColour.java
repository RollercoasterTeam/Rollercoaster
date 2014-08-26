package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.gui.GuiTrackFabricator;
import robomuss.rc.item.RCItems;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.tracks.TrackHandler;

public class PacketChangePaintColour extends AbstractPacket {

    public PacketChangePaintColour() {
    }

    private int meta;

    public PacketChangePaintColour(int meta) {
        this.meta = meta;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(this.meta);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.meta = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    	player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(RCItems.brush, 1, meta));
    }

}