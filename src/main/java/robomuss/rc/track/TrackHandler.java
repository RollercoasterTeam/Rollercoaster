package robomuss.rc.track;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import robomuss.rc.RCMod;
import robomuss.rc.block.model.*;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.extra.TrackExtraAirLauncher;
import robomuss.rc.track.extra.TrackExtraChain;
import robomuss.rc.track.extra.TrackExtraStation;
import robomuss.rc.track.piece.*;
import robomuss.rc.track.style.TrackStyle;

import java.util.ArrayList;

public class TrackHandler {
//	public static ArrayList<TrackPiece> pieces = new ArrayList<TrackPiece>();
//	public static ArrayList<TrackExtra> extras = new ArrayList<TrackExtra>();
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
//		pieces.add(new TrackPieceHorizontal(0, "horizontal", 3, 1, 0));
//		pieces.add(new TrackPieceCorner(1, "curve", 3, 1, 0));
//		pieces.add(new TrackPieceSlopeUp(2, "slope_up", 3, 2, 1));
//		pieces.add(new TrackPieceSlope(3, "slope", 3, 1, 1));
//		pieces.add(new TrackPieceSlopeDown(4, "slope_down", 3, 2, 3));
//		pieces.add(new TrackPieceLoop(5, "loop", 10, 1, 0));                    //TODO: change model
//		pieces.add(new TrackPieceHeartlineRoll(6, "heartline_roll", 12, 1, 0)); //TODO: change model
	}
	
	public static void addTrackExtras() {
		//String name, ModelBase model, Object[] recipe, int amount, int renderStages, TrackPiece ... allowedTypes
//		extras.add(new TrackExtra("brakes", new ModelBrakes(), null, 0, TrackHandler.findTrackType("horizontal")));
//		extras.add(new TrackExtraChain("chain", new ModelChain(), null, 0, TrackHandler.findTrackType("horizontal"), TrackHandler.findTrackType("slope_up"), TrackHandler.findTrackType("slope"), TrackHandler.findTrackType("slope_down")));
//		extras.add(new TrackExtra("tires", new ModelTires(), null, 0, TrackHandler.findTrackType("horizontal")));
//		extras.add(new TrackExtraStation("station", new ModelStation(), new Object[] {"XXX", "   ", "XXX", 'X', new ItemStack(Blocks.stained_hardened_clay, 1, 8)}, 6, TrackHandler.findTrackType("horizontal")));
//		extras.add(new TrackExtraAirLauncher("airLauncher", new ModelAirLauncher(), new Object[] {"XXX", "YYY", 'X', Items.redstone, 'Y', Items.iron_ingot}, 1, TrackHandler.findTrackType("horizontal")));
	}

	public static Types getTrackTypeFromID(int id) {
		for (Types type : Types.values()) {
			if (type.typeID == id) {
				return type;
			}
		}

		return null;
	}

	public static Types findTrackType(Item item) {
		for (Types type : Types.values()) {
			if (item.getUnlocalizedName().substring(5, item.getUnlocalizedName().length() - 6).equalsIgnoreCase(type.typeName)) {
				return type;
			}
		}

		return null;
	}
	
	public static Types findTrackType(String name) {
		for (Types type : Types.values()) {
			if (name.equalsIgnoreCase(type.typeName) || name.equalsIgnoreCase(type.name())) {
				return type;
			}
		}

		return null;
	}

	public static Types findTrackTypeFull(String name) {
		for (Types type : Types.values()) {
			if (name.equalsIgnoreCase("tile." + type.typeName + "_track")) {
				return type;
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

	public static TrackExtra findTrackExtra(int id) {
		for (Extras extra : Extras.values()) {
			if (extra.extraID == id) {
				return extra.extra;
			}
		}

		return null;
	}

	public static enum Types {
		HORIZONTAL(0, "horizontal", 3, 1, 0),
		CORNER(1, "curve", 3, 1, 0),
		SLOPE_UP(2, "slope_up", 3, 2, 1),
		SLOPE(3, "slope", 3, 1, 1),
		SLOPE_DOWN(4, "slope_down", 3, 2, 3),
		LOOP(5, "loop", 10, 1, 0),
		HEART_LINE_ROLL(6, "heartline_roll", 12, 1, 0);

		public final int        typeID;
		public final String     typeName;
		public final TrackPiece type;
		public final int        craftingCost;
		public final int        renderStages;
		public final int        numberOfDummies;

		private Types(int typeID, String typeName, int craftingCost, int renderStages, int numberOfDummies) {
			this.typeID = typeID;
			this.typeName = typeName;
			this.craftingCost = craftingCost;
			this.renderStages = renderStages;
			this.numberOfDummies = numberOfDummies;

			switch (typeID) {
				case 0:  this.type = new TrackPieceHorizontal(typeID, typeName, craftingCost, renderStages, numberOfDummies);    break;
				case 1:  this.type = new TrackPieceCorner(typeID, typeName, craftingCost, renderStages, numberOfDummies);        break;
				case 2:  this.type = new TrackPieceSlopeUp(typeID, typeName, craftingCost, renderStages, numberOfDummies);       break;
				case 3:  this.type = new TrackPieceSlope(typeID, typeName, craftingCost, renderStages, numberOfDummies);         break;
				case 4:  this.type = new TrackPieceSlopeDown(typeID, typeName, craftingCost, renderStages, numberOfDummies);     break;
				case 5:  this.type = new TrackPieceLoop(typeID, typeName, craftingCost, renderStages, numberOfDummies);          break;
				case 6:  this.type = new TrackPieceHeartlineRoll(typeID, typeName, craftingCost, renderStages, numberOfDummies); break;
				default: this.type = null;
			}
		}
	}

	public static enum Extras {
		BRAKES(0, "brakes", new ModelBrakes(), 1, 0, null, Types.HORIZONTAL),
		CHAIN(1, "chain", new ModelChain(), 1, 0, null, Types.HORIZONTAL, Types.SLOPE_UP, Types.SLOPE, Types.SLOPE_DOWN),
		TIRES(2, "tires", new ModelTires(), 1, 0, null, Types.HORIZONTAL),
		STATION(3, "station", new ModelStation(), 1, 6, new Object[] {"XXX", "   ", "XXX", 'X', new ItemStack(Blocks.stained_hardened_clay, 1, 8)}, Types.HORIZONTAL),
		AIR_LAUNCHER(4, "airLauncher", new ModelAirLauncher(), 1, 1, new Object[] {"XXX", "YYY", 'X', Items.redstone, 'Y', Items.iron_ingot}, Types.HORIZONTAL);

		public final TrackExtra       extra;
		public final int              extraID;
		public final String           extraName;
		public final ModelBase        model;
		public final ResourceLocation texture;
		public final Item             sourceItem;
		public final int              renderStages;
		public final int              amountCrafted;
		public final Object[]         craftingRecipe;
		public final Types[]          allowedTrackTypes;

		private Extras(int extraID, String extraName, ModelBase model, int renderStages, int amountCrafted, Object[] craftingRecipe, Types... allowedTrackTypes) {
			this.extraID = extraID;
			this.extraName = extraName;
			this.model = model;
			this.texture = new ResourceLocation(String.format("rc:textures/models/extras/%s.png", extraName));
			this.sourceItem = new ItemExtra(extraID).setUnlocalizedName(String.format("%s_extra", extraName)).setTextureName(String.format("rc:extras/%s", extraName)).setCreativeTab(RCMod.tools);
			this.renderStages = renderStages;
			this.amountCrafted = amountCrafted;
			this.craftingRecipe = craftingRecipe;
			this.allowedTrackTypes = allowedTrackTypes;

			switch (extraID) {
				case 0:  this.extra = new TrackExtra(extraID, extraName, new ModelBrakes(), renderStages, amountCrafted, craftingRecipe, allowedTrackTypes);                 break;
				case 1:  this.extra = new TrackExtraChain(extraID, extraName, new ModelChain(), renderStages, amountCrafted, craftingRecipe, allowedTrackTypes);             break;
				case 2:  this.extra = new TrackExtra(extraID, extraName, new ModelTires(), renderStages, amountCrafted, craftingRecipe, allowedTrackTypes);                  break;
				case 3:  this.extra = new TrackExtraStation(extraID, extraName, new ModelStation(), renderStages, amountCrafted, craftingRecipe, allowedTrackTypes);         break;
				case 4:  this.extra = new TrackExtraAirLauncher(extraID, extraName, new ModelAirLauncher(), renderStages, amountCrafted, craftingRecipe, allowedTrackTypes); break;
				default: this.extra = null;
			}
		}

		public void registerItem() {
			GameRegistry.registerItem(sourceItem, extraName + "_extra");
		}

		public void addRecipe() {
			if (craftingRecipe != null && amountCrafted > 0) {
				GameRegistry.addRecipe(new ItemStack(sourceItem, amountCrafted), craftingRecipe);
			}
		}
	}
}
