package robomuss.rc.block;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
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
		setBlockBounds(0F, 0F, 0F, 1F, 0.625F, 1F);
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
		FMLNetworkHandler.openGui(player, RCMod.instance, 3, world, x, y, z);
		return true;
	}
}
