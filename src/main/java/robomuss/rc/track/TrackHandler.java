package robomuss.rc.track;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import robomuss.rc.block.model.ModelAirLauncher;
import robomuss.rc.block.model.ModelBrakes;
import robomuss.rc.block.model.ModelChain;
import robomuss.rc.block.model.ModelStation;
import robomuss.rc.block.model.ModelTires;
import robomuss.rc.rollercoaster.RollercoasterType;
import robomuss.rc.rollercoaster.RollercoasterTypeCorkscrew;
import robomuss.rc.rollercoaster.RollercoasterTypeFlumeEnclosed;
import robomuss.rc.rollercoaster.RollercoasterTypeFlumeOpen;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.extra.TrackExtraAirLauncher;
import robomuss.rc.track.extra.TrackExtraChain;
import robomuss.rc.track.extra.TrackExtraStation;

public class TrackHandler {

	public static ArrayList<TrackType> tracks = new ArrayList<TrackType>();
	public static ArrayList<TrackExtra> extras = new ArrayList<TrackExtra>();
	public static ArrayList<RollercoasterType> types = new ArrayList<RollercoasterType>();
	
	public static ArrayList<TrackDesign> designs = new ArrayList<TrackDesign>();
	
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
		
		extras.add(new TrackExtra("brakes", new ModelBrakes(), null, 0, TrackHandler.findTrackType("horizontal")));
		extras.add(new TrackExtraChain("chain", new ModelChain(), null, 0, TrackHandler.findTrackType("horizontal"), TrackHandler.findTrackType("slope_up"), TrackHandler.findTrackType("slope"), TrackHandler.findTrackType("slope_down")));
		extras.add(new TrackExtra("tires", new ModelTires(), null, 0, TrackHandler.findTrackType("horizontal")));
		extras.add(new TrackExtraStation("station", new ModelStation(), new Object[] {
			"XXX", "   ", "XXX", 'X', new ItemStack(Blocks.stained_hardened_clay, 1, 8)
		}, 6, TrackHandler.findTrackType("horizontal")));
		extras.add(new TrackExtraAirLauncher("airLauncher", new ModelAirLauncher(), new Object[] {
			"XXX", "YYY", 'X', Items.redstone, 'Y', Items.iron_ingot
		}, 1, TrackHandler.findTrackType("horizontal")));
		
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
