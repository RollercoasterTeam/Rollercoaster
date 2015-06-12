/**
* @author roboyobo
*/
package modforgery.forgerylib;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiUtils {
	
	public static void renderEntityIntoGui(int par0, int par1, int par2, float par3, float par4, EntityLivingBase par5EntityLivingBase) {
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par0, (float) par1, 50.0F);
		GL11.glScalef((float) (-par2), (float) par2, (float) par2);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f2 = par5EntityLivingBase.renderYawOffset;
		float f3 = par5EntityLivingBase.rotationYaw;
		float f4 = par5EntityLivingBase.rotationPitch;
		float f5 = par5EntityLivingBase.prevRotationYawHead;
		float f6 = par5EntityLivingBase.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan((double) (par4 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		par5EntityLivingBase.renderYawOffset = (float) Math.atan((double) (par3 / 40.0F)) * 20.0F;
		par5EntityLivingBase.rotationYaw = (float) Math.atan((double) (par3 / 40.0F)) * 40.0F;
		par5EntityLivingBase.rotationPitch = -((float) Math.atan((double) (par4 / 40.0F))) * 20.0F;
		par5EntityLivingBase.rotationYawHead = par5EntityLivingBase.rotationYaw;
		par5EntityLivingBase.prevRotationYawHead = par5EntityLivingBase.rotationYaw;
		GL11.glTranslatef(0.0F, par5EntityLivingBase.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(par5EntityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		par5EntityLivingBase.renderYawOffset = f2;
		par5EntityLivingBase.rotationYaw = f3;
		par5EntityLivingBase.rotationPitch = f4;
		par5EntityLivingBase.prevRotationYawHead = f5;
		par5EntityLivingBase.rotationYawHead = f6;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
	
	public static void renderBlockIntoGui(Block block, int x, int y, float scale, FontRenderer fontRenderer, Minecraft mc) {
		RenderItem itemRenderer = new RenderItem();
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
        GL11.glTranslated(-x /2, -y /2, 0);
		itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.getTextureManager(), new ItemStack(block), x, y);
		GL11.glPopMatrix();
	}
	
	public static void renderItemIntoGui(Item item, int x, int y, float scale, FontRenderer fontRenderer, Minecraft mc) {
		RenderItem itemRenderer = new RenderItem();
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
        GL11.glTranslated(-x /2, -y /2, 0);
		itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.getTextureManager(), new ItemStack(item), x, y);
		GL11.glPopMatrix();
	}
	
	public static void renderItemStackIntoGui(ItemStack itemstack, int x, int y, float scale, FontRenderer fontRenderer, Minecraft mc) {
		RenderItem itemRenderer = new RenderItem();
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
        GL11.glTranslated(-x /2, -y /2, 0);
		itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.getTextureManager(), itemstack, x, y);
		GL11.glPopMatrix();
	}
}
