package robomuss.rc.track.piece;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockPath;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.style.TrackStyle;

import java.util.ArrayList;
import java.util.List;

public class TrackPiece {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public int    id;
	public int    crafting_cost;
	public int    render_stage;
	public String unlocalized_name;
	public Block  block;
	public boolean inverted = false;

	public TrackPiece(String unlocalized_name, int crafting_cost, int render_stage) {
		this.id = RCBlocks.last_track_id++;
		this.unlocalized_name = unlocalized_name;
		this.crafting_cost = crafting_cost;
		this.render_stage = render_stage;
	}

	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, BlockPos pos) {}

	//TODO: models!
//	public void renderItem(int render_stage, IItemRenderer.ItemRenderType render_type, TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {}

	public BlockTrackBase getTrack() {
		return TrackManager.isTrack(block) ? (BlockTrackBase) block : null;
	}

	//TODO: figure out blockStates!
	public void rotate(TileEntityTrackBase teTrack, World world, BlockPos pos) {
		//rotate based on property
	}

	public float getX(int render_stage, double x, TileEntityTrackBase teTrack, World world, int lx, int ly, int l) {
		return (float) (x + 0.5f);
	}

	public float getY(int render_stage, double y, TileEntityTrackBase teTrack, World world, int lx, int ly, int l) {
		return (float) (y + 1.5f);
	}

	public float getZ(int render_stage, double z, TileEntityTrackBase teTrack, World world, int lx, int ly, int l) {
		return (float) (z + 0.5f);
	}

	public AxisAlignedBB getRenderBoundingBox(World world, BlockPos pos) {
//		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
		return AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
	}

	public AxisAlignedBB getBlockBounds(IBlockAccess iba, BlockPos pos) {
		return AxisAlignedBB.fromBounds(0, 0, 0, 1, 1, 1);
	}

    public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {}
    
    public TrackPiece invertTrack() {
    	inverted = true;
		return this;
	}
}
