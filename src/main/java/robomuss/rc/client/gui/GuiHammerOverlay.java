package robomuss.rc.client.gui;

//import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.event.RenderWorldLast;
import robomuss.rc.track.TrackHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;

public class GuiHammerOverlay extends GuiScreen {
	private Minecraft minecraft;
	private ArrayList<String> textList = new ArrayList<String>();
	private boolean showText = true;
	private int textX = 10;
	private int textY = 110;
	
	public GuiHammerOverlay(Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	//TODO: fix player gui when in survival and looking at a track block.
	//TODO: add a range limit to when this renders.
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void eventHandler(RenderGameOverlayEvent.Text event) {
		MovingObjectPosition pos = rayTraceMouse();
		if (pos != null) {
			if (minecraft.theWorld.getBlock(pos.blockX, pos.blockY, pos.blockZ) instanceof BlockTrackBase) {
				BlockTrackBase track = (BlockTrackBase) minecraft.theWorld.getBlock(pos.blockX, pos.blockY, pos.blockZ);
				if (track.style == null) {
					track.style = TrackHandler.findTrackStyle("corkscrew");
				}

				if (track.track_type == null) {
					track.track_type = TrackHandler.findTrackType("horizontal");
				}

				textList.add("Track Type: " + track.track_type.unlocalized_name);
				textList.add("Track Style: " + track.style.name);
				textList.add("Direction: " + track.direction.toString());

				if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.ALL) {
					if (!minecraft.gameSettings.showDebugInfo && this.showText) {
						minecraft.fontRenderer.drawStringWithShadow(textList.get(0), textX, textY, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(1), textX, textY + 10, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(2), textX, textY + 20, 0xFFFFFF);
					}
				}
			}
		}
	}
//	@SubscribeEvent(priority = EventPriority.NORMAL)
//    public void eventHandler(RenderGameOverlayEvent event) {
//		MovingObjectPosition pos = rayTraceMouse();
//		if(pos != null) {
//			if(minecraft.theWorld.getTileEntity(pos.blockX, pos.blockY, pos.blockZ) instanceof TileEntityTrackBase) {
//				TileEntityTrackBase te = (TileEntityTrackBase) minecraft.theWorld.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
//				if (te.track.style == null) {
//					te.track.style = TrackHandler.findTrackStyle("corkscrew");
//				}
//				drawString(minecraft.fontRenderer, "Track Style: " + te.track.style.name, 10, 100, 0xFFFFFF);
//
//
//				if (te.track.direction != null) {
//					switch (te.track.direction) {
//						case NORTH:
//							drawString(minecraft.fontRenderer, "Direction: North", 10, 110, 0xFFFFFF);
//							break;
//						case SOUTH:
//							drawString(minecraft.fontRenderer, "Direction: South", 10, 110, 0xFFFFFF);
//							break;
//						case WEST:
//							drawString(minecraft.fontRenderer, "Direction: West", 10, 110, 0xFFFFFF);
//							break;
//						case EAST:
//							drawString(minecraft.fontRenderer, "Direction: East", 10, 110, 0xFFFFFF);
//							break;
//					}
//				}
//			}
//		}
//	}
	
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
