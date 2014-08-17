package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelTrackSupport;
import robomuss.rc.block.te.TileEntitySupport;

public class TileEntityRenderSupport extends TileEntitySpecialRenderer {
    
    public ModelTrackSupport model;

    public TileEntityRenderSupport() {
        this.model = new ModelTrackSupport();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8) {
        GL11.glPushMatrix();
        int colour = ((TileEntitySupport) te).colour;
		//GL11.glColor4f(ColourUtil.getRed(colour), ColourUtil.getGreen(colour), ColourUtil.getBlue(colour), ColourUtil.getAlpha(colour));
        GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
        ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
        TileEntitySupport stile = ((TileEntitySupport) te);
        if(!stile.down)
        {
            this.model.Top.render(0.0625F);
        }

        if(!stile.up)
        {
            this.model.Bottom.render(0.0625F);
        }

        this.model.Middle.render(0.0625F);


        GL11.glPopMatrix();
    }
}
