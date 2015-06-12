package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRideFencePanel extends ModelBase {

	public ModelRenderer Shape1;
	public ModelRenderer Shape3;
	public ModelRenderer Shape2;
	public ModelRenderer Shape4;
	public ModelRenderer Shape5;
	public ModelRenderer Shape6;
	public ModelRenderer Shape7;

	public ModelRideFencePanel() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 3, 12, 4);
		Shape1.setRotationPoint(-8F, 12F, -2F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 0);
		Shape3.addBox(0F, 0F, 0F, 3, 12, 4);
		Shape3.setRotationPoint(5F, 12F, -2F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 0);
		Shape2.addBox(0F, 0F, 0F, 10, 2, 2);
		Shape2.setRotationPoint(-5F, 13F, -1F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 0);
		Shape4.addBox(-5F, 0F, -3F, 10, 3, 8);
		Shape4.setRotationPoint(0F, 12F, -2F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0.7853982F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 0, 11);
		Shape5.addBox(-1F, 0F, -1F, 2, 1, 2);
		Shape5.setRotationPoint(-2F, 10F, -1F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0.7853982F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 8, 11);
		Shape6.addBox(-1F, 0F, -1F, 2, 1, 2);
		Shape6.setRotationPoint(2F, 10F, -1F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0.7853982F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 16, 11);
		Shape7.addBox(-1F, 0F, -0.5F, 2, 1, 1);
		Shape7.setRotationPoint(-1F, 12F, -2.75F);
		Shape7.setTextureSize(64, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0.7853982F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		Shape1.render(f5);
		Shape3.render(f5);
		Shape2.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}

}
