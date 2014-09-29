package robomuss.rc.client.gui;

import java.awt.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.entity.Entity3rdPerson;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.util.OsUtil;

public class GuiTrackDesigner extends GuiScreen {

	private TileEntityTrackDesigner te;

    public Entity3rdPerson entity3rdPerson;
    private int thirdPersonView = 0;

    private double posX, posY, posZ;
    public GuiTrackDesigner(EntityPlayer player, World world, int x, int y, int z) {
		te = (TileEntityTrackDesigner) world.getTileEntity(x, y, z);
		
        entity3rdPerson = new Entity3rdPerson(Minecraft.getMinecraft().theWorld);
        if (entity3rdPerson != null) {
            Minecraft.getMinecraft().theWorld.spawnEntityInWorld(entity3rdPerson);

            entity3rdPerson.setLocationAndAngles(x, y, z, 0 , 50);

            Minecraft.getMinecraft().renderViewEntity = entity3rdPerson;
            thirdPersonView = Minecraft.getMinecraft().gameSettings.thirdPersonView;
            Minecraft.getMinecraft().gameSettings.thirdPersonView = 8;

            posX = entity3rdPerson.posX + 0.5;
            posY = entity3rdPerson.posY + 5;
            posZ = entity3rdPerson.posZ - 5;

            entity3rdPerson.setPositionAndUpdate(posX, posY, posZ);
        }
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.clear();
		
		buttonList.add(new GuiButton(0, 10, 10, 100, 20, "Place"));
	}*/
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
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

		String controls2 = "Use PAGE-UP and PAGE-DOWN to move up and down";
        if(OsUtil.isMac()){
            controls2 = "Use UP and DOWN to move up and down";
        }
		drawString(fontRendererObj, controls2, this.width / 2 - (fontRendererObj.getStringWidth(controls2) / 2), this.height / 2 + 40, 0xFFFFFF);
		
		String controls3 = "Use Q and E to rotate left and right";
		drawString(fontRendererObj, controls3, this.width / 2 - (fontRendererObj.getStringWidth(controls3) / 2), this.height / 2 + 60, 0xFFFFFF);
		
		String controls4 = "Use SHIFT-W and SHIFT-S to rotate up and down";
		drawString(fontRendererObj, controls4, this.width / 2 - (fontRendererObj.getStringWidth(controls4) / 2), this.height / 2 + 80, 0xFFFFFF);

       /*GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(trackDesignerGuiTextures);
        int k = (this.width - this.mc.displayWidth) / 2;
        int l = (this.height - this.mc.displayHeight) / 2;
        this.drawTexturedModalRect(k, l, 0, 100, this.mc.displayWidth, this.mc.displayHeight);*/
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		NetworkHandler.handleTrackDesignerButtonClick(te, button.id);
	}
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		Rectangle mouse = new Rectangle(x, y, 1, 1);
		Rectangle bounds = new Rectangle(10, 10, 100, 20);
		if(!mouse.intersects(bounds)) {
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
