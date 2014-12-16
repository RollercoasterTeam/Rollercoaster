package robomuss.rc.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelConveyor extends ModelBase {
<<<<<<< HEAD
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer conveyor;
=======
    public ModelRenderer side1;
    public ModelRenderer side2;
    public ModelRenderer conveyor;
>>>>>>> master
  
  public ModelConveyor() {
		textureWidth = 128;
		textureHeight = 64;
    
	  side1 = new ModelRenderer(this, 0, 17);
	  side1.addBox(0F, 0F, 0F, 1, 4, 16);
	  side1.setRotationPoint(-8F, 20F, -8F);
	  side1.setTextureSize(128, 64);
	  side1.mirror = true;
	  setRotation(side1, 0F, 0F, 0F);
	  side2 = new ModelRenderer(this, 0, 17);
	  side2.addBox(0F, 0F, 0F, 1, 4, 16);
	  side2.setRotationPoint(7F, 20F, -8F);
	  side2.setTextureSize(128, 64);
	  side2.mirror = true;
	  setRotation(side2, 0F, 0F, 0F);
	  conveyor = new ModelRenderer(this, 0, 0);
	  conveyor.addBox(0F, 0F, 0F, 14, 1, 16);
	  conveyor.setRotationPoint(-7F, 23F, -8F);
	  conveyor.setTextureSize(128, 64);
	  conveyor.mirror = true;
	  setRotation(conveyor, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    side1.render(f5);
    side2.render(f5);
    conveyor.render(f5);
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
