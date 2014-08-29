package robomuss.rc.item;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.EntityTrainDefault;

public class ItemTrain extends Item
{
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int face, float hitX, float hitY, float hitZ){
        if(!world.isRemote) {
	        EntityTrain creeper = new EntityTrainDefault(world);
	
	        creeper.setLocationAndAngles(x, y, z, 0, 0);
	
	        world.spawnEntityInWorld(creeper);
	        --stack.stackSize;
	        return true;
        }
        return false;
    }
}