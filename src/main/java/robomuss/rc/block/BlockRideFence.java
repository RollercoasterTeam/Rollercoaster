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
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

public class BlockRideFence extends BlockContainer implements IPaintable {

	public BlockRideFence() {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityRideFence();
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
		if(!world.isRemote) {
			if(player.getHeldItem() != null) {
				if(player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityRideFence terf = (TileEntityRideFence) world.getTileEntity(x, y, z);
					terf.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(x, y, z);
					return true;
				}
				else if(player.getHeldItem().getItem() == RCItems.panel) {
					TileEntityRideFence terf = (TileEntityRideFence) world.getTileEntity(x, y, z);
					if(terf.getBlockType() == RCBlocks.ride_fence) {
						world.setBlock(x, y, z, RCBlocks.ride_fence_panel);
						player.inventory.consumeInventoryItem(RCItems.panel);
						return true;
					}
					else {
						return false;
					}
				}
				else { 
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			if(world.getBlock(x, y, z) == RCBlocks.ride_fence_panel && player.getHeldItem() == null) {
				FMLNetworkHandler.openGui(player, RCMod.instance, 3, world, x, y, z);
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getPaintMeta(World world, int x, int y, int z) {
		return ((TileEntityRideFence) world.getTileEntity(x, y, z)).colour;
	}
	
	@Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if(!world.isRemote) {
        	if(this == RCBlocks.ride_fence_gate) {
	        	TileEntityRideFence te = (TileEntityRideFence) world.getTileEntity(x, y, z);
	        	te.open = world.isBlockIndirectlyGettingPowered(x, y, z);
	        	world.markBlockForUpdate(x, y, z);
        	}
        }
    }
	
	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return super.getItemDropped(i, random, j);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		TileEntityRideFence terf = (TileEntityRideFence) iba.getTileEntity(x, y, z);
		if(!terf.open) {
			if(terf.blockType == RCBlocks.ride_fence_gate || terf.blockType == RCBlocks.ride_fence) {
				if(terf.direction == 0 || terf.direction == 2) { 
					setBlockBounds(0.375F, 0, 0, 0.625F, 0.75F, 1);
				}
				else if(terf.direction == 1 || terf.direction == 3) {
					setBlockBounds(0, 0, 0.375F, 1, 0.75F, 0.625F);
				}
			}
			else if(terf.blockType == RCBlocks.ride_fence_corner) {
				if(terf.direction == 0) {
					setBlockBounds(0.375F, 0, 0.375F, 1, 0.75F, 1);
				}
				else if(terf.direction == 1) {
					setBlockBounds(0, 0, 0.375F, 0.625F, 0.75F, 1);
				}
				else if(terf.direction == 2) {
					setBlockBounds(0, 0, 0, 0.625F, 0.75F, 0.625F);
				}
				else if(terf.direction == 3) {
					setBlockBounds(0.375F, 0, 0, 1, 0.75F, 0.625F);
				}
			}
			else if(terf.blockType == RCBlocks.ride_fence_triangle) {
				if(terf.direction == 0) {
					setBlockBounds(0, 0, 0.375F, 1, 0.75F, 1);
				}
				else if(terf.direction == 1) {
					setBlockBounds(0, 0, 0, 0.625F, 0.75F, 1);
				}
				else if(terf.direction == 2) {
					setBlockBounds(0, 0, 0, 1, 0.75F, 0.625F);
				}
				else if(terf.direction == 3) {
					setBlockBounds(0.375F, 0, 0, 1, 0.75F, 1F);
				}
			}
			else if(terf.blockType == RCBlocks.ride_fence_square) {
				setBlockBounds(0, 0, 0, 1, 0.75F, 1);
			}
			else if(terf.blockType == RCBlocks.ride_fence_panel) {
				if(terf.direction == 0 || terf.direction == 2) { 
					setBlockBounds(0.375F, 0, 0, 0.625F, 1, 1);
				}
				else if(terf.direction == 1 || terf.direction == 3) {
					setBlockBounds(0, 0, 0.375F, 1, 1, 0.625F);
				}
			}
			else {
				setBlockBounds(0, 0, 0, 1, 1, 1);
			}
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		TileEntityRideFence terf = (TileEntityRideFence) world.getTileEntity(x, y, z);
		if(terf != null) {
			if(terf.open) {
				return null;
			}
			else {
				return super.getCollisionBoundingBoxFromPool(world, x, y, z);
			}
		}
		else {
			return super.getCollisionBoundingBoxFromPool(world, x, y, z);
		}
	}
}
