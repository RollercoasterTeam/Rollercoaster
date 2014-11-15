package robomuss.rc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.multiblock.MultiBlockTrackFabricator;

public class BlockTrackFabricator extends BlockContainer {

	public BlockTrackFabricator() {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);

//		new MultiBlockManager();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
			return teFab.testStruct(player);
//			if(MultiBlockTrackFabricator.getInstance().isStructureFormed(teFab)) {
////				FMLNetworkHandler.openGui(player, RCMod.instance, 1, world, x, y, z);
//				return teFab.openGui(world, x, y, z, player);
//			}
		}

		return true;
	}

//	@SideOnly(Side.CLIENT)
//	public IIcon particleIcon;
//
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon("rc:track_fabricator_casing");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTrackFabricator();
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
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
//        ++l;
//		System.out.println(l);
        l %= 4;

//		System.out.println(l);
		TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
//		teFab.direction = ForgeDirection.VALID_DIRECTIONS[l + 2].getOpposite();
		switch (l) {
			case 0: teFab.direction = ForgeDirection.SOUTH; break;
			case 1: teFab.direction = ForgeDirection.EAST;  break;
			case 2: teFab.direction = ForgeDirection.NORTH; break;
			case 3: teFab.direction = ForgeDirection.WEST;  break;
		}

//		teFab.testStruct((EntityPlayer) entity);

//        if (l == 0) {
//            TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
////            te.direction = 2;
//	        te.direction = te.direction.getOpposite();
//        }
//
//        if (l == 1) {
//        	TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
//            te.direction = 3;
//        }
//
//        if (l == 2) {
//        	TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
//            te.direction = 0;
//        }
//
//        if (l == 3) {
//        	TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
//            te.direction = 1;
//        }
    }
}
