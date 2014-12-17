package robomuss.rc.block;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
