package robomuss.rc.track.extra;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.model.ModelBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.item.RCItems;
import robomuss.rc.track.piece.TrackPiece;

public class TrackExtra {
	public int id;
	public Item source;
	public String name;
	public ResourceLocation texture;
	public ModelBase model;
	public ArrayList<TrackPiece> allowedTrackTypes;
	public int amount;
	public Object[] recipe;
	public int render_stage;
	
	public TrackExtra(String name, ModelBase model, Object[] recipe, int amount, TrackPiece... allowedTrackTypes) {
		this.id = RCItems.last_extra_id++;
		this.name = name;
		this.texture = new ResourceLocation("rc:textures/models/extras/" + name + ".png");
		this.model = model;
		this.recipe = recipe;
		this.amount = amount;
		this.allowedTrackTypes = new ArrayList<TrackPiece>(Arrays.asList(allowedTrackTypes));
	}
	
	public TrackExtra(String name, ModelBase model, Object[] recipe, int amount, int render_stage, TrackPiece... allowedTrackTypes) {
		this.id = RCItems.last_extra_id++;
		this.name = name;
		this.texture = new ResourceLocation("rc:textures/models/extras/" + name + ".png");
		this.model = model;
		this.recipe = recipe;
		this.amount = amount;
		this.render_stage = render_stage;
		this.allowedTrackTypes = new ArrayList<TrackPiece>(Arrays.asList(allowedTrackTypes));
	}

	public void render(TrackPiece track) {
		model.render(null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
	}
	
	public float getX(int render_stage, double x, TileEntityTrackBase track) {
		return (float) (x + 0.5f);
	}

	public float getY(int render_stage, double y, TileEntityTrackBase track) {
		return (float) (y + 1.5f);
	}

	public float getZ(int render_stage, double z, TileEntityTrackBase track) {
		return (float) (z + 0.5f);
	}

	public void render(int i, TrackPiece track_type, TileEntityTrackBase track) {
		
	}
	
	public static void rotate(BlockTrackBase track, TileEntityTrackBase teTrack) {
		if (teTrack != null) {
			EnumFacing facing = (EnumFacing) teTrack.getWorld().getBlockState(teTrack.getPos()).getValue(BlockTrackBase.FACING);

			switch (facing) {
				case NORTH: GL11.glRotatef(180f, -180f, 0f, 180f); break;
				case SOUTH: GL11.glRotatef(180f, -180f, 0f, 0f);   break;
				case WEST:  GL11.glRotatef(180f, 180f, 0f, 180f);  break;
				case EAST:  GL11.glRotatef(180f, 0f, 0f, 180f);    break;
			}
		}
	}

	public void applyEffectToTrain(BlockTrackBase track, EntityTrainDefault entity) {}
}
