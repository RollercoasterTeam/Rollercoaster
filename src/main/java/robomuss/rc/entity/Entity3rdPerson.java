package robomuss.rc.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.events.renderWorldLast;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Mark on 17/08/2014.
 */
public class Entity3rdPerson extends EntityLivingBase {

    private static FloatBuffer modelviewF = GLAllocation.createDirectFloatBuffer(16);
    private static FloatBuffer projectionF = GLAllocation.createDirectFloatBuffer(16);
    private static DoubleBuffer modelviewD = ByteBuffer.allocateDirect(16 * 8).asDoubleBuffer();
    private static DoubleBuffer projectionD = ByteBuffer.allocateDirect(16 * 8).asDoubleBuffer();
    private static IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private static FloatBuffer winZ = ByteBuffer.allocateDirect(1 * 4).asFloatBuffer();
    private static FloatBuffer pos = ByteBuffer.allocateDirect(3 * 4).asFloatBuffer();

    public EntityLivingBase player;
    public TileEntityTrackDesigner tile;

    private boolean debugPointer = false;

    public Entity3rdPerson(World par1World) {
        super(par1World);

        width = 0;
        height = 0;
    }

    @Override
    public void onUpdate() {
        Vec3 look = this.getLook(1.0F).normalize();

        Vec3 worldUp = Vec3.createVectorHelper(0, 1, 0);
        Vec3 side = worldUp.crossProduct(look).normalize();
        Vec3 forward = side.crossProduct(worldUp).normalize();

        motionX = 0;
        motionY = 0;
        motionZ = 0;

        if (!Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())) {
            if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode())) {
                motionX = side.xCoord * 0.5;
                motionZ = side.zCoord * 0.5;
            } else if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode())) {
                motionX = side.xCoord * -0.5;
                motionZ = side.zCoord * -0.5;
            }
        } else {
            if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode())) {
                setAngles (-10, 0);
            } else if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode())) {
                setAngles (10, 0);
            }
        }

        if (!Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())) {
            if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode())) {
                motionX = forward.xCoord * 0.5;
                motionZ = forward.zCoord * 0.5;
            } else if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode())) {
                motionX = forward.xCoord * -0.5;
                motionZ = forward.zCoord * -0.5;
            }
        } else {
            if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode())) {
                setAngles(0, 10);
            } else if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode())) {
                setAngles(0, -10);
            }
        }


        if (Keyboard.isKeyDown(Keyboard.KEY_PRIOR)) {
            motionY = 0.2;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_NEXT)) {
            motionY = -0.2;
        }

        super.onUpdate();
    }

    @Override
    public ItemStack getHeldItem() {
        return null;
    }

    @Override
    public void setCurrentItemOrArmor(int i, ItemStack itemstack) {

    }

    @Override
    public ItemStack[] getLastActiveItems() {
        return null;
    }

    public MovingObjectPosition rayTraceMouse() {
        double distance = 1000;

        Vec3 localPos = this.getPosition(1.0F);
        Vec3 look = this.getLook(1.0F).normalize();

        localPos.xCoord += renderWorldLast.diffX;
        localPos.yCoord += renderWorldLast.diffY;
        localPos.zCoord += renderWorldLast.diffZ;

        Vec3 vec32 = localPos.addVector(look.xCoord * distance, look.yCoord * distance, look.zCoord * distance);

        MovingObjectPosition result = this.worldObj.rayTraceBlocks(localPos, vec32);

        if (debugPointer) {
            localPos = this.getPosition(1.0F);
            localPos.xCoord += renderWorldLast.diffX;
            localPos.yCoord += renderWorldLast.diffY + 0.1F;
            localPos.zCoord += renderWorldLast.diffZ;

            look = this.getLook(1.0F).normalize();

            Vec3 aimed = Vec3.createVectorHelper(
                    localPos.xCoord + look.xCoord * 200,
                    localPos.yCoord + look.yCoord * 200,
                    localPos.zCoord + look.zCoord * 200);
        }

        return result;
    }

    @Override
    public ItemStack getEquipmentInSlot(int var1) {
        return null;
    }
}
