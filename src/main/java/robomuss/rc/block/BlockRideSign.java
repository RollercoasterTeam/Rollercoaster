package robomuss.rc.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityCatwalk;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityRideSign;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.item.RCItems;

public class BlockRideSign extends BlockContainer {

	public BlockRideSign() {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityRideSign();
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
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			if(player.getHeldItem() != null) {
				if(player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityRideSign ters = (TileEntityRideSign) world.getTileEntity(x, y, z);
					ters.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(x, y, z);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        ++l;
        l %= 4;

        if (l == 0) {
        	TileEntityRideSign te = (TileEntityRideSign) world.getTileEntity(x, y, z);
            te.direction = 2;
        }

        if (l == 1) {
        	TileEntityRideSign te = (TileEntityRideSign) world.getTileEntity(x, y, z);
            te.direction = 3;
        }

        if (l == 2) {
        	TileEntityRideSign te = (TileEntityRideSign) world.getTileEntity(x, y, z);
            te.direction = 0;
        }

        if (l == 3) {
        	TileEntityRideSign te = (TileEntityRideSign) world.getTileEntity(x, y, z);
            te.direction = 1;
        }
        
        TileEntityRideSign te = ((TileEntityRideSign) world.getTileEntity(x, y, z));
        te.name = entity.getCommandSenderName();
        world.setTileEntity(x, y, z, te);
        System.out.println(te.name);
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		TileEntityRideSign ters = (TileEntityRideSign) iba.getTileEntity(x, y, z);
		
		if(ters.direction == 0) {
			setBlockBounds(0, 0, -2, 0.15F, 0.75F, 3);
		}
		else if(ters.direction == 1) {
			setBlockBounds(-2, 0, 0, 3, 0.75F, 0.15F);
		}
		else if(ters.direction == 2) {
			setBlockBounds(1, 0, -2, 0.85F, 0.75F, 3);
		}
		else if(ters.direction == 3) {
			setBlockBounds(-2, 0, 1, 3, 0.75F, 0.85F);
		}
	}
}
