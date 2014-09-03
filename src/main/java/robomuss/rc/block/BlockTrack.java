package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.OldEntityTrain;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.item.RCItems;
import robomuss.rc.tracks.TrackHandler;
import robomuss.rc.tracks.TrackType;
import robomuss.rc.util.IPaintable;

public class BlockTrack extends BlockContainer implements IPaintable {

	public TrackType track_type;
	
	public BlockTrack(TrackType track) {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);
		
		this.track_type = track;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		AxisAlignedBB bounds = track_type.getBlockBounds(iba, x, y, z);
		setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTrack();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			if(player.getHeldItem() != null) {
				if(player.getHeldItem().getItem() == RCItems.hammer) {
					TileEntityTrack tet = (TileEntityTrack) world.getTileEntity(x, y, z);
					if(tet.direction == 3) {
						tet.direction = 0;
					}
					else {
						tet.direction++;
					}
					world.markBlockForUpdate(x, y, z);
					return true;
				}
				else if(player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityTrack tet = (TileEntityTrack) world.getTileEntity(x, y, z);
					tet.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(x, y, z);
					return true;
				}
				else if(player.getHeldItem().getItem() instanceof ItemExtra) {
					TileEntityTrack tet = (TileEntityTrack) world.getTileEntity(x, y, z);
					tet.extra = TrackHandler.extras.get(((ItemExtra) player.getHeldItem().getItem()).id);
					world.markBlockForUpdate(x, y, z);
					return true;
				}
				else {
					return false;
				}
				
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 110;
	}
	
	@Override
	public int damageDropped(int dmg) {
		return dmg;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getPaintMeta(World world, int x, int y, int z) {
		return ((TileEntityTrack) world.getTileEntity(x, y, z)).colour;
	}

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        if(par5Entity instanceof OldEntityTrain && par5Entity != null){
            onTrainCollidedWithBlock(par1World, par2, par3, par4, (OldEntityTrain) par5Entity);
        }
    }

    public void onTrainCollidedWithBlock(World world, int x, int y, int z, OldEntityTrain train) {
        TrackType trackType = unLocalNameTotrack(this);
        if(trackType != null){
            trackType.onTrainCollidedWithTrack(world, x, y, z, train);
        }

    }

    public TrackType unLocalNameTotrack(Block block){
        String name = block.getUnlocalizedName();
        if(name.equals("tile.horizontal_track")){
            return TrackHandler.findTrackType("horizontal");
        } else if(name.equals("tile.slope_up_track")){
            return TrackHandler.findTrackType("slope_up");
        } else if(name.equals("tile.slope_track")){
            return TrackHandler.findTrackType("slope");
        } else if(name.equals("tile.slope_down_track")){
            return TrackHandler.findTrackType("slope_down");
        } else if(name.equals("tile.loop_track")){
            return TrackHandler.findTrackType("loop");
        } else if(name.equals("tile.heartline_roll_track")){
            return TrackHandler.findTrackType("heartline_roll");
        }else if(name.equals("tile.curve_track")){
            return TrackHandler.findTrackType("curve");
        }
        return null;
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
    	TileEntityTrack te = (TileEntityTrack) world.getTileEntity(x, y, z);
    	System.out.println("Adding block");
    	te.type = TrackHandler.types.get(0);
    }
}
