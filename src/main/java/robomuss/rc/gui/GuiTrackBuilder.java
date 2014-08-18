package robomuss.rc.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.entity.Entity3rdPerson;
import robomuss.rc.network.NetworkHandler;

public class GuiTrackBuilder extends GuiScreen {

	private TileEntityTrackDesigner te;

    public Entity3rdPerson entity3rdPerson;
    EntityLivingBase player3rd;
    int thirdPersonView = 0;

    double posX, posY, posZ;
    float yaw;


    public GuiTrackBuilder(EntityPlayer player, World world, int x, int y, int z) {
		te = (TileEntityTrackDesigner) world.getTileEntity(x, y, z);


        entity3rdPerson = new Entity3rdPerson(Minecraft.getMinecraft().theWorld);
        if (entity3rdPerson != null) {
            Minecraft.getMinecraft().theWorld.spawnEntityInWorld(entity3rdPerson);
            player3rd = Minecraft.getMinecraft().renderViewEntity;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.clear();
		
		/*buttonList.add(new GuiButton(1, 10, 10, 100, 20, "Start"));
		buttonList.add(new GuiButton(1, 120, 10, 100, 20, "Left"));
		buttonList.add(new GuiButton(2, 230, 10, 100, 20, "Forward"));
		buttonList.add(new GuiButton(3, 340, 10, 100, 20, "Right"));*/
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		String string = "Coming in v1.4!";
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
		drawString(fontRendererObj, controls2, this.width / 2 - (fontRendererObj.getStringWidth(controls2) / 2), this.height / 2 + 40, 0xFFFFFF);
		
		String controls3 = "Use Q and E to rotate left and right";
		drawString(fontRendererObj, controls3, this.width / 2 - (fontRendererObj.getStringWidth(controls3) / 2), this.height / 2 + 60, 0xFFFFFF);
		
		String controls4 = "Use SHIFT-W and SHIFT-S to rotate up and down";
		drawString(fontRendererObj, controls4, this.width / 2 - (fontRendererObj.getStringWidth(controls4) / 2), this.height / 2 + 80, 0xFFFFFF);
		

        MovingObjectPosition movingObjectPosition = entity3rdPerson.rayTraceMouse();
        if(movingObjectPosition != null){
            //drawString(fontRendererObj, ("Current cross hair location: " + movingObjectPosition.blockX + ":" + movingObjectPosition.blockY + ":" + movingObjectPosition.blockZ), 10, 80, 0xFFFFFF);
        }
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		MovingObjectPosition movingObjectPosition = entity3rdPerson.rayTraceMouse();
		NetworkHandler.updateTrackBuilderTE(te, movingObjectPosition, button.id);
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
