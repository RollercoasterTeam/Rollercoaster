package robomuss.rc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrack;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.TrackHandler;

public class ItemTrain extends Item {

	public ItemTrain() {

	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			if(world.getBlock(x, y, z) instanceof BlockTrack) {
				BlockTrack block = (BlockTrack) world.getBlock(x, y, z);
				if(block.track_type == TrackHandler.findTrackType("horizontal")) {
					if(((TileEntityTrack) world.getTileEntity(x, y, z)).extra == TrackHandler.extras.get(3)) {
						EntityTrainDefault entity = spawnCart(world, x + 0.5F, y + 0.0F, z + 0.5F, 0);
						entity = rotateOnPlace(entity, (TileEntityTrack) world.getTileEntity(x, y, z));
						if(stack.hasDisplayName()) {
							entity.setMinecartName(stack.getDisplayName());
						}
						entity.selfPowered = true;
						world.spawnEntityInWorld(entity);
						--stack.stackSize;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static EntityTrainDefault spawnCart(World world, float x, float y, float z, int colour) {
		return EntityTrain.createMinecart(world,(double) ((float) x), (double) ((float) y), (double) ((float) z), colour);
	}
	
	private EntityTrainDefault rotateOnPlace(EntityTrainDefault entity, TileEntityTrack tileentity) {
        if (tileentity != null & tileentity instanceof TileEntityTrack) {
            TileEntityTrack te = (TileEntityTrack) tileentity;
            if (tileentity.getBlockType() == TrackHandler.findTrackType("horizontal").block) {
                if (te.direction == 0) {
                    entity.rotationYaw = 90f;
                } else if (te.direction == 1) {
                	entity.rotationYaw = 0f;
                } else if (te.direction == 2) {
                	entity.rotationYaw = 270f;
                } else if (te.direction == 3) {
                	entity.rotationYaw = 180f;
                }
                entity.direction = te.direction;
            }
        }
        return entity;
    }
}