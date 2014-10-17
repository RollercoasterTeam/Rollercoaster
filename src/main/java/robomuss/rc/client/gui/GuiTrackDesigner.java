package robomuss.rc.client.gui;

import modforgery.forgerylib.ChatColours;
import modforgery.forgerylib.GuiUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.client.Keybindings;
import robomuss.rc.client.gui.exList.ExpandableListNode;
import robomuss.rc.client.gui.exList.ExpandableListNodeRollercoasters;
import robomuss.rc.entity.Entity3rdPerson;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.track.TrackHandler;
import scala.actors.threadpool.Arrays;

import java.awt.*;
import java.util.ArrayList;

public class GuiTrackDesigner extends GuiScreen {

    private TileEntityTrackDesigner te;

    public static Entity3rdPerson entity3rdPerson;
    private int thirdPersonView = 0;

    private static final ResourceLocation TOOLBAR_TEXTURE = new ResourceLocation("rc", "textures/gui/track_designer_toolbar.png");
    private static final int TOOLBAR_TEXTURE_WIDTH = 194;
    private static final int TOOLBAR_TEXTURE_HEIGHT = 36;
    private int selectedSlot = 0;
    private boolean showHelp = false;
    private ArrayList<Block> blocks =  new ArrayList<Block>();

	public static int keyForward = 17;                      //W
	public static int keyBackward = 31;                     //S
	public static int keyLeft = 30;                         //A
	public static int keyRight = 32;                        //D
	public static int keyLookLeft = 16;                     //Q
	public static int keyLookRight = 18;                    //E
	public static int keyUp = 201;                          //PgUp
	public static int keyDown = 209;                        //PgDn

	private ExpandableListNode[] nodes = {
    	new ExpandableListNodeRollercoasters("Rollercoasters", null, null),
    	new ExpandableListNode("Paths", null, null),
    	new ExpandableListNode("Fences", null, null)
    };

    private double posX, posY, posZ;

