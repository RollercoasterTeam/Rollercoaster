package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRMCTopperCoaster extends ModelBase {
	//fields
	ModelRenderer top1;
	ModelRenderer top2;
	ModelRenderer bottom1;
	ModelRenderer bottom2;
	ModelRenderer topper1;
	ModelRenderer topper2;

	public ModelRMCTopperCoaster() {
		textureWidth = 64;
		textureHeight = 32;

		top1 = new ModelRenderer(this, 0, 0);
		top1.addBox(0F, 0F, 0F, 16, 2, 3);
		top1.setRotationPoint(-8F, 17.5F, -6.5F);
		top1.setTextureSize(64, 32);
		top1.mirror = true;
		setRotation(top1, 0F, 0F, 0F);
		top2 = new ModelRenderer(this, 0, 0);
		top2.addBox(0F, 0F, 0F, 16, 2, 3);
		top2.setRotationPoint(-8F, 17.5F, 3.5F);
		top2.setTextureSize(64, 32);
		top2.mirror = true;
		setRotation(top2, 0F, 0F, 0F);
		bottom1 = new ModelRenderer(this, 0, 0);
		bottom1.addBox(0F, 0F, 0F, 1, 2, 21);
		bottom1.setRotationPoint(-3F, 19.5F, -10.5F);
		bottom1.setTextureSize(64, 32);
		bottom1.mirror = true;
		setRotation(bottom1, 0F, 0F, 0F);
		bottom2 = new ModelRenderer(this, 0, 0);
		bottom2.addBox(0F, 0F, 0F, 1, 2, 21);
		bottom2.setRotationPoint(5F, 19.5F, -10.5F);
		bottom2.setTextureSize(64, 32);
		bottom2.mirror = true;
		setRotation(bottom2, 0F, 0F, 0F);
		topper1 = new ModelRenderer(this, 0, 0);
		topper1.addBox(0F, 0F, 0F, 16, 1, 3);
		topper1.setRotationPoint(-8F, 16.5F, 3.5F);
		topper1.setTextureSize(64, 32);
		topper1.mirror = true;
		setRotation(topper1, 0F, 0F, 0F);
		topper2 = new ModelRenderer(this, 0, 0);
		topper2.addBox(0F, 0F, 0F, 16, 1, 3);
		topper2.setRotationPoint(-8F, 16.5F, -6.5F);
		topper2.setTextureSize(64, 32);
		topper2.mirror = true;
		setRotation(topper2, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		top1.render(f5);
		top2.render(f5);
		bottom1.render(f5);
		bottom2.render(f5);
		topper1.render(f5);
		topper2.render(f5);
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
