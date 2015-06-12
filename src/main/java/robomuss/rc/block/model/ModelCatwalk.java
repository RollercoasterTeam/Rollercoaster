package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCatwalk extends ModelBase {
	public ModelRenderer base;
	public ModelRenderer pole1;
	public ModelRenderer pole2;
	public ModelRenderer beam1;
	public ModelRenderer beam2;
	public ModelRenderer beam3;

	public ModelCatwalk() {
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 16, 3, 16);
		base.setRotationPoint(-8F, 21F, -8F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		pole1 = new ModelRenderer(this, 0, 0);
		pole1.addBox(0F, 0F, 0F, 2, 11, 2);
		pole1.setRotationPoint(-8F, 10F, 6F);
		pole1.setTextureSize(64, 32);
		pole1.mirror = true;
		setRotation(pole1, 0F, 0F, 0F);
		pole2 = new ModelRenderer(this, 0, 0);
		pole2.addBox(0F, 0F, 0F, 2, 11, 2);
		pole2.setRotationPoint(6F, 10F, 6F);
		pole2.setTextureSize(64, 32);
		pole2.mirror = true;
		setRotation(pole2, 0F, 0F, 0F);
		beam1 = new ModelRenderer(this, 0, 0);
		beam1.addBox(-6F, -1F, 0F, 12, 2, 1);
		beam1.setRotationPoint(0F, 18.5F, 6.5F);
		beam1.setTextureSize(64, 32);
		beam1.mirror = true;
		setRotation(beam1, 0F, 0F, 0F);
		beam2 = new ModelRenderer(this, 0, 0);
		beam2.addBox(0F, 0F, 0F, 12, 2, 1);
		beam2.setRotationPoint(-6F, 14F, 6.5F);
		beam2.setTextureSize(64, 32);
		beam2.mirror = true;
		setRotation(beam2, 0F, 0F, 0F);
		beam3 = new ModelRenderer(this, 0, 0);
		beam3.addBox(0F, 0F, 0F, 12, 2, 1);
		beam3.setRotationPoint(-6F, 10.5F, 6.5F);
		beam3.setTextureSize(64, 32);
		beam3.mirror = true;
		setRotation(beam3, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		base.render(f5);
		pole1.render(f5);
		pole2.render(f5);
		beam1.render(f5);
		beam2.render(f5);
		beam3.render(f5);
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
