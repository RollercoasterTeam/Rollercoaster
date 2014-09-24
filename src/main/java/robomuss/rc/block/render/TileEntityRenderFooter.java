package robomuss.rc.block.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockSupport;
import robomuss.rc.block.BlockTrack;
import robomuss.rc.block.model.ModelFooter;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;

public class TileEntityRenderFooter extends TileEntitySpecialRenderer {
    
    public ModelFooter model;
    public static TileEntityRenderFooter instance = new TileEntityRenderFooter();

    public TileEntityRenderFooter() {
        this.model = new ModelFooter();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8) {
    	boolean connectNorth = false;
    	boolean connectEast = false;
    	boolean connectSouth = false;
    	boolean connectWest = false;
    	
        TileEntity north = te.getWorldObj().getTileEntity(te.xCoord, te.yCoord + 1, te.zCoord + 1);
        TileEntity east = te.getWorldObj().getTileEntity(te.xCoord + 1, te.yCoord + 1, te.zCoord);
        TileEntity south = te.getWorldObj().getTileEntity(te.xCoord, te.yCoord + 1, te.zCoord - 1);
        TileEntity west = te.getWorldObj().getTileEntity(te.xCoord - 1, te.yCoord + 1, te.zCoord);
    	
        if(north instanceof TileEntityTrack) {
        	TileEntityTrack track = (TileEntityTrack) north;
        	BlockTrack block = (BlockTrack) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == 2) {
        			connectNorth = true;
        		}
        	}
        }
        
        if(east instanceof TileEntityTrack) {
        	TileEntityTrack track = (TileEntityTrack) east;
        	BlockTrack block = (BlockTrack) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == 1) {
        			connectEast = true;
        		}
        	}
        }
        
    	if(south instanceof TileEntityTrack) {
        	TileEntityTrack track = (TileEntityTrack) south;
        	BlockTrack block = (BlockTrack) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == 0) {
        			connectSouth = true;
        		}
        	}
        }
    	
    	if(west instanceof TileEntityTrack) {
        	TileEntityTrack track = (TileEntityTrack) west;
        	BlockTrack block = (BlockTrack) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == 3) {
        			connectWest = true;
        		}
        	}
        }

    	
        GL11.glPushMatrix();
        int colour = ((TileEntityFooter) te).colour;
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        ResourceLocation textures = (new ResourceLocation("rc:textures/models/footer.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        this.model.footer.render(0.0625F);  
        
        textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
        
        Block above = te.getWorldObj().getBlock(te.xCoord, te.yCoord + 1, te.zCoord);
        
        if(above instanceof BlockSupport || (connectNorth || connectEast || connectSouth || connectWest) || ((TileEntityFooter) te).forceConnection) {
        	 this.model.middle.render(0.0625F);  
        	 this.model.middle2.render(0.0625F);  
        	 
        	 this.model.flange1.render(0.0625F);  
        	 this.model.flange2.render(0.0625F);  
        }
        
        if(above instanceof BlockTrack) {
        	BlockTrack track = (BlockTrack) above;
        	if(track.track_type == TrackHandler.findTrackType("horizontal") || track.track_type == TrackHandler.findTrackType("curve")) {
        		 this.model.middle.render(0.0625F);  
            	 this.model.middle2.render(0.0625F);  
            	 
            	 this.model.flange1.render(0.0625F);  
            	 this.model.flange2.render(0.0625F);  
        	}
        }
        
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        if(connectNorth) {
        	GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        }
        else if(connectEast) {
        	GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        }
        else if(connectWest) {
        	GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        }
        else {
        	GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        }
        GL11.glRotatef(180, 1, 0, 0);
        int connector_colour = 0;
        if(connectNorth) {
        	connector_colour = ((TileEntityTrack) north).colour;
        }
        else if(connectEast) {
        	connector_colour = ((TileEntityTrack) east).colour;
        }
        else if(connectSouth) {
        	connector_colour = ((TileEntityTrack) south).colour;
        }
        else if(connectWest) {
        	connector_colour = ((TileEntityTrack) west).colour;
        }
        ResourceLocation connector_texture = (new ResourceLocation("rc:textures/models/colour_" + connector_colour + ".png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(connector_texture);

        if(connectNorth) {
        	GL11.glRotatef(180f, -180f, 0f, 0f);
        }
        else if(connectEast) {
        	GL11.glRotatef(180f, 180f, 0f, 180f);
        }
        else if(connectWest) {
        	GL11.glRotatef(180f, -180f, 0f, 180f);
        }
        
        if(connectNorth || connectEast || connectSouth || connectWest) {
        	this.model.connector.render(0.0625F);
        }
        
        GL11.glPopMatrix();
    }

	private boolean isConnectable(TrackPiece track_type) {
		if(track_type == TrackHandler.findTrackType("slope_up")) {
			return true;
		}
		else if(track_type == TrackHandler.findTrackType("slope")) {
			return true;
		}
		else if(track_type == TrackHandler.findTrackType("slope_down")) {
			return true;
		}
		else {
			return false;
		}
	}
}
