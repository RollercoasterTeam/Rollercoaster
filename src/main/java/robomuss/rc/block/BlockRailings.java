package robomuss.rc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class BlockRailings extends BlockPane {

	public BlockRailings(String par1) {
		super("rc:" + par1, "rc:" + par1, Material.iron, true);
		setHardness(1F);
		setResistance(3F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return 0;
	}
}
