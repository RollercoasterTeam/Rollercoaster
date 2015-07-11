package robomuss.rc.client.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import robomuss.rc.item.RCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiUserGuide extends GuiScreen {
    private static final ResourceLocation bookGuiTextures = new ResourceLocation("rc:textures/gui/userGuide.png");
    /**
     * The player editing the book
     */
    private final EntityPlayer editingPlayer;
    /**
     * Whether the book is signed or can still be edited
     */
    private final boolean bookIsUnsigned = false;
    private boolean field_146480_s;
    /**
     * Update ticks since the gui was opened
     */
    private int updateCount;
    private int bookImageWidth = 192;
    private int bookImageHeight = 192;
    private int bookTotalPages = 1;
    private int currPage;
    private NBTTagList bookPages;
    private String bookTitle = "";
    private GuiUserGuide.NextPageButton buttonNextPage;
    private GuiUserGuide.NextPageButton buttonPreviousPage;
    private GuiButton buttonDone;
    /**
     * The GuiButton to sign this book.
     */
    private GuiButton buttonSign;
    private GuiButton buttonFinalize;
    private GuiButton buttonCancel;

    private ItemStack stack;

    public GuiUserGuide(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack, boolean par3) {
        this.editingPlayer = par1EntityPlayer;

        stack = par2ItemStack;
        if (par2ItemStack.hasTagCompound()) {
            NBTTagCompound nbttagcompound = par2ItemStack.getTagCompound();
            this.bookPages = nbttagcompound.getTagList("pages", 8);

            if (this.bookPages != null) {
                this.bookPages = (NBTTagList) this.bookPages.copy();
                this.bookTotalPages = this.bookPages.tagCount();

                if (this.bookTotalPages < 1) {
                    this.bookTotalPages = 1;
                }
            }
        }

        if (this.bookPages == null && par3) {
            this.bookPages = new NBTTagList();
            this.bookPages.appendTag(new NBTTagString(""));
            this.bookTotalPages = 1;
        }

        this.bookTotalPages = 8;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        super.updateScreen();
        ++this.updateCount;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @SuppressWarnings("unchecked")
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);

        if (this.bookIsUnsigned) {
            this.buttonList.add(this.buttonSign = new GuiButton(3,
                    this.width / 2 - 100, 4 + this.bookImageHeight, 98, 20,
                    I18n.format("book.signButton", new Object[0])));
            this.buttonList.add(this.buttonDone = new GuiButton(0,
                    this.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, I18n
                    .format("gui.done", new Object[0])));
            this.buttonList.add(this.buttonFinalize = new GuiButton(5,
                    this.width / 2 - 100, 4 + this.bookImageHeight, 98, 20,
                    I18n.format("book.finalizeButton", new Object[0])));
            this.buttonList.add(this.buttonCancel = new GuiButton(4,
                    this.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, I18n
                    .format("gui.cancel", new Object[0])));
        } else {
            this.buttonList.add(this.buttonDone = new GuiButton(0,
                    this.width / 2 - 100, 4 + this.bookImageHeight, 200, 20,
                    I18n.format("gui.done", new Object[0])));
        }

        int i = (this.width - this.bookImageWidth) / 2;
        byte b0 = 2;
        this.buttonList
                .add(this.buttonNextPage = new GuiUserGuide.NextPageButton(
                        1, i + 120, b0 + 154, true));
        this.buttonList
                .add(this.buttonPreviousPage = new GuiUserGuide.NextPageButton(
                        2, i + 38, b0 + 154, false));
        this.updateButtons();
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat
     * events
     */
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    private void updateButtons() {
        this.buttonNextPage.visible = !this.field_146480_s
                && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned);
        this.buttonPreviousPage.visible = !this.field_146480_s
                && this.currPage > 0;
        this.buttonDone.visible = !this.bookIsUnsigned;

        if (this.bookIsUnsigned) {
            this.buttonSign.visible = !this.field_146480_s;
            this.buttonCancel.visible = this.field_146480_s;
            this.buttonFinalize.visible = this.field_146480_s;
            this.buttonFinalize.enabled = this.bookTitle.trim().length() > 0;
        }
    }

    private void sendBookToServer(boolean p_146462_1_) {

    }

    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 0) {
                this.mc.displayGuiScreen((GuiScreen) null);
                this.sendBookToServer(false);
            } else if (p_146284_1_.id == 1) {
                if (this.currPage < this.bookTotalPages - 1) {
                    ++this.currPage;
                } else if (this.bookIsUnsigned) {
                    this.addNewPage();

                    if (this.currPage < this.bookTotalPages - 1) {
                        ++this.currPage;
                    }
                }
            } else if (p_146284_1_.id == 2) {
                if (this.currPage > 0) {
                    --this.currPage;
                }
            } else if (p_146284_1_.id == 5 && this.field_146480_s) {
                this.sendBookToServer(true);
                this.mc.displayGuiScreen((GuiScreen) null);
            } else if (p_146284_1_.id == 4 && this.field_146480_s) {
                this.field_146480_s = false;
            }

            this.updateButtons();
        }
    }

    private void addNewPage() {
        if (this.bookPages != null && this.bookPages.tagCount() < 50) {
            this.bookPages.appendTag(new NBTTagString(""));
            ++this.bookTotalPages;
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {
        super.keyTyped(par1, par2);

        if (this.bookIsUnsigned) {
            if (this.field_146480_s) {
                this.func_146460_c(par1, par2);
            } else {
                this.keyTypedInBook(par1, par2);
            }
        }
    }

    /**
     * Processes keystrokes when editing the text of a book
     */
    private void keyTypedInBook(char p_146463_1_, int p_146463_2_) {
        switch (p_146463_1_) {
            case 22:
                this.func_146459_b(GuiScreen.getClipboardString());
                return;
            default:
                switch (p_146463_2_) {
                    case 14:
                        String s = this.func_146456_p();

                        if (s.length() > 0) {
                            this.func_146457_a(s.substring(0, s.length() - 1));
                        }

                        return;
                    case 28:
                    case 156:
                        this.func_146459_b("\n");
                        return;
                    default:
                        if (ChatAllowedCharacters.isAllowedCharacter(p_146463_1_)) {
                            this.func_146459_b(Character.toString(p_146463_1_));
                        }
                }
        }
    }

    private void func_146460_c(char p_146460_1_, int p_146460_2_) {
        switch (p_146460_2_) {
            case 14:
                if (!this.bookTitle.isEmpty()) {
                    this.bookTitle = this.bookTitle.substring(0,
                            this.bookTitle.length() - 1);
                    this.updateButtons();
                }

                return;
            case 28:
            case 156:
                if (!this.bookTitle.isEmpty()) {
                    this.sendBookToServer(true);
                    this.mc.displayGuiScreen((GuiScreen) null);
                }

                return;
            default:
                if (this.bookTitle.length() < 16
                        && ChatAllowedCharacters.isAllowedCharacter(p_146460_1_)) {
                    this.bookTitle = this.bookTitle
                            + Character.toString(p_146460_1_);
                    this.updateButtons();
                }
        }
    }

    private String func_146456_p() {
        return this.bookPages != null && this.currPage >= 0
                && this.currPage < this.bookPages.tagCount() ? this.bookPages
                .getStringTagAt(this.currPage) : "";
    }

    private void func_146457_a(String p_146457_1_) {
        if (this.bookPages != null && this.currPage >= 0
                && this.currPage < this.bookPages.tagCount()) {
            this.bookPages.func_150304_a(this.currPage, new NBTTagString(
                    p_146457_1_));
        }
    }

    private void func_146459_b(String p_146459_1_) {
        String s1 = this.func_146456_p();
        String s2 = s1 + p_146459_1_;
        int i = this.fontRendererObj.splitStringWidth(s2 + ""
                + EnumChatFormatting.BLACK + "_", 118);

        if (i <= 118 && s2.length() < 256) {
            this.func_146457_a(s2);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(bookGuiTextures);
        int k = (this.width - this.bookImageWidth) / 2;
        int l2 = (this.height - this.bookImageHeight) / 2;
        byte b0 = 2;
        this.drawTexturedModalRect(k, b0, 0, 0, this.bookImageWidth,
                this.bookImageHeight);
        String s;
        String s1;
        int l;

        if (this.field_146480_s) {
            s = this.bookTitle;

            if (this.bookIsUnsigned) {
                if (this.updateCount / 6 % 2 == 0) {
                    s = s + "" + EnumChatFormatting.BLACK + "_";
                } else {
                    s = s + "" + EnumChatFormatting.GRAY + "_";
                }
            }

            s1 = I18n.format("book.editTitle", new Object[0]);
            l = this.fontRendererObj.getStringWidth(s1);
            this.fontRendererObj.drawString(s1, k + 36 + (116 - l) / 2,
                    b0 + 16 + 16, 0);
            int i1 = this.fontRendererObj.getStringWidth(s);
            this.fontRendererObj.drawString(s, k + 36 + (116 - i1) / 2,
                    b0 + 48, 0);
            String s2 = I18n.format("book.byAuthor",
                    new Object[]{this.editingPlayer.getCommandSenderName()});
            int j1 = this.fontRendererObj.getStringWidth(s2);
            this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + s2,
                    k + 36 + (116 - j1) / 2, b0 + 48 + 10, 0);
            String s3 = I18n.format("book.finalizeWarning", new Object[0]);
            this.fontRendererObj.drawSplitString(s3, k + 36, b0 + 80, 116, 0);
        } else {
            ArrayList<String> strings = new ArrayList<String>();
            String name = "";
            if (currPage > 0) {
                strings.add("The Basics");
                strings.add("Starting Construction");
                strings.add("Designing Your Track");
                strings.add("Different Track Types");
                strings.add("Running & Riding");
                strings.add("Tips");
                strings.add("Credits");
                name = currPage - 1 < strings.size() ? strings.get(currPage - 1) : "";

                int iconX = k + (this.bookImageWidth / 2) - 8;
                int iconY = l2 + (this.bookImageHeight / 2) + 28;
                RenderManager.instance.renderEngine.bindTexture(stack.getItemSpriteNumber() == 0 ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);
                IIcon icon = RCItems.train.getIconFromDamage(0);
                IIcon icon2 = RCItems.hammer.getIconFromDamage(0);
                IIcon icon3 = RCItems.brush.getIconFromDamage(14);
                
                itemRender.renderIcon(iconX - 20, iconY, icon, 16, 16);
                itemRender.renderIcon(iconX, iconY, icon2, 16, 16);
                itemRender.renderIcon(iconX + 20, iconY, icon3, 16, 16);
            }
            int textX = k - (this.fontRendererObj.getStringWidth(name) / 2) + (this.bookImageWidth / 2);
            int textY = (this.bookImageHeight / 2) - 3 - (currPage == 0 ? 0 : 70);
            this.fontRendererObj.drawString(name, textX, textY, 0x000000);
            
            if(currPage == 0) {
	            this.fontRendererObj.drawString("Rollercoaster", k + 36, textY, 0x000000);
	            this.fontRendererObj.drawString("Construction", k + 72, textY + 10, 0x000000);
	            this.fontRendererObj.drawString("Manual", k + 120, textY + 20, 0x000000);
            }

            if (currPage > 0) {
                /*for (int i = 0; i < 5; i++) {
                    String desc = LanguageHandler.getLocalization("atomicraft.desc." + name + "." + i);
                    if (!desc.contains("atomicraft.desc")) {
                        int descX = k - (this.fontRendererObj.getStringWidth(desc) / 2) + (this.bookImageWidth / 2);
                        int descY = (this.bookImageHeight / 2) - 3 - (currPage == 0 ? 0 : 70) + 85 + (i * 10);
                        this.fontRendererObj.drawString(desc, descX, descY, 0x000000);
                    }
                }*/
            }
        }

        super.drawScreen(par1, par2, par3);
    }

    @SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton {
        private final boolean field_146151_o;

        public NextPageButton(int par1, int par2, int par3, boolean par4) {
            super(par1, par2, par3, 23, 13, "");
            this.field_146151_o = par4;
        }

        /**
         * Draws this button to the screen.
         */
        public void drawButton(Minecraft p_146112_1_, int p_146112_2_,
                               int p_146112_3_) {
            if (this.visible) {
                boolean flag = p_146112_2_ >= this.xPosition
                        && p_146112_3_ >= this.yPosition
                        && p_146112_2_ < this.xPosition + this.width
                        && p_146112_3_ < this.yPosition + this.height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                p_146112_1_.getTextureManager().bindTexture(
                        GuiUserGuide.bookGuiTextures);
                int k = 0;
                int l = 192;

                if (flag) {
                    k += 23;
                }

                if (!this.field_146151_o) {
                    l += 13;
                }

                this.drawTexturedModalRect(this.xPosition, this.yPosition, k,
                        l, 23, 13);
            }
        }
    }
}