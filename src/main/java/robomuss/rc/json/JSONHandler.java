package robomuss.rc.json;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import robomuss.rc.block.model.ModelCorkscrewCoaster;
import robomuss.rc.block.model.ModelCorkscrewCoasterCorner;
import robomuss.rc.block.model.ModelCorkscrewCoasterExtended;
import robomuss.rc.block.model.ModelCorkscrewCoasterLarge;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.style.TrackStyle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cpw.mods.fml.common.FMLLog;

public class JSONHandler {

	public static void loadTrackStyles() throws IOException {
		File dir = new File("rollercoaster/track-styles/");
		if(!dir.exists()) {
			dir.mkdirs();
		}
		else {
			for(File file : dir.listFiles()) {
				if(file.getName().contains(".json")) {
					JsonParser parser = new JsonParser();
					JsonObject obj = (JsonObject) parser.parse(new FileReader(file));
					JsonArray whitelistedPiecesArray = obj.getAsJsonArray("whitelistedPieces");
					
					String name = obj.get("name").getAsString();
					ArrayList<TrackPiece> whitelistedPieces = new ArrayList<TrackPiece>();
					
					StringBuilder sb = new StringBuilder();
					
					for(JsonElement element : whitelistedPiecesArray) {
						if(element.isJsonObject()) {
							JsonObject whitelistedPiece = element.getAsJsonObject();
							whitelistedPieces.add(TrackHandler.findTrackType(whitelistedPiece.get("piece").getAsString()));
							sb.append(whitelistedPiece.get("piece") + ", ");
						}
					}
					
					TrackStyle style = new TrackStyle(file.getName().substring(0, file.getName().lastIndexOf(".json")));
					
					style.standard = new ModelCorkscrewCoaster();
					style.large = new ModelCorkscrewCoasterLarge();
					style.extended = new ModelCorkscrewCoasterExtended();
					style.corner = new ModelCorkscrewCoasterCorner();
					
					style.whitelistedPieces = whitelistedPieces;
					
					style.riddenUsingCart = true;
					
					TrackHandler.styles.add(style);
					
					FMLLog.info("[Rollercoaster] Loaded track style: '" + name + "', Whitelisted Pieces: " + sb.toString());
				}
			}
		}
	}

}
