package robomuss.rc.block;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackStorage;

public class BlockTrackStorage extends BlockContainer {

	public BlockTrackStorage() {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler
					.openGui(player, RCMod.instance, 2, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTrackStorage();
	}

	@SideOnly(Side.CLIENT)
	private IIcon side;
	@SideOnly(Side.CLIENT)
	private IIcon top;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		side = register.registerIcon("rc:track_storage_side");
		top = register.registerIcon("rc:track_storage_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int face, int meta) {
		if (face == 0 || face == 1) {
			return top;
		} else {
			return side;
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntityTrackStorage te = (TileEntityTrackStorage) world.getTileEntity(x, y, z);

		if (te != null) {
			for (int i1 = 0; i1 < te.getSizeInventory(); ++i1) {
				ItemStack itemstack = te.getStackInSlot(i1);

				if (itemstack != null) {
					float f = world.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = world.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world
							.spawnEntityInWorld(entityitem)) {
						int j1 = world.rand.nextInt(21) + 10;

						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						entityitem = new EntityItem(world,
								(double) ((float) x + f),
								(double) ((float) y + f1),
								(double) ((float) z + f2),
								new ItemStack(itemstack.getItem(), j1,
										itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (double) ((float) world.rand
								.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) world.rand
								.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double) ((float) world.rand
								.nextGaussian() * f3);

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound(
									(NBTTagCompound) itemstack.getTagCompound()
											.copy());
						}
					}
				}
			}

			world.func_147453_f(x, y, z,
					block);
		}

		super.breakBlock(world, x, y, z,
				block, meta);
	}
}
