package robomuss.rc.client.gui;

import cpw.mods.fml.common.network.NetworkRegistry;
import modforgery.forgerylib.ChatColours;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import robomuss.rc.RCMod;
import robomuss.rc.block.container.ContainerStallMerchantCamouflage;

public class GuiStallMerchantCamouflage extends GuiContainer {
	private InventoryPlayer player;
	private GuiScreen parentScreen;
//	private ContainerStallMerchantCamouflage comouflage;
	private static final ResourceLocation stallGuiTexture = new ResourceLocation("rc", "textures/gui/stall.png");

	public GuiStallMerchantCamouflage(GuiScreen parentScreen, InventoryPlayer player, int ID, int x, int y) {
		super(new ContainerStallMerchantCamouflage(player, ID, x, y));
//		this.comouflage = (ContainerStallMerchantCamouflage) super.inventorySlots;
//		this.comouflage.addSlots(this.width, this.height, this.xSize, this.ySize);
		this.parentScreen = parentScreen;
		this.player = player;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		int drawX = (this.width  - this.xSize) / 2;
		int drawY = (this.height - this.ySize) / 2;
		this.buttonList.add(new GuiButton(4, (this.width / 2) + 55, drawY + 3, this.fontRendererObj.getStringWidth("Back") + 8, 20, "Back"));
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		int drawCamoX = (this.width / 2) - (this.fontRendererObj.getStringWidth("Camouflage") / 2);
		int drawCamoY = (this.height - this.ySize) / 2;
		int drawInvX  = (this.width  - this.xSize) / 2;
		int drawInvY  = (this.height - this.ySize) / 2;
		this.fontRendererObj.drawString("Camouflage", drawCamoX, drawCamoY + 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object()), drawInvX + 8, drawInvY + 72, 4210752);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		this.mc.getTextureManager().bindTexture(stallGuiTexture);
		int drawX = (this.width - this.xSize) / 2;
		int drawY = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(drawX, drawY, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		int id = button.id;

		if (id == 4) {
			this.player.player.openGui(RCMod.instance, 3, this.mc.theWorld, (int) this.player.player.posX, (int) this.player.player.posY, (int) this.player.player.posZ);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}
}
