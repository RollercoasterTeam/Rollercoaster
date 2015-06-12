package robomuss.rc.util;

import java.io.Serializable;

public class Point implements Serializable {
	
	public int x, y, z;
	
	public Point(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
