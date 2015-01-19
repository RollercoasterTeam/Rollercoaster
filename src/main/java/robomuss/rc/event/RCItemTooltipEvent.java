package robomuss.rc.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.input.Keyboard;
import robomuss.rc.client.gui.GuiTrackDesigner;
import robomuss.rc.client.gui.keybinding.TrackDesignerKeyBindings;
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
