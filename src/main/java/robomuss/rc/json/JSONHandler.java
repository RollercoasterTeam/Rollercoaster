package robomuss.rc.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraftforge.client.model.AdvancedModelLoader;

import org.apache.commons.io.FileUtils;

import robomuss.rc.block.model.ModelCorkscrewCoaster;
import robomuss.rc.block.model.ModelCorkscrewCoasterCorner;
import robomuss.rc.block.model.ModelCorkscrewCoasterExtended;
import robomuss.rc.block.model.ModelCorkscrewCoasterLarge;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.style.TrackStyle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import cpw.mods.fml.common.FMLLog;

public class JSONHandler {

	private static String[] defaults = {
		"corkscrew", "body_slide", "body_slide_tunnel"	
	};
	
	public static void loadTrackStyles() throws IOException {
		File dir = new File("rollercoaster/track-styles/");
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		ClassLoader loader = JSONHandler.class.getClassLoader();
		for(String string : defaults) {
			FileUtils.copyURLToFile(loader.getResource("assets/rc/trackStyles/" + string + ".json"), new File("rollercoaster/track-styles/" + string + ".json"));
		}
		
		loadJSONFilesFromDir(dir);
	}

	private static void loadJSONFilesFromDir(File dir) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		for(File file : dir.listFiles()) {
			if(file.getName().contains(".json")) {
				JsonParser parser = new JsonParser();
				JsonObject obj = (JsonObject) parser.parse(new FileReader(file));
				JsonArray whitelistedPiecesArray = obj.getAsJsonArray("Whitelisted Pieces");
				
				String name = obj.get("Name").getAsString();
				
				/*String standard = obj.get("Standard Model").getAsString();
				String large = obj.get("Large Model").getAsString();
				String extended = obj.get("Extended Model").getAsString();
				String corner = obj.get("Corner Model").getAsString();*/
				
				ArrayList<TrackPiece> whitelistedPieces = new ArrayList<TrackPiece>();
				
				StringBuilder sb = new StringBuilder();
				
				for(JsonElement element : whitelistedPiecesArray) {
					if(element.isJsonObject()) {
						JsonObject whitelistedPiece = element.getAsJsonObject();
						whitelistedPieces.add(TrackHandler.findTrackType(whitelistedPiece.get("Piece").getAsString()));
						sb.append(whitelistedPiece.get("Piece") + ", ");
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
