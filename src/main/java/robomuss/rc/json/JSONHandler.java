package robomuss.rc.json;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
					FMLLog.info("[Rollercoaster] Found track style: " + file.getName().substring(0, file.getName().lastIndexOf(".json")));
					
					JsonParser parser = new JsonParser();
					JsonObject obj = (JsonObject) parser.parse(new FileReader(file));
					JsonArray whitelistedPieces = obj.getAsJsonArray("whitelistedPieces");
					for(JsonElement element : whitelistedPieces) {
						if(element.isJsonObject()) {
							JsonObject whitelistedPiece = element.getAsJsonObject();
							FMLLog.info("[Rollercoaster] Whitelisted Piece: " + whitelistedPiece.get("piece"));
						}
					}
				}
			}
		}
	}

}
