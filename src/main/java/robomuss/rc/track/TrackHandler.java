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
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.extra.TrackExtraAirLauncher;
import robomuss.rc.track.extra.TrackExtraChain;
import robomuss.rc.track.extra.TrackExtraStation;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.piece.TrackPieceCorner;
import robomuss.rc.track.piece.TrackPieceHeartlineRoll;
import robomuss.rc.track.piece.TrackPieceHorizontal;
import robomuss.rc.track.piece.TrackPieceLoop;
import robomuss.rc.track.piece.TrackPieceSlope;
import robomuss.rc.track.piece.TrackPieceSlopeDown;
import robomuss.rc.track.piece.TrackPieceSlopeUp;
import robomuss.rc.track.style.TrackStyle;

public class TrackHandler {

	public static ArrayList<TrackPiece> pieces = new ArrayList<TrackPiece>();
	public static ArrayList<TrackExtra> extras = new ArrayList<TrackExtra>();
	public static ArrayList<TrackStyle> styles = new ArrayList<TrackStyle>();
	
	public static void registerTracks() {
		addTrackPieces();
		addTrackExtras();
		
		/*RCBlocks.tracks.add(new TrackTypeHorizontal("inverted_horizontal", 3).invertTrack());
		RCBlocks.tracks.add(new TrackTypeSlopeUp("inverted_slope_up", 3).invertTrack());
		RCBlocks.tracks.add(new TrackTypeSlope("inverted_slope", 3).invertTrack());
		RCBlocks.tracks.add(new TrackTypeSlopeDown("inverted_slope_down", 3, 2).invertTrack());
		RCBlocks.tracks.add(new TrackTypeCurve("inverted_curve", 3, 3).invertTrack());
		RCBlocks.tracks.add(new TrackTypeLoop("inverted_loop", 10, 2).invertTrack());
		RCBlocks.tracks.add(new TrackTypeHeartlineRoll("inverted_heartline_roll", 12, 19).invertTrack());*/
	}
	
	public static void addTrackPieces() {
		TrackHandler.pieces.add(new TrackPieceHorizontal("horizontal", 3));
		TrackHandler.pieces.add(new TrackPieceSlopeUp("slope_up", 3, 2));
		TrackHandler.pieces.add(new TrackPieceSlope("slope", 3));
		TrackHandler.pieces.add(new TrackPieceSlopeDown("slope_down", 3, 2));
		TrackHandler.pieces.add(new TrackPieceCorner("curve", 3));
		TrackHandler.pieces.add(new TrackPieceLoop("loop", 10, 2));
		TrackHandler.pieces.add(new TrackPieceHeartlineRoll("heartline_roll", 12, 19));
	}
	
	public static void addTrackExtras() {
		extras.add(new TrackExtra("brakes", new ModelBrakes(), null, 0, TrackHandler.findTrackType("horizontal")));
		extras.add(new TrackExtraChain("chain", new ModelChain(), null, 0, TrackHandler.findTrackType("horizontal"), TrackHandler.findTrackType("slope_up"), TrackHandler.findTrackType("slope"), TrackHandler.findTrackType("slope_down")));
		extras.add(new TrackExtra("tires", new ModelTires(), null, 0, TrackHandler.findTrackType("horizontal")));
		extras.add(new TrackExtraStation("station", new ModelStation(), new Object[] {
			"XXX", "   ", "XXX", 'X', new ItemStack(Blocks.stained_hardened_clay, 1, 8)
		}, 6, TrackHandler.findTrackType("horizontal")));
		extras.add(new TrackExtraAirLauncher("airLauncher", new ModelAirLauncher(), new Object[] {
			"XXX", "YYY", 'X', Items.redstone, 'Y', Items.iron_ingot
		}, 1, TrackHandler.findTrackType("horizontal")));
	}

	public static TrackPiece findTrackType(Item item) {
		for(TrackPiece track : TrackHandler.pieces) {
			if(item.getUnlocalizedName().substring(5, item.getUnlocalizedName().length() - 6).equalsIgnoreCase(track.unlocalized_name)) {
				return track;
			}
		}
		return null;
	}
	
	public static TrackPiece findTrackType(String name) {
		for(TrackPiece track : TrackHandler.pieces) {
			if(name.equalsIgnoreCase(track.unlocalized_name)) {
				return track;
			}
		}
		return null;
	}

	public static TrackPiece findTrackTypeFull(String name) {
		for(TrackPiece track : TrackHandler.pieces) {
			if(name.equalsIgnoreCase("tile." + track.unlocalized_name + "_track")) {
				return track;
			}
		}
		return null;
	}

	public static TrackStyle findTrackStyle(String string) {
		for(TrackStyle style : styles) {
			if(style.name.contains(string)) {
				return style;
			}
		}
		return null;
	}
}
