package robomuss.rc.client.gui;

//import robomuss.rc.block.te.TileEntityTrack;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.RCMod;
import robomuss.rc.block.*;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.event.RenderWorldLast;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;

import java.util.ArrayList;

public class GuiHammerOverlay extends GuiIngameForge {
	private Minecraft minecraft;
	private ArrayList<String> textList = new ArrayList<String>();
	private boolean showText = true;
	private int textX = 10;
	private int textY = 110;
	private BlockTrackBase            track;
	private TileEntityTrackBase       teTrack;
	private BlockSupport              support;
	private TileEntitySupport         teSupport;
	private BlockFooter               footer;
	private TileEntityFooter          teFooter;
	private BlockTrackFabricator      fabricator;
	private TileEntityTrackFabricator teFabricator;
	
	public GuiHammerOverlay(Minecraft minecraft) {
		super(minecraft);
		this.minecraft = minecraft;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
	}

	public void clearTextList() {
		this.textList.clear();
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void eventHandler(RenderGameOverlayEvent.Text event) {
		MovingObjectPosition viewEntityTrace = minecraft.thePlayer.rayTrace(20, 20);

		if (viewEntityTrace != null) {
			clearTextList();

			/* TRACK BLOCK */
			if (TrackManager.isBlockAtCoordsTrack(minecraft.theWorld, viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ)) {
				this.teTrack = TrackManager.getTrackTileAtCoords(minecraft.theWorld, viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ);
				this.track = TrackManager.getTrackAtCoords(minecraft.theWorld, viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ);

				if (teTrack.style == null) {
					teTrack.style = TrackHandler.findTrackStyle("corkscrew");
				}

				if (track.track_type == null) {
					track.track_type = TrackHandler.Types.HORIZONTAL.type;
				}

				int meta = minecraft.theWorld.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				int displayMeta = meta > 11 ? meta - 10 : meta;

				textList.add("Track Type: " + track.track_type.unlocalized_name);
				textList.add("Track Style: " + teTrack.style.name);
				textList.add("Track Direction: " + ForgeDirection.getOrientation(displayMeta).name());
				textList.add("Track Meta: " + meta);
				textList.add("Is Dummy: " + teTrack.isDummy);
				textList.add("Dummy ID: " + teTrack.dummyID);
				textList.add("Track Extras: " + (teTrack.extra != null ? teTrack.extra.name : "None"));
				textList.add(String.format("Player Facing: %s (%d)", TrackManager.getDirectionFromPlayerFacing(minecraft.thePlayer).name(), TrackManager.getPlayerFacing(minecraft.thePlayer)));
				textList.add(String.format("Location: X: %d, Y: %d, Z: %d", teTrack.xCoord, teTrack.yCoord, teTrack.zCoord));

				if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.ALL) {
					if (!minecraft.gameSettings.showDebugInfo && this.showText) {
						minecraft.fontRenderer.drawStringWithShadow(textList.get(0), textX, textY, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(1), textX, textY + 10, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(2), textX, textY + 20, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(3), textX, textY + 30, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(4), textX, textY + 40, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(5), textX, textY + 50, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(6), textX, textY + 60, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(7), textX, textY + 70, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(8), textX, textY + 80, 0xFFFFFF);
					}

					return;
				}
				/* CONVEYOR BLOCK */
			} else if (minecraft.theWorld.getBlock(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ) instanceof BlockConveyor) {
				clearTextList();
				textList.add(String.format("Metadata: %d", minecraft.theWorld.getBlockMetadata(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ)));
				textList.add(String.format("Player Facing: %s (%d)", TrackManager.getDirectionFromPlayerFacing(minecraft.thePlayer).name(), TrackManager.getPlayerFacing(minecraft.thePlayer)));

				if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.ALL) {
					if (!minecraft.gameSettings.showDebugInfo && this.showText) {
						minecraft.fontRenderer.drawStringWithShadow(textList.get(0), textX, textY, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(1), textX, textY + 10, 0xFFFFFF);
					}

					return;
				}
				/* SUPPORT BLOCK */
			} else if (minecraft.theWorld.getBlock(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ) instanceof BlockSupport) {
				clearTextList();
				this.teSupport = (TileEntitySupport) minecraft.theWorld.getTileEntity(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ);
				this.support = (BlockSupport) minecraft.theWorld.getBlock(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ);
				this.teFooter = RCMod.supportManager.getFooterFromSupport(teSupport);
				this.footer = this.teFooter != null ? (BlockFooter) minecraft.theWorld.getBlock(this.teFooter.xCoord, this.teFooter.yCoord, this.teFooter.zCoord) : null;

				textList.add("Steel Support");
				textList.add(String.format("Location: X: %d, Y: %d, Z: %d", viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ));
				textList.add("Support Index: " + RCMod.supportManager.getSupportIndex(this.teSupport));
				textList.add("Footer: " + (this.footer == null ? "null" : "exists"));

				if (this.footer != null) {
					textList.add(String.format("Footer Loc: X: %d, Y: %d, Z: %d", this.teFooter.xCoord, this.teFooter.yCoord, this.teFooter.zCoord));
					textList.add("Footer Index: " + RCMod.supportManager.getFooterIndex(this.teFooter));
				}

				if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.ALL) {
					if (!minecraft.gameSettings.showDebugInfo && this.showText) {
						minecraft.fontRenderer.drawStringWithShadow(textList.get(0), textX, textY, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(1), textX, textY + 10, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(2), textX, textY + 20, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(3), textX, textY + 30, 0xFFFFFF);

						if (textList.size() > 7) {
							minecraft.fontRenderer.drawStringWithShadow(textList.get(4), textX, textY + 40, 0xFFFFFF);
							minecraft.fontRenderer.drawStringWithShadow(textList.get(5), textX, textY + 50, 0xFFFFFF);
						}
					}

					return;
				}
				/* FOOTER BLOCK */
			} else if (minecraft.theWorld.getBlock(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ) instanceof BlockFooter) {
				clearTextList();
				this.teFooter = (TileEntityFooter) minecraft.theWorld.getTileEntity(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ);
				this.footer = (BlockFooter) minecraft.theWorld.getBlock(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ);

				textList.add("Steel Support Footer");
				textList.add(String.format("Location: X: %d, Y: %d, Z: %d", viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ));
				textList.add("Index: " + (RCMod.supportManager.getFooterIndex(this.teFooter)));
				textList.add(String.format("Number of Steel Supports: %d", RCMod.supportManager.getSupportStack(teFooter).size()));

				if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.ALL) {
					if (!minecraft.gameSettings.showDebugInfo && this.showText) {
						minecraft.fontRenderer.drawStringWithShadow(textList.get(0), textX, textY, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(1), textX, textY + 10, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(2), textX, textY + 20, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(3), textX, textY + 30, 0xFFFFFF);
					}

					return;
				}
				/* FABRICATOR BLOCK */
			} else if (minecraft.theWorld.getBlock(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ) instanceof BlockTrackFabricator) {
				clearTextList();
				this.teFabricator = (TileEntityTrackFabricator) minecraft.theWorld.getTileEntity(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ);
				this.fabricator = (BlockTrackFabricator) minecraft.theWorld.getBlock(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ);

//				int maxLength = String.format("Location: X: %d, Y: %d, Z: %d%n", viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ).length();

//				StringBuilder builder = new StringBuilder();
//				builder.append("Track Fabricator\n");
//				builder.append(String.format("Location: X: %d, Y: %d, Z: %d%n", viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ));
//				builder.append(String.format("Direction: %s%n", this.teFabricator.direction.name()));
//				String test = builder.toString();
				textList.add("Track Fabricator");
				textList.add(String.format("Location: X: %d, Y: %d, Z: %d", viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ));
				textList.add(String.format("Direction: %s", this.teFabricator.direction.name()));
				textList.add(String.format("Player Facing: %s (%d)", TrackManager.getDirectionFromPlayerFacing(minecraft.thePlayer).name(), TrackManager.getPlayerFacing(minecraft.thePlayer)));

				if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.ALL) {
					if (!minecraft.gameSettings.showDebugInfo && this.showText) {
						minecraft.fontRenderer.drawStringWithShadow(textList.get(0), textX, textY, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(1), textX, textY + 10, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(2), textX, textY + 20, 0xFFFFFF);
						minecraft.fontRenderer.drawStringWithShadow(textList.get(3), textX, textY + 30, 0xFFFFFF);
//						minecraft.fontRenderer.drawSplitString(test, textX, textY, maxLength, 0xFFFFFF);
					}
				}
			} else {
				clearTextList();
			}
		}
		return;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void eventHandler(DrawBlockHighlightEvent event) {
		clearTextList();

		if (TrackManager.isBlockAtCoordsTrack(minecraft.theWorld, event.target.blockX, event.target.blockY, event.target.blockZ)) {}

		return;
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
