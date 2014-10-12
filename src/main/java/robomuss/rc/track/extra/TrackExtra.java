package robomuss.rc.track.extra;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.te.TileEntityTrack;
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

	public int special_render_stages;
	
	public TrackExtra(String name, ModelBase model, Object[] recipe, int amount, TrackPiece... allowedTrackTypes) {
		this.id = RCItems.last_extra_id++;
		this.name = name;
		this.texture = new ResourceLocation("rc:textures/models/extras/" + name + ".png");
		this.model = model;
		this.recipe = recipe;
		this.amount = amount;
		this.allowedTrackTypes = new ArrayList<TrackPiece>(Arrays.asList(allowedTrackTypes));
	}
	
	public TrackExtra(String name, ModelBase model, Object[] recipe, int amount, int special_render_stages, TrackPiece... allowedTrackTypes) {
		this.id = RCItems.last_extra_id++;
		this.name = name;
		this.texture = new ResourceLocation("rc:textures/models/extras/" + name + ".png");
		this.model = model;
		this.recipe = recipe;
		this.amount = amount;
		this.special_render_stages = special_render_stages;
		this.allowedTrackTypes = new ArrayList<TrackPiece>(Arrays.asList(allowedTrackTypes));
	}

	public void render(TrackPiece track) {
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
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

	public void renderSpecial(int i, TrackPiece track_type, TileEntityTrack te) {
		
	}
	
	public static void rotate(TileEntityTrack te) {
		switch(te.direction.ordinal() - 2){
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

	public void applyEffectToTrain(TileEntityTrack te, EntityTrainDefault entity) {
		
	}
}
