package robomuss.rc.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by Mark on 25/08/2014.
 */
public class ModelTrain extends ModelBase
{
    //fields
    public ModelRenderer Main;
    public ModelRenderer Shape1;

    public ModelTrain()
    {
        textureWidth = 256;
        textureHeight = 256;

        Main = new ModelRenderer(this, 0, 0);
        Main.addBox(0F, 0F, 0F, 8, 14, 22);
        Main.setRotationPoint(0F, 0F, 0F);
        Main.setTextureSize(256, 256);
        Main.mirror = true;
        setRotation(Main, 0F, 0F, 0F);
        Shape1 = new ModelRenderer(this, 0, 0);
        Shape1.addBox(0F, 0F, 0F, 1, 9, 21);
        Shape1.setRotationPoint(0F, 4F, 0F);
        Shape1.setTextureSize(256, 256);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Main.render(f5);
        Shape1.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
    }

}
