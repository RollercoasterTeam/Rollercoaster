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
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.style.TrackStyle;

import java.util.ArrayList;
import java.util.List;

public class TrackPiece {
	public int id;
	public int crafting_cost;
	public int render_stage;
	public String unlocalized_name;
	public Block  block;
	public boolean inverted = false;
	
	private List<TileEntityTrackBase> teList = new ArrayList<TileEntityTrackBase>();
	
	public TrackPiece(String unlocalized_name, int crafting_cost, int render_stage) {
		this.id = RCBlocks.last_track_id++;
		this.unlocalized_name = unlocalized_name;
		this.crafting_cost = crafting_cost;
		this.render_stage = render_stage;
	}
	
	public void addTileEntityToList(TrackPiece type, TileEntityTrackBase teTrack) {
		if (teTrack != null) {
			teList.add(teTrack);
			teTrack.setListTypeAndIndex(type, teList.indexOf(teTrack));
		}
	}
	
	public void addTileEntityToList(TrackPiece type, TileEntityTrackBase teTrack, int index) {
		if (teTrack != null) {
			teList.add(index, teTrack);
			teTrack.setListTypeAndIndex(type, index);
		}
	}

	public TileEntityTrackBase getTileEntityFromList(int index) {
		return this.teList.size() > index ? this.teList.get(index) : null;
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

//	public void renderSpecialTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x, int y, int z) {}
//
//	public void renderSpecialItem(int render_stage, IItemRenderer.ItemRenderType render_type, TrackStyle style, BlockTrackBase track, World world, int x, int y, int z) {}

//	public float getSpecialX(int render_stage, double x, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
//		return (float) (x + 0.5F);
//	}
//
//	public float getSpecialY(int render_stage, double y, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
//		return (float) (y + 0.5F);
//	}
//
//	public float getSpecialZ(int render_stage, double z, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
//		return (float) (z + 0.5F);
//	}

	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
	}

    public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {}
    
    public TrackPiece invertTrack() {
    	inverted = true;
		return this;
	}
}
