package robomuss.rc.client.gui;

import java.awt.event.KeyEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

import robomuss.rc.block.RCBlocks;
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

public class GuiTrackDesigner extends GuiScreen {

	private TileEntityTrackDesigner te;

    public Entity3rdPerson entity3rdPerson;
    private int thirdPersonView = 0;

    private double posX, posY, posZ;
    
    private boolean showHelp = false;
    public static boolean createMenu = false, placeMenu = false, loadMenu = false, escapeMenu = false;
    
    private GuiTextField rideName, loadName;
    private int theme, trackPaint, supportPaint, fencePaint;
    
    private static final ResourceLocation texture = new ResourceLocation("rc", "textures/gui/track_designer.png");
    private static final ResourceLocation texture2 = new ResourceLocation("rc", "textures/gui/track_designer_2.png");
    private static final ResourceLocation texture3 = new ResourceLocation("rc", "textures/gui/track_designer_3.png");
    
    private int extra = 1;
    
    private static final double MAX = 1.05;
	private static final double MIN = -0.05;
    
	public GuiTrackDesigner() {
		
	}
	
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
        
        createMenu = false;
        placeMenu = false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		int k = (this.width - 176) / 2;
		int l = (this.height - 166) / 2;
		
		buttonList.clear();
		
		buttonList.add(new GuiButton(0, this.width - 120, 0, 120, 20, "Help"));
		buttonList.add(new GuiButton(1, this.width - 120, 20, 120, 20, "New RC"));
		buttonList.add(new GuiButton(2, (this.width / 2) + 43, (this.height / 2) - 12, 40, 20, "Start"));
		
		buttonList.add(new GuiButton(3, k + 5, l + 21, 20, 20, ""));
		buttonList.add(new GuiButton(4, k + 5, l + 43, 20, 20, ""));
		
		buttonList.add(new GuiButton(5, 2, this.height - 44, 130, 20, "Place Start"));
		
		buttonList.add(new GuiButton(6, 2, this.height - 22, 20, 20, "<"));
		buttonList.add(new GuiButton(7, 24, this.height - 22, 20, 20, "^"));
		buttonList.add(new GuiButton(8, 46, this.height - 22, 20, 20, ">"));
		buttonList.add(new GuiButton(9, 68, this.height - 22, 31, 20, "Loop"));
		
		buttonList.add(new GuiButton(10, 2, this.height - 44, 42, 20, "Rotate"));
		buttonList.add(new GuiButton(11, 2, this.height - 22, 130, 20, "Confirm"));
		
		buttonList.add(new GuiButton(12, 134, this.height - 22, 100, 20, PacketTrackDesigner.extras[0]));
		
		buttonList.add(new GuiButton(13, 2, this.height - 44, 20, 20, "-\\"));
		buttonList.add(new GuiButton(14, 24, this.height - 44, 20, 20, "\\"));
		buttonList.add(new GuiButton(15, 46, this.height - 44, 20, 20, "\\_"));
		
		buttonList.add(new GuiButton(16, 46, this.height - 44, 42, 20, "-"));
		buttonList.add(new GuiButton(17, 90, this.height - 44, 42, 20, "+"));
		
		buttonList.add(new GuiButton(18, k + 5, l + 65, 20, 20, ""));
		buttonList.add(new GuiButton(19, (this.width / 2) + 43, l + 21, 40, 20, ""));
		
		buttonList.add(new GuiButton(20, (this.width / 2) - 83, (this.height / 2) - 12, 40, 20, "Cancel"));
		buttonList.add(new GuiButton(21, (this.width / 2) + 43, (this.height / 2) - 12, 40, 20, "Ok"));
		
		buttonList.add(new GuiButton(22, this.width - 120, 40, 120, 20, "Load RC " + ChatFormatting.GREEN + ChatFormatting.BOLD + "[WIP]" + ChatFormatting.RESET));
		
		buttonList.add(new GuiButton(23, 68, this.height - 44, 20, 20, "_/"));
		buttonList.add(new GuiButton(24, 90, this.height - 44, 20, 20, "/"));
		buttonList.add(new GuiButton(25, 112, this.height - 44, 20, 20, "/-"));
		buttonList.add(new GuiButton(26, 236, this.height - 22, 100, 20, "Track Types ^"));
		
