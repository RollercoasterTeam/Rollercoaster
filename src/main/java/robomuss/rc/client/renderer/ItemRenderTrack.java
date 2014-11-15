package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.util.IInventoryRenderSettings;

//import robomuss.rc.block.te.TileEntityTrack;

public class ItemRenderTrack implements IItemRenderer {
//	private static RenderItem renderItem = new RenderItem();

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

		TrackPiece track_type = TrackHandler.findTrackType(item.getItem());
		
		float inventoryX = 0f;
		float inventoryY = 1f;
		float inventoryZ = 0f;
		
		float inventoryScale = 1f;
		float inventoryRotation = 0f;
		
		if (track_type instanceof IInventoryRenderSettings) {
			inventoryX = ((IInventoryRenderSettings) track_type).getInventoryX();
			inventoryY = ((IInventoryRenderSettings) track_type).getInventoryY();
			inventoryZ = ((IInventoryRenderSettings) track_type).getInventoryZ();
		
			inventoryScale = ((IInventoryRenderSettings) track_type).getInventoryScale();
			inventoryRotation = ((IInventoryRenderSettings) track_type).getInventoryRotation();
		}

		GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
		if (type == ItemRenderType.INVENTORY) {
			GL11.glRotatef(inventoryRotation, 0, 1, 0);
		}

		if (track_type.special_render_stages != 0) {
			for(int i = 0; i < track_type.special_render_stages; i++) {
				GL11.glPushMatrix();
				GL11.glScalef(inventoryScale, inventoryScale, inventoryScale);
				GL11.glPushMatrix();
				if (track_type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				track_type.renderSpecialItem(i, TrackHandler.findTrackStyle("corkscrew"), (BlockTrackBase) track_type.block, Minecraft.getMinecraft().thePlayer.worldObj, 0, 0, 0);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
		} else {
			GL11.glPushMatrix();
			GL11.glTranslatef(inventoryX, inventoryY, inventoryZ);
			GL11.glRotatef(180, 1, 0, 0);
			if (type == ItemRenderType.EQUIPPED) {
				GL11.glScalef(inventoryScale, inventoryScale, inventoryScale);
				GL11.glRotatef(45, 0, 1, 0);
				GL11.glRotatef(20, 0, 0, 1);
				GL11.glTranslatef(7, -10, 0);
			} else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
				GL11.glScalef(inventoryScale, inventoryScale, inventoryScale);
				GL11.glRotatef(45, 0, -1, 0);
				GL11.glRotatef(30, 0, 0, -1);
				GL11.glTranslatef(0, -5, -7);
			} else if (type == ItemRenderType.INVENTORY) {
				GL11.glScalef(1, 1, 1);
				GL11.glTranslatef(0, 2, 0);
			}
			track_type.renderItem(TrackHandler.findTrackStyle("corkscrew"), (BlockTrackBase) track_type.block, Minecraft.getMinecraft().thePlayer.worldObj, 0, 0, 0);
			GL11.glPopMatrix();
		}
	}
}