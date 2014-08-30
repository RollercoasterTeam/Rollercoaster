package robomuss.rc.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTrackFabricator extends BlockContainer {

	public BlockTrackFabricator() {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			FMLNetworkHandler.openGui(player, RCMod.instance, 1, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTrackFabricator();
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon side;
	@SideOnly(Side.CLIENT)
	private IIcon top;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		side = register.registerIcon("rc:track_fabricator_side");
		top = register.registerIcon("rc:track_fabricator_top");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int face, int meta) {
		if(face == 0 || face == 1) {
			return top;
		}
		else {
			return side;
		}
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 110;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int i1 = world.getBlockMetadata(x, y, z) >> 2;
        ++l;
        l %= 4;

        if (l == 0) {
            TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
            te.direction = 2;
        }

        if (l == 1) {
        	TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
            te.direction = 3;
        }

        if (l == 2) {
        	TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
            te.direction = 0;
        }

        if (l == 3) {
        	TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
            te.direction = 1;
        }
    }
}