    public GuiTrackDesigner(EntityPlayer player, World world, int x, int y, int z) {
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

        //Add all of the blocks that you want in the hotbar here
        for (int i = 0; i < TrackHandler.pieces.size(); i++) {
            blocks.add(TrackHandler.pieces.get(i).block);
        }

        blocks.add(RCBlocks.support);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        buttonList.clear();

        buttonList.add(new GuiButton(0, this.width - 40, 10, 30, 20, "Help"));
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        GL11.glPushMatrix();
        GL11.glScalef(0.125f, 0.125f, 0.125f);
        int k = (this.width - 176) / 2;
	    int l = (this.height - 166) / 2;
        Rectangle mouse = new Rectangle(x, y, 1, 1);
        for(int i = 0; i < nodes.length; i++) {
        	ExpandableListNode node = nodes[i];
        	Rectangle bounds = new Rectangle(k + 50, l + 350 + (i * 250), 32, 32);
        	if(mouse.intersects(bounds)) {
        		drawHoveringText(Arrays.asList(new Object[]{node.getName()}), x, y, fontRendererObj);
        		
        	}
    		mc.renderEngine.bindTexture(node.texture);
	        drawTexturedModalRect(58, 358 + (i * 250), 0, 0, 256, 256);
        }
        GL11.glPopMatrix();
        //Only draw when the help button has been pressed
        if (showHelp) {
            /*String string = "Coming in v1.5!";
            drawString(fontRendererObj, string, this.width / 2 - (fontRendererObj.getStringWidth(string) / 2), this.height / 2 - 60, 0xFFFFFF);

            String string2 = "===============";
            drawString(fontRendererObj, string2, this.width / 2 - (fontRendererObj.getStringWidth(string2) / 2), this.height / 2 - 50, 0xFFFFFF);

            String string3 = "Track Designer";
            drawString(fontRendererObj, string3, this.width / 2 - (fontRendererObj.getStringWidth(string3) / 2), this.height / 2 - 30, 0xFFFFFF);

            String string4 = "===============";
            drawString(fontRendererObj, string4, this.width / 2 - (fontRendererObj.getStringWidth(string4) / 2), this.height / 2 - 10, 0xFFFFFF);
*/
            String controls1 = "Use W A S D to move around";
//	        String controls1 = String.format("Use %s %s %s %s to move around", Keyboard.getKeyName(keyForward).toUpperCase(), Keyboard.getKeyName(keyLeft).toUpperCase(), Keyboard.getKeyName(keyBackward).toUpperCase(), Keyboard.getKeyName(keyRight).toUpperCase());
            drawString(fontRendererObj, controls1, this.width / 2 - (fontRendererObj.getStringWidth(controls1) / 2), this.height / 2 + 20, 0xFFFFFF);

//            String controls2 = "Use " + Keyboard.getKeyName(Keybindings.up.getKeyCode()).toUpperCase() + " and " + Keyboard.getKeyName(Keybindings.down.getKeyCode()).toUpperCase() + " to move up and down";
	        String controls2 = "Use " + Keyboard.getKeyName(keyUp).toUpperCase() + " and " + Keyboard.getKeyName(keyDown).toUpperCase() + " to move up and down";
            drawString(fontRendererObj, controls2, this.width / 2 - (fontRendererObj.getStringWidth(controls2) / 2), this.height / 2 + 40, 0xFFFFFF);

//            String controls3 = "Use " + Keyboard.getKeyName(Keybindings.lookLeft.getKeyCode()).toUpperCase() + " and " + Keyboard.getKeyName(Keybindings.lookRight.getKeyCode()).toUpperCase() + " to rotate left and right";
	        String controls3 = "Use " + Keyboard.getKeyName(keyLookLeft).toUpperCase() + " and " + Keyboard.getKeyName(keyLookRight).toUpperCase() + " to rotate left and right";
            drawString(fontRendererObj, controls3, this.width / 2 - (fontRendererObj.getStringWidth(controls3) / 2), this.height / 2 + 60, 0xFFFFFF);

            String controls4 = "Use SHIFT-W and SHIFT-S to rotate up and down";
            drawString(fontRendererObj, controls4, this.width / 2 - (fontRendererObj.getStringWidth(controls4) / 2), this.height / 2 + 80, 0xFFFFFF);

        }


        /*
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(trackDesignerGuiTextures);
        int k = (this.width - this.mc.displayWidth) / 2;
        int l = (this.height - this.mc.displayHeight) / 2;
        this.drawTexturedModalRect(k, l, 0, 100, this.mc.displayWidth, this.mc.displayHeight);*/

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int cornerX = (width - TOOLBAR_TEXTURE_WIDTH) / 2;
        int cornerY = -5;

        mc.renderEngine.bindTexture(TOOLBAR_TEXTURE);
        drawTexturedModalRect(cornerX, cornerY, 0, 0, TOOLBAR_TEXTURE_WIDTH, TOOLBAR_TEXTURE_HEIGHT);

        //Renders all the blocks in the hotbar
        for (int i = 0; i < blocks.size(); i++) {
        	//GuiUtils.renderBlockIntoGui(blocks.get(i), cornerX + TOOLBAR_TEXTURE_WIDTH / 2 + (i * 30) - 10, cornerY + 6, 1.2F, this.fontRendererObj, this.mc);
        }

        //Renders all the things in the hotbar
        for (int i = 0; i < 10; i++) {
            drawString(this.fontRendererObj, "0", cornerX + (i * 18) + 12, cornerY + 25, ChatColours.WHITE);
        }

        //Draws the block name in a gui
        if (selectedSlot != -1) {
            //This is always recommend my me(modmuss50)
            try{
                //drawString(this.fontRendererObj, blocks.get(selectedSlot).getLocalizedName(), 10, 55, ChatColours.WHITE);
            } catch (IndexOutOfBoundsException e){

            }
        }

        //Draw the selected
        if (selectedSlot != -1) {
            mc.renderEngine.bindTexture(TOOLBAR_TEXTURE);
            drawTexturedModalRect(cornerX + 8 + selectedSlot * 18, cornerY + 8, 194, 0, 18, 18);
        }

        //Changes the slot based on the mouse wheel
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
        if (button.id == 0) {
            //Show the help
            this.showHelp = !this.showHelp;
        } else {
            //send the packet to the server
            NetworkHandler.handleTrackDesignerButtonClick(te, button.id, entity3rdPerson.rayTraceMouse(), selectedSlot);
        }

    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        Rectangle mouse = new Rectangle(x, y, 1, 1);
        Rectangle bounds = new Rectangle(10, 10, 100, 20);
        if (!mouse.intersects(bounds)) {
            MovingObjectPosition pos = entity3rdPerson.rayTraceMouse();
            //NetworkHandler.placeTrackStartPoint(te, pos);
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
//        super.onGuiClosed();
	    Keyboard.enableRepeatEvents(false);
        Minecraft.getMinecraft().renderViewEntity = Minecraft.getMinecraft().thePlayer;
        Minecraft.getMinecraft().gameSettings.thirdPersonView = thirdPersonView;
        Minecraft.getMinecraft().theWorld.removeEntity(entity3rdPerson);
        entity3rdPerson = null;
//	    super.mc.displayGuiScreen((GuiScreen)null);
//	    super.mc.setIngameFocus();
    }

	@Override
	public void keyTyped(char key, int value) {
		super.keyTyped(key, value);

		if ((value == keyForward || value == keyLeft || value == keyRight || value == keyLeft || value == keyLookLeft || value == keyLookRight || value == keyUp || value == keyDown) && entity3rdPerson != null) {
			this.entity3rdPerson.onUpdate();
		} else if (value >= 2 && value < 12) {
			this.selectedSlot = value - 2;
		}
	}
}
