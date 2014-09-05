package robomuss.rc.tracks;

import java.util.ArrayList;

import net.minecraft.item.Item;
import robomuss.rc.block.model.ModelBrakes;
import robomuss.rc.block.model.ModelChain;
import robomuss.rc.block.model.ModelStation;
import robomuss.rc.block.model.ModelTires;
import robomuss.rc.rollercoaster.RollercoasterType;
import robomuss.rc.rollercoaster.RollercoasterTypeCorkscrew;
import robomuss.rc.rollercoaster.RollercoasterTypeFlumeEnclosed;
import robomuss.rc.rollercoaster.RollercoasterTypeFlumeOpen;
import robomuss.rc.tracks.extra.TrackExtra;
import robomuss.rc.tracks.extra.TrackExtraChain;

public class TrackHandler {

	public static ArrayList<TrackType> tracks = new ArrayList<TrackType>();
	public static ArrayList<TrackExtra> extras = new ArrayList<TrackExtra>();
	public static ArrayList<RollercoasterType> types = new ArrayList<RollercoasterType>();
	
	public static void registerTracks() {
		TrackHandler.tracks.add(new TrackTypeHorizontal("horizontal", 3));
		TrackHandler.tracks.add(new TrackTypeSlopeUp("slope_up", 3));
		TrackHandler.tracks.add(new TrackTypeSlope("slope", 3));
		TrackHandler.tracks.add(new TrackTypeSlopeDown("slope_down", 3, 2));
		TrackHandler.tracks.add(new TrackTypeCurve("curve", 3, 3));
		TrackHandler.tracks.add(new TrackTypeLoop("loop", 10, 2));
		TrackHandler.tracks.add(new TrackTypeHeartlineRoll("heartline_roll", 12, 19));
		
		/*RCBlocks.tracks.add(new TrackTypeHorizontal("inverted_horizontal", 3).invertTrack());
		RCBlocks.tracks.add(new TrackTypeSlopeUp("inverted_slope_up", 3).invertTrack());
		RCBlocks.tracks.add(new TrackTypeSlope("inverted_slope", 3).invertTrack());
		RCBlocks.tracks.add(new TrackTypeSlopeDown("inverted_slope_down", 3, 2).invertTrack());
		RCBlocks.tracks.add(new TrackTypeCurve("inverted_curve", 3, 3).invertTrack());
		RCBlocks.tracks.add(new TrackTypeLoop("inverted_loop", 10, 2).invertTrack());
		RCBlocks.tracks.add(new TrackTypeHeartlineRoll("inverted_heartline_roll", 12, 19).invertTrack());*/
		
		extras.add(new TrackExtra("brakes", new ModelBrakes(), TrackHandler.findTrackType("horizontal")));
		extras.add(new TrackExtraChain("chain", new ModelChain(), TrackHandler.findTrackType("horizontal"), TrackHandler.findTrackType("slope_up"), TrackHandler.findTrackType("slope"), TrackHandler.findTrackType("slope_down")));
		extras.add(new TrackExtra("tires", new ModelTires(), TrackHandler.findTrackType("horizontal")));
		extras.add(new TrackExtra("station", new ModelStation(), TrackHandler.findTrackType("horizontal")));
		
		types.add(new RollercoasterTypeCorkscrew("corkscrew"));
		types.add(new RollercoasterTypeFlumeOpen("flume_open"));
		types.add(new RollercoasterTypeFlumeEnclosed("flume_enclosed"));
	}

	public static TrackType findTrackType(Item item) {
		for(TrackType track : TrackHandler.tracks) {
			if(item.getUnlocalizedName().substring(5, item.getUnlocalizedName().length() - 6).equalsIgnoreCase(track.unlocalized_name)) {
				return track;
			}
		}
		return null;
	}
	
	public static TrackType findTrackType(String name) {
		for(TrackType track : TrackHandler.tracks) {
			if(name.equalsIgnoreCase(track.unlocalized_name)) {
				return track;
			}
		}
		return null;
	}

	public static TrackType findTrackTypeFull(String name) {
		for(TrackType track : TrackHandler.tracks) {
			if(name.equalsIgnoreCase("tile." + track.unlocalized_name + "_track")) {
				return track;
			}
		}
		return null;
	}
}
