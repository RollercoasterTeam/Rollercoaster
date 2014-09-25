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
			
		}
	}

}
