package robomuss.rc.client.gui;

<<<<<<< HEAD
=======
//import robomuss.rc.block.te.TileEntityTrack;

>>>>>>> master
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
<<<<<<< HEAD
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.event.RenderWorldLast;
import robomuss.rc.track.TrackHandler;
=======
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.RCMod;
import robomuss.rc.block.BlockConveyor;
import robomuss.rc.block.BlockFooter;
import robomuss.rc.block.BlockSupport;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.event.RenderWorldLast;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
>>>>>>> master

import java.util.ArrayList;

public class GuiHammerOverlay extends GuiIngameForge {
	private Minecraft minecraft;
	private ArrayList<String> textList = new ArrayList<String>();
	private boolean showText = true;
	private int textX = 10;
	private int textY = 110;
	private BlockTrackBase      track;
	private TileEntityTrackBase teTrack;
	private BlockSupport        support;
	private TileEntitySupport   teSupport;
	private BlockFooter         footer;
	private TileEntityFooter    teFooter;
	
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
<<<<<<< HEAD
    public void eventHandler(RenderGameOverlayEvent event) {
		MovingObjectPosition pos = rayTraceMouse();
		if(pos != null) {
			if(minecraft.theWorld.getTileEntity(pos.blockX, pos.blockY, pos.blockZ) instanceof TileEntityTrack) {
				TileEntityTrack te = (TileEntityTrack) minecraft.theWorld.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
				if(te.style == null) {
					te.style = TrackHandler.findTrackStyle("corkscrew");
				}
				drawString(minecraft.fontRenderer, "Track Style: " + te.style.name, 10, 100, 0xFFFFFF);
=======
	public void eventHandler(RenderGameOverlayEvent.Text event) {
		MovingObjectPosition viewEntityTrace = minecraft.thePlayer.rayTrace(20, 20);

		if (viewEntityTrace != null) {
			clearTextList();

			if (TrackManager.isBlockAtCoordsTrack(minecraft.theWorld, viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ)) {
				this.teTrack = TrackManager.getTrackTileAtCoords(minecraft.theWorld, viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ);
				this.track = TrackManager.getTrackAtCoords(minecraft.theWorld, viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ);

				if (teTrack.style == null) {
					teTrack.style = TrackHandler.findTrackStyle("corkscrew");
				}

				if (track.track_type == null) {
					track.track_type = TrackHandler.findTrackType("horizontal");
				}

				int meta = minecraft.theWorld.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				int displayMeta = meta > 11 ? meta - 10 : meta;

				textList.add("Track Type: " + track.track_type.unlocalized_name);
				textList.add("Track Style: " + teTrack.style.name);
				textList.add("Track Direction: " + ForgeDirection.getOrientation(displayMeta).name());
				textList.add("Track Meta: " + meta);
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
					}

					return;
				}
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
			} else {
				clearTextList();
>>>>>>> master
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
