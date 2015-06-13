package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBenchHalf extends ModelBase
{
  //fields
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    public ModelRenderer side1;
    public ModelRenderer side2;
    public ModelRenderer side3;
    public ModelRenderer side4;
    public ModelRenderer plank1;
    public ModelRenderer plank2;
    public ModelRenderer plank3;
    public ModelRenderer plank4;
  
  public ModelBenchHalf()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      leg1 = new ModelRenderer(this, 0, 19);
      leg1.addBox(0F, 0F, 0F, 1, 8, 1);
      leg1.setRotationPoint(-7F, 16F, -5F);
      leg1.setTextureSize(128, 64);
      leg1.mirror = true;
      setRotation(leg1, 0F, 0F, 0F);
      leg2 = new ModelRenderer(this, 0, 19);
      leg2.addBox(0F, 0F, 0F, 1, 8, 1);
      leg2.setRotationPoint(-7F, 16F, 7F);
      leg2.setTextureSize(128, 64);
      leg2.mirror = true;
      setRotation(leg2, 0F, 0F, 0F);
      leg3 = new ModelRenderer(this, 0, 19);
      leg3.addBox(0F, 0F, -2F, 1, 8, 1);
      leg3.setRotationPoint(22F, 16F, -3F);
      leg3.setTextureSize(128, 64);
      leg3.mirror = true;
      setRotation(leg3, 0F, 0F, 0F);
      leg4 = new ModelRenderer(this, 0, 19);
      leg4.addBox(0F, 0F, 0F, 1, 8, 1);
      leg4.setRotationPoint(22F, 16F, 7F);
      leg4.setTextureSize(128, 64);
      leg4.mirror = true;
      setRotation(leg4, 0F, 0F, 0F);
      side1 = new ModelRenderer(this, 0, 16);
      side1.addBox(0F, 0F, 0F, 28, 2, 1);
      side1.setRotationPoint(-6F, 16F, -5F);
      side1.setTextureSize(128, 64);
      side1.mirror = true;
      setRotation(side1, 0F, 0F, 0F);
      side2 = new ModelRenderer(this, 0, 16);
      side2.addBox(0F, 0F, 0F, 28, 2, 1);
      side2.setRotationPoint(-6F, 16F, 7F);
      side2.setTextureSize(128, 64);
      side2.mirror = true;
      setRotation(side2, 0F, 0F, 0F);
      side3 = new ModelRenderer(this, 0, 3);
      side3.addBox(0F, 0F, 0F, 1, 2, 11);
      side3.setRotationPoint(-7F, 16F, -4F);
      side3.setTextureSize(128, 64);
      side3.mirror = true;
      setRotation(side3, 0F, 0F, 0F);
      side4 = new ModelRenderer(this, 0, 3);
      side4.addBox(0F, 0F, 0F, 1, 2, 11);
      side4.setRotationPoint(22F, 16F, -4F);
      side4.setTextureSize(128, 64);
      side4.mirror = true;
      setRotation(side4, 0F, 0F, 0F);
      plank1 = new ModelRenderer(this, 0, 0);
      plank1.addBox(0F, 0F, 0F, 32, 1, 2);
      plank1.setRotationPoint(-8F, 15F, -4F);
      plank1.setTextureSize(128, 64);
      plank1.mirror = true;
      setRotation(plank1, 0F, 0F, 0F);
      plank2 = new ModelRenderer(this, 0, 0);
      plank2.addBox(0F, 0F, 0F, 32, 1, 2);
      plank2.setRotationPoint(-8F, 15F, -1F);
      plank2.setTextureSize(128, 64);
      plank2.mirror = true;
      setRotation(plank2, 0F, 0F, 0F);
      plank3 = new ModelRenderer(this, 0, 0);
      plank3.addBox(0F, 0F, 0F, 32, 1, 2);
      plank3.setRotationPoint(-8F, 15F, 2F);
      plank3.setTextureSize(128, 64);
      plank3.mirror = true;
      setRotation(plank3, 0F, 0F, 0F);
      plank4 = new ModelRenderer(this, 0, 0);
      plank4.addBox(0F, 0F, 0F, 32, 1, 2);
      plank4.setRotationPoint(-8F, 15F, 5F);
      plank4.setTextureSize(128, 64);
      plank4.mirror = true;
      setRotation(plank4, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    leg1.render(f5);
    leg2.render(f5);
    leg3.render(f5);
    leg4.render(f5);
    side1.render(f5);
    side2.render(f5);
    side3.render(f5);
    side4.render(f5);
    plank1.render(f5);
    plank2.render(f5);
    plank3.render(f5);
    plank4.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
