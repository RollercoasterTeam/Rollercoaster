package robomuss.rc.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.ArrayList;

import cpw.mods.fml.common.network.ByteBufUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityCatwalk;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityWoodenSupport;
import robomuss.rc.network.AbstractPacket;
import robomuss.rc.track.Theme;
import robomuss.rc.track.TrackDesign;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.util.ColourUtil;
import robomuss.rc.util.EnumStationType;
import robomuss.rc.util.Point;

public class PacketTrackDesigner extends AbstractPacket {

    public PacketTrackDesigner() {
    	
    }
    
    public static enum types {
    	DEBUG,
    	LOAD,
    	DELETE_RIDE,
    	CLEAR_TD,
    	CHANGE_NAME,
    	CHANGE_THEME,
    	CHANGE_TRACK_PAINT,
    	CHANGE_SUPPORT_PAINT,
    	CHANGE_FENCE_PAINT,
    	CHANGE_EXTRA,
    	PLACE,
    	LIFT,
    	ROTATE,
    	CONFIRM,
    	STRAIGHT,
    	CURVED,
    	SLOPE_UP_1,
    	SLOPE_UP_2,
    	SLOPE_1,
    	SLOPE_2,
    	SLOPE_DOWN_1,
    	SLOPE_DOWN_2,
    	UPDATE_GATES;
    }
    
    private PacketTrackDesigner.types type;
    private int teX, teY, teZ;
    private static int rayX, rayY, rayZ;
    private int flag;
    private String name;
    
    private static int supportCount, fenceCount, heightDirection; //0 is flat, 1 is up, 2 is down
    
    public static final Theme[] themes = {new Theme("Generic", 0, 0, 0, EnumStationType.WOODEN), new Theme("Hell", 23, 22, 22, EnumStationType.NETHER), new Theme("Sci-Fi", 8, 7, 0, EnumStationType.IRON), new Theme("Forest", 24, 25, 24, EnumStationType.LOG), new Theme("Temple", 4, 12, 4, EnumStationType.SAND)};
    public static final String[] extras = {"Standard Track", "Station", "Brake Run", "Lift Hill", "Friction Wheels"};
    private static TrackDesign td = new TrackDesign();

    public PacketTrackDesigner(int type, int teX, int teY, int teZ, int flag) {
        this.type = types.values()[type];
        
        this.teX = teX;
        this.teY = teY;
        this.teZ = teZ;
        
        this.flag = flag;
    }
    
    public PacketTrackDesigner(int type, int teX, int teY, int teZ, int flag, String name) {
        this.type = types.values()[type];
        
        this.teX = teX;
        this.teY = teY;
        this.teZ = teZ;
        
        this.flag = flag;
        
        //this.name = name;
    }
    
