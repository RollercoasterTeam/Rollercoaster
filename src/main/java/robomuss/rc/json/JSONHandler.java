package robomuss.rc.json;

import java.io.File;
import java.io.IOException;

public class JSONHandler {

	public static void loadTrackStyles() throws IOException {
		File dir = new File("rollercoaster/track-styles/");
		if(!dir.exists()) {
			dir.mkdirs();
		}
		else {
			for(File file : dir.listFiles()) {
				if(file.getName().contains(".json")) {
					System.out.println("Found JSON file: " + file.getName().substring(0, file.getName().lastIndexOf(".json")));
				}
			}
		}
	}

}
