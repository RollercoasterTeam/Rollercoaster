package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
<<<<<<< HEAD
<<<<<<< HEAD
=======

import net.minecraftforge.common.util.ForgeDirection;
>>>>>>> master
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrack;
=======
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
>>>>>>> master
import robomuss.rc.block.model.ModelSupport;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;

public class TileEntityRenderSupport extends TileEntitySpecialRenderer {
    public ModelSupport model;

    public TileEntityRenderSupport() {
        this.model = new ModelSupport();
    }

    @Override
<<<<<<< HEAD
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8, int i) {
=======
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
>>>>>>> master
    	boolean connectNorth = false;
    	boolean connectEast = false;
    	boolean connectSouth = false;
    	boolean connectWest = false;
<<<<<<< HEAD

        TileEntity north = te.getWorld().getTileEntity(te.getPos().add(0, 1, 1));
        TileEntity east = te.getWorld().getTileEntity(te.getPos().add(1, 1, 0));
        TileEntity south = te.getWorld().getTileEntity(te.getPos().add(0, 1, -1));
        TileEntity west = te.getWorld().getTileEntity(te.getPos().add(-1, 1, 0));
    	
        if(north instanceof TileEntityTrack) {
        	TileEntityTrack track = (TileEntityTrack) north;
        	BlockTrack block = (BlockTrack) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == ForgeDirection.NORTH) {
        			connectNorth = true;
        		}
        	}
        }
        
        if(east instanceof TileEntityTrack) {
        	TileEntityTrack track = (TileEntityTrack) east;
        	BlockTrack block = (BlockTrack) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == ForgeDirection.WEST) {
        			connectEast = true;
        		}
        	}
        }
        
    	if(south instanceof TileEntityTrack) {
        	TileEntityTrack track = (TileEntityTrack) south;
        	BlockTrack block = (BlockTrack) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == ForgeDirection.SOUTH) {
        			connectSouth = true;
        		}
        	}
        }
    	
    	if(west instanceof TileEntityTrack) {
        	TileEntityTrack track = (TileEntityTrack) west;
        	BlockTrack block = (BlockTrack) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == ForgeDirection.EAST) {
        			connectWest = true;
        		}
        	}
        }
=======
    	
        TileEntity north = te.getWorldObj().getTileEntity(te.xCoord, te.yCoord + 1, te.zCoord + 1);
		TileEntity south = te.getWorldObj().getTileEntity(te.xCoord, te.yCoord + 1, te.zCoord - 1);
		TileEntity west = te.getWorldObj().getTileEntity(te.xCoord - 1, te.yCoord + 1, te.zCoord);
		TileEntity east = te.getWorldObj().getTileEntity(te.xCoord + 1, te.yCoord + 1, te.zCoord);

	    TileEntityTrackBase trackNorth = north instanceof TileEntityTrackBase ? (TileEntityTrackBase) north : null;
	    TileEntityTrackBase trackSouth = south instanceof TileEntityTrackBase ? (TileEntityTrackBase) south : null;
	    TileEntityTrackBase trackWest  = west  instanceof TileEntityTrackBase ? (TileEntityTrackBase) west  : null;
	    TileEntityTrackBase trackEast  = east  instanceof TileEntityTrackBase ? (TileEntityTrackBase) east  : null;

	    BlockTrackBase blockNorth = trackNorth != null ? (BlockTrackBase) trackNorth.getBlockType() : null;
	    BlockTrackBase blockSouth = trackSouth != null ? (BlockTrackBase) trackSouth.getBlockType() : null;
	    BlockTrackBase blockWest  = trackWest  != null ? (BlockTrackBase) trackWest.getBlockType()  : null;
	    BlockTrackBase blockEast  = trackEast  != null ? (BlockTrackBase) trackEast.getBlockType()  : null;

	    int metaNorth = blockNorth != null ? trackNorth.getWorldObj().getBlockMetadata(trackNorth.xCoord, trackNorth.yCoord, trackNorth.zCoord) : -1;
	    int metaSouth = blockSouth != null ? trackSouth.getWorldObj().getBlockMetadata(trackSouth.xCoord, trackSouth.yCoord, trackSouth.zCoord) : -1;
	    int metaWest  = blockWest  != null ? trackWest.getWorldObj().getBlockMetadata(trackWest.xCoord, trackWest.yCoord, trackWest.zCoord)     : -1;
	    int metaEast  = blockEast  != null ? trackEast.getWorldObj().getBlockMetadata(trackEast.xCoord, trackEast.yCoord, trackEast.zCoord)     : -1;

	    connectNorth = (blockNorth != null && isConnectable(blockNorth.track_type)) && metaNorth == 2;
	    connectSouth = (blockSouth != null && isConnectable(blockSouth.track_type)) && metaSouth == 3;
	    connectWest  = (blockWest  != null && isConnectable(blockWest.track_type))  && metaWest  == 4;
	    connectEast  = (blockEast  != null && isConnectable(blockEast.track_type))  && metaEast  == 5;
