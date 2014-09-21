package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import robomuss.rc.block.render.TileEntityRenderTrack;
import robomuss.rc.block.te.TileEntityTrack;

public class ItemRenderTrack implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		TileEntityTrack te = new TileEntityTrack();
		te.setWorldObj(Minecraft.getMinecraft().theWorld);
		TileEntityRenderTrack.instance.renderTileEntityAt(te, 0, 0, 0, 0);
	}
}