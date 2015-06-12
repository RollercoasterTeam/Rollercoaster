package robomuss.rc.block;

import javax.naming.directory.DirContext;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.item.ItemRemote;
import robomuss.rc.item.RCItems;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTrackDesigner extends BlockContainer {

	public BlockTrackDesigner() {
		super(Material.rock);
		setHardness(1F);
		setResistance(3F);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == RCItems.remote) {
			TileEntityTrackDesigner te = (TileEntityTrackDesigner) world.getTileEntity(x, y, z);
			
			te.hasTarget = true;
			
			te.targetX = player.getHeldItem().stackTagCompound.getInteger("posX");
			te.targetY = player.getHeldItem().stackTagCompound.getInteger("posY");
			te.targetZ = player.getHeldItem().stackTagCompound.getInteger("posZ");
			return true;
		}
		else {
			FMLNetworkHandler.openGui(player, RCMod.instance, 0, world, x, y, z);
			return true;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon other;
	@SideOnly(Side.CLIENT)
	public IIcon side;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		other = icon.registerIcon("rc:track_designer_other");
		side = icon.registerIcon("rc:track_designer_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int face, int meta) {
		if(face == 0 || face == 1) {
			return other;
		}
		else {
			return side;
		}
	}

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityTrackDesigner();
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
        ++l;
        l %= 4;

        if (l == 0) {
            TileEntityTrackDesigner te = (TileEntityTrackDesigner) world.getTileEntity(x, y, z);
            te.direction = 2;
        }

        if (l == 1) {
        	TileEntityTrackDesigner te = (TileEntityTrackDesigner) world.getTileEntity(x, y, z);
            te.direction = 3;
        }

        if (l == 2) {
        	TileEntityTrackDesigner te = (TileEntityTrackDesigner) world.getTileEntity(x, y, z);
            te.direction = 0;
        }

        if (l == 3) {
        	TileEntityTrackDesigner te = (TileEntityTrackDesigner) world.getTileEntity(x, y, z);
            te.direction = 1;
        }
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		TileEntityTrackDesigner te = (TileEntityTrackDesigner) iba.getTileEntity(x, y, z);
		if(te.direction == 0) {
			setBlockBounds(0, 0, 0.15F, 0.4F, 1, 0.85F);
		}
		else if(te.direction == 1) {
			setBlockBounds(0.15F, 0, 0, 0.85F, 1, 0.4F);
		}
		else if(te.direction == 2) {
			setBlockBounds(0.6F, 0, 0.15F, 1, 1, 0.85F);
		}
		else if(te.direction == 3) {
			setBlockBounds(0.15F, 0, 0.6F, 0.85F, 1, 1);
		}
	}
}
