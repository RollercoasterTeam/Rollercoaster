package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSupport extends ModelBase {
	//fields
	public ModelRenderer middle;
	public ModelRenderer middle2;
	public ModelRenderer connector;
	public ModelRenderer flange1;
	public ModelRenderer flange2;

	public ModelSupport() {
		textureWidth = 64;
		textureHeight = 32;

		middle = new ModelRenderer(this, 0, 0);
		middle.addBox(0F, 0F, 0F, 8, 16, 8);
		middle.setRotationPoint(-4F, 8F, -4F);
		middle.setTextureSize(64, 32);
		middle.mirror = true;
		setRotation(middle, 0F, 0F, 0F);
		middle2 = new ModelRenderer(this, 0, 0);
		middle2.addBox(0F, 0F, 0F, 8, 16, 8);
		middle2.setRotationPoint(-6F, 8F, 0F);
		middle2.setTextureSize(64, 32);
		middle2.mirror = true;
		setRotation(middle2, 0F, 0.7853982F, 0F);
		connector = new ModelRenderer(this, 0, 0);
		connector.addBox(0F, 0F, 0F, 4, 8, 8);
		connector.setRotationPoint(-2F, 3F, 2F);
		connector.setTextureSize(64, 32);
		connector.mirror = true;
		setRotation(connector, -0.7853982F, 0F, 0F);
		flange1 = new ModelRenderer(this, 0, 0);
		flange1.addBox(0F, 0F, 0F, 10, 1, 10);
		flange1.setRotationPoint(-5F, 15F, -5F);
		flange1.setTextureSize(64, 32);
		flange1.mirror = true;
		setRotation(flange1, 0F, 0F, 0F);
		flange2 = new ModelRenderer(this, 0, 0);
		flange2.addBox(0F, 0F, 0F, 10, 1, 10);
		flange2.setRotationPoint(-7F, 15F, 0F);
		flange2.setTextureSize(64, 32);
		flange2.mirror = true;
		setRotation(flange2, 0F, 0.7853982F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		middle.render(f5);
		middle2.render(f5);
		connector.render(f5);
		flange1.render(f5);
		flange2.render(f5);
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
