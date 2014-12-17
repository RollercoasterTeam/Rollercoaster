package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAirLauncher extends ModelBase {
	//fields
	ModelRenderer airLauncher;

	public ModelAirLauncher() {
		textureWidth = 64;
		textureHeight = 32;

		airLauncher = new ModelRenderer(this, 0, 0);
		airLauncher.addBox(0F, 0F, 0F, 16, 2, 3);
		airLauncher.setRotationPoint(-8F, 19F, -1.5F);
		airLauncher.setTextureSize(64, 32);
		airLauncher.mirror = true;
		setRotation(airLauncher, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		airLauncher.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}

}
