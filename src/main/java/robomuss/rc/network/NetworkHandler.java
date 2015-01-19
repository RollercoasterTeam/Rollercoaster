package robomuss.rc.network;

import net.minecraft.util.MovingObjectPosition;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.network.packets.*;

public class NetworkHandler {
    public static void updateTrackFabricatorTE(TileEntityTrackFabricator te, int current_track, int amount) {
        RCMod.packetPipeline.sendToServer(new PacketTrackFabricatorUpdate(te.xCoord, te.yCoord, te.zCoord, amount, current_track));
    }

	public static void placeTrackStartPoint(TileEntityTrackDesigner te, MovingObjectPosition movingObjectPosition) {
        RCMod.packetPipeline.sendToServer(new PacketTrackDesignerStartPoint(te.xCoord, te.yCoord, te.zCoord, movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ));
	}
	
	public static void handleTrackDesignerButtonClick(TileEntityTrackDesigner te, int id, MovingObjectPosition xHair, int slection) {
		RCMod.packetPipeline.sendToServer(new PacketTrackDesignerButtonClick(te.xCoord, te.yCoord, te.zCoord, id, xHair, slection));
	}

	public static void changePaintColour(int meta) {
		RCMod.packetPipeline.sendToServer(new PacketChangePaintColour(meta));
	}

	public static void killAll() {
		RCMod.packetPipeline.sendToServer(new PacketKillAll());
	}

	public static void rotateTrack(int trackX, int trackY, int trackZ, int facing, boolean settingDirection, boolean rotateClockwise) {
		RCMod.packetPipeline.sendToServer(new PacketRotateTrack(trackX, trackY, trackZ, facing, settingDirection, rotateClockwise));
	}
}
