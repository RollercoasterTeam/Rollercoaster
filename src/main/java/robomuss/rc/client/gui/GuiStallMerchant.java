package robomuss.rc.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import robomuss.rc.RCMod;
import robomuss.rc.block.container.ContainerStallMerchant;
import robomuss.rc.block.container.ContainerStallMerchantCamouflage;
import robomuss.rc.util.stall.IStallMerchant;
import robomuss.rc.util.stall.StallMerchantRecipe;
import robomuss.rc.util.stall.StallMerchantRecipeList;

public class GuiStallMerchant extends GuiContainer {
	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation texture = new ResourceLocation("rc", "textures/gui/stall_main.png");
	private static final ResourceLocation camouflage = new ResourceLocation("rc", "textures/gui/stall_camouflage.png");
	private GuiStallMerchantButton rightButton;
	private GuiStallMerchantButton leftButton;
	private GuiRCButton camoButton;
	private GuiRCButton backButton;
	private boolean camoMode = false;
	private int           currentSale;
	private String        displayName;
	private String        stallName;
	private String        camoName;
	private ContainerStallMerchantCamouflage camoContainer;
	public IStallMerchant merchant;
	private int drawX = 0;
	private int drawY = 0;

	public GuiStallMerchant(InventoryPlayer inventoryPlayer, IStallMerchant merchant, World world, String name) {
		super(new ContainerStallMerchant(inventoryPlayer, merchant, world, name));
		this.drawX = (this.width - this.xSize) / 2;
		this.drawY = (this.height - this.ySize) / 2;
		this.camoContainer = new ContainerStallMerchantCamouflage(inventoryPlayer, 0, this.drawX, this.drawY);
		this.merchant = merchant;
		this.merchant.setCustomer(inventoryPlayer.player);
		this.camoName = I18n.format("tile.stall.camouflage", new Object());
		this.stallName = name != null && name.length() >= 1 ? name : "Stall Merchant";
		this.displayName = this.camoMode ? this.camoName : this.stallName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		this.drawX = (this.width - this.xSize) / 2;
		this.drawY = (this.height - this.ySize) / 2;
		this.buttonList.add(this.rightButton = new GuiStallMerchantButton(1, this.drawX + 147, this.drawY + 23, true));
		this.buttonList.add(this.leftButton = new GuiStallMerchantButton(2, this.drawX + 17, this.drawY + 23, false));
		this.buttonList.add(this.camoButton = new GuiRCButton(3, (this.width / 2) + 53, this.drawY + 3, this.mc.fontRenderer.getStringWidth("Camo") + 8, 16, "Camo"));
		this.buttonList.add(this.backButton = new GuiRCButton(4, (this.width / 2) + 55, this.drawY + 3, this.mc.fontRenderer.getStringWidth("Back") + 8, 16, "Back"));
		this.rightButton.enabled = true;
		this.leftButton.enabled = false;
		this.camoButton.visible = !this.camoMode;
		this.backButton.visible = this.camoMode;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		this.displayName = this.camoMode ? this.camoName : this.stallName;
		this.fontRendererObj.drawString(this.displayName, this.xSize / 2 - this.fontRendererObj.getStringWidth(this.displayName) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object()), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		this.drawX = (this.width - this.xSize) / 2;
		this.drawY = (this.height - this.ySize) / 2;
		this.mc.getTextureManager().bindTexture(this.camoMode ? camouflage : texture);
		this.drawTexturedModalRect(this.drawX, this.drawY, 0, 0, this.xSize, this.ySize);                       //draws the gui background
		StallMerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);

