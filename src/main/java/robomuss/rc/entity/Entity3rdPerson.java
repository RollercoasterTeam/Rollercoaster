package robomuss.rc.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import robomuss.rc.client.Keybindings;
import robomuss.rc.event.RenderWorldLast;


/**
 * Created by Mark on 17/08/2014.
 */
public class Entity3rdPerson extends EntityLivingBase {

    public EntityLivingBase player;

    public static boolean needsToMoveUp = false;


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
        }

        int dx = Mouse.getDX();
        int dy = Mouse.getDY();
        boolean leftButtonDown = Mouse.isButtonDown(0);
        boolean rightButtonDown = Mouse.isButtonDown(1);
        boolean middleButtonDown = Mouse.isButtonDown(2);
        int speed = 10;
        if(leftButtonDown || middleButtonDown){
            setAngles(dx * speed, dy * speed);
        }

        //TODO come back and work on this later, not too sure at the moment
        int dxWheel = Mouse.getDWheel();
        if(dxWheel != 0){
           motionZ = dxWheel / 100;
        }

        if (Keyboard.isKeyDown(Keybindings.lookLeft.getKeyCode())) {
            setAngles(-10, 0);
        } else if (Keyboard.isKeyDown(Keybindings.lookRight.getKeyCode())) {
            setAngles(10, 0);

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

        if (Keyboard.isKeyDown(Keybindings.up.getKeyCode())) {
            motionY = 0.2;
        } else if (Keyboard.isKeyDown(Keybindings.down.getKeyCode())) {
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

        localPos.xCoord += RenderWorldLast.diffX;
        localPos.yCoord += RenderWorldLast.diffY;
        localPos.zCoord += RenderWorldLast.diffZ;

        Vec3 vec32 = localPos.addVector(look.xCoord * distance, look.yCoord * distance, look.zCoord * distance);

        MovingObjectPosition result = this.worldObj.rayTraceBlocks(localPos, vec32);

        return result;
    }

    @Override
    public ItemStack getEquipmentInSlot(int var1) {
        return null;
    }
}
