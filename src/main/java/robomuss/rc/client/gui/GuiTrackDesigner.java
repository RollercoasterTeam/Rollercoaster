package robomuss.rc.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import robomuss.rc.RCMod;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.client.gui.exList.ExpandableListNode;
import robomuss.rc.client.gui.exList.ExpandableListNodeRollercoasters;
import robomuss.rc.client.gui.keybinding.TrackDesignerKeyBindings;
import robomuss.rc.entity.Entity3rdPerson;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.util.GuiPanel;

<<<<<<< HEAD
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

=======
>>>>>>> origin/One8PortTake2
public class GuiTrackDesigner extends GuiScreen {
	private TileEntityTrackDesigner te;

//	private final GuiRCControls GUI_RC_CONTROLS = new GuiRCControls(this, RCMod.rcOptions);
//	private final GuiRCKeyBindingList GUI_RC_KEY_BINDING_LIST = new GuiRCKeyBindingList(GUI_RC_CONTROLS, Minecraft.getMinecraft());

	private static final String CONTROLS = "Controls";
	private static final String HELP = "Help";
	private static final String EXIT = "Exit";

	private static final String[] BUTTON_NAMES = {CONTROLS, HELP, EXIT};
	private static final int[] BUTTON_IDS = {99, 100, 101};

	public EntityPlayer player;
	public Entity3rdPerson entity3rdPerson;
	private int thirdPersonView = 0;

	private static final GuiPanel NEW_COASTER_PANEL = new GuiPanel("newCoaster", "Create Rollercoaster", 128, 148);
	private int selectedSlot = 0;
	private boolean showHelp = false;
	private boolean shouldDeleteEntity = false;
	private ArrayList<Block> blocks = new ArrayList<Block>();

	private ExpandableListNode[] nodes = {
			new ExpandableListNodeRollercoasters("Rollercoasters", null, null),
			new ExpandableListNode("Paths", null, new ExpandableListNode[0]),
			new ExpandableListNode("Fences", null, new ExpandableListNode[0]) 
	};

	private double posX, posY, posZ;

