package robomuss.rc.block;

import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackStorage;

public class BlockTrackStorage extends BlockContainer {
	public BlockTrackStorage() {
		super(Material.iron);
		setHardness(1f);
		setResistance(3f);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, RCMod.instance, 2, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTrackStorage();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityTrackStorage te = (TileEntityTrackStorage) world.getTileEntity(pos);

		if (te != null) {
			for (int i = 0; i < te.getSizeInventory(); ++i) {
				ItemStack itemstack = te.getStackInSlot(i);

				if (itemstack != null) {
					float f = world.rand.nextFloat()  * 0.8f + 0.1f;
					float f1 = world.rand.nextFloat() * 0.8f + 0.1f;
					EntityItem entityitem;

					for (float f2 = world.rand.nextFloat() * 0.8f + 0.1f; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem)) {
						int j1 = world.rand.nextInt(21) + 10;

						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						entityitem = new EntityItem(world, (double) ((float) pos.getX() + f), (double) ((float) pos.getY() + f1), (double) ((float) pos.getZ() + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

						float f3 = 0.05f;

						entityitem.motionX = (double) ((float) world.rand.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) world.rand.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double) ((float) world.rand.nextGaussian() * f3);

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
					}
				}
			}

			//TODO: figure out if this is the proper method for the commented func
			world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
//			world.func_147453_f(pos, block);
		}

		super.breakBlock(world, pos, state);
	}
}
