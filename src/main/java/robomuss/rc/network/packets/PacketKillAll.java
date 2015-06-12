package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import robomuss.rc.network.AbstractPacket;

public class PacketKillAll extends AbstractPacket {

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		World world = player.worldObj;
		for(int i = 0; i < world.loadedEntityList.size(); i++) {
			Entity entity = (Entity) world.loadedEntityList.get(i);
			entity.setDead();
		}
	}

}
