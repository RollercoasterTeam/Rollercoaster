package robomuss.rc.client.gui;

//import robomuss.rc.block.te.TileEntityTrack;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
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
	public void eventHandler(RenderGameOverlayEvent.Text event) {
		MovingObjectPosition viewEntityTrace = minecraft.thePlayer.rayTrace(20, 20);
		BlockPos selected = viewEntityTrace.getBlockPos();

		if (TrackManager.isBlockAtCoordsTrack(minecraft.theWorld, selected)) {
			clearTextList();
			this.teTrack = TrackManager.getTrackTileAtCoords(minecraft.theWorld, viewEntityTrace.getBlockPos());
			this.track = TrackManager.getTrackAtCoords(minecraft.theWorld, viewEntityTrace.getBlockPos());

			if (teTrack.style == null) {
				teTrack.style = TrackHandler.findTrackStyle("corkscrew");
			}

			if (track.track_type == null) {
				track.track_type = TrackHandler.findTrackType("horizontal");
			}

			EnumFacing facing = (EnumFacing) minecraft.theWorld.getBlockState(teTrack.getPos()).getValue(BlockTrackBase.FACING);
			boolean dummy = (Boolean) minecraft.theWorld.getBlockState(teTrack.getPos()).getValue(BlockTrackBase.DUMMY);

//			int meta = minecraft.theWorld.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
//			int displayMeta = meta > 11 ? meta - 10 : meta;

			textList.add("Track Type: " + track.track_type.unlocalized_name);
			textList.add("Track Style: " + teTrack.style.name);
			textList.add("Track Direction: " + facing.getName());
			textList.add("Track Dummy: " + dummy);
			textList.add(String.format("Player Facing: %s (%d)", minecraft.thePlayer.getHorizontalFacing().getName(), minecraft.thePlayer.getHorizontalFacing().getIndex()));
			textList.add(String.format("Location: X: %d, Y: %d, Z: %d", teTrack.getPos().getX(), teTrack.getPos().getY(), teTrack.getPos().getZ()));

			if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.ALL) {
				if (!minecraft.gameSettings.showDebugInfo && this.showText) {
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(0), textX, textY, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(1), textX, textY + 10, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(2), textX, textY + 20, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(3), textX, textY + 30, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(4), textX, textY + 40, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(5), textX, textY + 50, 0xFFFFFF);
				}
			}
		} else if (minecraft.theWorld.getBlockState(viewEntityTrace.getBlockPos()).getBlock() instanceof BlockConveyor) {
			clearTextList();
			EnumFacing facing = (EnumFacing) minecraft.theWorld.getBlockState(viewEntityTrace.getBlockPos()).getValue(BlockConveyor.FACING);
			textList.add(String.format("Conveyor Facing: %s (%d)", facing.getName(), facing.getIndex()));
			textList.add(String.format("Player Facing: %s (%d)", minecraft.thePlayer.getHorizontalFacing(), minecraft.thePlayer.getHorizontalFacing().getIndex()));

			if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.ALL) {
				if (!minecraft.gameSettings.showDebugInfo && this.showText) {
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(0), textX, textY, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(1), textX, textY + 10, 0xFFFFFF);
				}
			}
		} else if (minecraft.theWorld.getBlockState(viewEntityTrace.getBlockPos()).getBlock() instanceof BlockSupport) {
			clearTextList();
			this.teSupport = (TileEntitySupport) minecraft.theWorld.getTileEntity(viewEntityTrace.getBlockPos());
			this.support = (BlockSupport) minecraft.theWorld.getBlockState(viewEntityTrace.getBlockPos()).getBlock();
			this.teFooter = RCMod.supportManager.getFooterFromSupport(teSupport);
			this.footer = this.teFooter != null ? (BlockFooter) minecraft.theWorld.getBlockState(this.teFooter.getPos()) : null;

			textList.add("Steel Support");
			textList.add(String.format("Location: X: %d, Y: %d, Z: %d", viewEntityTrace.getBlockPos().getX(), viewEntityTrace.getBlockPos().getY(), viewEntityTrace.getBlockPos().getZ()));
			textList.add("Support Index: " + RCMod.supportManager.getSupportIndex(this.teSupport));
			textList.add("Footer: " + (this.footer == null ? "null" : "exists"));

			if (this.footer != null) {
				textList.add(String.format("Footer Loc: X: %d, Y: %d, Z: %d", this.teFooter.getPos().getX(), this.teFooter.getPos().getY(), this.teFooter.getPos().getZ()));
				textList.add("Footer Index: " + RCMod.supportManager.getFooterIndex(this.teFooter));
			}

			if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.ALL) {
				if (!minecraft.gameSettings.showDebugInfo && this.showText) {
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(0), textX, textY, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(1), textX, textY + 10, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(2), textX, textY + 20, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(3), textX, textY + 30, 0xFFFFFF);

					if (textList.size() > 7) {
						minecraft.fontRendererObj.drawStringWithShadow(textList.get(4), textX, textY + 40, 0xFFFFFF);
						minecraft.fontRendererObj.drawStringWithShadow(textList.get(5), textX, textY + 50, 0xFFFFFF);
					}
				}
			}
		} else if (minecraft.theWorld.getBlockState(viewEntityTrace.getBlockPos()).getBlock() instanceof BlockFooter) {
			clearTextList();
			this.teFooter = (TileEntityFooter) minecraft.theWorld.getTileEntity(viewEntityTrace.getBlockPos());
			this.footer = (BlockFooter) minecraft.theWorld.getBlockState(viewEntityTrace.getBlockPos()).getBlock();

			textList.add("Steel Support Footer");
			textList.add(String.format("Location: X: %d, Y: %d, Z: %d", viewEntityTrace.getBlockPos().getX(), viewEntityTrace.getBlockPos().getY(), viewEntityTrace.getBlockPos().getZ()));
			textList.add("Index: " + (RCMod.supportManager.getFooterIndex(this.teFooter)));
			textList.add(String.format("Number of Steel Supports: %d", RCMod.supportManager.getSupportStack(teFooter).size()));

			if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.ALL) {
				if (!minecraft.gameSettings.showDebugInfo && this.showText) {
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(0), textX, textY, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(1), textX, textY + 10, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(2), textX, textY + 20, 0xFFFFFF);
					minecraft.fontRendererObj.drawStringWithShadow(textList.get(3), textX, textY + 30, 0xFFFFFF);
				}
			}
		} else {
			clearTextList();
		}
	}

//	@SubscribeEvent(priority = EventPriority.NORMAL)
//	public void eventHandler(DrawBlockHighlightEvent event) {
//		clearTextList();
//		if (TrackManager.isBlockAtCoordsTrack(minecraft.theWorld, event.target.getBlockPos())) {}
//	}
}
