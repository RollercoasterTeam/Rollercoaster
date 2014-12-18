package robomuss.rc.block;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;

public class BlockRideFenceGate extends BlockDirectional {
	public static final PropertyBool OPEN = PropertyBool.create("open");
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	protected BlockRideFenceGate(Material materialIn) {
		super(materialIn);
	}
}
