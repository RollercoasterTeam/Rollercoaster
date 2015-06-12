package robomuss.rc.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBin extends TileEntity {
	
	@Override
	public boolean canUpdate() {
		return false;
	}
}
