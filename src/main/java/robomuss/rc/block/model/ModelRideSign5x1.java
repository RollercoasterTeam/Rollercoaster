package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRideSign5x1 extends ModelBase {
	public ModelRenderer border1;
	public ModelRenderer display;
	public ModelRenderer border3;
	public ModelRenderer border2;
	public ModelRenderer border4;

	public ModelRideSign5x1() {
		textureWidth = 256;
		textureHeight = 128;

		border1 = new ModelRenderer(this, 0, 0);
		border1.addBox(-40F, 0F, 0F, 80, 1, 1);
		border1.setRotationPoint(0F, 12F, 7F);
		border1.setTextureSize(256, 128);
		border1.mirror = true;
		setRotation(border1, 0F, 0F, 0F);
		display = new ModelRenderer(this, 0, 0);
		display.addBox(-40F, -6F, 0F, 78, 10, 1);
		display.setRotationPoint(1F, 19F, 7F);
		display.setTextureSize(256, 128);
		display.mirror = true;
		setRotation(display, 0F, 0F, 0F);
		border3 = new ModelRenderer(this, 0, 0);
		border3.addBox(-40F, 0F, 0F, 80, 1, 1);
		border3.setRotationPoint(0F, 23F, 7F);
		border3.setTextureSize(256, 128);
		border3.mirror = true;
		setRotation(border3, 0F, 0F, 0F);
		border2 = new ModelRenderer(this, 0, 0);
		border2.addBox(0F, -5F, 0F, 1, 10, 1);
		border2.setRotationPoint(39F, 18F, 7F);
		border2.setTextureSize(256, 128);
		border2.mirror = true;
		setRotation(border2, 0F, 0F, 0F);
		border4 = new ModelRenderer(this, 0, 0);
		border4.addBox(0F, -5F, 0F, 1, 10, 1);
		border4.setRotationPoint(-40F, 18F, 7F);
		border4.setTextureSize(256, 128);
		border4.mirror = true;
		setRotation(border4, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		border1.render(f5);
		display.render(f5);
		border3.render(f5);
		border2.render(f5);
		border4.render(f5);
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
