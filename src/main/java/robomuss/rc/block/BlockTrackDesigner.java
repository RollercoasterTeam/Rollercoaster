package robomuss.rc.block;

import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackDesigner;

public class BlockTrackDesigner extends BlockContainer {
	public BlockTrackDesigner() {
		super(Material.rock);
		setHardness(1f);
		setResistance(3f);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		FMLNetworkHandler.openGui(player, RCMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityTrackDesigner();
    }
}
