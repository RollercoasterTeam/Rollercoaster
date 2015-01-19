package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import robomuss.rc.item.model.ModelBalloon;

public class ItemRenderBalloon implements IItemRenderer {
	private ModelBalloon modelBalloon = new ModelBalloon();
	private EntityPlayer player;

	public ItemRenderBalloon(EntityPlayer player) {
		this.player = player;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
//		ResourceLocation texture = new ResourceLocation("rc:textures/model/balloon.png");

		ModelBalloon balloon = new ModelBalloon();
		ResourceLocation balloon_texture = new ResourceLocation("rc:textures/models/balloon.png");

		Minecraft.getMinecraft().renderEngine.bindTexture(balloon_texture);

//		if (type == ItemRenderType.EQUIPPED) {
//			balloon.render(this.player, (float) this.player.posX, (float) this.player.posY, (float) this.player.posZ, (float) this.player.cameraYaw, (float) this.player.cameraPitch, 0f);
//		}

		GL11.glPopMatrix();
	}
}