		int j = 0;
		for(int i = 1; i < PacketTrackDesigner.extras.length; i++) {
			buttonList.add(new GuiButton(27 + (i - 1), 236, this.height - 44 - ((i - 1) * 22), 100, 20, PacketTrackDesigner.extras[i]));
			j = i;
		}
		
		buttonList.add(new GuiButton(31, 338, this.height - 22, 40, 20, ChatFormatting.YELLOW + "Day"));
		buttonList.add(new GuiButton(32, 380, this.height - 22, 40, 20, ChatFormatting.BLACK + "Night"));
		buttonList.add(new GuiButton(33, 422, this.height - 22, 40, 20, "" + ChatFormatting.AQUA + ChatFormatting.STRIKETHROUGH + " Rain "));
		
		buttonList.add(new GuiButton(34, 102, this.height - 22, 31, 20, "" + ChatFormatting.RED + ChatFormatting.STRIKETHROUGH + " Roll "));
		
		((GuiButton) buttonList.get(2)).visible = false;
		((GuiButton) buttonList.get(3)).visible = false;
		((GuiButton) buttonList.get(4)).visible = false;
		((GuiButton) buttonList.get(5)).visible = false;
		((GuiButton) buttonList.get(6)).visible = false;
		((GuiButton) buttonList.get(7)).visible = false;
		((GuiButton) buttonList.get(8)).visible = false;
		((GuiButton) buttonList.get(9)).visible = false;
		((GuiButton) buttonList.get(10)).visible = false;
		((GuiButton) buttonList.get(11)).visible = false;
		((GuiButton) buttonList.get(12)).visible = false;
		((GuiButton) buttonList.get(13)).visible = false;
		((GuiButton) buttonList.get(14)).visible = false;
		((GuiButton) buttonList.get(15)).visible = false;
		((GuiButton) buttonList.get(16)).visible = false;
		((GuiButton) buttonList.get(17)).visible = false;
		((GuiButton) buttonList.get(18)).visible = false;
		((GuiButton) buttonList.get(19)).visible = false;
		((GuiButton) buttonList.get(20)).visible = false;
		((GuiButton) buttonList.get(21)).visible = false;
		((GuiButton) buttonList.get(23)).visible = false;
		((GuiButton) buttonList.get(24)).visible = false;
		((GuiButton) buttonList.get(25)).visible = false;
		((GuiButton) buttonList.get(26)).visible = false;
		((GuiButton) buttonList.get(31)).visible = false;
		((GuiButton) buttonList.get(32)).visible = false;
		((GuiButton) buttonList.get(33)).visible = false;
		((GuiButton) buttonList.get(34)).visible = false;
		
		for(int i = 1; i < PacketTrackDesigner.extras.length; i++) {
			((GuiButton) buttonList.get(27 + (i - 1))).visible = false;
		}
		
		rideName = new GuiTextField(fontRendererObj, k + 6, l + 6, 164, 12);
		rideName.setFocused(false);
		rideName.setMaxStringLength(26);
		rideName.setText("");
		
		loadName = new GuiTextField(fontRendererObj, k + 6, l + 6, 164, 12);
		loadName.setFocused(false);
		loadName.setMaxStringLength(26);
		loadName.setText("");
		
		trackPaint = PacketTrackDesigner.themes[theme].trackPaint;
		supportPaint = PacketTrackDesigner.themes[theme].supportPaint;
		fencePaint = PacketTrackDesigner.themes[theme].fencePaint;
		
		((GuiButton) buttonList.get(19)).displayString = PacketTrackDesigner.themes[theme].name;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (166 / 2);
		if(createMenu || loadMenu || escapeMenu) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(texture);
	        this.drawTexturedModalRect(k, l, 0, 0, 176, 166);
	       
