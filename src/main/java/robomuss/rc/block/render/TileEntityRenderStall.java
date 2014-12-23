package robomuss.rc.block.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.model.ModelStall;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.StallItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityRenderStall extends TileEntitySpecialRenderer {

	private ModelBase model;
	private RenderItem render;
	private int timer, counter; 
	private ArrayList<StallItem> array;
	
	public TileEntityRenderStall() {
		model = new ModelStall();
		
		render = new RenderItem() {
			@Override
			public boolean shouldBob() {
				return false;
			}
		};
		render.setRenderManager(RenderManager.instance);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

		ResourceLocation textures = (new ResourceLocation("rc:textures/models/stall.png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		GL11.glPushMatrix();
		GL11.glRotatef(180f, -180f, 0f, 180f);
		
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		
		array = te.getBlockType() == RCBlocks.food_stall ? RCItems.food : RCItems.merch;
		
		timer++;
		if(timer % 250 == 0) {
			if(counter < array.size() - 1) {
				counter++;
			}
			else {
				counter = 0;
			}
		}
		if(counter >= array.size()) {
			counter = 0;
		}
		
        ItemStack stack = new ItemStack(array.get(counter).item);
        System.out.println(stack.getDisplayName());
        if(stack != null) {
        	float scaleFactor = 1F;
            float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

            EntityItem ghostEntityItem = new EntityItem(te.getWorldObj());
            ghostEntityItem.hoverStart = 0.0F;
            ghostEntityItem.setEntityItemStack(stack);

            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.5F);
            GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
            GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);

            render.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
        }

		GL11.glPopMatrix();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

}
