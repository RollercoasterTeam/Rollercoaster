package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.util.IInventoryRenderSettings;

public class ItemRenderTrack implements IItemRenderer {
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
	public void renderItem(ItemRenderType render_type, ItemStack item, Object... data) {
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

		if (render_type == ItemRenderType.INVENTORY) {                                 //master rotation, angles models in inventory like blocks
			GL11.glRotatef(inventoryRotation, 0, 1, 0);
		}

		if (track_type.render_stage != 1) {                            //sloped pieces
			for(int i = 0; i < track_type.render_stage; i++) {
				GL11.glPushMatrix();
				GL11.glScalef(inventoryScale, inventoryScale, inventoryScale);  //scales model to a smaller size
				GL11.glPushMatrix();

				if (track_type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);                               //flips model if it is inverted
				}

//				track_type.renderItem(i, render_type, TrackHandler.findTrackStyle("corkscrew"), (BlockTrackBase) track_type.block, Minecraft.getMinecraft().thePlayer.worldObj, 0, 0, 0);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
		} else {                                                                //horizontal and corner pieces
			GL11.glPushMatrix();
			GL11.glTranslatef(inventoryX, inventoryY, inventoryZ);              //adds proper render offsets
			GL11.glRotatef(180, 1, 0, 0);                                       //inverts model to render upright

			if (render_type == ItemRenderType.EQUIPPED) {                              /* 3rd Person */
				GL11.glScalef(inventoryScale, inventoryScale, inventoryScale);  //scales model to a smaller size
				GL11.glRotatef(45, 0, 1, 0);                                    //rotates model as if it was a full block in the inventory
				GL11.glRotatef(20, 0, 0, 1);                                    //further rotates model to point straight out of player's hand
				GL11.glTranslatef(7, -10, 0);                                   //translates to look like player is holding the piece by the main crossbar
			} else if (render_type == ItemRenderType.EQUIPPED_FIRST_PERSON) {          /* 1st Person */
				GL11.glScalef(inventoryScale, inventoryScale, inventoryScale);  //scales model to a smaller size
				GL11.glRotatef(45, 0, -1, 0);                                   //rotates model as if it was a full block in the inventory
				GL11.glRotatef(30, 0, 0, -1);                                   //further rotates model to point forward
				GL11.glTranslatef(0, -5, -7);                                   //translates to a nicer position on screen
			} else if (render_type == ItemRenderType.INVENTORY) {                      /* Inventory Slot */
				GL11.glScalef(1, 1, 1);                                         //scales model to full size
				GL11.glTranslatef(0, 2, 0);                                     //centers model in slot
				RenderHelper.enableGUIStandardItemLighting();
			}

//			track_type.renderItem(track_type.render_stage, render_type, TrackHandler.findTrackStyle("corkscrew"), (BlockTrackBase) track_type.block, Minecraft.getMinecraft().thePlayer.worldObj, 0, 0, 0);
			GL11.glPopMatrix();
		}
	}
}