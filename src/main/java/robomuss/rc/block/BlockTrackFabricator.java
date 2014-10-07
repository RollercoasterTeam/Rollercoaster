package robomuss.rc.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackFabricator;

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
