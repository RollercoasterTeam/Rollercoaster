package robomuss.rc.block.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
<<<<<<< HEAD
<<<<<<< HEAD
=======

import net.minecraftforge.common.util.ForgeDirection;
>>>>>>> master
=======
>>>>>>> master
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockSupport;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.model.ModelFooter;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;

public class TileEntityRenderFooter extends TileEntitySpecialRenderer {
    
    public ModelFooter model;
    public static TileEntityRenderFooter instance = new TileEntityRenderFooter();

    public TileEntityRenderFooter() {
        this.model = new ModelFooter();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float var8, int i) {
    	boolean connectNorth = false;
    	boolean connectEast = false;
    	boolean connectSouth = false;
    	boolean connectWest = false;

<<<<<<< HEAD
        TileEntity north = te.getWorld().getTileEntity(te.getPos().add(0, 1, 1));
        TileEntity east = te.getWorld().getTileEntity(te.getPos().add(1, 1, 0));
        TileEntity south = te.getWorld().getTileEntity(te.getPos().add(0, 1, -1));
        TileEntity west = te.getWorld().getTileEntity(te.getPos().add(-1, +1, 0));

        if(north instanceof TileEntityTrack) {
        	TileEntityTrack track = (TileEntityTrack) north;
        	BlockTrack block = (BlockTrack) track.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
        		if(track.direction == ForgeDirection.NORTH) {
=======
        TileEntity north = te.getWorldObj().getTileEntity(te.xCoord, te.yCoord + 1, te.zCoord + 1);
        TileEntity east = te.getWorldObj().getTileEntity(te.xCoord + 1, te.yCoord + 1, te.zCoord);
        TileEntity south = te.getWorldObj().getTileEntity(te.xCoord, te.yCoord + 1, te.zCoord - 1);
        TileEntity west = te.getWorldObj().getTileEntity(te.xCoord - 1, te.yCoord + 1, te.zCoord);
    	
        if(north instanceof TileEntityTrackBase) {
	        TileEntityTrackBase teTrack = (TileEntityTrackBase) north;
	        BlockTrackBase block = (BlockTrackBase) teTrack.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
		        int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				if (meta == 2) {
>>>>>>> master
					connectNorth = true;
				}
        	}
        }
        
        if(east instanceof TileEntityTrackBase) {
	        TileEntityTrackBase teTrack = (TileEntityTrackBase) east;
	        BlockTrackBase block = (BlockTrackBase) teTrack.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
<<<<<<< HEAD
        		if(track.direction == ForgeDirection.WEST) {
        			connectEast = true;
        		}
=======
		        int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
		        if (meta == 5) {
			        connectEast = true;
		        }
>>>>>>> master
        	}
        }
        
    	if(south instanceof TileEntityTrackBase) {
		    TileEntityTrackBase teTrack = (TileEntityTrackBase) south;
		    BlockTrackBase block = (BlockTrackBase) teTrack.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
<<<<<<< HEAD
        		if(track.direction == ForgeDirection.SOUTH) {
        			connectSouth = true;
        		}
=======
		        int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
		        if (meta == 3) {
			        connectSouth = true;
		        }
>>>>>>> master
        	}
        }
    	
    	if(west instanceof TileEntityTrackBase) {
		    TileEntityTrackBase teTrack = (TileEntityTrackBase) west;
		    BlockTrackBase block = (BlockTrackBase) teTrack.getBlockType();
        	if(block != null && isConnectable(block.track_type)) {
<<<<<<< HEAD
        		if(track.direction == ForgeDirection.EAST) {
        			connectWest = true;
        		}
=======
		        int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
		        if (meta == 4) {
			        connectWest = true;
		        }
>>>>>>> master
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
        
        Block above = te.getWorld().getBlockState(te.getPos().offsetUp()).getBlock();
        
        if(above instanceof BlockSupport || (connectNorth || connectEast || connectSouth || connectWest) || ((TileEntityFooter) te).forceConnection) {
        	 this.model.middle.render(0.0625F);  
        	 this.model.middle2.render(0.0625F);  
        	 
        	 this.model.flange1.render(0.0625F);  
        	 this.model.flange2.render(0.0625F);  
        }
        
        if(above instanceof BlockTrackBase) {
	        BlockTrackBase track = (BlockTrackBase) above;
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
        } else if(connectEast) {
        	GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        } else if(connectWest) {
        	GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        } else {
        	GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        }
        GL11.glRotatef(180, 1, 0, 0);
        int connector_colour = 0;
        if(connectNorth) {
        	connector_colour = ((TileEntityTrackBase) north).colour;
        } else if(connectEast) {
        	connector_colour = ((TileEntityTrackBase) east).colour;
        } else if(connectSouth) {
        	connector_colour = ((TileEntityTrackBase) south).colour;
        } else if(connectWest) {
        	connector_colour = ((TileEntityTrackBase) west).colour;
        }
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
        	this.model.connector.render(0.0625F);
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
