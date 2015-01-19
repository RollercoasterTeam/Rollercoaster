package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.model.ModelConveyor;
import robomuss.rc.block.te.TileEntityConveyor;

public class TileEntityRenderConveyor extends TileEntitySpecialRenderer {

	private ModelBase model;
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/conveyor.png"));

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5f, (float) z + 0.5F);
//		GL11.glRotatef(180f, 180f, 0f, 0f);
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		if (te != null && te instanceof TileEntityConveyor) {
			TileEntityConveyor teConveyor = (TileEntityConveyor) te;

			int meta = teConveyor.getWorldObj().getBlockMetadata(teConveyor.xCoord, teConveyor.yCoord, teConveyor.zCoord);

			switch (meta) {
				case 2: GL11.glRotatef(180f, 0f, 0f, 180f); break;
				case 3: GL11.glRotatef(180f, 180f, 0f, 0f); break;
				case 4: GL11.glRotatef(180f, 180f, 0f, 180f); break;
				case 5: GL11.glRotatef(180f, -180f, 0f, 180f);
 			}
		}


//		int meta = te.getWorldObj().getBlockMetadata((int) x, (int) y, (int) z);
//		switch (meta) {
////			case 2: GL11.glRotatef(180f, -180f, 0f, 180f); break;
////			case 3: GL11.glRotatef(180f, -180f, 0f, 0f);   break;
////			case 4: GL11.glRotatef(180f, 180f, 0f, 180f);  break;
////			case 5: GL11.glRotatef(180f, 0f, 0f, 180f);    break;
//			case 2: GL11.glRotatef(180f, -90f, 0f, 0f); break;
//			case 3: GL11.glRotatef(180f, 180f, 0f, 0f);  break;
//		}
//		switch(((TileEntityConveyor) te).direction){
//            case 1:  GL11.glRotatef(180f, -180f, 0f, 0f); break;
//            case 2:  GL11.glRotatef(180f, 180f, 0f, 180f); break;
//            case 3:  GL11.glRotatef(180f, 0f, 0f, 180f); break;
//            default: GL11.glRotatef(180f, -180f, 0f, 180f); break;
//		}
		model = new ModelConveyor();
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
	}

}
