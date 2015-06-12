package robomuss.rc.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.util.ColourUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRemote extends Item {
	
	public boolean hasTarget = false;
	public int posX, posY, posZ;
	
	public ItemRemote() {
		
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			if(world.getBlock(x, y, z) == RCBlocks.ride_fence_panel) {
				stack.stackTagCompound = new NBTTagCompound();
				
				stack.stackTagCompound.setBoolean("hasTarget", hasTarget);
				
				stack.stackTagCompound.setInteger("posX", x);
				stack.stackTagCompound.setInteger("posY", y);
				stack.stackTagCompound.setInteger("posZ", z);
				
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if(stack.stackTagCompound != null) {
			list.add(stack.stackTagCompound.getString("posX") + ", " + stack.stackTagCompound.getString("posY") + ", " + stack.stackTagCompound.getString("posZ"));
		}
	}
}
