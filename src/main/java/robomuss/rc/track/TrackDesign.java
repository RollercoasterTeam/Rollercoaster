package robomuss.rc.track;

import java.io.Serializable;
import java.util.ArrayList;

import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.util.Point;
import net.minecraft.tileentity.TileEntity;

public class TrackDesign implements Serializable {
	
	public String name = "Unnamed Rollercoaster";
	
	public Point start;
	
	public int theme;
	
	public int trackPaint, supportPaint, fencePaint;
	
	public int extra = 1;
	
	public int direction;
	
	public ArrayList<Point> tracks = new ArrayList<Point>();
	public ArrayList<Point> stationBlocks = new ArrayList<Point>();
	public ArrayList<Point> supports = new ArrayList<Point>();
	public ArrayList<Point> gates = new ArrayList<Point>();
}
