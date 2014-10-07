package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.model.*;

public class ItemRenderFence implements IItemRenderer {

	private ModelRideFence normal = new ModelRideFence();
	private ModelRideFenceCorner corner = new ModelRideFenceCorner();
	private ModelRideFenceTriangle triangle = new ModelRideFenceTriangle();
	private ModelRideFenceSquare square = new ModelRideFenceSquare();
	private ModelRideFenceGate gate = new ModelRideFenceGate();
	private ModelRideFenceGateOpen gate_open = new ModelRideFenceGateOpen();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	private int gate_animation;
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float) 0 + 0.5F, (float) 0 + 1.35F, (float) 0 + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_0.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        if(item.getItem() == Item.getItemFromBlock(RCBlocks.ride_fence)) {
        	normal.Shape1.render(0.0625F);
        	normal.Shape2.render(0.0625F);
        	normal.Shape3.render(0.0625F);
        }
        else if(item.getItem() == Item.getItemFromBlock(RCBlocks.ride_fence_corner)) {
        	corner.Shape1.render(0.0625F);
        	corner.Shape2.render(0.0625F);
        	corner.Shape3.render(0.0625F);
        	corner.Shape4.render(0.0625F);
        }
        else if(item.getItem() == Item.getItemFromBlock(RCBlocks.ride_fence_triangle)) {
        	triangle.Shape1.render(0.0625F);
        	triangle.Shape2.render(0.0625F);
        	triangle.Shape3.render(0.0625F);
        	triangle.Shape4.render(0.0625F);
        	triangle.Shape5.render(0.0625F);
        }
        else if(item.getItem() == Item.getItemFromBlock(RCBlocks.ride_fence_square)) {
        	square.Shape1.render(0.0625F);
        	square.Shape2.render(0.0625F);
        	square.Shape3.render(0.0625F);
        	square.Shape4.render(0.0625F);
        	square.Shape5.render(0.0625F);
        	square.Shape6.render(0.0625F);
        }
        else if(item.getItem() == Item.getItemFromBlock(RCBlocks.ride_fence_gate)) {
        	if(gate_animation % 1000 < 500 && type == ItemRenderType.INVENTORY) {
	        	gate_open.pole1.render(0.0625F);
        		gate_open.pole2.render(0.0625F);
	        	
        		gate_open.bar1.render(0.0625F);
        		gate_open.bar2.render(0.0625F);
        		gate_open.bar3.render(0.0625F);
        		gate_open.bar4.render(0.0625F);
        		gate_open.bar5.render(0.0625F);
        		gate_open.bar6.render(0.0625F);
        		gate_open.bar7.render(0.0625F);
        		gate_open.bar8.render(0.0625F);
        		gate_open.bar9.render(0.0625F);
        		gate_open.bar10.render(0.0625F);
        	}
        	else {
        		gate.pole1.render(0.0625F);
	        	gate.pole2.render(0.0625F);
	        	
	        	gate.bar1.render(0.0625F);
	        	gate.bar2.render(0.0625F);
	        	gate.bar3.render(0.0625F);
	        	gate.bar4.render(0.0625F);
	        	gate.bar5.render(0.0625F);
	        	gate.bar6.render(0.0625F);
	        	gate.bar7.render(0.0625F);
	        	gate.bar8.render(0.0625F);
	        	gate.bar9.render(0.0625F);
	        	gate.bar10.render(0.0625F);
        	}
        	gate_animation++;
        }

        GL11.glPopMatrix();
	}
}