	        if(createMenu) {
		        String track = "Track: " + ColourUtil.colours[trackPaint];
		        drawString(fontRendererObj, track, k + 27, l + 23 + (fontRendererObj.FONT_HEIGHT / 2), 0xFFFFFF);
		        
		        String supports = "Supports: " + ColourUtil.colours[supportPaint];
		        drawString(fontRendererObj, supports, k + 27, l + 45 + (fontRendererObj.FONT_HEIGHT / 2), 0xFFFFFF);
		        
		        String fences = "Fences: " + ColourUtil.colours[fencePaint];
		        drawString(fontRendererObj, fences, k + 27, l + 67 + (fontRendererObj.FONT_HEIGHT / 2), 0xFFFFFF);
			
				rideName.drawTextBox();
	        }
	        else if(loadMenu) {
	        	loadName.drawTextBox();
	        }
	        else if(escapeMenu) {
	        	String check = "Are you sure you want to exit?";
	        	String warning = "(Exiting will finalise construction)";
	        	drawString(fontRendererObj, check, (this.width / 2) - (fontRendererObj.getStringWidth(check) / 2), l + 10 - (fontRendererObj.FONT_HEIGHT / 2), 0xFFFFFF);
		        drawString(fontRendererObj, warning, (this.width / 2) - (fontRendererObj.getStringWidth(warning) / 2), l + 10 - (fontRendererObj.FONT_HEIGHT / 2) + fontRendererObj.FONT_HEIGHT + 2, 0xFFFFFF);
	        }
		}
		if(placeMenu) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(texture2);
	        this.drawTexturedModalRect(0, this.height - 25, 0, 256 - 25, this.width, 256);
	        
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(texture3);
	        this.drawTexturedModalRect(0, this.height - 49, 0, 256 - 49, 176, 256);
		}
		
		drawString(fontRendererObj, te.targetX + ", " + te.targetY + ", " + te.targetZ, 2, 2, 0xFFFFFF);
        
		super.drawScreen(par1, par2, par3);
		
		if(showHelp) {
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
		}
	}
	
	@SubscribeEvent
	public void drawHitbox(RenderTickEvent event) {
		if(entity3rdPerson != null) {
			System.out.println("Drawing");
			renderMarker(255, 0, 0, 1);
		}
	}
	
	protected void renderMarker(float r, float g, float b, float a) {
		final Tessellator tessellator = Tessellator.instance;
		tessellator.setColorRGBA_F(r, g, b, a);
		renderMarkerP(tessellator, entity3rdPerson.rayTraceMouse().blockX, entity3rdPerson.rayTraceMouse().blockY + 1, entity3rdPerson.rayTraceMouse().blockZ);
	}

	private void renderMarkerP(Tessellator tessellator, int x, int y, int z) {
		tessellator.addVertex(x + MIN, y + MAX, z + MIN);
		tessellator.addVertex(x + MIN, y + MAX, z + MAX);
		tessellator.addVertex(x + MAX, y + MAX, z + MAX);
		tessellator.addVertex(x + MAX, y + MAX, z + MIN);

		tessellator.addVertex(x + MIN, y + MIN, z + MIN);
		tessellator.addVertex(x + MIN, y + MIN, z + MAX);
		tessellator.addVertex(x + MIN, y + MAX, z + MAX);
		tessellator.addVertex(x + MIN, y + MAX, z + MIN);

		tessellator.addVertex(x + MAX, y + MAX, z + MIN);
		tessellator.addVertex(x + MAX, y + MAX, z + MAX);
		tessellator.addVertex(x + MAX, y + MIN, z + MAX);
		tessellator.addVertex(x + MAX, y + MIN, z + MIN);

		tessellator.addVertex(x + MIN, y + MIN, z + MIN);
		tessellator.addVertex(x + MIN, y + MAX, z + MIN);
		tessellator.addVertex(x + MAX, y + MAX, z + MIN);
		tessellator.addVertex(x + MAX, y + MIN, z + MIN);

		tessellator.addVertex(x + MIN, y + MAX, z + MAX);
		tessellator.addVertex(x + MIN, y + MIN, z + MAX);
		tessellator.addVertex(x + MAX, y + MIN, z + MAX);
		tessellator.addVertex(x + MAX, y + MAX, z + MAX);

		tessellator.addVertex(x + MIN, y + MIN, z + MAX);
		tessellator.addVertex(x + MIN, y + MIN, z + MIN);
		tessellator.addVertex(x + MAX, y + MIN, z + MIN);
		tessellator.addVertex(x + MAX, y + MIN, z + MAX);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if(button.id == 0) {
			showHelp = !showHelp;
		}
		else if(button.id == 1 && !createMenu) {
			createMenu = true;
			((GuiButton) buttonList.get(1)).visible = false;
			((GuiButton) buttonList.get(22)).visible = false;
			
			((GuiButton) buttonList.get(2)).visible = true;
			((GuiButton) buttonList.get(3)).visible = true;
			((GuiButton) buttonList.get(4)).visible = true;
			((GuiButton) buttonList.get(18)).visible = true;
			((GuiButton) buttonList.get(19)).visible = true;
		}
		else if(button.id == 2) {
			createMenu = false;
			placeMenu = true;
			((GuiButton) buttonList.get(2)).visible = false;
			((GuiButton) buttonList.get(3)).visible = false;
			((GuiButton) buttonList.get(4)).visible = false;
			((GuiButton) buttonList.get(18)).visible = false;
			((GuiButton) buttonList.get(19)).visible = false;
			
			((GuiButton) buttonList.get(5)).visible = true;
		}
		else if(button.id == 3) {
			if(trackPaint >= ColourUtil.colours.length - 2) {
				trackPaint = 0;
			}
			else {
				trackPaint++;
			}
			
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.CHANGE_TRACK_PAINT, te, 0);
		}
		else if(button.id == 4) {
			if(supportPaint >= ColourUtil.colours.length - 2) {
				supportPaint = 0;
			}
			else {
				supportPaint++;
			}
			
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.CHANGE_SUPPORT_PAINT, te, 0);
		}
		else if(button.id == 5) {
			MovingObjectPosition pos = entity3rdPerson.rayTraceMouse();
			pos.blockY += 1;
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.PLACE, te, pos, 0);
			
			((GuiButton) buttonList.get(5)).visible = false;
			
			((GuiButton) buttonList.get(10)).visible = true;
			((GuiButton) buttonList.get(11)).visible = true;
			((GuiButton) buttonList.get(16)).visible = true;
			((GuiButton) buttonList.get(17)).visible = true;
		}
		else if(button.id == 6) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.CURVED, te, 0);
		}
		else if(button.id == 7 && !((GuiButton) buttonList.get(10)).visible) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.STRAIGHT, te, 0);
		}
		else if(button.id == 8 && !((GuiButton) buttonList.get(10)).visible) {
			/*int x = td.tracks.get(td.tracks.size() - 1).x;
			int y = td.tracks.get(td.tracks.size() - 1).y;
			int z = td.tracks.get(td.tracks.size() - 1).z;
			
			*/
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.CURVED, te, 1);
		}
		else if(button.id == 9) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.LOOP, te, 0);
		}
		else if(button.id == 10) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.ROTATE, te, 0);
		}
		else if(button.id == 11) {
			((GuiButton) buttonList.get(10)).visible = false;
			((GuiButton) buttonList.get(11)).visible = false;
			((GuiButton) buttonList.get(16)).visible = false;
			((GuiButton) buttonList.get(17)).visible = false;
			
			((GuiButton) buttonList.get(7)).visible = true;
			((GuiButton) buttonList.get(12)).visible = true;
			((GuiButton) buttonList.get(26)).visible = true;
			((GuiButton) buttonList.get(31)).visible = true;
			((GuiButton) buttonList.get(32)).visible = true;
			((GuiButton) buttonList.get(33)).visible = true;
			
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.CONFIRM, te, 0);
		}
		else if(button.id == 12) {
			((GuiButton) buttonList.get(6)).visible = true;
			((GuiButton) buttonList.get(7)).visible = true;
			((GuiButton) buttonList.get(8)).visible = true;
			((GuiButton) buttonList.get(9)).visible = true;
			((GuiButton) buttonList.get(13)).visible = true;
			((GuiButton) buttonList.get(23)).visible = true;
			((GuiButton) buttonList.get(34)).visible = true;
			
			for(int i = 1; i < PacketTrackDesigner.extras.length; i++) {
				((GuiButton) buttonList.get(27 + (i - 1))).visible = false;
			}
			
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.CHANGE_EXTRA, te, 0);
		}
		else if(button.id == 13) {
			((GuiButton) buttonList.get(6)).visible = false;
			((GuiButton) buttonList.get(7)).visible = false;
			((GuiButton) buttonList.get(8)).visible = false;
			((GuiButton) buttonList.get(13)).visible = false;
			((GuiButton) buttonList.get(23)).visible = false;
			
			((GuiButton) buttonList.get(14)).visible = true;
			((GuiButton) buttonList.get(15)).visible = true;
			
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.SLOPE_DOWN_1, te, 0);
		}
		else if(button.id == 14) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.SLOPE_1, te, 0);
		}
		else if(button.id == 15) {
			((GuiButton) buttonList.get(14)).visible = false;
			((GuiButton) buttonList.get(15)).visible = false;
			
			((GuiButton) buttonList.get(6)).visible = true;
			((GuiButton) buttonList.get(7)).visible = true;
			((GuiButton) buttonList.get(8)).visible = true;
			((GuiButton) buttonList.get(13)).visible = true;
			((GuiButton) buttonList.get(23)).visible = true;
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.SLOPE_UP_1, te, 0);
		}
		else if(button.id == 16) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.LIFT, te, -1);
		}
		else if(button.id == 17) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.LIFT, te, 1);
		}
		else if(button.id == 18) {
			if(fencePaint >= ColourUtil.colours.length - 2) {
				fencePaint = 0;
			}
			else {
				fencePaint++;
			}

			NetworkHandler.tdUpdate(PacketTrackDesigner.types.CHANGE_FENCE_PAINT, te, 0);
		}
		else if(button.id == 19) {
			if(theme >= PacketTrackDesigner.themes.length - 1) {
				theme = 0;
			}
			else {
				theme++;
			}
			
			trackPaint = PacketTrackDesigner.themes[theme].trackPaint;
			supportPaint = PacketTrackDesigner.themes[theme].supportPaint;
			fencePaint = PacketTrackDesigner.themes[theme].fencePaint;
			
			((GuiButton) buttonList.get(19)).displayString = PacketTrackDesigner.themes[theme].name;
			
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.CHANGE_THEME, te, 0);
		}
		else if(button.id == 20) {
			escapeMenu = false;
			
			((GuiButton) buttonList.get(20)).visible = false;
			((GuiButton) buttonList.get(21)).visible = false;
		}
		else if(button.id == 21) {
			escapeMenu = false;
			
            ((GuiButton) buttonList.get(20)).visible = false;
			((GuiButton) buttonList.get(21)).visible = false;
			
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.CLEAR_TD, te, 0);
			
			this.mc.displayGuiScreen((GuiScreen) null);
            this.mc.setIngameFocus();
		}
		else if(button.id == 22) {
			placeMenu = true;
			
			((GuiButton) buttonList.get(1)).visible = false;
			((GuiButton) buttonList.get(10)).visible = false;
			((GuiButton) buttonList.get(11)).visible = false;
			((GuiButton) buttonList.get(16)).visible = false;
			((GuiButton) buttonList.get(17)).visible = false;
			((GuiButton) buttonList.get(22)).visible = false;
			
			((GuiButton) buttonList.get(7)).visible = true;
			((GuiButton) buttonList.get(9)).visible = true;
			((GuiButton) buttonList.get(12)).visible = true;
			((GuiButton) buttonList.get(13)).visible = true;
			((GuiButton) buttonList.get(23)).visible = true;
			((GuiButton) buttonList.get(26)).visible = true;
			((GuiButton) buttonList.get(31)).visible = true;
			((GuiButton) buttonList.get(32)).visible = true;
			((GuiButton) buttonList.get(33)).visible = true;
			((GuiButton) buttonList.get(34)).visible = true;
		}
		else if(button.id == 23) {
			((GuiButton) buttonList.get(6)).visible = false;
			((GuiButton) buttonList.get(7)).visible = false;
			((GuiButton) buttonList.get(8)).visible = false;
			((GuiButton) buttonList.get(13)).visible = false;
			((GuiButton) buttonList.get(23)).visible = false;
			
			((GuiButton) buttonList.get(24)).visible = true;
			((GuiButton) buttonList.get(25)).visible = true;
			
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.SLOPE_UP_2, te, 0);
		}
		else if(button.id == 24) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.SLOPE_2, te, 0);
		}
		else if(button.id == 25) {
			((GuiButton) buttonList.get(24)).visible = false;
			((GuiButton) buttonList.get(25)).visible = false;
			
			((GuiButton) buttonList.get(6)).visible = true;
			((GuiButton) buttonList.get(7)).visible = true;
			((GuiButton) buttonList.get(8)).visible = true;
			((GuiButton) buttonList.get(13)).visible = true;
			((GuiButton) buttonList.get(23)).visible = true;
			
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.SLOPE_DOWN_2, te, 0);
		}
		else if(button.id == 26) {
			for(int i = 1; i < PacketTrackDesigner.extras.length; i++) {
				((GuiButton) buttonList.get(27 + (i - 1))).visible = !((GuiButton) buttonList.get(27 + (i - 1))).visible;
			}
		}
		else if(button.id == 31) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.DAY, te, 0);
		}
		else if(button.id == 32) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.NIGHT, te, 0);
		}
		else if(button.id == 33) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.NO_RAIN, te, 0);
		}
		
		int change = -1;
		for(int i = 1; i < PacketTrackDesigner.extras.length; i++) {
			if(button.id == 27 + (i - 1)) {
				NetworkHandler.tdUpdate(PacketTrackDesigner.types.CHANGE_EXTRA, te, i);
				change = i;
			}
		}
		
		if(change != -1) {
			for(int i = 1; i < PacketTrackDesigner.extras.length; i++) {
				((GuiButton) buttonList.get(27 + (i - 1))).visible = false;
			}
		}
		
		if(change == 1) {
			((GuiButton) buttonList.get(6)).visible = false;
			((GuiButton) buttonList.get(8)).visible = false;
			((GuiButton) buttonList.get(9)).visible = false;
			((GuiButton) buttonList.get(13)).visible = false;
			((GuiButton) buttonList.get(14)).visible = false;
			((GuiButton) buttonList.get(15)).visible = false;
			((GuiButton) buttonList.get(23)).visible = false;
			((GuiButton) buttonList.get(24)).visible = false;
			((GuiButton) buttonList.get(25)).visible = false;
			((GuiButton) buttonList.get(34)).visible = false;
		}
		else if(change == 2) {
			((GuiButton) buttonList.get(6)).visible = false;
			((GuiButton) buttonList.get(8)).visible = false;
			((GuiButton) buttonList.get(9)).visible = false;
			((GuiButton) buttonList.get(13)).visible = false;
			((GuiButton) buttonList.get(14)).visible = false;
			((GuiButton) buttonList.get(15)).visible = false;
			((GuiButton) buttonList.get(23)).visible = false;
			((GuiButton) buttonList.get(24)).visible = false;
			((GuiButton) buttonList.get(25)).visible = false;
			((GuiButton) buttonList.get(34)).visible = false;
		}
		else if(change == 3) {
			((GuiButton) buttonList.get(6)).visible = false;
			((GuiButton) buttonList.get(8)).visible = false;
			((GuiButton) buttonList.get(9)).visible = false;
			((GuiButton) buttonList.get(34)).visible = false;
			
			((GuiButton) buttonList.get(13)).visible = true;
			((GuiButton) buttonList.get(23)).visible = true;
		}
		else if(change == 4) {
			((GuiButton) buttonList.get(6)).visible = false;
			((GuiButton) buttonList.get(8)).visible = false;
			((GuiButton) buttonList.get(9)).visible = false;
			((GuiButton) buttonList.get(13)).visible = false;
			((GuiButton) buttonList.get(14)).visible = false;
			((GuiButton) buttonList.get(15)).visible = false;
			((GuiButton) buttonList.get(23)).visible = false;
			((GuiButton) buttonList.get(24)).visible = false;
			((GuiButton) buttonList.get(25)).visible = false;
			((GuiButton) buttonList.get(34)).visible = false;
		}
	}
	
	@Override
	public void keyTyped(char c, int i) {
		if(c == KeyEvent.VK_ESCAPE)  {
			escapeMenu = true;
			((GuiButton) buttonList.get(20)).visible = true;
			((GuiButton) buttonList.get(21)).visible = true;
		}
		if(Character.toString(c).matches("[0-9]") || Character.toString(c).matches("[a-z]") || Character.toString(c).matches("[A-Z]") || c == (char) KeyEvent.VK_BACK_SPACE || c == (char) KeyEvent.VK_SPACE) {
			rideName.textboxKeyTyped(c, i);
			
			//NetworkHandler.tdUpdate(PacketTrackDesigner.types.CHANGE_NAME, te, 0, rideName.getText());
		}
		if(c == KeyEvent.VK_PERIOD) {
			NetworkHandler.tdUpdate(PacketTrackDesigner.types.DEBUG, te, 0);
		}
	}
	
	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		rideName.mouseClicked(i, j, k);
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
