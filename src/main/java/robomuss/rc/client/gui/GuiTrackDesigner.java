package robomuss.rc.client.gui;

import modforgery.forgerylib.ChatColours;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.client.Keybindings;
import robomuss.rc.entity.Entity3rdPerson;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.track.TrackHandler;

import java.awt.*;

public class GuiTrackDesigner extends GuiScreen {

    private TileEntityTrackDesigner te;

    public static Entity3rdPerson entity3rdPerson;
    private int thirdPersonView = 0;

    private static final ResourceLocation TOOLBAR_TEXTURE = new ResourceLocation("rc", "textures/gui/track_designer_toolbar.png");
    private static final int TOOLBAR_TEXTURE_WIDTH = 194;
    private static final int TOOLBAR_TEXTURE_HEIGHT = 35;

    private int selectedSlot = 0;

    private static final ResourceLocation slot1 = new ResourceLocation("rc", "textures/blocks/tracks/curve.png");

    private boolean showHelp = false;

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
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        buttonList.clear();

        buttonList.add(new GuiButton(0, 10, 10, 100, 20, "Place"));
        buttonList.add(new GuiButton(1, this.width - 40, 10, 30, 20, "Help"));
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {

        if (showHelp) {
            String string = "Coming in v1.5!";
            drawString(fontRendererObj, string, this.width / 2 - (fontRendererObj.getStringWidth(string) / 2), this.height / 2 - 60, 0xFFFFFF);

            String string2 = "===============";
            drawString(fontRendererObj, string2, this.width / 2 - (fontRendererObj.getStringWidth(string2) / 2), this.height / 2 - 50, 0xFFFFFF);

            String string3 = "Track Designer";
            drawString(fontRendererObj, string3, this.width / 2 - (fontRendererObj.getStringWidth(string3) / 2), this.height / 2 - 30, 0xFFFFFF);

            String string4 = "===============";
            drawString(fontRendererObj, string4, this.width / 2 - (fontRendererObj.getStringWidth(string4) / 2), this.height / 2 - 10, 0xFFFFFF);

            String controls1 = "Use W A S D to move around";
            drawString(fontRendererObj, controls1, this.width / 2 - (fontRendererObj.getStringWidth(controls1) / 2), this.height / 2 + 20, 0xFFFFFF);

            String controls2 = "Use " + Keyboard.getKeyName(Keybindings.up.getKeyCode()).toUpperCase() + " and " + Keyboard.getKeyName(Keybindings.down.getKeyCode()).toUpperCase() + " to move up and down";
            drawString(fontRendererObj, controls2, this.width / 2 - (fontRendererObj.getStringWidth(controls2) / 2), this.height / 2 + 40, 0xFFFFFF);

            String controls3 = "Use " + Keyboard.getKeyName(Keybindings.lookLeft.getKeyCode()).toUpperCase() + " and " + Keyboard.getKeyName(Keybindings.lookRight.getKeyCode()).toUpperCase() + " to rotate left and right";
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

        //TODO this is broken fix it
//        for (int i = 0; i < TrackHandler.pieces.size(); i++) {
//            if(TrackHandler.pieces.get(i).block != null)
//            GuiUtils.renderItemIntoGui(ItemBlock.getItemFromBlock(TrackHandler.pieces.get(i).block), cornerX  + TOOLBAR_TEXTURE_WIDTH /2  + (i * 30) - 10, cornerY + 6 , 1.2F, this.fontRendererObj, this.mc);
//        }

        mc.renderEngine.bindTexture(slot1);
        drawTexturedModalRect(cornerX + 8 + 1 * 18, cornerY + 8, 0, 0, 18, 18);

        for (int i = 0; i < 10; i++) {
            drawString(this.fontRendererObj, "0", cornerX + (i * 18) + 12, cornerY + 25, ChatColours.WHITE);
        }


        if (selectedSlot <= TrackHandler.pieces.size()) {
            drawString(this.fontRendererObj, TrackHandler.extras.get(selectedSlot).name, 10, 55, ChatColours.WHITE);
        }


        if (selectedSlot != -1) {
            mc.renderEngine.bindTexture(TOOLBAR_TEXTURE);
            drawTexturedModalRect(cornerX + 8 + selectedSlot * 18, cornerY + 8, 194, 0, 18, 18);
        }

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

        super.drawScreen(par1, par2, par3);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            this.showHelp = !this.showHelp;
        } else {
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
        super.onGuiClosed();
        Minecraft.getMinecraft().renderViewEntity = Minecraft.getMinecraft().thePlayer;
        Minecraft.getMinecraft().gameSettings.thirdPersonView = thirdPersonView;
        Minecraft.getMinecraft().theWorld.removeEntity(entity3rdPerson);
        entity3rdPerson = null;
    }
}
