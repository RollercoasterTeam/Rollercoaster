package robomuss.rc.block.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockFooter;
import robomuss.rc.block.BlockSupport;
import robomuss.rc.block.model.ModelFooter;
import robomuss.rc.block.model.ModelTrackSupport;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntitySupport;

public class TileEntityRenderFooter extends TileEntitySpecialRenderer {
    
    public ModelFooter model;

    public TileEntityRenderFooter() {
        this.model = new ModelFooter();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8) {
        GL11.glPushMatrix();
        int colour = ((TileEntityFooter) te).colour;
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        
        ResourceLocation textures = (new ResourceLocation("rc:textures/models/footer.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
        
        this.model.footer.render(0.0625F);
        
        textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
        
        Block below = te.getWorldObj().getBlock(te.xCoord, te.yCoord - 1, te.zCoord);
        Block above = te.getWorldObj().getBlock(te.xCoord, te.yCoord + 1, te.zCoord);
        
        if(above instanceof BlockSupport) {
        	this.model.middle.render(0.0625F);
            this.model.middle2.render(0.0625F);
        }

        GL11.glPopMatrix();
    	
    	/*GL11.glPushMatrix();
    	IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("rc:models/test.obj"));
    	
    	model.renderAll();
    	GL11.glPopMatrix();*/
    }
}
