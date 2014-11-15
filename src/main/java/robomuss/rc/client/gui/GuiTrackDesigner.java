package robomuss.rc.client.gui;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.client.gui.exList.ExpandableListNode;
import robomuss.rc.client.gui.exList.ExpandableListNodeRollercoasters;
import robomuss.rc.entity.Entity3rdPerson;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.util.GuiPanel;

import java.awt.*;
import java.util.ArrayList;

public class GuiTrackDesigner extends GuiScreen {

	private TileEntityTrackDesigner te;

	public static Entity3rdPerson entity3rdPerson;
	private int thirdPersonView = 0;

	private static final GuiPanel NEW_COASTER_PANEL = new GuiPanel("newCoaster", "Create Rollercoaster", 128, 148);
	private int selectedSlot = 0;
	private boolean showHelp = false;
	private ArrayList<Block> blocks = new ArrayList<Block>();

	public static int keyForward = 17; // W
	public static int keyBackward = 31; // S
	public static int keyLeft = 30; // A
	public static int keyRight = 32; // D
	public static int keyLookLeft = 16; // Q
	public static int keyLookRight = 18; // E
	public static int keyUp = 201; // PgUp
	public static int keyDown = 209; // PgDn

	private ExpandableListNode[] nodes = {
			new ExpandableListNodeRollercoasters("Rollercoasters", null, null),
			new ExpandableListNode("Paths", null, new ExpandableListNode[0]),
			new ExpandableListNode("Fences", null, new ExpandableListNode[0]) 
	};

	private double posX, posY, posZ;

	public GuiTrackDesigner(EntityPlayer player, World world, int x, int y,
			int z) {
		te = (TileEntityTrackDesigner) world.getTileEntity(x, y, z);

		entity3rdPerson = new Entity3rdPerson(Minecraft.getMinecraft().theWorld);
		if (entity3rdPerson != null) {
			Minecraft.getMinecraft().theWorld.spawnEntityInWorld(entity3rdPerson);

			entity3rdPerson.setLocationAndAngles(x, y, z, 0, 50);

			Minecraft.getMinecraft().renderViewEntity = entity3rdPerson;
			thirdPersonView = Minecraft.getMinecraft().gameSettings.thirdPersonView;
			Minecraft.getMinecraft().gameSettings.thirdPersonView = 8;

			posX = entity3rdPerson.posX + 0.5;
			posY = entity3rdPerson.posY + 5;
			posZ = entity3rdPerson.posZ - 5;

			entity3rdPerson.setPositionAndUpdate(posX, posY, posZ);
		}

		// Add all of the blocks that you want in the hotbar here
		for (int i = 0; i < TrackHandler.pieces.size(); i++) {
			blocks.add(TrackHandler.pieces.get(i).block);
		}

		blocks.add(RCBlocks.support);
	}

	int val = -1;

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.clear();

		buttonList.add(new GuiButton(100, this.width - 40, 10, 30, 20, "Help"));

		for (int i = 0; i < nodes.length; i++) {
			ExpandableListNode node = nodes[i];
			if (val == i) {
				if (node.getChildren() != null) {
					for (int j = 0; j < node.getChildren().length; j++) {
						ExpandableListNode child = node.getChildren()[i];
						buttonList.add(new GuiButton(i + ((j + 1) * 10), 140,
								40 + (i * 40), 100, 20, child.getName()));
					}
				}
			}
			buttonList.add(new GuiButton(i, 20, 40 + (i * 40), 100, 20, node.getName()));
		}
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		if (showHelp) {
			String controls1 = "Use W A S D to move around";
			drawString(fontRendererObj, controls1, this.width / 2 - (fontRendererObj.getStringWidth(controls1) / 2), this.height / 2 + 20, 0xFFFFFF);

			String controls2 = "Use " + Keyboard.getKeyName(keyUp).toUpperCase() + " and " + Keyboard.getKeyName(keyDown).toUpperCase() + " to move up and down";
			drawString(fontRendererObj, controls2, this.width / 2 - (fontRendererObj.getStringWidth(controls2) / 2), this.height / 2 + 40, 0xFFFFFF);

			String controls3 = "Use " + Keyboard.getKeyName(keyLookLeft).toUpperCase() + " and " + Keyboard.getKeyName(keyLookRight).toUpperCase() + " to rotate left and right";
			drawString(fontRendererObj, controls3, this.width / 2 - (fontRendererObj.getStringWidth(controls3) / 2), this.height / 2 + 60, 0xFFFFFF);

			String controls4 = "Use SHIFT-W and SHIFT-S to rotate up and down";
			drawString(fontRendererObj, controls4, this.width / 2 - (fontRendererObj.getStringWidth(controls4) / 2), this.height / 2 + 80, 0xFFFFFF);
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int cornerX = (width - NEW_COASTER_PANEL.width) / 2;
		int cornerY = 40;

		mc.renderEngine.bindTexture(NEW_COASTER_PANEL.texture);
		drawTexturedModalRect(cornerX, cornerY, 0, 0, NEW_COASTER_PANEL.width, NEW_COASTER_PANEL.height);

		drawString(fontRendererObj, NEW_COASTER_PANEL.displayName, cornerX + ((NEW_COASTER_PANEL.width / 2) - (fontRendererObj.getStringWidth(NEW_COASTER_PANEL.displayName) / 2)), cornerY + 10, 16777215);
		
		// Changes the slot based on the mouse wheel
		int dxWheel = Mouse.getDWheel();
		if (dxWheel != 0) {
			if (dxWheel > 0) {
				if (selectedSlot != 9) {
					selectedSlot += 1;
				} else {
					selectedSlot = 0;
				}
			}
			if (dxWheel < 0) {
				if (selectedSlot != 0) {
					selectedSlot -= 1;
				} else {
					selectedSlot = 9;
				}
			}
		}

		super.drawScreen(x, y, f);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id == 100) {
			this.showHelp = !this.showHelp;
		}
		if (button.id > 0) {
			val = button.id;
			initGui();
		}
	}

	@Override
	public void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		Rectangle mouse = new Rectangle(x, y, 1, 1);
		Rectangle bounds = new Rectangle(10, 10, 100, 20);
		if (!mouse.intersects(bounds)) {
			MovingObjectPosition pos = entity3rdPerson.rayTraceMouse();
			// NetworkHandler.placeTrackStartPoint(te, pos);
		}
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {
		// super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
		Minecraft.getMinecraft().renderViewEntity = Minecraft.getMinecraft().thePlayer;
		Minecraft.getMinecraft().gameSettings.thirdPersonView = thirdPersonView;
		Minecraft.getMinecraft().theWorld.removeEntity(entity3rdPerson);
		entity3rdPerson = null;
		// super.mc.displayGuiScreen((GuiScreen)null);
		// super.mc.setIngameFocus();
	}

	@Override
	public void keyTyped(char key, int value) {
		super.keyTyped(key, value);

		if ((value == keyForward || value == keyLeft || value == keyRight
				|| value == keyLeft || value == keyLookLeft
				|| value == keyLookRight || value == keyUp || value == keyDown || value == keyBackward)
				&& entity3rdPerson != null) {
			this.entity3rdPerson.onUpdate();
		} else if (value >= 2 && value < 12) {
			this.selectedSlot = value - 2;
		}
	}
}
