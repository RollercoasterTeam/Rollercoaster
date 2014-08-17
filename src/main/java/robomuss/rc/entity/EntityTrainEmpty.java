package robomuss.rc.entity;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityTrainEmpty extends EntityTrain
{
    private static final String __OBFID = "CL_00001677";

    public EntityTrainEmpty(World par1World)
    {
        super(par1World);
    }

    public EntityTrainEmpty(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * First layer of player interaction
     */
    public boolean interactFirst(EntityPlayer par1EntityPlayer)
    {
        //if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartInteractEvent(this, par1EntityPlayer))) return true;
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer)
        {
            return true;
        }
        else if (this.riddenByEntity != null && this.riddenByEntity != par1EntityPlayer)
        {
            return false;
        }
        else
        {
            if (!this.worldObj.isRemote)
            {
                par1EntityPlayer.mountEntity(this);
            }

            return true;
        }
    }

    public int getMinecartType()
    {
        return 0;
    }
}