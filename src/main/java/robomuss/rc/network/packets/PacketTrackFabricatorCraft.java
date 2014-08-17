package robomuss.rc.network.packets;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;

public class PacketTrackFabricatorCraft extends FMLProxyPacket {

	public PacketTrackFabricatorCraft(ByteBuf payload, String channel) {
		super(payload, channel);
	}

	private int x, y, z;
	private float[] colors;


	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(this.x);
		buffer.writeInt(this.y);
		buffer.writeInt(this.z);
		buffer.writeFloat(this.colors[0]);
		buffer.writeFloat(this.colors[1]);
		buffer.writeFloat(this.colors[2]);

	}

	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
		this.colors[0] = buffer.readFloat();
		this.colors[1] = buffer.readFloat();
		this.colors[2] = buffer.readFloat();

	}

	public void handleClientSide(EntityPlayer player) {
	}

	public void handleServerSide(EntityPlayer player) {
		/*TileEntityColorizer tileEnt = (TileEntityColorizer) player.worldObj
				.getTileEntity(this.x, this.y, this.z);
		tileEnt.setColorStorage(this.colors);
*/
		System.out.println("PAcket!");
	}

}