package robomuss.rc.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import robomuss.rc.client.gui.GuiTrackDesigner;
import robomuss.rc.client.gui.keybinding.RCKeyBinding;
import robomuss.rc.client.gui.keybinding.TrackDesignerKeyBindings;
import robomuss.rc.event.RenderWorldLast;


/**
 * Created by Mark on 17/08/2014.
 */
public class Entity3rdPerson extends EntityLivingBase {

    public EntityLivingBase player = this;

    public static boolean needsToMoveUp = false;
	public static RCKeyBinding[] rcKeys;

	private GuiTrackDesigner designerGUI;

    public Entity3rdPerson(World world, RCKeyBinding[] rcKeys, GuiTrackDesigner designerGUI) {
        super(world);
		this.rcKeys = rcKeys;
		this.designerGUI = designerGUI;

        width = 0;
        height = 0;
    }

    @Override
    public void onUpdate() {
		TrackDesignerKeyBindings.init();

        Vec3 look = this.getLook(1.0F).normalize();

        Vec3 worldUp = Vec3.createVectorHelper(0, 1, 0);
        Vec3 side = worldUp.crossProduct(look).normalize();
        Vec3 forward = side.crossProduct(worldUp).normalize();

        motionX = 0;
        motionY = 0;
        motionZ = 0;

		if (!Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())) {
			if (Keyboard.isKeyDown(TrackDesignerKeyBindings.left.getRCKeyCode())) {
				motionX = side.xCoord * 0.5;
				motionZ = side.zCoord * 0.5;
			}
		    if (Keyboard.isKeyDown(TrackDesignerKeyBindings.right.getRCKeyCode())) {
				motionX = side.xCoord * -0.5;
		        motionZ = side.zCoord * -0.5;
		    }
			if (Keyboard.isKeyDown(TrackDesignerKeyBindings.forward.getRCKeyCode())) {
				motionX = forward.xCoord * 0.5;
			    motionZ = forward.zCoord * 0.5;
		    }
			if (Keyboard.isKeyDown(TrackDesignerKeyBindings.backward.getRCKeyCode())) {
				motionX = forward.xCoord * -0.5;
			    motionZ = forward.zCoord * -0.5;
		    }
	    } else {
		    if (Keyboard.isKeyDown(TrackDesignerKeyBindings.forward.getRCKeyCode())) {
			    setAngles(0, 10);
		    } else if (Keyboard.isKeyDown(TrackDesignerKeyBindings.backward.getRCKeyCode())) {
			    setAngles(0, -10);
		    }
	    }

	    if (Keyboard.isKeyDown(TrackDesignerKeyBindings.up.getRCKeyCode())) {
		    motionY = 0.2;
	    } else if (Keyboard.isKeyDown(TrackDesignerKeyBindings.down.getRCKeyCode())) {
		    motionY = -0.2;
	    }

	    int dx = Mouse.getDX();
	    int dy = Mouse.getDY();

	    boolean leftButtonDown = Mouse.isButtonDown(0);
		boolean rightButtonDown = Mouse.isButtonDown(1);
	    boolean middleButtonDown = Mouse.isButtonDown(2);

	    int speed = 10;

	    if (!isMouseOverAButton()) {
		    if (leftButtonDown || middleButtonDown) {
			    setAngles(dx * speed, dy * speed);
		    }
	    }

	    if (Keyboard.isKeyDown(TrackDesignerKeyBindings.lookLeft.getRCKeyCode())) {
		    setAngles(-10, 0);
	    } else if (Keyboard.isKeyDown(TrackDesignerKeyBindings.lookRight.getRCKeyCode())) {
		    setAngles(10, 0);
	    }
        super.onUpdate();
    }

	private boolean isMouseOverAButton() {
		List<GuiButton> buttons = this.designerGUI.getButtonList();

		boolean hovering = false;

		for (GuiButton button : buttons) {
			if (!button.func_146115_a()) {
				hovering = false;
			} else {
				hovering = true;
			}
		}

		return hovering;
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
