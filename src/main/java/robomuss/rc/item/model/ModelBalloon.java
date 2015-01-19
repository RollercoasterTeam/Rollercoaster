package robomuss.rc.item.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelBalloon - shadekiller666
 * Created using Tabula 4.0.2
 */
public class ModelBalloon extends ModelBase {
    public ModelRenderer center;
    public ModelRenderer bottom;
    public ModelRenderer top;
    public ModelRenderer right;
    public ModelRenderer left;
    public ModelRenderer front;
    public ModelRenderer back;
    public ModelRenderer bottom1;
    public ModelRenderer bottom2;

    public ModelBalloon() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.top = new ModelRenderer(this, 0, 16);
        this.top.setRotationPoint(-6.0F, -16.0F, -8.0F);
        this.top.addBox(-3.0F, -5.0F, -3.0F, 6, 1, 6);
        this.back = new ModelRenderer(this, 46, 7);
        this.back.setRotationPoint(-6.0F, -16.0F, -8.0F);
        this.back.addBox(-3.0F, -3.0F, 4.0F, 6, 6, 1);
        this.bottom2 = new ModelRenderer(this, 0, 35);
        this.bottom2.setRotationPoint(-6.0F, -16.0F, -8.0F);
        this.bottom2.addBox(-1.0F, 6.0F, -1.0F, 2, 1, 2);
        this.right = new ModelRenderer(this, 32, 0);
        this.right.setRotationPoint(-6.0F, -16.0F, -8.0F);
        this.right.addBox(-5.0F, -3.0F, -3.0F, 1, 6, 6);
        this.bottom = new ModelRenderer(this, 0, 23);
        this.bottom.setRotationPoint(-6.0F, -16.0F, -8.0F);
        this.bottom.addBox(-3.0F, 4.0F, -3.0F, 6, 1, 6);
        this.bottom1 = new ModelRenderer(this, 0, 30);
        this.bottom1.setRotationPoint(-6.0F, -16.0F, -8.0F);
        this.bottom1.addBox(-2.0F, 5.0F, -2.0F, 4, 1, 4);
        this.front = new ModelRenderer(this, 46, 0);
        this.front.setRotationPoint(-6.0F, -16.0F, -8.0F);
        this.front.addBox(-3.0F, -3.0F, -5.0F, 6, 6, 1);
        this.left = new ModelRenderer(this, 32, 12);
        this.left.setRotationPoint(-6.0F, -16.0F, -8.0F);
        this.left.addBox(4.0F, -3.0F, -3.0F, 1, 6, 6);
        this.center = new ModelRenderer(this, 0, 0);
        this.center.setRotationPoint(-6.0F, -16.0F, -8.0F);
        this.center.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.top.render(f5);
        this.back.render(f5);
        this.bottom2.render(f5);
        this.right.render(f5);
        this.bottom.render(f5);
        this.bottom1.render(f5);
        this.front.render(f5);
        this.left.render(f5);
        this.center.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
