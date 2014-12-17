package robomuss.rc.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import robomuss.rc.item.ItemBalloon;
import robomuss.rc.util.ColourUtil;

@SideOnly(Side.CLIENT)
public class RCItemTooltipEvent {
	@SubscribeEvent
	public void drawRCItemTooltip(ItemTooltipEvent event) {
		if (event.itemStack.getItem() instanceof ItemBalloon) {
			int j = MathHelper.clamp_int(event.itemStack.getItemDamage(), 0, ColourUtil.numColours);
			event.toolTip.add(ColourUtil.COLORS[j].display_name);
		}
	}
}
