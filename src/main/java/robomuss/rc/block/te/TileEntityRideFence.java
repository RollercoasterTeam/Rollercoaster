package robomuss.rc.block.te;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import robomuss.rc.track.TrackDesign;
import robomuss.rc.util.Point;


public class TileEntityRideFence extends TileEntity {
	
	public int direction;
	public int colour;
	public boolean open = false;
	public TrackDesign td = new TrackDesign();
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		direction = compound.getInteger("direction");
		colour = compound.getInteger("colour");
		open = compound.getBoolean("open");
		
		td.name = compound.getString("name");
		
		td.theme = compound.getInteger("theme");
		
		td.trackPaint = compound.getInteger("trackPaint");
		td.supportPaint = compound.getInteger("supportPaint");
		td.fencePaint = compound.getInteger("fencePaint");
		
		td.extra = compound.getInteger("extra");
		
		NBTTagList tracks = compound.getTagList("tracks", 11);
		
		for(int i = 0; i < tracks.tagCount(); i++) {
			int[] tag = tracks.func_150306_c(i);
			td.tracks.add(new Point(tag[0], tag[1], tag[2]));
		}
		
		NBTTagList supports = compound.getTagList("supports", 11);
		
		for(int i = 0; i < supports.tagCount(); i++) {
			int[] tag = supports.func_150306_c(i);
			td.supports.add(new Point(tag[0], tag[1], tag[2]));
		}
		
		NBTTagList stationBlocks = compound.getTagList("stationBlocks", 11);
		
		for(int i = 0; i < stationBlocks.tagCount(); i++) {
			int[] tag = stationBlocks.func_150306_c(i);
			td.stationBlocks.add(new Point(tag[0], tag[1], tag[2]));
		}
		
		NBTTagList gates = compound.getTagList("gates", 11);
		
		for(int i = 0; i < gates.tagCount(); i++) {
			int[] tag = gates.func_150306_c(i);
			td.gates.add(new Point(tag[0], tag[1], tag[2]));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("direction", direction);
		compound.setInteger("colour", colour);
		compound.setBoolean("open", open);
		
		compound.setString("name", td.name);
		
		compound.setInteger("theme", td.theme);
		
		compound.setInteger("trackPaint", td.trackPaint);
		compound.setInteger("supportPaint", td.supportPaint);
		compound.setInteger("fencePaint", td.fencePaint);
		
		compound.setInteger("extra", td.extra);
		
		NBTTagList tracks = new NBTTagList();
		
		for(Point point : td.tracks) {
			tracks.appendTag(new NBTTagIntArray(new int[]{point.x, point.y, point.z}));
		}
		
		NBTTagList supports = new NBTTagList();
		
		for(Point point : td.supports) {
			supports.appendTag(new NBTTagIntArray(new int[]{point.x, point.y, point.z}));
		}
		
		NBTTagList stationBlocks = new NBTTagList();
		
		for(Point point : td.stationBlocks) {
			stationBlocks.appendTag(new NBTTagIntArray(new int[]{point.x, point.y, point.z}));
		}
		
		NBTTagList gates = new NBTTagList();
		
		for(Point point : td.gates) {
			gates.appendTag(new NBTTagIntArray(new int[]{point.x, point.y, point.z}));
		}
		
		compound.setTag("tracks", tracks);
		compound.setTag("supports", supports);
		compound.setTag("stationBlocks", stationBlocks);
		compound.setTag("gates", gates);
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}
}
