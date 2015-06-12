package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTrackDesigner extends ModelBase {
	public ModelRenderer post;
	public ModelRenderer panel;
	public ModelRenderer up;
	public ModelRenderer down;
	public ModelRenderer left;
	public ModelRenderer right;

	public ModelTrackDesigner() {
		textureWidth = 64;
		textureHeight = 32;

		post = new ModelRenderer(this, 0, 13);
		post.addBox(-2F, 0F, -1.5F, 4, 12, 3);
		post.setRotationPoint(0F, 12F, 6F);
		post.setTextureSize(64, 32);
		post.mirror = true;
		setRotation(post, 0F, 0F, 0F);
		panel = new ModelRenderer(this, 0, 0);
		panel.addBox(-5F, 0F, -3F, 10, 3, 8);
		panel.setRotationPoint(0F, 12F, 4F);
		panel.setTextureSize(64, 32);
		panel.mirror = true;
		setRotation(panel, 0.7853982F, 0F, 0F);
		up = new ModelRenderer(this, 0, 11);
		up.addBox(-0.5F, 0F, -1F, 1, 1, 1);
		up.setRotationPoint(-2F, 9F, 6F);
		up.setTextureSize(64, 32);
		up.mirror = true;
		setRotation(up, 0.7853982F, 0F, 0F);
		down = new ModelRenderer(this, 0, 11);
		down.addBox(-0.5F, 0F, 1.5F, 1, 1, 1);
		down.setRotationPoint(-2F, 12F, 3F);
		down.setTextureSize(64, 32);
		down.mirror = true;
		setRotation(down, 0.7853982F, 0F, 0F);
		left = new ModelRenderer(this, 0, 11);
		left.addBox(-0.5F, 0F, -0.5F, 1, 1, 1);
		left.setRotationPoint(-3F, 10F, 5F);
		left.setTextureSize(64, 32);
		left.mirror = true;
		setRotation(left, 0.7853982F, 0F, 0F);
		right = new ModelRenderer(this, 0, 11);
		right.addBox(-0.5F, 0F, -0.5F, 1, 1, 1);
		right.setRotationPoint(-1F, 10F, 5F);
		right.setTextureSize(64, 32);
		right.mirror = true;
		setRotation(right, 0.7853982F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		post.render(f5);
		panel.render(f5);
		up.render(f5);
		down.render(f5);
		left.render(f5);
		right.render(f5);
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
