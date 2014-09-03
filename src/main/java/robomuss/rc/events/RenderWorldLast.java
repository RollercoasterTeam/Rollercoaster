package robomuss.rc.events;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by Mark on 17/08/2014.
 */
public class RenderWorldLast {

    public static FloatBuffer modelviewF;
    public static FloatBuffer projectionF;
    public static IntBuffer viewport;

    public static FloatBuffer pos = ByteBuffer.allocateDirect(3 * 4).asFloatBuffer();

    public static float diffX, diffY, diffZ;

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderLast (RenderWorldLastEvent evt) {

        if (modelviewF == null) {
            modelviewF = GLAllocation.createDirectFloatBuffer(16);
            projectionF = GLAllocation.createDirectFloatBuffer(16);
            viewport = GLAllocation.createDirectIntBuffer(16);

        }

        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelviewF);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionF);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
        float f = (viewport.get(0) + viewport.get(2)) / 2;
        float f1 = (viewport.get(1) + viewport.get(3)) / 2;

        float x = Mouse.getX();
        float y = Mouse.getY();

        // TODO: Minecraft seems to instist to have this winZ re-created at
        // each frame - looks like a memory leak to me but I couldn't use a
        // static variable instead, as for the rest.
        FloatBuffer winZ = GLAllocation.createDirectFloatBuffer(1);
        GL11.glReadPixels((int) x, (int) y, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, winZ);

        GLU.gluUnProject(x, y, winZ.get(), modelviewF, projectionF, viewport, pos);

        diffX = pos.get(0);
        diffY = pos.get(1);
        diffZ = pos.get(2);
    }

}
