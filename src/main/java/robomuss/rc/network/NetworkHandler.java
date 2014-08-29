package robomuss.rc.network;

import net.minecraft.util.MovingObjectPosition;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.network.packets.PacketChangePaintColour;
import robomuss.rc.network.packets.PacketTrackDesignerUpdate;
import robomuss.rc.network.packets.PacketTrackFabricatorUpdate;

public class NetworkHandler {
	
    public static void updateTrackFabricatorTE(TileEntityTrackFabricator te, int current_track, int amount) {
        RCMod.packetPipeline.sendToServer(new PacketTrackFabricatorUpdate(te.xCoord, te.yCoord, te.zCoord, amount, current_track));
    }

	public static void updateTrackBuilderTE(TileEntityTrackDesigner te, MovingObjectPosition movingObjectPosition, int id) {
        RCMod.packetPipeline.sendToServer(new PacketTrackDesignerUpdate(te.xCoord, te.yCoord, te.zCoord, movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ, id));
	}

	public static void changePaintColour(int meta) {
		RCMod.packetPipeline.sendToServer(new PacketChangePaintColour(meta));
	}

}
