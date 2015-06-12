package robomuss.rc.track;

import robomuss.rc.util.EnumStationType;

public class Theme {
	
	public String name;
	public int trackPaint, supportPaint, fencePaint;
	public EnumStationType stationType;
	
	public Theme(String name, int trackPaint, int supportPaint, int fencePaint, EnumStationType stationType) {
		this.name = name;
		
		this.trackPaint = trackPaint;
		this.supportPaint = supportPaint;
		this.fencePaint = fencePaint;
		
		this.stationType = stationType;
	}
}
