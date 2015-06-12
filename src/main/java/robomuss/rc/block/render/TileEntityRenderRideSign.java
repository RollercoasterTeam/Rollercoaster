package robomuss.rc.block.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelCatwalk;
import robomuss.rc.block.model.ModelRideSign5x1;
import robomuss.rc.block.model.ModelTrackDesigner;
import robomuss.rc.block.model.ModelTrackFabricator;
import robomuss.rc.block.te.TileEntityCatwalk;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityRideSign;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.util.ColourUtil;

public class TileEntityRenderRideSign extends TileEntitySpecialRenderer {

	private ModelRideSign5x1 model = new ModelRideSign5x1();

	private ResourceLocation textures = (new ResourceLocation("rc:textures/models/ride_sign.png"));

	private static final ResourceLocation field_147513_b = new ResourceLocation("textures/entity/sign.png");
	private final ModelSign signModel = new ModelSign();
	
	public static int countMax = 175, count = countMax, countDirection = 0;
	
	public TileEntityRenderRideSign() {

	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		GL11.glPushMatrix();
		switch (((TileEntityRideSign) te).direction) {
		case 1:
			GL11.glRotatef(180f, -180f, 0f, 0f);
			break;
		case 2:
			GL11.glRotatef(180f, 180f, 0f, 180f);
			break;
		case 3:
			GL11.glRotatef(180f, 0f, 0f, 180f);
			break;
		default:
			GL11.glRotatef(180f, -180f, 0f, 180f);
			break;
		}

		this.model.display.render(0.0625F);

		int colour = ((TileEntityRideSign) te).colour;
		Minecraft.getMinecraft().renderEngine.bindTexture(ColourUtil.textures
				.get(colour));

		this.model.border1.render(0.0625F);
		this.model.border2.render(0.0625F);
		this.model.border3.render(0.0625F);
		this.model.border4.render(0.0625F);

		GL11.glPopMatrix();
		GL11.glPopMatrix();

		renderText(te, x, y, z, f);
	}

	private void renderText(TileEntity te, double x, double y, double z, float f) {
		Block block = te.getBlockType();
		GL11.glPushMatrix();
		float f1 = 0.6666667F;
		float f3;
		int direction = ((TileEntityRideSign) te).direction;

		if (block == Blocks.standing_sign) {
			GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
			float f2 = (float) (direction * 360) / 16.0F;
			GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
			this.signModel.signStick.showModel = true;
		} 
		else {
			f3 = 0.0F;

			if(direction == 0) {
				f3 = -90.0F;
			}
			else if(direction == 1) {
				f3 = 0.0F;
			}
			else if(direction == 2) {
				f3 = 90.0F;
			}
			else if(direction == 3) {
				f3 = 180.0F;
			}

			GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
			GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
			//GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
			GL11.glTranslatef((float) (count * 0.01), -0.55F, -0.475F);
			this.signModel.signStick.showModel = false;
			
			String s = ((TileEntityRideSign) te).name + "'s Rollercoaster";
			//countMax = 300 - ((125 / 4) * s.length());
			countMax = -175;
			
			/*if(countDirection == 0) {
				if(count >= countMax) {
					countDirection = 1;
				}
				else {
					count++;
				}
			}
			else if(countDirection == 1) {
				if(count <= -175) {
					countDirection = 0;
				}
				else {
					count--;
				}
			}*/
		}

		this.bindTexture(field_147513_b);
		GL11.glPushMatrix();
		GL11.glScalef(f1, -f1, -f1);
		//this.signModel.renderSign();
		GL11.glPopMatrix();
		FontRenderer fontrenderer = this.func_147498_b();
		f3 = 0.016666668F * f1 * 5;
		GL11.glTranslatef(0.0F, 0.5F * f1, 0.07F * f1);
		GL11.glScalef(f3 / 2, -f3 / 2, f3);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
		GL11.glDepthMask(false);
		byte b0 = 0;

		if(!(countMax < -175)) {
			String s = ((TileEntityRideSign) te).name + "'s Rollercoaster";
			//fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0 * 10 - 1 * 5, b0);
			fontrenderer.drawString(s, -140, 0 * 10 - 1 * 5, b0);
		}

		GL11.glDepthMask(true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
}
