package robomuss.rc.client.gui;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.event.RenderWorldLast;
import robomuss.rc.track.TrackHandler;

public class GuiHammerOverlay extends GuiScreen {
	
	private Minecraft minecraft;
	
	public GuiHammerOverlay(Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	//TODO: fix player gui when in survival and looking at a track block.
	//TODO: add a range limit to when this renders.
	@SubscribeEvent(priority = EventPriority.NORMAL)
    public void eventHandler(RenderGameOverlayEvent event) {
		MovingObjectPosition pos = rayTraceMouse();
		if(pos != null) {
			if(minecraft.theWorld.getTileEntity(pos.blockX, pos.blockY, pos.blockZ) instanceof TileEntityTrack) {
				TileEntityTrack te = (TileEntityTrack) minecraft.theWorld.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
				if(te.style == null) {
					te.style = TrackHandler.findTrackStyle("corkscrew");
				}
				drawString(minecraft.fontRenderer, "Track Style: " + te.style.name, 10, 100, 0xFFFFFF);
			}
		}
	}
	
	private MovingObjectPosition rayTraceMouse() {
		double distance = 1000;

        Vec3 localPos = minecraft.thePlayer.getPosition(1.0f);
        Vec3 look = minecraft.thePlayer.getLook(1.0F).normalize();

        localPos.xCoord += RenderWorldLast.diffX;
        localPos.yCoord += RenderWorldLast.diffY;
        localPos.zCoord += RenderWorldLast.diffZ;

        Vec3 vec32 = localPos.addVector(look.xCoord * distance, look.yCoord * distance, look.zCoord * distance);

        MovingObjectPosition result = minecraft.theWorld.rayTraceBlocks(localPos, vec32);

        return result;
	}
}
