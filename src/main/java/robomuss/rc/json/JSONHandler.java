package robomuss.rc.json;

<<<<<<< HEAD
import com.google.gson.*;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.util.ResourceLocation;
=======
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;

>>>>>>> origin/One8PortTake2
import org.apache.commons.io.FileUtils;

import robomuss.rc.exception.TrackStyleModelNotFoundException;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.style.TrackStyle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JSONHandler {
	//TODO: temporarily disabled until we get the rendering sorted out
//
//	private static String[] defaults = {
//		"corkscrew", "body_slide", "body_slide_tunnel", "wooden_hybrid_ibox", "wooden_hybrid_topper"
//	};
//
////	private static String test = "test";
//
//	public static void loadTrackStyles() throws IOException, TrackStyleModelNotFoundException, JsonIOException, JsonSyntaxException, InstantiationException, IllegalAccessException, ClassNotFoundException {
//		File dir = new File("rollercoaster/track-styles/"); //destination directory
//		if (!dir.exists()) {
//			dir.mkdirs();
//		}
//
//		ClassLoader loader = JSONHandler.class.getClassLoader();
//		for (String string : defaults) {
////			File folder = new File("rollercoaster/track-styles/" + string + "/");
//			File folder = new File("rollercoaster/track-styles/corkscrew/"); // makes destination folder
//			if (!folder.exists()) {
//				folder.mkdirs();
//			} else {
//				folder.delete();
//				folder.mkdirs();
//			}
//			FileUtils.copyURLToFile(loader.getResource("assets/rc/models/track-styles/corkscrew/corkscrew.json"), new File("rollercoaster/track-styles/corkscrew/corkscrew.json"));
////			FileUtils.copyURLToFile(loader.getResource("assets/rc/track-styles/" + string + "/" + string + ".json"), new File(String.format("rollercoaster/track-styles/%s/%s.json", string, string)));
//		}
//
//		loadJSONFilesFromDir(dir);
////		File dir = new File("rollercoaster/track-styles/"); //destination
////		if(!dir.exists()) {
////			dir.mkdirs();
////		}
////
////		ClassLoader loader = JSONHandler.class.getClassLoader();
////		for(String string : defaults) {
////			File folder = new File("rollercoaster/track-styles/" + string + "/"); //makes destination files
////			if(!folder.exists()) {
////				folder.mkdirs();
////			}
////			FileUtils.copyURLToFile(loader.getResource("assets/rc/trackStyles/" + string + ".json"), new File("rollercoaster/track-styles/" + string + ".json")); //copies json files
////
////			try {
////				FileUtils.copyURLToFile(loader.getResource("assets/rc/models/trackStyles/" + string + "/standard.tcn"), new File("rollercoaster/track-styles/" + string + "/standard.tcn"));
////				FileUtils.copyURLToFile(loader.getResource("assets/rc/models/trackStyles/" + string + "/large.tcn"), new File("rollercoaster/track-styles/" + string + "/large.tcn"));
////				FileUtils.copyURLToFile(loader.getResource("assets/rc/models/trackStyles/" + string + "/extended.tcn"), new File("rollercoaster/track-styles/" + string + "/extended.tcn"));
////				FileUtils.copyURLToFile(loader.getResource("assets/rc/models/trackStyles/" + string + "/corner.tcn"), new File("rollercoaster/track-styles/" + string + "/corner.tcn"));
////				FileUtils.copyURLToFile(loader.getResource("assets/rc/models/trackStyles/" + string + "/standard1.obj"), new File("rollercoaster/track-styles/" + string + "/standard1.obj"));
////				FMLLog.info("[Rollercoaster] Successfully copied track models for the '" + string + "' track style");
////			}
////			catch(IOException e) {
////				throw new TrackStyleModelNotFoundException(e.getMessage().substring(e.getMessage().lastIndexOf("trackStyles/")), e.getMessage());
////			}
////		}
////
//////		try {
//////			FileUtils.copyURLToFile(loader.getResource("assets/rc/models/trackStyles/" + test + "/standard1.obj"), new File("rollercoaster/track-styles/" + test + "/standard1.obj"));
//////		}
//////		catch (IOException e) {
//////			throw new TrackStyleModelNotFoundException(e.getMessage().substring(e.getMessage().lastIndexOf("trackStyles/")), e.getMessage());
//////		}
////
////		loadJSONFilesFromDir(dir);
//	}
//
//	private static void loadJSONFilesFromDir(File dir) throws JsonIOException, JsonSyntaxException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
//		for (File file : dir.listFiles()) {
//			for (File file1 : file.listFiles()) {
//				if (file1.getName().contains(".json")) {
//					JsonParser parser = new JsonParser();
//					JsonObject object = (JsonObject) parser.parse(new FileReader(file1));
//					JsonArray whitelistedPiecesArray = object.getAsJsonArray("Whitelisted Pieces");
//
//					String modelFormat = object.get("Model Format").getAsString();
//
//					String displayName = object.get("Name").getAsString();
//					String fileName = file.getName();
//					String styleName = fileName + ".json";
////					String modelName = fileName.substring(0, fileName.lastIndexOf(".json")) + modelFormat;
//					String modelName = fileName + modelFormat;
//
//					ArrayList<TrackPiece> whitelistedPieces = new ArrayList<TrackPiece>();
//
//					StringBuilder builder = new StringBuilder();
//
//					for (JsonElement element : whitelistedPiecesArray) {
//						if (element.isJsonObject()) {
//							JsonObject whitelistedPiece = element.getAsJsonObject();
//							whitelistedPieces.add(TrackHandler.findTrackType(whitelistedPiece.get("Piece").getAsString()));
//							builder.append(whitelistedPiece.get("Piece") + ", ");
//						}
//					}
//
//					ClassLoader loader = JSONHandler.class.getClassLoader();
//					if (!modelName.startsWith(modelFormat) && !styleName.startsWith(".json")) {
//						FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/" + styleName), new File(loader.getResource("assets/rc/models/track-styles/" + fileName + "/" + styleName).getPath()));
//						FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/" + modelName), new File(loader.getResource("assets/rc/models/track-styles/" + fileName + "/" + modelName).getPath()));
//					}
//
//					TrackStyle style = new TrackStyle(fileName);
//
//					style.localizedName = displayName;
//
//					style.model = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/track-styles/" + fileName + "/" + modelName));
//
//					style.whitelistedPieces = whitelistedPieces;
//
//					TrackHandler.styles.add(style);
//
//					FMLLog.info("[Rollercoaster] Loaded track style: '" + displayName + "', Whitelisted Pieces: " + builder.toString());
//				}
////		for(File file : dir.listFiles()) {
////			if(file.getName().contains(".json")) {
////				JsonParser parser = new JsonParser();
////				JsonObject obj = (JsonObject) parser.parse(new FileReader(file));
////				JsonArray whitelistedPiecesArray = obj.getAsJsonArray("Whitelisted Pieces");
////
////				String name = obj.get("Name").getAsString();
////
////				String modelFormat = obj.get("Model Format").getAsString();
////
////				/*String standard = obj.get("Standard Model") != null ? obj.get("Standard Model").getAsString() : "corkscrew/Standard";
////				String large = obj.get("Large Model") != null ? obj.get("Large Model").getAsString() : "corkscrew/Large";
////				String extended = obj.get("Extended Model") != null ? obj.get("Extended Model").getAsString() : "corkscrew/Extended";
////				String corner = obj.get("Corner Model") != null ? obj.get("Corner Model").getAsString() : "corkscrew/Corner";*/
////
////				ArrayList<TrackPiece> whitelistedPieces = new ArrayList<TrackPiece>();
////
////				StringBuilder sb = new StringBuilder();
////
////				for(JsonElement element : whitelistedPiecesArray) {
////					if(element.isJsonObject()) {
////						JsonObject whitelistedPiece = element.getAsJsonObject();
////						whitelistedPieces.add(TrackHandler.findTrackType(whitelistedPiece.get("Piece").getAsString()));
////						sb.append(whitelistedPiece.get("Piece") + ", ");
////					}
////				}
////
////				String fileName = file.getName().substring(0, file.getName().lastIndexOf(".json"));
////				String testFileName = file.getName().substring(0, file.getName().lastIndexOf(".json"));
////
////				//TODO: REARANGE HOW THIS IS DONE!!! IF ONE JSON HAS A DIFFERENT MODEL FORMAT, THIS TRIES COPYING MODELS THAT AREN'T OF THAT FORMAT, IE. "standard.obj"
////				ClassLoader loader = JSONHandler.class.getClassLoader();
////				if (!modelFormat.equals("obj")) {
////					FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/standard." + modelFormat), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/standard." + modelFormat).getPath()));
////					FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/large." + modelFormat), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/large." + modelFormat).getPath()));
////					FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/extended." + modelFormat), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/extended." + modelFormat).getPath()));
////					FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/corner." + modelFormat), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/corner." + modelFormat).getPath()));
////					FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/standard1.obj"), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/standard1.obj").getPath()));
////				} else if (modelFormat.equals("obj")) {
////					FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/standard.tcn"), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/standard.tcn").getPath()));
////					FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/large.tcn"), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/large.tcn").getPath()));
////					FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/extended.tcn"), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/extended.tcn").getPath()));
////					FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/corner.tcn"), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/corner.tcn").getPath()));
////					FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/standard1." + modelFormat), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/standard1." + modelFormat).getPath()));
////				}
//////				FileUtils.copyFile(new File("rollercoaster/track-styles/" + fileName + "/test." + modelFormat), new File(loader.getResource("assets/rc/models/trackStyles/" + fileName + "/test." + modelFormat).getPath()));
//////				FileUtils.copyFile(new File("rollercoaster/track-styles/" + testFileName + "/test." + modelFormat), new File(loader.getResource("assets/rc/models/trackStyles/" + testFileName + "/test." + modelFormat).getPath()));
////
////				TrackStyle style = new TrackStyle(file.getName().substring(0, file.getName().lastIndexOf(".json")));
////
////				style.localizedName = name;
////
////				//TODO: FIX THIS, TRIES LOADING MODELS IN FORMATS THAT DON'T EXIST!!!
////				if (!modelFormat.equals("obj")) {
////					style.standard = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/trackStyles/" + fileName + "/standard." + modelFormat));
////					style.large = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/trackStyles/" + fileName + "/large." + modelFormat));
////					style.extended = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/trackStyles/" + fileName + "/extended." + modelFormat));
////					style.corner = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/trackStyles/" + fileName + "/corner." + modelFormat));
////					style.standard1 = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/trackStyles/" + fileName + "/standard1.obj"));
////				} else if (modelFormat.equals("obj")) {
////					style.standard = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/trackStyles/" + fileName + "/standard.tcn"));
////					style.large = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/trackStyles/" + fileName + "/large.tcn"));
////					style.extended = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/trackStyles/" + fileName + "/extended.tcn"));
////					style.corner = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/trackStyles/" + fileName + "/corner.tcn"));
////					style.standard1 = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/trackStyles/" + fileName + "/standard1." + modelFormat));
////				}
////
////				style.whitelistedPieces = whitelistedPieces;
////
////				style.riddenUsingCart = true;
////
////				TrackHandler.styles.add(style);
////
////				FMLLog.info("[Rollercoaster] Loaded track style: '" + name + "', Whitelisted Pieces: " + sb.toString());
//			}
//		}
//	}
}
