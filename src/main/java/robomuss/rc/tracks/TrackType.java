package robomuss.rc.tracks;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrain;

public class TrackType {
	
	public int id;
	
	public String unlocalized_name; 
	public int crafting_cost;
	
	public Block block;

	public int special_render_stages;
	
	public boolean inverted = false;
	
	public TrackType(String unlocalized_name, int crafting_cost) {
		this.id = RCBlocks.last_track_id++;
		this.unlocalized_name = unlocalized_name;
		this.crafting_cost = crafting_cost;
	}
	
	public TrackType(String unlocalized_name, int crafting_cost, int special_render_stages) {
		this.id = RCBlocks.last_track_id++;
		this.unlocalized_name = unlocalized_name;
		this.crafting_cost = crafting_cost;
		this.special_render_stages = special_render_stages;
	}

	public void render(ModelBase model, TileEntityTrack te) {
		
	}
	
	public static void rotate(TileEntityTrack te) {
		switch(te.direction){
        case 1:
        		GL11.glRotatef(180f, -180f, 0f, 0f);
                break;
        case 2:
                GL11.glRotatef(180f, 180f, 0f, 180f);
                break;
        case 3:
                GL11.glRotatef(180f, 0f, 0f, 180f);
                break;
        default:
        		GL11.glRotatef(180f, -180f, 0f, 180f);
        		break;
		}
	}

	public float getX(double x, TileEntityTrack te) {
		return (float) (x + 0.5F);
	}

	public float getY(double y, TileEntityTrack te) {
		return (float) (y + 1.5F);
	}

	public float getZ(double z, TileEntityTrack te) {
		return (float) (z + 0.5F);
	}

	public void renderSpecial(int renderStage, ModelBase model, TileEntityTrack te) {
		
	}

	public float getSpecialX(int renderStage, double x, TileEntityTrack te) {
		return (float) (x + 0.5F);
	}

	public float getSpecialY(int renderStage, double y, TileEntityTrack te) {
		return (float) (y + 1.5F);
	}

	public float getSpecialZ(int renderStage, double z, TileEntityTrack te) {
		return (float) (z + 0.5F);
	}

	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.4F, 1);
	}

    public void onTrainCollidedWithTrack(World world, int x, int y, int z, EntityTrain train) {

    }
    
    public TrackType invertTrack() {
    	inverted = true;
		return this;
	}
}
