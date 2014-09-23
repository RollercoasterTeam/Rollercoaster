package robomuss.rc.client.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackType;
import robomuss.rc.util.IInventoryRenderSettings;

public class ItemRenderTrack implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_0.png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		
		TileEntityTrack te = new TileEntityTrack();
		TrackType track_type = TrackHandler.findTrackType(item.getItem());
		
		float inventoryX = 0f;
		float inventoryY = 1f;
		float inventoryZ = 0f;
		
		float inventoryScale = 1f;
		
		if(track_type instanceof IInventoryRenderSettings) {
			inventoryX = ((IInventoryRenderSettings) track_type).getInventoryX();
			inventoryY = ((IInventoryRenderSettings) track_type).getInventoryY();
			inventoryZ = ((IInventoryRenderSettings) track_type).getInventoryZ();
			
			inventoryScale = ((IInventoryRenderSettings) track_type).getInventoryScale();
		}
		
		if(track_type.special_render_stages != 0) {
			for(int i = 0; i < track_type.special_render_stages; i++) {
				GL11.glPushMatrix();
				GL11.glTranslatef(track_type.getSpecialX(i, inventoryX, te), track_type.getSpecialY(i, inventoryY, te), track_type.getSpecialZ(i, inventoryZ, te));
				GL11.glScalef(inventoryScale, inventoryScale, inventoryScale);
				GL11.glPushMatrix();
				if(track_type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				track_type.renderSpecial(i, TrackHandler.types.get(0), te);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
		}
		else {
			GL11.glTranslatef(inventoryX, inventoryY, inventoryZ);
			GL11.glScalef(inventoryScale, inventoryScale, inventoryScale);
			track_type.render(TrackHandler.types.get(0), new TileEntityTrack());
		}
	}
}