	public GuiTrackDesigner(EntityPlayer player, World world, int x, int y, int z) {
		this.mc = Minecraft.getMinecraft();

		te = (TileEntityTrackDesigner) world.getTileEntity(new BlockPos(x, y, z));

		if (!TrackDesignerKeyBindings.haveKeyBindsBeenInitialized) {
			TrackDesignerKeyBindings.init();
		}

		this.player = player;

		entity3rdPerson = new Entity3rdPerson(Minecraft.getMinecraft().theWorld, TrackDesignerKeyBindings.getRCKeyBinds(), this);

		if (entity3rdPerson != null) {
			Minecraft.getMinecraft().theWorld.spawnEntityInWorld(entity3rdPerson);

			entity3rdPerson.setLocationAndAngles(x, y, z, 0, 50);

			Minecraft.getMinecraft().setRenderViewEntity(entity3rdPerson);
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

	public TileEntityTrackDesigner getTrackDesigner() {
		return this.te;
	}

	int val = -1;

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.clear();

		int offset = 9;
		for (int i = BUTTON_IDS.length - 1; i > -1; i--) {
			int width = mc.fontRendererObj.getStringWidth(BUTTON_NAMES[i]) + 9;
			width = width < 30 ? 30 : width;
			offset += width + 1;
			buttonList.add(new GuiButton(BUTTON_IDS[i], this.width - offset, 10, width, 20, BUTTON_NAMES[i]));
		}

		for (int i = 0; i < nodes.length; i++) {
			ExpandableListNode node = nodes[i];

			if (val == i) {
				if (node.getChildren() != null) {
					for (int j = 0; j < node.getChildren().length; j++) {
						ExpandableListNode child = node.getChildren()[i];
						buttonList.add(new GuiButton(i + ((j + 1) * 10), 140, 40 + (i * 40), 100, 20, child.getName()));
					}
				}
			}

			buttonList.add(new GuiButton(i, 20, 40 + (i * 40), 100, 20, node.getName()));
		}
	}

	public List<GuiButton> getButtonList() {
		return this.buttonList;
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		if (showHelp) {
			String kForward  = TrackDesignerKeyBindings.forward.getKeyNameUpper();
			String kLeft     = TrackDesignerKeyBindings.left.getKeyNameUpper();
			String kBackward = TrackDesignerKeyBindings.backward.getKeyNameUpper();
			String kRight    = TrackDesignerKeyBindings.right.getKeyNameUpper();
			String kUp       = TrackDesignerKeyBindings.up.getKeyNameUpper();
			String kDown     = TrackDesignerKeyBindings.down.getKeyNameUpper();
			String kLLeft    = TrackDesignerKeyBindings.lookLeft.getKeyNameUpper();
			String kLRight   = TrackDesignerKeyBindings.lookRight.getKeyNameUpper();

			String controls1 = String.format("Use %s %s %s %s to move around", kForward, kLeft, kBackward, kRight);
			String controls2 = String.format("Use %s and %s to move up and down", kUp, kDown);
			String controls3 = String.format("Use %s and %s to rotate left and right", kLLeft, kLRight);
			String controls4 = String.format("Use SHIFT-%S and SHIFT-%s to rotate up and down", kForward, kBackward); //TODO: allow these to be rebound separately?

			drawString(fontRendererObj, controls1, this.width / 2 - (fontRendererObj.getStringWidth(controls1) / 2), this.height / 2 + 20, 0xFFFFFF);
			drawString(fontRendererObj, controls2, this.width / 2 - (fontRendererObj.getStringWidth(controls2) / 2), this.height / 2 + 40, 0xFFFFFF);
			drawString(fontRendererObj, controls3, this.width / 2 - (fontRendererObj.getStringWidth(controls3) / 2), this.height / 2 + 60, 0xFFFFFF);
			drawString(fontRendererObj, controls4, this.width / 2 - (fontRendererObj.getStringWidth(controls4) / 2), this.height / 2 + 80, 0xFFFFFF);
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int cornerX = (width - NEW_COASTER_PANEL.width) / 2;
		int cornerY = 40;

//		mc.renderEngine.bindTexture(NEW_COASTER_PANEL.texture);
//		drawTexturedModalRect(cornerX, cornerY, 0, 0, NEW_COASTER_PANEL.width, NEW_COASTER_PANEL.height);
//		drawString(fontRendererObj, NEW_COASTER_PANEL.displayName, cornerX + ((NEW_COASTER_PANEL.width / 2) - (fontRendererObj.getStringWidth(NEW_COASTER_PANEL.displayName) / 2)), cornerY + 10, 16777215);
		
		// Changes the slot based on the mouse wheel
		int dxWheel = Mouse.getDWheel();

		if (dxWheel != 0) {
			if (dxWheel > 0) {
				selectedSlot = selectedSlot != 9 ? selectedSlot++ : 0;
			}
			if (dxWheel < 0) {
				selectedSlot = selectedSlot != 0 ? selectedSlot-- : 9;
			}
		}

		super.drawScreen(x, y, f);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id == 99) {
			shouldDeleteEntity = false;
			this.mc.displayGuiScreen(new GuiRCControls(this, RCMod.rcOptions));
		}

		if (button.id == 100) {
			this.showHelp = !this.showHelp;
			if (!TrackDesignerKeyBindings.haveKeyBindsBeenInitialized) {
				TrackDesignerKeyBindings.init();
			}
		}

		if (button.id == 101) {
			shouldDeleteEntity = true;
			this.player.closeScreen();
		}

		if (button.id > 0) {
			val = button.id;
			initGui();
		}
	}

	@Override
	public void mouseClicked(int x, int y, int button) {
		try {
			super.mouseClicked(x, y, button);
		} catch (IOException exception) {
			;
		}
//		Rectangle mouse = new Rectangle(x, y, 1, 1);
//		Rectangle bounds = new Rectangle(10, 10, 100, 20);

		if (entity3rdPerson != null) {
			if (!((GuiButton) this.buttonList.get(button)).isMouseOver()) {
				entity3rdPerson.rayTraceMouse();
			}
		}

//		if (!mouse.intersects(bounds)) {
//			MovingObjectPosition pos = entity3rdPerson.rayTraceMouse();
//			// NetworkHandler.placeTrackStartPoint(te, pos);
//		}
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {
		// super.onGuiClosed();
//		if (!(this.mc.currentScreen instanceof GuiRCControls)) {
		if (shouldDeleteEntity) {
			Keyboard.enableRepeatEvents(false);
			Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().thePlayer);
			Minecraft.getMinecraft().gameSettings.thirdPersonView = thirdPersonView;
			Minecraft.getMinecraft().theWorld.removeEntity(entity3rdPerson);
			entity3rdPerson = null;
//			TrackDesignerKeyBindings.unBindDesignerKeys();
		}
		// super.mc.displayGuiScreen((GuiScreen)null);
		// super.mc.setIngameFocus();
	}

	@Override
	public void keyTyped(char key, int value) {
		try {
			super.keyTyped(key, value);
		} catch (IOException exception) {
			;
		}

		if (value != Keyboard.KEY_ESCAPE) {
			entity3rdPerson.onUpdate();
		}
	}
}
