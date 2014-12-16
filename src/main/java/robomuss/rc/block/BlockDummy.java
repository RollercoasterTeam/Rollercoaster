package robomuss.rc.block;


import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robomuss.rc.block.te.TileEntityDummy;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.track.piece.TrackPieceSlope;
import robomuss.rc.track.piece.TrackPieceSlopeDown;
import robomuss.rc.track.piece.TrackPieceSlopeUp;

import java.util.Random;

public class BlockDummy extends BlockContainer {
	private int slopeLocX, slopeLocY, slopeLocZ;
	private TileEntityTrack teSlope;
	private BlockTrack blockSlope;
	private Random rand = new Random();

	private boolean breakSlope = true;
	private int slopeDirection;


	public BlockDummy() {
		super(Material.iron);
		setHardness(1F);
		setHardness(3F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World world, MovingObjectPosition position, EffectRenderer renderer) {
		int x = position.blockX;
		int y = position.blockY;
		int z = position.blockZ;

		Block block = world.getBlock(x, y, z);
		if (block == null) {
			return false;
		}

		int side = position.sideHit;

		float i = 0.1F;
		double posX = x + rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (i * 2.0F)) + i + block.getBlockBoundsMinX();
		double posY = y + rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (i * 2.0F)) + i + block.getBlockBoundsMinY();
		double posZ = z + rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (i * 2.0F)) + i + block.getBlockBoundsMinZ();

		if (side == 4) posX = x + block.getBlockBoundsMinX() - i;
		if (side == 5) posX = x + block.getBlockBoundsMaxX() + i;
		if (side == 0) posY = y + block.getBlockBoundsMinY() - i;
		if (side == 1) posY = y + block.getBlockBoundsMaxY() + i;
		if (side == 2) posZ = z + block.getBlockBoundsMinZ() - i;
		if (side == 3) posZ = z + block.getBlockBoundsMaxZ() + i;

		EntityDiggingFX diggingFX = new EntityDiggingFX(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D, block, side, world.getBlockMetadata(x, y, z));
		diggingFX.setParticleIcon(getIcon(0, 0));
		renderer.addEffect(diggingFX.applyColourMultiplier(x, y, z).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer renderer) {
		Block block = world.getBlock(x, y, z);
		if (block == null) {
			return false;
		}

		byte b0 = 4;
		for (int i = 0; i < b0; ++i) {
			for (int j = 0; j < b0; ++j) {
				for (int k = 0; k < b0; ++k) {
					double px = x + (i + 0.5D) / b0;
					double py = y + (j + 0.5D) / b0;
					double pz = z + (k + 0.5D) / b0;

					int random = rand.nextInt(6);

					EntityDiggingFX fx = new EntityDiggingFX(world, px, py, pz, px - x - 0.5D, py - y - 0.5D, pz - z - 0.5D, this, random, meta);
					fx.setParticleIcon(getIcon(0, 0));
					renderer.addEffect(fx.applyColourMultiplier(x, y, z));
				}
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int direction) {
		return new TileEntityDummy();
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, BlockPos pos) {
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	public void onBlockHarvested(World world, int x, int y, int z, EntityPlayer player) {
		onBlockHarvested(world ,new BlockPos(x, y ,z), world.getBlockState(new BlockPos(x, y, z)), player);
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn) {
		//		if (player.capabilities.isCreativeMode) {
//			System.out.println("Player in creative.");
//			System.out.println(getBreakSlope());
		if (getBreakSlope()) {
			world.setBlockToAir(slopeLoc());
			world.markBlockForUpdate(slopeLoc());
			world.setBlockToAir(pos);
			world.markBlockForUpdate(pos);
		} else {
			world.setBlockToAir(pos);
			world.markBlockForUpdate(pos);
		}
//		}
	}

	public void setBreakSlope(boolean breakSlope) {
		this.breakSlope = breakSlope;
	}

	public boolean getBreakSlope() {
		return this.breakSlope;
	}

//	public void setSlopeDirection(int direction) {
//		this.slopeDirection = direction;
//	}

	public void setParentSlope(TileEntityTrack teTrack, BlockTrack track) {
		this.slopeLocX = teTrack.xCoord;
		this.slopeLocY = teTrack.yCoord;
		this.slopeLocZ = teTrack.zCoord;
		this.slopeDirection = teTrack.direction.ordinal();
		this.teSlope = teTrack;
		this.blockSlope = track;
//		System.out.printf("Parent slope set: %d, %d, %d, %b, %b", slopeLocX, slopeLocY, slopeLocZ, teSlope != null, blockSlope != null);
//		System.out.println();
	}

	public BlockTrack getParentSlope() {
		return blockSlope;
	}


	public BlockPos slopeLoc() {
		return new BlockPos(slopeLocX, slopeLocY, slopeLocZ);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return null;

	}
}
