package robomuss.rc.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.network.packets.PacketChangePaintColour;
import robomuss.rc.network.packets.PacketKillAll;
import robomuss.rc.network.packets.PacketTrackDesignerButtonClick;
import robomuss.rc.network.packets.PacketTrackDesignerDeleteBlock;
import robomuss.rc.network.packets.PacketTrackDesignerPlaceOtherTrack;
import robomuss.rc.network.packets.PacketTrackDesigner;
import robomuss.rc.network.packets.PacketTrackFabricatorUpdate;

public class NetworkHandler {
	
    public static void updateTrackFabricatorTE(TileEntityTrackFabricator te, int current_track, int amount) {
        RCMod.packetPipeline.sendToServer(new PacketTrackFabricatorUpdate(te.xCoord, te.yCoord, te.zCoord, amount, current_track));
    }

	public static void tdUpdate(PacketTrackDesigner.types type, TileEntity te, MovingObjectPosition pos, int flag) {
		RCMod.packetPipeline.sendToServer(new PacketTrackDesigner(type.ordinal(), te.xCoord, te.yCoord, te.zCoord, pos.blockX, pos.blockY, pos.blockZ, flag));
	}
	
	public static void tdUpdate(PacketTrackDesigner.types type, TileEntity te, int flag) {
		RCMod.packetPipeline.sendToServer(new PacketTrackDesigner(type.ordinal(), te.xCoord, te.yCoord, te.zCoord, flag));
	}
	
	public static void tdUpdate(PacketTrackDesigner.types type, TileEntity te, int flag, String name) {
		RCMod.packetPipeline.sendToServer(new PacketTrackDesigner(type.ordinal(), te.xCoord, te.yCoord, te.zCoord, flag, name));
	}

	public static void changePaintColour(int meta) {
		RCMod.packetPipeline.sendToServer(new PacketChangePaintColour(meta));
	}

	public static void killAll() {
		RCMod.packetPipeline.sendToServer(new PacketKillAll());
	}

	public static void deleteBlock(int x, int y, int z) {
		RCMod.packetPipeline.sendToServer(new PacketTrackDesignerDeleteBlock(x, y, z));
	}
}
