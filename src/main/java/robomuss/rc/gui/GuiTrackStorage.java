package robomuss.rc.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.container.ContainerTrackStorage;
import robomuss.rc.block.te.TileEntityTrackStorage;

public class GuiTrackStorage extends GuiContainer {

	private static final ResourceLocation trackStorageGuiTextures = new ResourceLocation("rc", "textures/gui/track_storage.png");
	
	public GuiTrackStorage(InventoryPlayer inventory, EntityPlayer player, TileEntityTrackStorage te, World world) {
		super(new ContainerTrackStorage(player.inventory, player, te, world, te.xCoord, te.yCoord, te.zCoord));
	}
	
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString("Track Storage", 8, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 3, 4210752);
    }


	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		 this.mc.getTextureManager().bindTexture(trackStorageGuiTextures);
	     int k = (this.width - this.xSize) / 2;
	     int l = (this.height - this.ySize) / 2;
	     this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}
