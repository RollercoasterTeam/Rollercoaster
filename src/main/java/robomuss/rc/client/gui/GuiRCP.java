package robomuss.rc.client.gui;

import java.awt.event.KeyEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

import robomuss.rc.RCMod;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.entity.Entity3rdPerson;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.network.packets.PacketTrackDesigner;
import robomuss.rc.track.Theme;
import robomuss.rc.track.TrackDesign;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.util.ColourUtil;
import robomuss.rc.util.EnumStationType;
import robomuss.rc.util.Point;

public class GuiRCP extends GuiScreen {
    
	private TileEntityRideFence te;
	
	private static final ResourceLocation texture = new ResourceLocation("rc", "textures/gui/rcp.png");
	
	private GuiTextField carts;
	private int amount = 1;
	
	private int rideState, lightState;
	private static final String[] rideStates = {"Closed", "Testing", "Open"}, lightStates = {"Off", "Flashing", "On"};
	
    public GuiRCP(EntityPlayer player, World world, int x, int y, int z) {
		te = (TileEntityRideFence) world.getTileEntity(x, y, z);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		int k = (this.width - 176) / 2;
		int l = (this.height - 166) / 2;
		
		buttonList.clear();
		
		buttonList.add(new GuiButton(0, k + 5, l + 15, 82, 20, "Mode: " + rideStates[rideState]));
		buttonList.add(new GuiButton(1, k + 89, l + 15, 82, 20, "Lights: " + lightStates[lightState]));
		buttonList.add(new GuiButton(2, k + 5, l + 37, 82, 20, "Toggle Gates"));
		
		String s = "" + ChatFormatting.RED + ChatFormatting.BOLD;
		buttonList.add(new GuiButton(3, k + 89, l + 37, 82, 20, s + "Delete Ride"));
		
		buttonList.add(new GuiButton(4, k + 5, l + 59, 20, 20, "-"));
		buttonList.add(new GuiButton(5, k + 27, l + 59, 20, 20, "+"));
		buttonList.add(new GuiButton(6, k + 89, l + 59, 82, 20, "Deploy Carts"));
		
		carts = new GuiTextField(fontRendererObj, k + 50, l + 60, 36, 18);
		carts.setFocused(false);
		carts.setMaxStringLength(1);
		carts.setText("" + amount);
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (166 / 2);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(k, l, 0, 0, 176, 166);
        
        drawString(fontRendererObj, te.td.name, (this.width / 2) - (fontRendererObj.getStringWidth(te.td.name) / 2), l + 5, 0xFFFFFF);
        
        carts.drawTextBox();
        
        super.drawScreen(par1, par2, par3);
        
        /*for(Point point : te.td.gates) {
        	System.out.println("Gate at: " + point.x + ", " + point.y + ", " + point.z);
        }*/
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if(button.id == 0) {
			rideState = rideState == rideStates.length - 1 ? 0 : rideState + 1;
			((GuiButton) buttonList.get(0)).displayString = "Mode: " + rideStates[rideState];
		}
		else if(button.id == 1) {
			lightState = lightState == lightStates.length - 1 ? 0 : lightState + 1;
			((GuiButton) buttonList.get(1)).displayString = "Lights: " + lightStates[lightState];
		}
		else if(button.id == 2) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.UPDATE_GATES, te, 0);
		}
		else if(button.id == 3) {
			FMLNetworkHandler.openGui(Minecraft.getMinecraft().thePlayer, RCMod.instance, 4, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
		}
		else if(button.id == 4) {
			amount += amount > 0 ? -1 : 0;
			carts.setText("" + amount);
		}
		else if(button.id == 5) {
			amount += amount < 8 ? 1 : 0;
			carts.setText("" + amount);
		}
		else if(button.id == 6) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.DEPLOY_CARTS, te, 0);
		}
	}

    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		if(Character.toString(c).matches("[0-9]") || c == (char) KeyEvent.VK_BACK_SPACE) {
			carts.textboxKeyTyped(c, i);
		}
	}
    
    @Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		carts.mouseClicked(i, j, k);
		if(!carts.isFocused()) {
			if(Integer.parseInt(carts.getText()) == 0) {
				amount = 1;
				carts.setText("" + amount);
			}
			else if(Integer.parseInt(carts.getText()) > 8) {
				amount = 8;
				carts.setText("" + amount);
			}
			else {
				amount = Integer.parseInt(carts.getText());
			}
		}
	}
}
