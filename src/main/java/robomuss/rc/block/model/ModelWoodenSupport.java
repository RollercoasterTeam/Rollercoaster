package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWoodenSupport extends ModelBase {
	//fields
	public ModelRenderer pillar1;
	public ModelRenderer pillar2;
	public ModelRenderer pillar3;
	public ModelRenderer pillar4;
	public ModelRenderer beam1;
	public ModelRenderer beam2;
	public ModelRenderer beam3;
	public ModelRenderer beam4;

	public ModelWoodenSupport() {
		textureWidth = 64;
		textureHeight = 32;

		pillar1 = new ModelRenderer(this, 0, 0);
		pillar1.addBox(0F, 0F, 0F, 2, 16, 2);
		pillar1.setRotationPoint(6F, 8F, -8F);
		pillar1.setTextureSize(64, 32);
		pillar1.mirror = true;
		setRotation(pillar1, 0F, 0F, 0F);
		pillar2 = new ModelRenderer(this, 0, 0);
		pillar2.addBox(0F, 0F, 0F, 2, 16, 2);
		pillar2.setRotationPoint(6F, 8F, 6F);
		pillar2.setTextureSize(64, 32);
		pillar2.mirror = true;
		setRotation(pillar2, 0F, 0F, 0F);
		pillar3 = new ModelRenderer(this, 0, 0);
		pillar3.addBox(0F, 0F, 0F, 2, 16, 2);
		pillar3.setRotationPoint(-8F, 8F, -8F);
		pillar3.setTextureSize(64, 32);
		pillar3.mirror = true;
		setRotation(pillar3, 0F, 0F, 0F);
		pillar4 = new ModelRenderer(this, 0, 0);
		pillar4.addBox(0F, 0F, 0F, 2, 16, 2);
		pillar4.setRotationPoint(-8F, 8F, 6F);
		pillar4.setTextureSize(64, 32);
		pillar4.mirror = true;
		setRotation(pillar4, 0F, 0F, 0F);
		beam1 = new ModelRenderer(this, 8, 0);
		beam1.addBox(0F, 0F, 0F, 2, 19, 1);
		beam1.setRotationPoint(6F, 9F, -7F);
		beam1.setTextureSize(64, 32);
		beam1.mirror = true;
		setRotation(beam1, 0F, 0F, 0.7853982F);
		beam2 = new ModelRenderer(this, 8, 0);
		beam2.addBox(0F, 0F, 0F, 2, 19, 1);
		beam2.setRotationPoint(6F, 9F, 6F);
		beam2.setTextureSize(64, 32);
		beam2.mirror = true;
		setRotation(beam2, 0F, 0F, 0.7853982F);
		beam3 = new ModelRenderer(this, 8, 0);
		beam3.addBox(0F, 0F, 0F, 2, 19, 1);
		beam3.setRotationPoint(-7F, 9F, -6F);
		beam3.setTextureSize(64, 32);
		beam3.mirror = true;
		setRotation(beam3, 0F, 1.570796F, 0.7853982F);
		beam4 = new ModelRenderer(this, 8, 0);
		beam4.addBox(0F, 0F, 0F, 2, 19, 1);
		beam4.setRotationPoint(6F, 9F, -6F);
		beam4.setTextureSize(64, 32);
		beam4.mirror = true;
		setRotation(beam4, 0F, 1.570796F, 0.7853982F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		pillar1.render(f5);
		pillar2.render(f5);
		pillar3.render(f5);
		pillar4.render(f5);
		beam1.render(f5);
		beam2.render(f5);
		beam3.render(f5);
		beam4.render(f5);
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
