package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockTrackBase;
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
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8) {
    	boolean connectNorth = false;
    	boolean connectEast = false;
    	boolean connectSouth = false;
    	boolean connectWest = false;
    	
        TileEntity north = te.getWorldObj().getTileEntity(te.xCoord, te.yCoord + 1, te.zCoord + 1);
        TileEntity east = te.getWorldObj().getTileEntity(te.xCoord + 1, te.yCoord + 1, te.zCoord);
        TileEntity south = te.getWorldObj().getTileEntity(te.xCoord, te.yCoord + 1, te.zCoord - 1);
        TileEntity west = te.getWorldObj().getTileEntity(te.xCoord - 1, te.yCoord + 1, te.zCoord);
    	
        if(north instanceof TileEntityTrackBase) {
        	TileEntityTrackBase track = (TileEntityTrackBase) north;
	        BlockTrackBase block = (BlockTrackBase) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == ForgeDirection.NORTH) {
        			connectNorth = true;
        		}
        	}
        }
        
        if(east instanceof TileEntityTrackBase) {
	        TileEntityTrackBase track = (TileEntityTrackBase) east;
	        BlockTrackBase block = (BlockTrackBase) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == ForgeDirection.WEST) {
        			connectEast = true;
        		}
        	}
        }
        
    	if(south instanceof TileEntityTrackBase) {
		    TileEntityTrackBase track = (TileEntityTrackBase) south;
		    BlockTrackBase block = (BlockTrackBase) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == ForgeDirection.SOUTH) {
        			connectSouth = true;
        		}
        	}
        }
    	
    	if(west instanceof TileEntityTrackBase) {
		    TileEntityTrackBase track = (TileEntityTrackBase) west;
		    BlockTrackBase block = (BlockTrackBase) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == ForgeDirection.EAST) {
        			connectWest = true;
        		}
        	}
        }

    	
        GL11.glPushMatrix();
        int colour = ((TileEntitySupport) te).colour;
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        this.model.middle.render(0.0625F);
        this.model.middle2.render(0.0625F);  
        
        if(((TileEntitySupport) te).flange) {
        	this.model.flange1.render(0.0625F);
            this.model.flange2.render(0.0625F);  
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
        	connector_colour = ((TileEntityTrackBase) north).track.colour;
        }
        else if(connectEast) {
        	connector_colour = ((TileEntityTrackBase) east).track.colour;
        }
        else if(connectSouth) {
        	connector_colour = ((TileEntityTrackBase) south).track.colour;
        }
        else if(connectWest) {
        	connector_colour = ((TileEntityTrackBase) west).track.colour;
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
