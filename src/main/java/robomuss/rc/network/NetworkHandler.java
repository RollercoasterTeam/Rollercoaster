package robomuss.rc.network;

import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.network.packets.PacketTrackFabricatorCraft;

public class NetworkHandler {

    public static void updateTrackFabricatorTE(TileEntityTrackFabricator te, int current_track, int amount) {
        RCMod.packetPipeline.sendToServer(new PacketTrackFabricatorCraft(te.xCoord, te.yCoord, te.zCoord, amount, current_track));
    }

}
