package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelTrackFabricator;
import robomuss.rc.block.render.TileEntityRenderTrackFabricator;
import robomuss.rc.block.te.TileEntityTrackFabricator;

public class ItemRenderTrackFabricator implements IItemRenderer {
	
	private ModelTrackFabricator model;

	public ItemRenderTrackFabricator() {
		model = new ModelTrackFabricator();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		/*switch (helper) {
			case ENTITY_ROTATION:
			case ENTITY_BOBBING:
				return true;
			default:
				return false;
		}*/
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		TileEntityRenderTrackFabricator.instance.renderTileEntityAt(new TileEntityTrackFabricator(), 0, 0, 0, 0);
	}
}