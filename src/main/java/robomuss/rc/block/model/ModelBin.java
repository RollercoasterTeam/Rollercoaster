package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBin extends ModelBase {
	public ModelRenderer base;
	public ModelRenderer standBase;
	public ModelRenderer stand;
	public ModelRenderer side1;
	public ModelRenderer side2;
	public ModelRenderer side3;
	public ModelRenderer side4;

	public ModelBin() {
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 10, 1, 10);
		base.setRotationPoint(-5F, 19F, -5F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		standBase = new ModelRenderer(this, 0, 0);
		standBase.addBox(0F, 0F, 0F, 6, 1, 6);
		standBase.setRotationPoint(-3F, 23F, -3F);
		standBase.setTextureSize(64, 32);
		standBase.mirror = true;
		setRotation(standBase, 0F, 0F, 0F);
		stand = new ModelRenderer(this, 0, 0);
		stand.addBox(0F, 0F, 0F, 2, 3, 2);
		stand.setRotationPoint(-1F, 20F, -1F);
		stand.setTextureSize(64, 32);
		stand.mirror = true;
		setRotation(stand, 0F, 0F, 0F);
		side1 = new ModelRenderer(this, 0, 0);
		side1.addBox(0F, 0F, 0F, 10, 11, 1);
		side1.setRotationPoint(-5F, 8F, -5F);
		side1.setTextureSize(64, 32);
		side1.mirror = true;
		setRotation(side1, 0F, 0F, 0F);
		side2 = new ModelRenderer(this, 0, 0);
		side2.addBox(0F, 0F, 0F, 10, 11, 1);
		side2.setRotationPoint(-5F, 8F, 4F);
		side2.setTextureSize(64, 32);
		side2.mirror = true;
		setRotation(side2, 0F, 0F, 0F);
		side3 = new ModelRenderer(this, 0, 1);
		side3.addBox(0F, 0F, 0F, 1, 11, 8);
		side3.setRotationPoint(-5F, 8F, -4F);
		side3.setTextureSize(64, 32);
		side3.mirror = true;
		setRotation(side3, 0F, 0F, 0F);
		side4 = new ModelRenderer(this, 0, 1);
		side4.addBox(0F, 0F, 0F, 1, 11, 8);
		side4.setRotationPoint(4F, 8F, -4F);
		side4.setTextureSize(64, 32);
		side4.mirror = true;
		setRotation(side4, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		base.render(f5);
		standBase.render(f5);
		stand.render(f5);
		side1.render(f5);
		side2.render(f5);
		side3.render(f5);
		side4.render(f5);
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
