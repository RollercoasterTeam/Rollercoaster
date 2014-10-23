package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import robomuss.rc.RCMod;
import robomuss.rc.block.BlockTrackBase;
//import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.util.IInventoryRenderSettings;

public class ItemRenderTrack implements IItemRenderer {
	
	private static RenderItem renderItem = new RenderItem();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		if(type == ItemRenderType.INVENTORY) {
			TrackPiece track_type = TrackHandler.findTrackType(item.getItem());
			if(track_type != null && track_type instanceof IInventoryRenderSettings) {
				if(((IInventoryRenderSettings) track_type).useIcon()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_0.png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		
//		TileEntityTrack te = new TileEntityTrack();
//		TileEntityTrack te = TrackManager.getTrackAtCoords()
		TrackPiece track_type = TrackHandler.findTrackType(item.getItem());
		
		float inventoryX = 0f;
		float inventoryY = 1f;
		float inventoryZ = 0f;
		
		float inventoryScale = 1f;
		
		float inventoryRotation = 0f;
		
		if(track_type instanceof IInventoryRenderSettings) {
			inventoryX = ((IInventoryRenderSettings) track_type).getInventoryX();
			inventoryY = ((IInventoryRenderSettings) track_type).getInventoryY();
			inventoryZ = ((IInventoryRenderSettings) track_type).getInventoryZ();
		
			inventoryScale = ((IInventoryRenderSettings) track_type).getInventoryScale();
			inventoryRotation = ((IInventoryRenderSettings) track_type).getInventoryRotation();
		}

		GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
		if(type == ItemRenderType.INVENTORY) {
			GL11.glRotatef(inventoryRotation, 0, 1, 0);
		}
		if(track_type.special_render_stages != 0) {
			for(int i = 0; i < track_type.special_render_stages; i++) {
				GL11.glPushMatrix();
				if (track_type != null && track_type.block != null) {
					//((BlockTrackBase) track_type.block).direction = ForgeDirection.SOUTH;
//					GL11.glTranslatef(track_type.getSpecialX(i, inventoryX, (BlockTrackBase) track_type.block, Minecraft.getMinecraft().thePlayer.worldObj, 0, 0, 0), track_type.getSpecialY(i, inventoryY, (BlockTrackBase) track_type.block, Minecraft.getMinecraft().thePlayer.worldObj, 0, 0, 0), track_type.getSpecialZ(i, inventoryZ, (BlockTrackBase) track_type.block, Minecraft.getMinecraft().thePlayer.worldObj, 0, 0, 0));
				}
				GL11.glScalef(inventoryScale, inventoryScale, inventoryScale);
				GL11.glPushMatrix();
				if(track_type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				track_type.renderSpecial(i, TrackHandler.findTrackStyle("corkscrew"), (BlockTrackBase) track_type.block, Minecraft.getMinecraft().thePlayer.worldObj, 0, 0, 0);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
		}
		else {
			GL11.glPushMatrix();
			GL11.glTranslatef(inventoryX, inventoryY - 2, inventoryZ);
			GL11.glScalef(inventoryScale, inventoryScale, inventoryScale);
			track_type.render(TrackHandler.findTrackStyle("corkscrew"), (BlockTrackBase) track_type.block, Minecraft.getMinecraft().thePlayer.worldObj, 0, 0, 0);
			GL11.glPopMatrix();
		}
	}
}