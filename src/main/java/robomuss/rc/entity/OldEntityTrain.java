package robomuss.rc.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import robomuss.rc.item.RCItems;

public class OldEntityTrain  extends EntityLiving
{

    public OldEntityTrain(World par1World)
    {
        super(par1World);
        this.setSize(0.9F, 0.9F);

        this.boundingBox.setBounds(0.9D, 0.9D, 0.9D, 0D, 0D, 0D);

    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {}

    public OldEntityTrain(World par1World, double par2, double par4, double par6)
    {
        this(par1World);
        this.setPosition(par2, par4 + 1, par6);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }


    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        if(par1EntityPlayer.isSneaking())
        {
            this.setDead();
            if(!this.worldObj.isRemote)
                this.dropItem(this.getDropItem(), 1);
        }

        if (super.interact(par1EntityPlayer))
        {
            return true;
        }
        else if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer))
        {
            par1EntityPlayer.mountEntity(this);
            return true;
        }
        else
        {
            return false;
        }
    }

    protected Item getDropItem()
    {
        return RCItems.train;
    }

}