		if (!this.camoMode) {
			if (recipes != null && !recipes.isEmpty()) {
				int currentSale = this.currentSale;
				StallMerchantRecipe recipe = (StallMerchantRecipe) recipes.get(currentSale);

				if (recipe.isRecipeDisabled()) {
					this.mc.getTextureManager().bindTexture(texture);
					GL11.glColor4f(1f, 1f, 1f, 1f);
					GL11.glDisable(GL11.GL_LIGHTING);
					this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 21, 212, 0, 28, 21);                //draws red xs over the arrows
					this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 51, 212, 0, 28, 21);                //draws red xs over the arrows
				}
			}
		}

		int rightX = this.rightButton.xPosition;
		int rightY = this.rightButton.yPosition;
		int leftX  = this.leftButton.xPosition;
		int leftY  = this.leftButton.yPosition;
		this.rightButton.drawButton(this.mc, rightX, rightY);
		this.leftButton.drawButton(this.mc, leftX, leftY);

		int buttonX = this.camoMode ? this.backButton.xPosition : this.camoButton.xPosition;
		int buttonY = this.camoMode ? this.backButton.yPosition : this.camoButton.yPosition;
		this.camoButton.visible = !this.camoMode;
		this.backButton.visible = this.camoMode;

		this.backButton.drawButton(this.mc, buttonX, buttonY);
		this.camoButton.drawButton(this.mc, buttonX, buttonY);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);

		if (!this.camoMode) {
			StallMerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);

			if (recipes != null && !recipes.isEmpty()) {
				int drawX = (this.width - this.xSize) / 2;
				int drawY = (this.height - this.ySize) / 2;
				int currentSale = this.currentSale;
				StallMerchantRecipe recipe = (StallMerchantRecipe) recipes.get(currentSale);

				GL11.glPushMatrix();
				ItemStack buyStackA = recipe.getItemToBuy();
				ItemStack buyStackB = recipe.getSecondItemToBuy();
				ItemStack sellStack = recipe.getItemToSell();
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_COLOR_MATERIAL);
				GL11.glEnable(GL11.GL_LIGHTING);
				itemRender.zLevel = 100f;
				itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), buyStackA, drawX + 36, drawY + 24);
				itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), buyStackA, drawX + 36, drawY + 24);

				if (buyStackB != null) {
					itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), buyStackB, drawX + 62, drawY + 24);
					itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), buyStackB, drawX + 62, drawY + 24);
				}

				itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), sellStack, drawX + 120, drawY + 24);
				itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), sellStack, drawX + 120, drawY + 24);
				itemRender.zLevel = 0f;
				GL11.glDisable(GL11.GL_LIGHTING);

				if (this.func_146978_c(36, 24, 16, 16, i, j)) {
					this.renderToolTip(buyStackA, i, j);
				} else if (buyStackB != null && this.func_146978_c(62, 24, 16, 16, i, j)) {
					this.renderToolTip(buyStackB, i, j);
				} else if (this.func_146978_c(120, 24, 16, 16, i, j)) {
					this.renderToolTip(sellStack, i, j);
				}

				GL11.glPopMatrix();
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				RenderHelper.disableStandardItemLighting();
			}
		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		boolean buttonEnabled = false;

		if (button == this.rightButton) {
			this.currentSale++;
			buttonEnabled = true;
		} else if (button == this.leftButton) {
			this.currentSale--;
			buttonEnabled = true;
		}

		if (buttonEnabled) {
			((ContainerStallMerchant) this.inventorySlots).setCurrentRecipeIndex(currentSale);
			ByteBuf buf = Unpooled.buffer();

			try {
				buf.writeInt(this.currentSale);
				this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", buf));
			} catch (Exception exception) {
				logger.error("Couldn\'t send trade info", exception);
			} finally {
				buf.release();
			}
		}

		this.camoMode = button.id == 3 || button.id != 4 && camoMode;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		StallMerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);

		if (recipes != null) {
			this.rightButton.enabled = this.currentSale < recipes.size() - 1;
			this.rightButton.visible = !this.camoMode;
			this.leftButton.enabled = this.currentSale > 0;
			this.leftButton.visible = !this.camoMode;
		}

		this.camoButton.visible = !this.camoMode;
		this.backButton.visible = this.camoMode;
	}

	public IStallMerchant getMerchant() {
		return this.merchant;
	}
}