>>>>>>> master

        GL11.glPushMatrix();
        int colour = ((TileEntitySupport) te).colour;
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f, (float) z + 0.5f);
        GL11.glRotatef(180, 1, 0, 0);
        ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        this.model.middle.render(0.0625f);
        this.model.middle2.render(0.0625f);
        
        if (((TileEntitySupport) te).flange) {
        	this.model.flange1.render(0.0625f);
            this.model.flange2.render(0.0625f);
        }

        GL11.glPopMatrix();
        
       /* boolean isTop = false;
        int supportHeight = 0;
        Block above = te.getWorldObj().getBlock(te.xCoord, te.yCoord + 1, te.zCoord);
        if(above == null || above.getClass() != BlockSupport.class) {
        	isTop = true;
        	for(int i = te.yCoord; i > 0; i--) {
        		Block support = te.getWorldObj().getBlock(te.xCoord, i, te.zCoord);
        		if(support != null && support instanceof BlockSupport) {
        			supportHeight++;
        		}
        		else {
        			break;
        		}
        	}
        }
        
        if(isTop) {
	    	for(int i = 0; i < supportHeight / 2; i++) {
	    		GL11.glPushMatrix();
	            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.3F - supportHeight + (i * 2), (float) z + 0.5F);
	            GL11.glRotatef(180, 1, 0, 0);
	            Minecraft.getMinecraft().renderEngine.bindTexture(textures);
	
	            this.model.flange1.render(0.0625F);
	            this.model.flange2.render(0.0625F);  
	
	            GL11.glPopMatrix();
	    	}
        }*/
        
        GL11.glPushMatrix();

        if(connectNorth || connectEast || connectWest) {
        	GL11.glTranslatef((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f);
        } else {
        	GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f, (float) z + 0.5f);
        }

        GL11.glRotatef(180, 1, 0, 0);
        int connector_colour = 0;

	    connector_colour = (connectNorth ? trackNorth.colour : (connectSouth ? trackSouth.colour : (connectWest ? trackWest.colour : (connectEast ? trackEast.colour : 0))));

        ResourceLocation connector_texture = (new ResourceLocation("rc:textures/models/colour_" + connector_colour + ".png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(connector_texture);

        if(connectNorth) {
	        GL11.glRotatef(180f, -180f, 0f, 0f);
        } else if(connectEast) {
        	GL11.glRotatef(180f, 180f, 0f, 180f);
        } else if(connectWest) {
        	GL11.glRotatef(180f, -180f, 0f, 180f);
        }
        
        if(connectNorth || connectEast || connectSouth || connectWest) {
        	this.model.connector.render(0.0625f);
        }
        
        GL11.glPopMatrix();
    }

	private boolean isConnectable(TrackPiece track_type) {
		if(track_type == TrackHandler.findTrackType("slope_up")) {
			return true;
		} else if(track_type == TrackHandler.findTrackType("slope")) {
			return true;
		} else if(track_type == TrackHandler.findTrackType("slope_down")) {
			return true;
		} else {
			return false;
		}
	}
}
