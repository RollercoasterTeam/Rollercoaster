package robomuss.rc.block.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.model.ModelSupport;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;

public class TileEntityRenderSupport extends TileEntitySpecialRenderer {
    public ModelSupport model;

    public TileEntityRenderSupport() {
        this.model = new ModelSupport();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double posX, double posZ, double posY, float f, int i) {
    	boolean connectNorth = false;
    	boolean connectEast = false;
    	boolean connectSouth = false;
    	boolean connectWest = false;

	    BlockPos pos = te.getPos();

	    IBlockState stateNorth = te.getWorld().getBlockState(pos.north());
	    IBlockState stateSouth = te.getWorld().getBlockState(pos.south());
	    IBlockState stateWest  = te.getWorld().getBlockState(pos.west());
	    IBlockState stateEast  = te.getWorld().getBlockState(pos.east());

	    BlockTrackBase trackNorth = stateNorth.getBlock() instanceof BlockTrackBase ? (BlockTrackBase) stateNorth.getBlock() : null;
	    BlockTrackBase trackSouth = stateSouth.getBlock() instanceof BlockTrackBase ? (BlockTrackBase) stateSouth.getBlock() : null;
	    BlockTrackBase trackWest  = stateWest.getBlock()  instanceof BlockTrackBase ? (BlockTrackBase) stateWest.getBlock()  : null;
	    BlockTrackBase trackEast  = stateEast.getBlock()  instanceof BlockTrackBase ? (BlockTrackBase) stateEast.getBlock()  : null;

	    EnumFacing northFacing = trackNorth != null ? (EnumFacing) stateNorth.getValue(BlockTrackBase.FACING) : null;
	    EnumFacing southFacing = trackSouth != null ? (EnumFacing) stateSouth.getValue(BlockTrackBase.FACING) : null;
	    EnumFacing westFacing  = trackWest  != null ? (EnumFacing) stateWest.getValue(BlockTrackBase.FACING)  : null;
	    EnumFacing eastFacing  = trackEast  != null ? (EnumFacing) stateEast.getValue(BlockTrackBase.FACING)  : null;

	    connectNorth = (northFacing != null && northFacing == EnumFacing.NORTH);
	    connectSouth = (southFacing != null && southFacing == EnumFacing.SOUTH);
	    connectWest  = (westFacing  != null && westFacing  == EnumFacing.WEST);
	    connectEast  = (eastFacing  != null && eastFacing  == EnumFacing.EAST);

        GL11.glPushMatrix();
        int colour = ((TileEntitySupport) te).colour;
        GL11.glTranslatef((float) posX + 0.5f, (float) posY + 1.5f, (float) posZ + 0.5f);
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
        
        GL11.glPushMatrix();

        if(connectNorth || connectEast || connectWest) {
        	GL11.glTranslatef((float) posX + 0.5f, (float) posY + 0.5f, (float) posZ + 0.5f);
        } else {
        	GL11.glTranslatef((float) posX + 0.5f, (float) posY + 1.5f, (float) posZ + 0.5f);
        }

        GL11.glRotatef(180, 1, 0, 0);
        int connector_colour = 0;

	    if (connectNorth) {
		    connector_colour = ((TileEntityTrackBase) te.getWorld().getTileEntity(pos.north())).colour;
	    } else if (connectSouth) {
		    connector_colour = ((TileEntityTrackBase) te.getWorld().getTileEntity(pos.south())).colour;
	    } else if (connectWest) {
		    connector_colour = ((TileEntityTrackBase) te.getWorld().getTileEntity(pos.west())).colour;
	    } else if (connectEast) {
		    connector_colour = ((TileEntityTrackBase) te.getWorld().getTileEntity(pos.east())).colour;
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
        	this.model.connector.render(0.0625f);
        }
        
        GL11.glPopMatrix();
    }

	private boolean isConnectable(TrackPiece track_type) {
		return TrackManager.isSloped(track_type);
	}
}