    public PacketTrackDesigner(int type, int teX, int teY, int teZ, int rayX, int rayY, int rayZ, int flag) {
        this.type = types.values()[type];
        
        this.teX = teX;
        this.teY = teY;
        this.teZ = teZ;
        
        this.rayX = rayX;
        this.rayY = rayY;
        this.rayZ = rayZ;
        
        this.flag = flag;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    	buffer.writeInt(this.type.ordinal());

        buffer.writeInt(this.teX);
        buffer.writeInt(this.teY);
        buffer.writeInt(this.teZ);
    	
        buffer.writeInt(this.rayX);
        buffer.writeInt(this.rayY);
        buffer.writeInt(this.rayZ);
        
        buffer.writeInt(this.flag);
        
        //ByteBufUtils.writeUTF8String(buffer, name);
        
        /*PacketBuffer pb = new PacketBuffer(buffer);
        try {
			pb.writeStringToBuffer(this.name);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {  
    	this.type = types.values()[buffer.readInt()];
    	
    	this.teX = buffer.readInt();
        this.teY = buffer.readInt();
        this.teZ = buffer.readInt();
        
        this.rayX = buffer.readInt();
        this.rayY = buffer.readInt();
        this.rayZ = buffer.readInt();
        
        this.flag = buffer.readInt();
        
        //this.name = ByteBufUtils.readUTF8String(buffer);
        
        /*PacketBuffer pb = new PacketBuffer(buffer);
        try {
			pb.readStringFromBuffer(100);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }
    
    public void addBlock(ArrayList<Point> list, EntityPlayer player, int blockX, int blockY, int blockZ, Block block) {
    	System.out.println("Adding block");
    	player.worldObj.setBlock(blockX, blockY, blockZ, block);
    	if(list != null) {
    		list.add(new Point(blockX, blockY, blockZ));
    	}
    	
    	player.worldObj.markBlockForUpdate(blockX, blockY, blockZ);
    }
    
    public void removeBlock(ArrayList<Point> list, EntityPlayer player, int blockX, int blockY, int blockZ) {
    	player.worldObj.setBlockToAir(blockX, blockY, blockZ);
    	
    	Point remove = null;
    	
    	for(Point point : list) {
    		if(point.x == blockX && point.y == blockY && point.z == blockZ) {
    			remove = point;
    			break;
    		}
    	}
    	
    	list.remove(remove);
    }
    
    public void removeBlockAlt(ArrayList<Point> list, EntityPlayer player, int blockX, int blockY, int blockZ) {
    	player.worldObj.setBlockToAir(blockX, blockY, blockZ);
    	System.out.println(blockX + ", " + blockY + ", " + blockZ);
    }
    
    public void placeStation(EntityPlayer player, TileEntityTrack tet) {
    	tet.extra = TrackHandler.extras.get(3);
		Block block = EnumStationType.values()[td.theme].block;
		Block fence = EnumStationType.values()[td.theme].fence;
		
		if(td.direction == 0 || td.direction == 2) {
			addBlock(td.stationBlocks, player, rayX - 3, rayY, rayZ, block);
			addBlock(td.stationBlocks, player, rayX - 2, rayY, rayZ, block);
			addBlock(td.stationBlocks, player, rayX - 1, rayY, rayZ, block);
			addBlock(td.stationBlocks, player, rayX + 1, rayY, rayZ, block);
			addBlock(td.stationBlocks, player, rayX + 2, rayY, rayZ, block);
			addBlock(td.stationBlocks, player, rayX + 3, rayY, rayZ, block);
			
			addBlock(td.stationBlocks, player, rayX - 3, rayY + 4, rayZ, block);
			addBlock(td.stationBlocks, player, rayX - 2, rayY + 4, rayZ, block);
			addBlock(td.stationBlocks, player, rayX - 1, rayY + 4, rayZ, block);
			addBlock(td.stationBlocks, player, rayX, rayY + 4, rayZ, block);
			addBlock(td.stationBlocks, player, rayX + 1, rayY + 4, rayZ, block);
			addBlock(td.stationBlocks, player, rayX + 2, rayY + 4, rayZ, block);
			addBlock(td.stationBlocks, player, rayX + 3, rayY + 4, rayZ, block);
			
			addBlock(td.stationBlocks, player, rayX - 3, rayY + 1, rayZ, fence);
			addBlock(td.stationBlocks, player, rayX + 3, rayY + 1, rayZ, fence);
			
			if(fenceCount == 0) {
				addBlock(td.stationBlocks, player, rayX - 2, rayY + 1, rayZ, RCBlocks.ride_fence);
				addBlock(null, player, rayX + 2, rayY + 1, rayZ, RCBlocks.ride_fence_panel);
			}
			
			addBlock(td.stationBlocks, player, rayX - 1, rayY + 1, rayZ, fenceCount == 0 ? RCBlocks.ride_fence_corner : RCBlocks.ride_fence);
			
			if(fenceCount % 2 == 0) {
				addBlock(td.stationBlocks, player, rayX + 1, rayY + 1, rayZ, fenceCount == 0 ? RCBlocks.ride_fence_corner : RCBlocks.ride_fence_triangle);
			}
			else {
				addBlock(td.gates, player, rayX + 1, rayY + 1, rayZ, RCBlocks.ride_fence_gate);
			}
			
			TileEntityRideFence terf = new TileEntityRideFence();
			if(fenceCount == 0) {
				terf.xCoord = rayX - 2;
				terf.yCoord = rayY + 1;
				terf.zCoord = rayZ;
				terf.colour = td.fencePaint;
				terf.direction = fenceCount == 0 ? 1 : 0;
			}
			
			TileEntityRideFence terf2 = new TileEntityRideFence();
			terf2.xCoord = rayX - 1;
			terf2.yCoord = rayY + 1;
			terf2.zCoord = rayZ;
			terf2.colour = td.fencePaint;
			terf2.direction = fenceCount == 0 ? td.direction == 0 ? 1 : 2 : 0;
			
			TileEntityRideFence terf3 = new TileEntityRideFence();
			terf3.xCoord = rayX + 1;
			terf3.yCoord = rayY + 1;
			terf3.zCoord = rayZ;
			terf3.colour = td.fencePaint;
			terf3.direction = (fenceCount % 2 == 0 ? fenceCount == 0 ? td.direction == 0 ? 0 : 3 : 3 : 0);
			
			TileEntityRideFence terf4 = new TileEntityRideFence();
			if(fenceCount == 0) {
				terf4.xCoord = rayX + 2;
				terf4.yCoord = rayY + 1;
				terf4.zCoord = rayZ;
				terf4.colour = td.fencePaint;
				terf4.direction = fenceCount == 0 ? td.direction == 0 ? 1 : 3 : 0;
				terf4.td = td;
			}
			
			if(fenceCount == 0) {
				player.worldObj.setTileEntity(rayX - 2, rayY + 1, rayZ, terf);
				player.worldObj.setTileEntity(rayX + 2, rayY + 1, rayZ, terf4);
			}
			player.worldObj.setTileEntity(rayX - 1, rayY + 1, rayZ, terf2);
			player.worldObj.setTileEntity(rayX + 1, rayY + 1, rayZ, terf3);
			
			if(fenceCount > 1) {
    			removeBlock(td.stationBlocks, player, rayX - 3, rayY + 2, rayZ + (td.direction == 2 ? 1 : - 1));
    			removeBlock(td.stationBlocks, player, rayX - 3, rayY + 3, rayZ + (td.direction == 2 ? 1 : - 1));
    			removeBlock(td.stationBlocks, player, rayX + 3, rayY + 2, rayZ + (td.direction == 2 ? 1 : - 1));
    			removeBlock(td.stationBlocks, player, rayX + 3, rayY + 3, rayZ + (td.direction == 2 ? 1 : - 1));
			}
			
			addBlock(td.stationBlocks, player, rayX - 3, rayY + 2, rayZ, fence);
			addBlock(td.stationBlocks, player, rayX - 3, rayY + 3, rayZ, fence);
			addBlock(td.stationBlocks, player, rayX + 3, rayY + 2, rayZ, fence);
			addBlock(td.stationBlocks, player, rayX + 3, rayY + 3, rayZ, fence);
			
			int stationSupportCount1 = fenceCount % 2, stationSupportCount2 = fenceCount % 2;
			
			for(int y = rayY + flag - 1; y > 0; y--) {
    			if(player.worldObj.isAirBlock(rayX + 3, y, rayZ)) {
    				addBlock(td.supports, player, rayX + 3, y, rayZ, EnumStationType.values()[td.theme].support);
    				
    				if(EnumStationType.values()[td.theme].support == RCBlocks.woodenSupport) {
	    				TileEntityWoodenSupport tes = new TileEntityWoodenSupport();
	    				tes.xCoord = rayX + 3;
	    				tes.yCoord = y;
	    				tes.zCoord = rayZ;
	    				tes.direction = stationSupportCount1 % 2 == 0 ? 0 : 2;
	    				
	    				player.worldObj.setTileEntity(rayX + 3, y, rayZ, tes);
    				}
    				stationSupportCount1++;
				}
    			else {
    				break;
    			}
			}

			for(int y = rayY + flag - 1; y > 0; y--) {
				if(player.worldObj.isAirBlock(rayX - 3, y, rayZ)) {
    				addBlock(td.supports, player, rayX - 3, y, rayZ, EnumStationType.values()[td.theme].support);
    				
    				if(EnumStationType.values()[td.theme].support == RCBlocks.woodenSupport) {
        				TileEntityWoodenSupport tes = new TileEntityWoodenSupport();
        				tes.xCoord = rayX - 3;
        				tes.yCoord = y;
        				tes.zCoord = rayZ;
        				tes.direction = stationSupportCount2 % 2 == 0 ? 0 : 2;
        				
        				player.worldObj.setTileEntity(rayX - 3, y, rayZ, tes);
    				}
    				stationSupportCount2++;
				}
				else {
					break;
				}
			}
			fenceCount++;
		}
		else if(td.direction == 1 || td.direction == 3) {
			addBlock(td.stationBlocks, player, rayX, rayY, rayZ - 3, block);
			addBlock(td.stationBlocks, player, rayX, rayY, rayZ - 2, block);
			addBlock(td.stationBlocks, player, rayX, rayY, rayZ - 1, block);
			addBlock(td.stationBlocks, player, rayX, rayY, rayZ + 1, block);
			addBlock(td.stationBlocks, player, rayX, rayY, rayZ + 2, block);
			addBlock(td.stationBlocks, player, rayX, rayY, rayZ + 3, block);
			
			addBlock(td.stationBlocks, player, rayX, rayY + 4, rayZ - 3, block);
			addBlock(td.stationBlocks, player, rayX, rayY + 4, rayZ - 2, block);
			addBlock(td.stationBlocks, player, rayX, rayY + 4, rayZ - 1, block);
			addBlock(td.stationBlocks, player, rayX, rayY + 4, rayZ, block);
			addBlock(td.stationBlocks, player, rayX, rayY + 4, rayZ + 1, block);
			addBlock(td.stationBlocks, player, rayX, rayY + 4, rayZ + 2, block);
			addBlock(td.stationBlocks, player, rayX, rayY + 4, rayZ + 3, block);
			
			addBlock(td.stationBlocks, player, rayX, rayY + 1, rayZ - 3, fence);
			addBlock(td.stationBlocks, player, rayX, rayY + 1, rayZ + 3, fence);
			
			if(fenceCount == 0) {
				addBlock(td.stationBlocks, player, rayX, rayY + 1, rayZ - 2, RCBlocks.ride_fence);
				addBlock(null, player, rayX, rayY + 1, rayZ + 2, RCBlocks.ride_fence_panel);
			}
			
			addBlock(td.stationBlocks, player, rayX, rayY + 1, rayZ - 1, fenceCount == 0 ? RCBlocks.ride_fence_corner : RCBlocks.ride_fence);
			
			if(fenceCount % 2 == 0) {
				addBlock(td.stationBlocks, player, rayX, rayY + 1, rayZ + 1, fenceCount == 0 ? RCBlocks.ride_fence_corner : RCBlocks.ride_fence_triangle);
			}
			else {
				addBlock(td.gates, player, rayX, rayY + 1, rayZ + 1, RCBlocks.ride_fence_gate);
			}
			
			TileEntityRideFence terf = new TileEntityRideFence();
			if(fenceCount == 0) {
				terf.xCoord = rayX;
				terf.yCoord = rayY + 1;
				terf.zCoord = rayZ - 2;
				terf.colour = td.fencePaint;
				terf.direction = fenceCount == 0 ? 0 : 1;
			}
			
			TileEntityRideFence terf2 = new TileEntityRideFence();
			terf2.xCoord = rayX;
			terf2.yCoord = rayY + 1;
			terf2.zCoord = rayZ - 1;
			terf2.colour = td.fencePaint;
			terf2.direction = fenceCount == 0 ? td.direction == 1 ? 2 : 3 : 1;
			
			TileEntityRideFence terf3 = new TileEntityRideFence();
			terf3.xCoord = rayX;
			terf3.yCoord = rayY + 1;
			terf3.zCoord = rayZ + 1;
			terf3.colour = td.fencePaint;
			terf3.direction = (fenceCount % 2 == 0 ? fenceCount == 0 ? td.direction == 1 ? 1 : 0 : 0 : 1);
			
			TileEntityRideFence terf4 = new TileEntityRideFence();
			if(fenceCount == 0) {
				terf4.xCoord = rayX;
				terf4.yCoord = rayY + 1;
				terf4.zCoord = rayZ + 2;
				terf4.colour = td.fencePaint;
				terf4.direction = fenceCount == 0 ? td.direction == 1 ? 2 : 0 : 0;
				terf4.td = td;
			}
			
			if(fenceCount == 0) {
				player.worldObj.setTileEntity(rayX, rayY + 1, rayZ - 2, terf);
				player.worldObj.setTileEntity(rayX, rayY + 1, rayZ + 2, terf4);
			}
			player.worldObj.setTileEntity(rayX, rayY + 1, rayZ - 1, terf2);
			player.worldObj.setTileEntity(rayX, rayY + 1, rayZ + 1, terf3);
			
			if(fenceCount > 1) {
    			removeBlock(td.stationBlocks, player, rayX + (td.direction == 1 ? 1 : - 1), rayY + 2, rayZ - 3);
    			removeBlock(td.stationBlocks, player, rayX + (td.direction == 1 ? 1 : - 1), rayY + 3, rayZ - 3);
    			removeBlock(td.stationBlocks, player, rayX + (td.direction == 1 ? 1 : - 1), rayY + 2, rayZ + 3);
    			removeBlock(td.stationBlocks, player, rayX + (td.direction == 1 ? 1 : - 1), rayY + 3, rayZ + 3);
			}
			
			addBlock(td.stationBlocks, player, rayX, rayY + 2, rayZ - 3, fence);
			addBlock(td.stationBlocks, player, rayX, rayY + 3, rayZ - 3, fence);
			addBlock(td.stationBlocks, player, rayX, rayY + 2, rayZ + 3, fence);
			addBlock(td.stationBlocks, player, rayX, rayY + 3, rayZ + 3, fence);
			
			int stationSupportCount1 = fenceCount % 2, stationSupportCount2 = fenceCount % 2;
			
			for(int y = rayY + flag - 1; y > 0; y--) {
    			if(player.worldObj.isAirBlock(rayX, y, rayZ + 3)) {
    				addBlock(td.supports, player, rayX, y, rayZ + 3, EnumStationType.values()[td.theme].support);
    				
    				if(EnumStationType.values()[td.theme].support == RCBlocks.woodenSupport) {
	    				TileEntityWoodenSupport tes = new TileEntityWoodenSupport();
	    				tes.xCoord = rayX;
	    				tes.yCoord = y;
	    				tes.zCoord = rayZ + 3;
	    				tes.direction = stationSupportCount1 % 2 == 0 ? 0 : 2;
	    				
	    				player.worldObj.setTileEntity(rayX, y, rayZ + 3, tes);
    				}
    				stationSupportCount1++;
				}
    			else {
    				break;
    			}
			}

			for(int y = rayY + flag - 1; y > 0; y--) {
				if(player.worldObj.isAirBlock(rayX, y, rayZ - 3)) {
    				addBlock(td.supports, player, rayX, y, rayZ - 3, EnumStationType.values()[td.theme].support);
    				
    				if(EnumStationType.values()[td.theme].support == RCBlocks.woodenSupport) {
        				TileEntityWoodenSupport tes = new TileEntityWoodenSupport();
        				tes.xCoord = rayX;
        				tes.yCoord = y;
        				tes.zCoord = rayZ - 3;
        				tes.direction = stationSupportCount2 % 2 == 0 ? 0 : 2;
        				
        				player.worldObj.setTileEntity(rayX, y, rayZ - 3, tes);
    				}
    				stationSupportCount2++;
				}
				else {
					break;
				}
			}
			fenceCount++;
		}
    }
    
    public void placeCatwalk(EntityPlayer player, TileEntityTrack tet) {
    	if(tet.direction == 0 || tet.direction == 2) {
    		addBlock(td.stationBlocks, player, rayX - 1, rayY, rayZ, RCBlocks.catwalk);
    		addBlock(td.stationBlocks, player, rayX + 1, rayY, rayZ, RCBlocks.catwalk);
    		
    		TileEntityCatwalk ter = (TileEntityCatwalk) player.worldObj.getTileEntity(rayX - 1, rayY, rayZ);
    		ter.colour = td.fencePaint;
    		player.worldObj.setTileEntity(rayX - 1, rayY, rayZ, ter);
    		
    		TileEntityCatwalk ter2 = (TileEntityCatwalk) player.worldObj.getTileEntity(rayX + 1, rayY, rayZ);
    		ter2.colour = td.fencePaint;
    		ter2.direction = 2;
    		player.worldObj.setTileEntity(rayX + 1, rayY, rayZ, ter2);
    		
    		tet.hasCatwalk = true;
    		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    	}
    	else if(tet.direction == 1 || tet.direction == 3) {
    		addBlock(td.stationBlocks, player, rayX, rayY, rayZ - 1, RCBlocks.catwalk);
    		addBlock(td.stationBlocks, player, rayX, rayY, rayZ + 1, RCBlocks.catwalk);
    		
    		TileEntityCatwalk ter = (TileEntityCatwalk) player.worldObj.getTileEntity(rayX, rayY, rayZ - 1);
    		ter.colour = td.fencePaint;
    		player.worldObj.setTileEntity(rayX, rayY, rayZ - 1, ter);
    		
    		TileEntityCatwalk ter2 = (TileEntityCatwalk) player.worldObj.getTileEntity(rayX, rayY, rayZ + 1);
    		ter2.colour = td.fencePaint;
    		ter2.direction = 1;
    		player.worldObj.setTileEntity(rayX, rayY, rayZ + 1, ter2);
    		
    		tet.hasCatwalk = true;
    		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    	}
    }
    
    public void placeStartPoint(EntityPlayer player, boolean placeStation) {
    	System.out.println("Flag: " + flag);
    	if(flag != 0) {
			removeBlock(td.tracks, player, rayX, rayY, rayZ);
		}
		
		addBlock(td.tracks, player, rayX, rayY + flag, rayZ, TrackHandler.tracks.get(0).block);
		TileEntityTrack tet = new TileEntityTrack();
		tet.xCoord = rayX;
		tet.yCoord = rayY;
		tet.zCoord = rayZ;
		
		tet.direction = td.direction;
		tet.colour = td.trackPaint;
		
		tet.extra = TrackHandler.extras.get(3);
		
		player.worldObj.setTileEntity(rayX, rayY + flag, rayZ, tet);
		
		if(td.extra == 1 && placeStation) {
			for(Point point : td.stationBlocks) {
				removeBlockAlt(td.stationBlocks, player, point.x, point.y, point.z);
			}
			td.stationBlocks.clear();
			
			placeStation(player, tet);
		}
		
		supportCount = 0;
		fenceCount = 0;
    }

    public void placeStraightTrack(EntityPlayer player) {
    	addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(0).block);
		TileEntityTrack tet = new TileEntityTrack();
		tet.xCoord = rayX;
		tet.yCoord = rayY;
		tet.zCoord = rayZ;
		
		tet.direction = td.direction;
		tet.colour = td.trackPaint;
		
		if(td.extra == 1) {
			placeStation(player, tet);
		}
		else if(td.extra == 2) {
			placeCatwalk(player, tet);
			tet.extra = TrackHandler.extras.get(0);
		}
		else if(td.extra == 3) {
			placeCatwalk(player, tet);
			tet.extra = TrackHandler.extras.get(1);
		}
		else if(td.extra == 4) {
			placeCatwalk(player, tet);
			tet.extra = TrackHandler.extras.get(2);
		}
		
		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
		supportCount++;
    }
    
    public void clearSupports(EntityPlayer player) {
    	if(supportCount % 4 == 0) {
    		for(int i = 0; i < 256 - rayY; i++) {
    			if(player.worldObj.getBlock(this.rayX, this.rayY - i, this.rayZ) == RCBlocks.support || player.worldObj.getBlock(this.rayX, this.rayY - i, this.rayZ) == RCBlocks.footer) {
    				removeBlock(td.supports, player, this.rayX, this.rayY - i, this.rayZ);
    			}
    		}
    	}
    }
    
    public void placeSupports(EntityPlayer player) {
    	if(supportCount % 4 == 0) {
    		for(int y = rayY + (type == types.LIFT ? flag : 0) - 1; y > 0; y--) {
    			if(player.worldObj.isAirBlock(rayX, y, rayZ) || player.worldObj.getBlock(rayX, y, rayZ) == Blocks.water) {
    				if(player.worldObj.isAirBlock(rayX, y - 1, rayZ)) {
	    				addBlock(td.supports, player, rayX, y, rayZ, RCBlocks.support);
	    				
	    				TileEntitySupport tes = new TileEntitySupport();
	    				tes.xCoord = rayX;
	    				tes.yCoord = y;
	    				tes.zCoord = rayZ;
	    				tes.colour = td.supportPaint;
	    				
	    				player.worldObj.setTileEntity(rayX, y, rayZ, tes);
	    				
    				}
    				else {
    					addBlock(td.supports, player, rayX, y, rayZ, RCBlocks.footer);
	    				
	    				TileEntityFooter tef = new TileEntityFooter();
	    				tef.xCoord = rayX;
	    				tef.yCoord = y;
	    				tef.zCoord = rayZ;
	    				tef.colour = td.supportPaint;
	    				
	    				player.worldObj.setTileEntity(rayX, y, rayZ, tef);
	    				
	    				break;
    				}
    			}
    		}
    	}
    }
    
    public void handleSupports(EntityPlayer player) {
    	clearSupports(player);
    	placeSupports(player);
    }
    
    public void updateBlocks(EntityPlayer player) {
    	player.worldObj.markBlockForUpdate(rayX, rayY, rayZ);
    }
    
    public void handleStraight(EntityPlayer player, boolean start) {
    	if(heightDirection == 0) {
			if(td.direction == 0) {
				rayZ += 1;
			}
			else if(td.direction == 1) {
				rayX -= 1;
			}
			else if(td.direction == 2) {
				rayZ -= 1;
			}
			else if(td.direction == 3) {
				rayX += 1;
			}
			
			if(start) {
	    		placeStartPoint(player, true);
	    	}
	    	else {
	    		placeStraightTrack(player);
	    	}
			
			TileEntityRideFence terf4 = new TileEntityRideFence();
			if(fenceCount == 0) {
				terf4.xCoord = rayX + 2;
				terf4.yCoord = rayY + 1;
				terf4.zCoord = rayZ;
				terf4.td = td;
			}
			
			if(fenceCount == 0) {
				player.worldObj.setTileEntity(rayX + 2, rayY + 1, rayZ, terf4);
			}
    	}
    }
    
    public void alterStartPointHeight(EntityPlayer player) {
    	placeStartPoint(player, false);
    	
		if(flag == -1) {
    		removeBlock(td.tracks, player, rayX, rayY, rayZ);
    	}
		
    	rayY += flag;
    }
    
    public void handleCurvedTrackSupports(EntityPlayer player) {
		supportCount = 0;
		
		int rayXCopy = rayX, rayZCopy = rayZ;
		
		rayXCopy = rayX + 1;
		if(supportCount % 4 == 0) {
    		for(int i = 0; i < 256 - rayY; i++) {
    			if(player.worldObj.getBlock(rayXCopy, this.rayY - i, rayZCopy) == RCBlocks.support || player.worldObj.getBlock(rayXCopy, this.rayY - i, rayZCopy) == RCBlocks.footer) {
    				removeBlock(td.supports, player, rayXCopy, this.rayY - i, rayZCopy);
    			}
    		}
    	}
		
		rayXCopy = rayX - 1;
		if(supportCount % 4 == 0) {
    		for(int i = 0; i < 256 - rayY; i++) {
    			if(player.worldObj.getBlock(rayXCopy, this.rayY - i, rayZCopy) == RCBlocks.support || player.worldObj.getBlock(rayXCopy, this.rayY - i, rayZCopy) == RCBlocks.footer) {
    				removeBlock(td.supports, player, rayXCopy, this.rayY - i, rayZCopy);
    			}
    		}
    	}
		
		rayZCopy = rayZ + 1;
		if(supportCount % 4 == 0) {
    		for(int i = 0; i < 256 - rayY; i++) {
    			if(player.worldObj.getBlock(rayXCopy, this.rayY - i, rayZCopy) == RCBlocks.support || player.worldObj.getBlock(rayXCopy, this.rayY - i, rayZCopy) == RCBlocks.footer) {
    				removeBlock(td.supports, player, rayXCopy, this.rayY - i, rayZCopy);
    			}
    		}
    	}
		
		rayZCopy = rayZ - 1;
		if(supportCount % 4 == 0) {
    		for(int i = 0; i < 256 - rayY; i++) {
    			if(player.worldObj.getBlock(rayXCopy, this.rayY - i, rayZCopy) == RCBlocks.support || player.worldObj.getBlock(rayXCopy, this.rayY - i, rayZCopy) == RCBlocks.footer) {
    				removeBlock(td.supports, player, rayXCopy, this.rayY - i, rayZCopy);
    			}
    		}
    	}		
    }
    
    public void placeCurvedTrack(EntityPlayer player) {
    	if(flag == 0) {
	    	if(td.direction == 0) {
				rayZ += 1;
				td.direction = 1;
			}
			else if(td.direction == 1) {
				rayX -= 1;
				td.direction = 2;
			}
			else if(td.direction == 2) {
				rayZ -= 1;
				td.direction = 3;
			}
			else if(td.direction == 3) {
				rayX += 1;
				td.direction = 0;
			}
			
			addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(4).block);
			
			TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
			
			tet.direction = td.direction;
			tet.colour = td.trackPaint;
			
			player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
			
			if(td.direction == 0) {
				td.direction = 2;
			}
			else if(td.direction == 1) {
				td.direction = 3;
			}
			else if(td.direction == 2) {
				td.direction = 0;
			}
			else if(td.direction == 3) {
				td.direction = 1;
			}
			
			handleCurvedTrackSupports(player);
    	}
    	else if(flag == 1) {
    		if(td.direction == 0) {
				rayZ += 1;
			}
			else if(td.direction == 1) {
				rayX -= 1;
			}
			else if(td.direction == 2) {
				rayZ -= 1;
			}
			else if(td.direction == 3) {
				rayX += 1;
			}
    		
			addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(4).block);
    		
    		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
			
			tet.direction = td.direction;
			tet.colour = td.trackPaint;
			
			player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
			
			if(td.direction == 0) {
				td.direction = 1;
			}
			else if(td.direction == 1) {
				td.direction = 2;
			}
			else if(td.direction == 2) {
				td.direction = 3;
			}
			else if(td.direction == 3) {
				td.direction = 0;
			}
			
			handleCurvedTrackSupports(player);
    	}
    }

    /*public void placeSlopeUp(EntityPlayer player) {
		Point previous = td.tracks.get(td.tracks.size() - 1);
		if(player.worldObj.getBlock(previous.x, previous.y, previous.z) == TrackHandler.tracks.get(0).block || (player.worldObj.getBlock(previous.x, previous.y, previous.z) == TrackHandler.tracks.get(1).block && ((TileEntityTrack) player.worldObj.getTileEntity(previous.x, previous.y, previous.z)).direction != td.direction)) {
			if(td.direction == 0) {
				rayZ += 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
				
				heightDirection = 1;
			}
			else if(td.direction == 1) {
				rayX -= 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
				
				heightDirection = 1;
			}
			else if(td.direction == 2) {
				rayZ -= 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
				
				heightDirection = 1;
			}
			else if(td.direction == 3) {
				rayX += 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
				
				heightDirection = 1;
			}
		}
		else if(player.worldObj.getBlock(previous.x, previous.y, previous.z) == TrackHandler.tracks.get(1).block || player.worldObj.getBlock(previous.x, previous.y, previous.z) == TrackHandler.tracks.get(2).block) {
			if(td.direction == 0) {
				rayY += 1;
				rayZ += 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
				
				heightDirection = 1;
			}
			else if(td.direction == 1) {
				rayX -= 1;
				rayY += 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
			}
			else if(td.direction == 2) {
				rayY += 1;
				rayZ -= 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
				
				heightDirection = 1;
			}
			else if(td.direction == 3) {
				rayX += 1;
				rayY += 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
			}
		}
		supportCount++;
    }*/

    /*public void flattenSlope(EntityPlayer player) {
    	if(td.direction == 0) {
    		if(heightDirection == 1) {
	    		rayY += 1;
	    		rayZ += 1;
	    		
	    		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
	    		
	    		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
	    		
	    		rayY += 1;
	    		rayZ += 2;
	    		
	    		heightDirection = 0;
    		}
    		else if(heightDirection == 2) {
	    		rayY -= 1;
	    		rayZ -= 1;
	    		
	    		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
	    		
	    		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
	    		
	    		td.direction = 2;
	    		heightDirection = 0;
    		}
    	}
    	else if(td.direction == 1) {
    		if(heightDirection == 1) {
	    		rayX -= 1;
	    		rayY += 1;
	    		
	    		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
	    		
	    		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
	    		
	    		rayX -= 2;
	    		rayY += 1;
	    		
	    		heightDirection = 0;
    		}
    		else if(heightDirection == 2) {
	    		rayX -= 1;
	    		rayY -= 1;
	    		
	    		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
	    		
	    		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
	    		
	    		td.direction = 1;
	    		heightDirection = 0;
    		}
    	}
    	else if(td.direction == 2) {
    		if(heightDirection == 1) {
    			rayY += 1;
	    		rayZ -= 1;
	    		
	    		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
	    		
	    		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
	    		
				rayY += 1;
	    		rayZ -= 2;
	    		
	    		heightDirection = 0;
    		}
    		else if(heightDirection == 2) {
	    		rayY -= 1;
	    		rayZ += 1;
	    		
	    		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
	    		
	    		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
	    		
	    		td.direction = 0;
	    		heightDirection = 0;
    		}
    	}
    	else if(td.direction == 3) {
    		System.out.println("Dir: " + heightDirection);
    		if(heightDirection == 1) {
	    		rayX += 1;
	    		rayY += 1;
	    		
	    		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
	    		
	    		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
	    		
	    		rayX -= 2;
	    		rayY += 1;
	    		
	    		heightDirection = 0;
    		}
    		else if(heightDirection == 2) {
	    		rayX -= 1;
	    		rayY -= 1;
	    		
	    		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
	    		
	    		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
	    		
	    		td.direction = 1;
	    		heightDirection = 0;
    		}
    	}
    	supportCount++;
    }*/
    
    /*public void placeSlopeDown(EntityPlayer player) {
    	Point previous = td.tracks.get(td.tracks.size() - 1);
		if(heightDirection == 0) {
			Point previous2 = td.tracks.get(td.tracks.size() - 2);
			if(player.worldObj.getBlock(previous2.x, previous2.y, previous2.z) == TrackHandler.tracks.get(0).block || player.worldObj.getBlock(previous2.x, previous2.y, previous2.z) == TrackHandler.tracks.get(3).block) {
				if(td.direction == 0) {
					rayY -= 1;
					rayZ -= 1;
					
					addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
					
					TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
					
					tet.direction = td.direction;
					tet.colour = td.trackPaint;
					
					player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
				}
				else if(td.direction == 1) {
					rayX -= 1;
					rayY -= 1;
					
					addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
					
					TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
					
					tet.direction = td.direction;
					tet.colour = td.trackPaint;
					
					player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
				}
				else if(td.direction == 2) {
					rayY -= 1;
					rayZ += 1;
					
					addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
					
					TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
					
					tet.direction = td.direction;
					tet.colour = td.trackPaint;
					
					player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
				}
				else if(td.direction == 3) {
					rayX += 1;
					rayY -= 1;
					
					addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
					
					TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
					
					tet.direction = td.direction;
					tet.colour = td.trackPaint;
					
					player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
				}
			}
			else {
				if(td.direction == 0) {
					td.direction = 2;
					
					rayY -= 1;
					rayZ += 3;
					
					addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
					
					TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
					
					tet.direction = td.direction;
					tet.colour = td.trackPaint;
					
					player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
					
					heightDirection = 2;
				}
				else if(td.direction == 1) {
					td.direction = 3;
					
					rayX -= 3;
					rayY -= 1;
					
					addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
					
					TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
					
					tet.direction = td.direction;
					tet.colour = td.trackPaint;
					
					player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
					
					heightDirection = 2;
				}
				else if(td.direction == 2) {
					td.direction = 0;
					
					rayY -= 1;
					rayZ -= 3;
					
					addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
					
					TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
					
					tet.direction = td.direction;
					tet.colour = td.trackPaint;
					
					player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
					
					heightDirection = 2;
				}
				else if(td.direction == 3) {
					System.out.println("TD 3 S DOWN");
					td.direction = 1;
					
					rayX += 7;
					rayY -= 1;
					
					addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
					
					TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
					
					tet.direction = td.direction;
					tet.colour = td.trackPaint;
					
					player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
					
					heightDirection = 2;
				}
			}
		}
		else if(heightDirection == 2) {
			System.out.println("Reached");
			if(td.direction == 0) {
				rayY -= 1;
				rayZ -= 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
			}
			else if(td.direction == 1) {
				rayX -= 1;
				rayY -= 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
			}
			else if(td.direction == 2) {
				rayY -= 1;
				rayZ += 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
			}
			else if(td.direction == 3) {
				rayX += 1;
				rayY -= 1;
				
				addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
				
				TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
				
				tet.direction = td.direction;
				tet.colour = td.trackPaint;
				
				player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
			}
		}
		supportCount++;
    }*/
    
    public void sd(EntityPlayer player, int type) {
    	if(type == 1) {
    		if(td.direction == 0) {
    			rayY -= 1;
    			rayZ += 3;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 2;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 1) {
    			rayX -= 3;
    			rayY -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 3;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 2) {
    			rayY -= 1;
    			rayZ -= 3;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 0;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 3) {
    			rayX += 3;
    			rayY -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 1;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    	}
    	else if(type == 2) {
    		if(td.direction == 0) {
    			rayY += 1;
    			rayZ += 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 0;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
        		
        		rayY += 1;
        		rayZ += 2;
    		}
    		else if(td.direction == 1) {
    			rayX -= 1;
    			rayY += 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 1;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
        		
        		rayX -= 2;
        		rayY += 1;
    		}
    		else if(td.direction == 2) {
    			rayY += 1;
    			rayZ -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 2;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
        		
        		rayY += 1;
        		rayZ -= 2;
    		}
    		else if(td.direction == 3) {
    			rayX += 1;
    			rayY += 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(3).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 3;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
        		
        		rayX += 2;
        		rayY += 1;
    		}
    	}

		supportCount++;
    }
    
    public void s(EntityPlayer player, int type) {
    	if(type == 1) {
    		if(td.direction == 0) {
    			rayY -= 1;
    			rayZ += 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 2;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 1) {
    			rayX -= 1;
    			rayY -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 3;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 2) {
    			rayY -= 1;
    			rayZ -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 0;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 3) {
    			rayX += 1;
    			rayY -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 1;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    	}
    	else if(type == 2) {
    		if(td.direction == 0) {
    			rayY += 1;
    			rayZ += 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 0;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 1) {
    			rayX -= 1;
    			rayY += 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 1;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 2) {
    			rayY += 1;
    			rayZ -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 2;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 3) {
    			rayX += 1;
    			rayY += 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(2).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 3;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    	}
    	
		supportCount++;
    }
    
    public void su(EntityPlayer player, int type) {
    	if(type == 1) {
    		if(td.direction == 0) {
    			rayY -= 1;
    			rayZ += 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 2;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 1) {
    			rayX -= 1;
    			rayY -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 3;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 2) {
    			rayY -= 1;
    			rayZ -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 0;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 3) {
    			rayX += 1;
    			rayY -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 1;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    	}
    	else if(type == 2) {
    		if(td.direction == 0) {
    			rayZ += 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 0;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 1) {
    			rayX -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 1;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 2) {
    			rayZ -= 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 2;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    		else if(td.direction == 3) {
    			rayX += 1;
        		addBlock(td.tracks, player, rayX, rayY, rayZ, TrackHandler.tracks.get(1).block);
        		
        		TileEntityTrack tet = (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ);
        		tet.direction = 3;
        		tet.colour = td.trackPaint;
        		player.worldObj.setTileEntity(rayX, rayY, rayZ, tet);
    		}
    	}

		supportCount++;
    }
    
    @Override
    public void handleServerSide(EntityPlayer player) {
    	if(type == types.DEBUG) {
    		/*System.out.println(td.name);
    		System.out.println(themes[td.theme].name);
    		System.out.println(td.trackPaint);
    		System.out.println(td.supportPaint);
    		System.out.println(td.fencePaint);*/
    		
    		/*try {
				TrackDesignHandler.saveTrackDesign(td.name, td);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
    	}
    	else if(type == types.CLEAR_TD) {
    		td = new TrackDesign();
    		heightDirection = 0;
    	}
    	else if(type == types.CHANGE_NAME) {
    		
    	}
    	else if(type == types.CHANGE_THEME) {
    		td.trackPaint = 0;
    		td.supportPaint = 0;
    		td.fencePaint = 0;
    		if(td.theme >= themes.length - 1) {
				td.theme = 0;
			}
			else {
				td.theme++;
			}
    		
    		td.trackPaint = PacketTrackDesigner.themes[td.theme].trackPaint;
    		td.supportPaint = PacketTrackDesigner.themes[td.theme].supportPaint;
    		td.fencePaint = PacketTrackDesigner.themes[td.theme].fencePaint;
    	}
    	else if(type == types.CHANGE_TRACK_PAINT) {
    		if(td.trackPaint >= ColourUtil.colours.length - 1) {
    			td.trackPaint = 0;
			}
			else {
				td.trackPaint++;
			}
    	}
    	else if(type == types.CHANGE_SUPPORT_PAINT) {
    		if(td.supportPaint >= ColourUtil.colours.length - 1) {
    			td.supportPaint = 0;
			}
			else {
				td.supportPaint++;
			}
    	}
    	else if(type == types.CHANGE_FENCE_PAINT) {
    		if(td.fencePaint >= ColourUtil.colours.length - 1) {
    			td.fencePaint = 0;
			}
			else {
				td.fencePaint++;
			}
    	}
    	else if(type == types.CHANGE_EXTRA) {
    		td.extra = flag;
    	}
    	else if(type == types.PLACE) {
    		td.start = new Point(rayX, rayY, rayZ);
    		placeStartPoint(player, false);
    	}
    	else if(type == types.LIFT) {
    		alterStartPointHeight(player);
    	}
    	else if(type == types.CONFIRM) {
    		placeStation(player, (TileEntityTrack) player.worldObj.getTileEntity(rayX, rayY, rayZ));
    	}
    	else if(type == types.ROTATE) {
    		td.direction += td.direction == 3 ? -3 : 1;
    		placeStartPoint(player, false);
    	}
    	else if(type == types.STRAIGHT) {
    		handleStraight(player, false);
    	}
    	else if(type == types.SLOPE_DOWN_1) {
    		sd(player, 1);
    	}
    	else if(type == types.SLOPE_1) {
    		s(player, 1);
    	}
    	else if(type == types.SLOPE_UP_1) {
    		su(player, 1);
    	}
    	else if(type == types.SLOPE_UP_2) {
    		su(player, 2);
    	}
    	else if(type == types.SLOPE_2) {
    		s(player, 2);
    	}
    	else if(type == types.SLOPE_DOWN_2) {
    		sd(player, 2);
    	}
    	else if(type == types.CURVED) {
    		placeCurvedTrack(player);
    	}
    	else if(type == types.UPDATE_GATES) {
    		for(Point point : ((TileEntityRideFence) player.worldObj.getTileEntity(teX, teY, teZ)).td.gates) {
    			TileEntityRideFence terf = (TileEntityRideFence) player.worldObj.getTileEntity(point.x, point.y, point.z);
    			
    			terf.open = !terf.open;
    			
    			player.worldObj.setTileEntity(point.x, point.y, point.z, terf);
    			
    			player.worldObj.markBlockForUpdate(point.x, point.y, point.z);
    		}
    	}
    	else if(type == types.DELETE_RIDE) {
    		System.out.println("Delete ride" + td);
    		if(td.tracks != null) {
	    		for(Point point : ((TileEntityRideFence) player.worldObj.getTileEntity(teX, teY, teZ)).td.tracks) {
	    			removeBlockAlt(((TileEntityRideFence) player.worldObj.getTileEntity(teX, teY, teZ)).td.tracks, player, point.x, point.y, point.z);
	    		}
    		}
    		if(td.stationBlocks != null) {
	    		for(Point point : ((TileEntityRideFence) player.worldObj.getTileEntity(teX, teY, teZ)).td.stationBlocks) {
	    			//if(point.x != teX && point.y != teY && point.z != teZ) {
	    				removeBlockAlt(((TileEntityRideFence) player.worldObj.getTileEntity(teX, teY, teZ)).td.stationBlocks, player, point.x, point.y, point.z);
	    			//}
				}
    		}
    		if(td.supports != null) {
	    		for(Point point : ((TileEntityRideFence) player.worldObj.getTileEntity(teX, teY, teZ)).td.supports) {
	    			removeBlockAlt(((TileEntityRideFence) player.worldObj.getTileEntity(teX, teY, teZ)).td.supports, player, point.x, point.y, point.z);
				}
    		}
    		if(td.gates != null) {
	    		for(Point point : ((TileEntityRideFence) player.worldObj.getTileEntity(teX, teY, teZ)).td.gates) {
	    			removeBlockAlt(((TileEntityRideFence) player.worldObj.getTileEntity(teX, teY, teZ)).td.gates, player, point.x, point.y, point.z);
				}
    		}
    		
    		removeBlockAlt(((TileEntityRideFence) player.worldObj.getTileEntity(teX, teY, teZ)).td.gates, player, teX, teY, teZ);
    		
    		td.tracks.clear();
    		td.stationBlocks.clear();
    		td.supports.clear();
    		td.gates.clear();
    	}
    	
    	handleSupports(player);
    	updateBlocks(player);
    }
}