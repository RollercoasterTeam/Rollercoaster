package robomuss.rc.client.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class GuiRCPDR extends GuiScreen {
    
	private TileEntityRideFence te;
	
	private static final ResourceLocation texture = new ResourceLocation("rc", "textures/gui/rcp.png");
	
	private static final String formatting = "" + ChatFormatting.RED + ChatFormatting.BOLD;
	
	private GuiTextField verify;
	
	private boolean help = false;
	
	private List<Object> list = Arrays.asList(new Object[]{"Type DELETE and click Confirm", "Changes are NOT reversable"});
	
    public GuiRCPDR(EntityPlayer player, World world, int x, int y, int z) {
		te = (TileEntityRideFence) world.getTileEntity(x, y, z);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		int k = (this.width - 176) / 2;
		int l = (this.height - 166) / 2;
		
		buttonList.clear();
		
		buttonList.add(new GuiButton(0, (this.width / 2) - 40, (this.height / 2) - 46, 80, 20, "Cancel"));
		buttonList.add(new GuiButton(1, (this.width / 2) - 40, (this.height / 2) - 24, 80, 20, "Confirm"));
		buttonList.add(new GuiButton(2, (this.width / 2) + 42, (this.height / 2) - 68, 20, 20, "?"));
		
		((GuiButton) buttonList.get(1)).visible = false;
		
		verify = new GuiTextField(fontRendererObj, (this.width / 2) - 39, (this.height / 2) - 67, 78, 18);
		verify.setFocused(true);
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (166 / 2);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(k, l, 0, 0, 176, 166);
        
        drawCenteredString(fontRendererObj, formatting + "Delete Ride", this.width / 2, l + 5, 0xFFFFFF);
        
        verify.drawTextBox();
        
        if(help) {
        	drawHoveringText(list, k, 20, fontRendererObj);
        }
        
        super.drawScreen(par1, par2, par3);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if(button.id == 0) {
			FMLNetworkHandler.openGui(Minecraft.getMinecraft().thePlayer, RCMod.instance, 3, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
		}
		else if(button.id == 1) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.DELETE_RIDE, te, 0);
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
		else if(button.id == 2) {
			help = !help;
		}
	}

    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		verify.textboxKeyTyped(c, i);
		((GuiButton) buttonList.get(1)).visible = verify.getText().equals("DELETE");
	}
    
    @Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		verify.mouseClicked(i, j, k);
	}
}
