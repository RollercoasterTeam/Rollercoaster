package robomuss.rc.gui;

import java.awt.event.KeyEvent;

import modforgery.forgerylib.GuiUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.container.ContainerTrackFabricator;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.util.TrackEntry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class GuiTrackFabricator extends GuiContainer {
	
	private TileEntityTrackFabricator te;
	private EntityPlayer player;
	private int current_track;
	private int amount = 1;
	private GuiTextField textField;
	
	private TrackEntry[] tracks = { 
		//new TrackEntry(RCBlocks.flat_track, 3),
		//new TrackEntry(RCBlocks.transition_track, 3),
		//new TrackEntry(RCBlocks.transition_track_2, 3),
		//new TrackEntry(RCBlocks.sloped_track, 3),
		//new TrackEntry(RCBlocks.curved_track, 3),
		//new TrackEntry(RCBlocks.loop, 12)
	};

    private static final ResourceLocation trackFabricatorGuiTextures = new ResourceLocation("rc", "textures/gui/track_fabricator.png");
    
    public GuiTrackFabricator(InventoryPlayer par1InventoryPlayer, EntityPlayer player, TileEntityTrackFabricator te, World par2World, int par3, int par4, int par5) {
        super(new ContainerTrackFabricator(par1InventoryPlayer, player, te, par2World, par3, par4, par5));
        this.te = te;
        this.player = player;
    }

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		buttonList.clear();
		
		buttonList.add(new GuiButton(0, k + 82, l + 20, 20, 20, "<"));
		buttonList.add(new GuiButton(1, k + 104, l + 20, 20, 20, ">"));
		buttonList.add(new GuiButton(2, k + 82, l + 48, 20, 20, "-"));
		buttonList.add(new GuiButton(3, k + 104, l + 48, 20, 20, "+"));
		
		buttonList.add(new GuiButton(4, k + 126, l + 20, 40, 20, "Craft"));
		
		textField = new GuiTextField(fontRendererObj, k + 127, l + 49, 38, 18);
		textField.setFocused(false);
		textField.setMaxStringLength(2);
		textField.setText("" + amount);
	}


    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString("Fabricating", 8, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 3, 4210752);
        this.fontRendererObj.drawString("WIP (Coming v1.3)", 85, 6, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(trackFabricatorGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        GuiUtils.renderBlockIntoGui(Blocks.furnace, k, l, 2F, this.fontRendererObj, this.mc);
    }
    
    @Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		if(Character.toString(c).matches("[0-9]") || c == (char) KeyEvent.VK_BACK_SPACE) {
			textField.textboxKeyTyped(c, i);
		}
	}
	
	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		textField.mouseClicked(i, j, k);
		if(!textField.isFocused()) {
			if(Integer.parseInt(textField.getText()) == 0) {
				amount = 1;
				textField.setText("" + amount);
			}
			else if(Integer.parseInt(textField.getText()) > 64) {
				amount = 64;
				textField.setText("" + amount);
			}
			else {
				amount = Integer.parseInt(textField.getText());
			}
		}
	}
	
	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
		textField.drawTextBox();
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		int id = button.id;
		
		if(id == 0) {
			current_track += current_track == 0 ? 0 : -1;
		}
		if(id == 1) {
			current_track += current_track < tracks.length - 1 ? 1 : 0;
		}
		if(id == 2) {
			amount += amount == 1 ? 0 : -1;
			textField.setText("" + amount);
		}
		if(id == 3) {
			amount += amount < 64 ? 1 : 0;
			textField.setText("" + amount);
		}
		if(id == 4) {
			
		}
	}
}