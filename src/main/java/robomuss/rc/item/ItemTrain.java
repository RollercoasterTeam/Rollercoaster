package robomuss.rc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.TrackHandler;

public class ItemTrain extends Item {

	public ItemTrain() {

	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
<<<<<<< HEAD
			if(world.getBlockState(pos).getBlock() instanceof BlockTrack) {
				BlockTrack block = (BlockTrack) world.getBlockState(pos).getBlock();
				if(block.track_type == TrackHandler.findTrackType("horizontal")) {
					if(((TileEntityTrack) world.getTileEntity(pos)).extra == TrackHandler.extras.get(3)) {
						EntityTrainDefault entity = spawnCart(world, pos);
=======
			if(world.getBlock(x, y, z) instanceof BlockTrackBase) {
				BlockTrackBase block = (BlockTrackBase) world.getBlock(x, y, z);
				if(block.track_type == TrackHandler.findTrackType("horizontal")) {
					if(((TileEntityTrackBase) world.getTileEntity(x, y, z)).extra == TrackHandler.extras.get(3)) {
						EntityTrainDefault entity = spawnCart(world, x, y, z);
>>>>>>> master
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
	
	public static EntityTrainDefault spawnCart(World world, BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		return EntityTrain.createMinecart(world,(double) ((float) x + 0.5F), (double) ((float) y + 0.4F), (double) ((float) z + 0.5F), 0);
	}
}