package robomuss.rc.block;

import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityStall;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStall extends BlockContainer {
	public BlockStall() {
		super(Material.wood);
		setBlockBounds(0f, 0f, 0f, 1f, 0.625f, 1f);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityStall();
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntityStall teStall = (TileEntityStall) world.getTileEntity(x, y, z);

		if (!teStall.hasStallMerchantBeenInitialized) {
			teStall.initStallMerchant(player, x, y, z, this.getLocalizedName());
		}

		if (!world.isRemote) {
			player.openGui(RCMod.instance, 3, world, x, y, z);
		}

//		if(Minecraft.getMinecraft().currentScreen.isCtrlKeyDown()) {
//			TileEntityStall te = (TileEntityStall) world.getTileEntity(x, y, z);
//
//			if(player.getHeldItem() != null) {
//				te.camouflage = player.getHeldItem();
//			}
//		} else {
//			FMLNetworkHandler.openGui(player, RCMod.instance, 3, world, x, y, z);
//		}

		return true;
	}

//	@Override
//	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
//		TileEntityStall teStall = (TileEntityStall) world.getTileEntity(x, y, z);
//
//		if (player.getHeldItem() == null) {
//			return teStall.camouflage;
//		} else if (player.getHeldItem().getItem() == Item.getItemFromBlock(this)) {
//			return new ItemStack(this);
//		} else {
//			return null;
//		}
//	}
}
