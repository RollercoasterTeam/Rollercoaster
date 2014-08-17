package robomuss.rc.item;

import robomuss.rc.block.BlockTrack;
import robomuss.rc.entity.EntityTrain;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemTrain extends Item
{
    private static final IBehaviorDispenseItem dispenserMinecartBehavior = new BehaviorDefaultDispenseItem()
    {
        private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
        {
            EnumFacing enumfacing = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
            World world = par1IBlockSource.getWorld();
            double d0 = par1IBlockSource.getX() + (double)((float)enumfacing.getFrontOffsetX() * 1.125F);
            double d1 = par1IBlockSource.getY() + (double)((float)enumfacing.getFrontOffsetY() * 1.125F);
            double d2 = par1IBlockSource.getZ() + (double)((float)enumfacing.getFrontOffsetZ() * 1.125F);
            int i = par1IBlockSource.getXInt() + enumfacing.getFrontOffsetX();
            int j = par1IBlockSource.getYInt() + enumfacing.getFrontOffsetY();
            int k = par1IBlockSource.getZInt() + enumfacing.getFrontOffsetZ();
            Block block = world.getBlock(i, j, k);
            double d3;

            if (BlockRailBase.func_150051_a(block))
            {
                d3 = 0.0D;
            }
            else
            {
                if (block.getMaterial() != Material.air || !BlockRailBase.func_150051_a(world.getBlock(i, j - 1, k)))
                {
                    return this.behaviourDefaultDispenseItem.dispense(par1IBlockSource, par2ItemStack);
                }

                d3 = -1.0D;
            }

            EntityTrain entityminecart = EntityTrain.createMinecart(world, d0, d1 + d3, d2, ((ItemTrain)par2ItemStack.getItem()).minecartType);

            if (par2ItemStack.hasDisplayName())
            {
                entityminecart.setMinecartName(par2ItemStack.getDisplayName());
            }

            world.spawnEntityInWorld(entityminecart);
            par2ItemStack.splitStack(1);
            return par2ItemStack;
        }
        /**
         * Play the dispense sound from the specified block.
         */
        protected void playDispenseSound(IBlockSource par1IBlockSource)
        {
            par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
        }
    };
    public int minecartType;
    private static final String __OBFID = "CL_00000049";

    public ItemTrain(int p_i45345_1_)
    {
        this.maxStackSize = 1;
        this.minecartType = p_i45345_1_;
        this.setCreativeTab(CreativeTabs.tabTransport);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinecartBehavior);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.getBlock(par4, par5, par6) instanceof BlockTrack)
        {
            if (!par3World.isRemote)
            {
                EntityTrain entityminecart = EntityTrain.createMinecart(par3World, (double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), this.minecartType);

                if (par1ItemStack.hasDisplayName())
                {
                    entityminecart.setMinecartName(par1ItemStack.getDisplayName());
                }

                par3World.spawnEntityInWorld(entityminecart);
            }

            --par1ItemStack.stackSize;
            return true;
        }
        else
        {
            return false;
        }
    }
}