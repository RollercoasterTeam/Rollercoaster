package robomuss.rc.block;

import java.util.Random;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityBench;
import robomuss.rc.block.te.TileEntityBin;
import robomuss.rc.block.te.TileEntityCatwalk;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

public class BlockBench extends BlockContainer {

	public BlockBench() {
		super(Material.wood);
		setHardness(1F);
		setResistance(3F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityBench();
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
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		TileEntityBench teb = (TileEntityBench) iba.getTileEntity(x, y, z);
		if(teb.direction == 0) {
			setBlockBounds(0, 0, -1, 1, 0.6F, 1);
		}
		else if(teb.direction == 1) {
			setBlockBounds(0, 0, 0, 2, 0.6F, 1);
		}
		else if(teb.direction == 2) {
			setBlockBounds(0, 0, 0, 1, 0.6F, 2);
		}
		else if(teb.direction == 3) {
			setBlockBounds(-1, 0, 0, 1, 0.6F, 1);
		}
	}
}
