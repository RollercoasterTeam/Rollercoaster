package robomuss.rc.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;

import org.apache.commons.io.FileUtils;

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
			File folder = new File("rollercoaster/track-styles/" + string + "/");
			if(!folder.exists()) {
				folder.mkdirs();
			}
			FileUtils.copyURLToFile(loader.getResource("assets/rc/trackStyles/" + string + ".json"), new File("rollercoaster/track-styles/" + string + ".json"));
			
			/*FileUtils.copyURLToFile(loader.getResource("assets/rc/models/track-styles/" + string + "/standard.tcn"), new File("rollercoaster/track-styles/" + string + "/standard.tcn"));
			FileUtils.copyURLToFile(loader.getResource("assets/rc/models/track-styles/" + string + "/large.tcn"), new File("rollercoaster/track-styles/" + string + "/large.tcn"));
			FileUtils.copyURLToFile(loader.getResource("assets/rc/models/track-styles/" + string + "/extended.tcn"), new File("rollercoaster/track-styles/" + string + "/extended.tcn"));
			FileUtils.copyURLToFile(loader.getResource("assets/rc/models/track-styles/" + string + "/corner.tcn"), new File("rollercoaster/track-styles/" + string + "/corner.tcn"));*/
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
			
				ResourceLocation loc = new ResourceLocation("standard.tcn") {
					@Override
					public String getResourcePath() {
						return "rollercoaster/track-styles/corkscrew/standard.tcn";
					}
					
					@Override
					public String getResourceDomain() {
						return "";
					}
				};
				//System.out.println(loc.getResourcePath() + loc.getResourceDomain());
				style.standard = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/track-styles/corkscrew/standard.tcn"));
				style.large = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/track-styles/corkscrew/large.tcn"));
				style.extended = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/track-styles/corkscrew/extended.tcn"));
				style.corner = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/track-styles/corkscrew/corner.tcn"));
				
				style.whitelistedPieces = whitelistedPieces;
				
				style.riddenUsingCart = true;
				
				TrackHandler.styles.add(style);
				
				FMLLog.info("[Rollercoaster] Loaded track style: '" + name + "', Whitelisted Pieces: " + sb.toString());
			}
		}
	}
}
