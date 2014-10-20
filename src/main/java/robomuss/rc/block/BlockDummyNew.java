package robomuss.rc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityDummyNew;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.*;

import java.util.Random;

public class BlockDummyNew extends BlockTrackBase {
	public BlockDummyNew(TrackPiece track_type) {
		super(track_type);
	}
//	private TileEntityDummyNew teDummy;
//	private IIcon[] icons = new IIcon[3];
//
//	Random rand;
//
//	public BlockDummyNew() {
//		super();
//	}
//
//	@Override
//	public IIcon getIcon(int side, int meta) {
//		if (this.teDummy.track == null) {
//
//		} else if (this.teDummy.track.track_type instanceof TrackPieceSlopeUp) {
//			return icons[0];
//		} else if (this.teDummy.track.track_type instanceof TrackPieceSlope) {
//			return icons[1];
//		} else if (this.teDummy.track.track_type instanceof TrackPieceSlopeDown) {
//			return icons[2];
//		}
//		return this.blockIcon;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(IIconRegister iconRegister) {
//		this.icons[0] = iconRegister.registerIcon("rc:tracks/slope_up");
//		this.icons[1] = iconRegister.registerIcon("rc:tracks/slope");
//		this.icons[2] = iconRegister.registerIcon("rc:tracks/slope_down");
//		this.blockIcon = iconRegister.registerIcon("rc:tracks/horizontal");
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public boolean addHitEffects(World world, MovingObjectPosition position, EffectRenderer renderer) {
//		int x = position.blockX;
//		int y = position.blockY;
//		int z = position.blockZ;
//
//		Block block = world.getBlock(x, y, z);
//		if (block == null) {
//			return false;
//		}
//
//		int side = position.sideHit;
//
//		float i = 0.1F;
//		double posX = x + rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (i * 2.0F)) + i + block.getBlockBoundsMinX();
//		double posY = y + rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (i * 2.0F)) + i + block.getBlockBoundsMinY();
//		double posZ = z + rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (i * 2.0F)) + i + block.getBlockBoundsMinZ();
//
//		if (side == 4) posX = x + block.getBlockBoundsMinX() - i;
//		if (side == 5) posX = x + block.getBlockBoundsMaxX() + i;
//		if (side == 0) posY = y + block.getBlockBoundsMinY() - i;
//		if (side == 1) posY = y + block.getBlockBoundsMaxY() + i;
//		if (side == 2) posZ = z + block.getBlockBoundsMinZ() - i;
//		if (side == 3) posZ = z + block.getBlockBoundsMaxZ() + i;
//
//		EntityDiggingFX diggingFX = new EntityDiggingFX(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D, block, side, world.getBlockMetadata(x, y, z));
//		diggingFX.setParticleIcon(getIcon(0, 0));
//		renderer.addEffect(diggingFX.applyColourMultiplier(x, y, z).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
//		return true;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer renderer) {
//		Block block = world.getBlock(x, y, z);
//		if (block == null) {
//			return false;
//		}
//
//		byte b0 = 4;
//		for (int i = 0; i < b0; ++i) {
//			for (int j = 0; j < b0; ++j) {
//				for (int k = 0; k < b0; ++k) {
//					double px = x + (i + 0.5D) / b0;
//					double py = y + (j + 0.5D) / b0;
//					double pz = z + (k + 0.5D) / b0;
//
//					int random = rand.nextInt(6);
//
//					EntityDiggingFX fx = new EntityDiggingFX(world, px, py, pz, px - x - 0.5D, py - y - 0.5D, pz - z - 0.5D, this, random, meta);
//					fx.setParticleIcon(getIcon(0, 0));
//					renderer.addEffect(fx.applyColourMultiplier(x, y, z));
//				}
//			}
//		}
//		return true;
//	}
//
//	@Override
//	public TileEntity createNewTileEntity(World world, int meta) {
//		teDummy = new TileEntityDummyNew();
//		teDummy.track = this;
//		return teDummy;
//	}
//
//	@Override
//	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
//		setBlockBounds(0, 0, 0, 1, 1, 1);
//	}
//
//	@Override
//	public boolean isOpaqueCube() {
//		return false;
//	}
//
//	@Override
//	public int getRenderType() {
//		return -1;
//	}
//
//	@Override
//	public boolean renderAsNormalBlock() {
//		return false;
//	}
//
//	@Override
//	public void onBlockAdded(World world, int x, int y, int z) {
//		//TODO: adjust for TrackPieceSlopeDown placement
//	}
}
