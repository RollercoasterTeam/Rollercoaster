package robomuss.rc.track.piece;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.entity.EntityTrainDefault2;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.style.TrackStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackPiece {
	public int id;
	public int crafting_cost;
	public int render_stage;
	public int number_of_dummies;
	public String unlocalized_name;
	public Block  block;
	public boolean inverted = false;
	
	public TrackPiece(int id, String unlocalized_name, int crafting_cost, int render_stage, int number_of_dummies) {
//		this.id = RCBlocks.last_track_id++;
		this.id = id;
		this.unlocalized_name = unlocalized_name;
		this.crafting_cost = crafting_cost;
		this.render_stage = render_stage;
		this.number_of_dummies = number_of_dummies;
//		pieceMap.put(id, this);
	}

	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x, int y, int z) {}

	public void renderItem(int render_stage, IItemRenderer.ItemRenderType render_type, TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {}

	public BlockTrackBase getTrack() {
		return TrackManager.isTrack(block) ? (BlockTrackBase) block :  null;
	}
	
	public void rotate(TileEntityTrackBase teTrack, World world, int x, int y, int z) {
		int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

		switch (meta) {
			case 2: GL11.glRotatef(180f, 180f, 0f, 180f);  break;
			case 3: GL11.glRotatef(180f, -180f, 0f, 180f); break;
			case 4: GL11.glRotatef(180f, -180f, 0f, 0f);   break;
			case 5: GL11.glRotatef(180f, 0f, 0f, 180f);    break;
 		}
	}

	public float getX(int render_stage, double x, TileEntityTrackBase teTrack, World world, int lx, int ly, int l) {
		return (float) (x + 0.5F);
	}

	public float getY(int render_stage, double y, TileEntityTrackBase teTrack, World world, int lx, int ly, int l) {
		return (float) (y + 1.5F);
	}

	public float getZ(int render_stage, double z, TileEntityTrackBase teTrack, World world, int lx, int ly, int l) {
		return (float) (z + 0.5F);
	}

	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
	}

    public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {}

	public void moveTrain(BlockTrackBase track, EntityTrainDefault2 entity, TileEntityTrackBase teTrack) {}
    
    public TrackPiece invertTrack() {
    	inverted = true;
		return this;
	}
}
