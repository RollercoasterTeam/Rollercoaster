package robomuss.rc.block.te;

import com.sun.corba.se.impl.encoding.TypeCodeInputStream;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.piece.TrackPieceSlopeDown;
import robomuss.rc.track.style.TrackStyle;

import java.util.ArrayList;
import java.util.List;

/*@Optional.InterfaceList(
       @Optional.Interface(iface = "pneumaticCraft.api.tileentity.IPneumaticMachine", modid = "PneumaticCraft")
)*/
public class TileEntityTrackBase extends TileEntity {
	//private IAirHandler airHandler;
	public BlockTrackBase track;
	public int trackMeta;
	public boolean converted = false;
	public boolean isDummy = false;
	public int dummyID = 0;
	public TrackExtra extra;
	public int colour = 0;
	public TrackStyle style;

	private TrackPiece trackPieceListHolder;
	private String trackPieceListID;
	private int trackPieceListIndex = -1;

	private List<TileEntityTrackBase> dummies = new ArrayList<TileEntityTrackBase>();

	public TileEntityTrackBase() {}

	public TileEntityTrackBase(World world, int trackMeta, BlockTrackBase track) {
		this.worldObj = world;
		this.trackMeta = trackMeta;
		this.track = track;
	}

	public void setListTypeAndIndex(TrackPiece type, int index) {
		if (type != null) {
			this.trackPieceListHolder = type;
			this.trackPieceListID = type.unlocalized_name;
			this.trackPieceListIndex = index;
		}
	}

	public TrackPiece getTrackPieceListHolder() {
		return this.trackPieceListHolder;
	}

	public String getTrackPieceListID() {
		return this.trackPieceListID;
	}

	public int getTrackPieceListIndex() {
		return this.trackPieceListIndex;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.colour = compound.getInteger("colour");
		this.converted = compound.getBoolean("converted");
		this.isDummy = compound.getBoolean("dummy");
		this.dummyID = compound.getInteger("dummyID");
		this.worldObj = super.getWorldObj();

		for (TrackStyle style : TrackHandler.styles) {
			if (style.getId().contains(compound.getString("styleName"))) {
				this.style = style;
			}
		}

		int extraID = compound.getInteger("extraID");
		this.extra = extraID == -1 ? null : TrackHandler.findTrackExtra(extraID);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (!this.converted || this.style == null) {
			this.style = TrackHandler.findTrackStyle("corkscrew");
			this.converted = true;
		}

		compound.setInteger("colour", this.colour);
		compound.setString("styleName", this.style.getId());
		compound.setBoolean("converted", this.converted);
		compound.setBoolean("dummy", this.isDummy);
		compound.setInteger("dummyID", this.dummyID);
		compound.setInteger("extraID", this.extra != null ? this.extra.id : -1);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		if (this.getWorldObj().getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) > 11) {
			this.isDummy = true;
		}

		readFromNBT(packet.func_148857_g());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		Block block = this.getWorldObj().getBlock(xCoord, yCoord, zCoord);
		TrackPiece type = TrackHandler.findTrackTypeFull(block.getUnlocalizedName()).type;

		if (type != null) {
			return type.getRenderBoundingBox(this.getWorldObj(), xCoord, yCoord, zCoord);
		} else {
			return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
		}
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	public void placeDummies(TrackPiece track_type) {
		if (!this.worldObj.isRemote) {
			this.track = (BlockTrackBase) this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
			this.trackMeta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			int numDummies = track_type.number_of_dummies;

			if (this.track.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
				switch (this.trackMeta) {
					case 2: /* North */
						this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord - 1, this.track.track_type.block);
						this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord - 1, this.track.track_type.block);
						this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord - 2, this.track.track_type.block);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord - 1, trackMeta + 10, 2);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord + 1, this.zCoord - 1, trackMeta + 10, 2);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord + 1, this.zCoord - 2, trackMeta + 10, 2);
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1)).dummyID = 0;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord - 1)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord - 1)).dummyID = 1;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord - 2)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord - 2)).dummyID = 2;
						break;
					case 3: /* South */
						this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord + 1, this.track.track_type.block);
						this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord + 1, this.track.track_type.block);
						this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord + 2, this.track.track_type.block);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord + 1, trackMeta + 10, 2);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord + 1, this.zCoord + 1, trackMeta + 10, 2);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord + 1, this.zCoord + 2, trackMeta + 10, 2);
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1)).dummyID = 0;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord + 1)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord + 1)).dummyID = 1;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord + 2)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord + 2)).dummyID = 2;
						break;
					case 4: /* West */
						this.worldObj.setBlock(this.xCoord - 1, this.yCoord, this.zCoord, this.track.track_type.block);
						this.worldObj.setBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord, this.track.track_type.block);
						this.worldObj.setBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord, this.track.track_type.block);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord - 1, this.yCoord, this.zCoord, trackMeta + 10, 2);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord - 1, this.yCoord + 1, this.zCoord, trackMeta + 10, 2);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord - 2, this.yCoord + 1, this.zCoord, trackMeta + 10, 2);
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord)).dummyID = 0;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord + 1, this.zCoord)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord + 1, this.zCoord)).dummyID = 1;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord - 2, this.yCoord + 1, this.zCoord)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord - 2, this.yCoord + 1, this.zCoord)).dummyID = 2;
						break;
					case 5: /* East */
						this.worldObj.setBlock(this.xCoord + 1, this.yCoord, this.zCoord, this.track.track_type.block);
						this.worldObj.setBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord, this.track.track_type.block);
						this.worldObj.setBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord, this.track.track_type.block);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord + 1, this.yCoord, this.zCoord, trackMeta + 10, 2);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord + 1, this.yCoord + 1, this.zCoord, trackMeta + 10, 2);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord + 2, this.yCoord + 1, this.zCoord, trackMeta + 10, 2);
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord)).dummyID = 0;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord + 1, this.zCoord)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord + 1, this.zCoord)).dummyID = 1;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord + 2, this.yCoord + 1, this.zCoord)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord + 2, this.yCoord + 1, this.zCoord)).dummyID = 2;
						break;
				}
			} else {
				switch (this.trackMeta) {
					case 2: /* North */
						this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord - 1, this.track.track_type.block);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord - 1, trackMeta + 10, 2);
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1)).dummyID = 0;
						break;
					case 3: /* South */
						this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord + 1, this.track.track_type.block);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord + 1, trackMeta + 10, 2);
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1)).dummyID = 0;
						break;
					case 4: /* West */
						this.worldObj.setBlock(this.xCoord - 1, this.yCoord, this.zCoord, this.track.track_type.block);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord - 1, this.yCoord, this.zCoord, trackMeta + 10, 2);
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord)).dummyID = 0;
						break;
					case 5: /* East */
						this.worldObj.setBlock(this.xCoord + 1, this.yCoord, this.zCoord, this.track.track_type.block);
						this.worldObj.setBlockMetadataWithNotify(this.xCoord + 1, this.yCoord, this.zCoord, trackMeta + 10, 2);
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord)).isDummy = true;
						((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord)).dummyID = 0;
						break;
				}
			}
		}
	}

	private void reorient(boolean counterClockwise, int currentFacing) {
		int nextFacing = 0;

		switch (currentFacing) {
			case 2: nextFacing = counterClockwise ? 4 : 5; break;
			case 3: nextFacing = counterClockwise ? 5 : 4; break;
			case 4: nextFacing = counterClockwise ? 3 : 2; break;
			case 5: nextFacing = counterClockwise ? 2 : 3; break;
		}

		this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, nextFacing, 2);
	}

	private void reorientDummies(boolean counterClockwise, int currentFacing) {
		int nextFacing = 0;
		boolean isDummy = currentFacing >= 11;
		currentFacing = isDummy ? currentFacing - 10 : currentFacing;

		switch (currentFacing) {
			case 2: nextFacing = counterClockwise ? 4 : 5; break;
			case 3: nextFacing = counterClockwise ? 5 : 4; break;
			case 4: nextFacing = counterClockwise ? 3 : 2; break;
			case 5: nextFacing = counterClockwise ? 2 : 3; break;
		}

		int offsetX = isDummy ? ForgeDirection.getOrientation(nextFacing).getOpposite().offsetX : ForgeDirection.getOrientation(nextFacing).offsetX;
		int offsetY = isDummy ? ForgeDirection.getOrientation(nextFacing).getOpposite().offsetY : ForgeDirection.getOrientation(nextFacing).offsetY;
		int offsetZ = isDummy ? ForgeDirection.getOrientation(nextFacing).getOpposite().offsetZ : ForgeDirection.getOrientation(nextFacing).offsetZ;

		int currentOffsetX = isDummy ? ForgeDirection.getOrientation(currentFacing).getOpposite().offsetX : ForgeDirection.getOrientation(currentFacing).offsetX;
		int currentOffsetY = isDummy ? ForgeDirection.getOrientation(currentFacing).getOpposite().offsetY : ForgeDirection.getOrientation(currentFacing).offsetY;
		int currentOffsetZ = isDummy ? ForgeDirection.getOrientation(currentFacing).getOpposite().offsetZ : ForgeDirection.getOrientation(currentFacing).offsetZ;

		Block blockReplaced = this.worldObj.getBlock(this.xCoord + offsetX, this.yCoord + offsetY, this.zCoord + offsetZ);
		int metaReplaced = this.worldObj.getBlockMetadata(this.xCoord + offsetX, this.yCoord + offsetY, this.zCoord + offsetZ);
		BlockSnapshot snapshotReplaced = new BlockSnapshot(this.worldObj, this.xCoord + offsetX, this.yCoord + offsetY, this.zCoord + offsetZ, blockReplaced, metaReplaced);

		if (!snapshotReplaced.world.isAirBlock(snapshotReplaced.x, snapshotReplaced.y, snapshotReplaced.z) && !(snapshotReplaced.world.getBlock(snapshotReplaced.x, snapshotReplaced.y, snapshotReplaced.z) instanceof BlockLiquid)) {
			snapshotReplaced.restore();
		} else {
			if (!isDummy) {
				snapshotReplaced.world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, nextFacing, 2);
				snapshotReplaced.world.setBlockToAir(this.xCoord + currentOffsetX, this.yCoord + currentOffsetY, this.zCoord + currentOffsetZ);
				snapshotReplaced.world.setBlock(snapshotReplaced.x, snapshotReplaced.y, snapshotReplaced.z, this.track.track_type.block);
				snapshotReplaced.world.setBlockMetadataWithNotify(snapshotReplaced.x, snapshotReplaced.y, snapshotReplaced.z, nextFacing + 10, 2);
				((TileEntityTrackBase) snapshotReplaced.world.getTileEntity(snapshotReplaced.x, snapshotReplaced.y, snapshotReplaced.z)).isDummy = true;
			} else {
				snapshotReplaced.world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, nextFacing + 10, 2);
				snapshotReplaced.world.setBlockToAir(this.xCoord + currentOffsetX, this.yCoord + currentOffsetY, this.zCoord + currentOffsetZ);
				snapshotReplaced.world.setBlock(snapshotReplaced.x, snapshotReplaced.y, snapshotReplaced.z, this.track.track_type.block);
				snapshotReplaced.world.setBlockMetadataWithNotify(snapshotReplaced.x, snapshotReplaced.y, snapshotReplaced.z, nextFacing, 2);
			}
		}
	}

	public void rotate(boolean counterClockwise) {
		int currentFacing = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		BlockTrackBase track = (BlockTrackBase) this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);

		if (TrackManager.isSloped(track.track_type)) {
			this.reorientDummies(counterClockwise, currentFacing);
		} else {
			this.reorient(counterClockwise, currentFacing);
		}
	}

	/**
	 * returns the height in blocks of the hill starting or ending at the passed-in coordinates.
	 * if track at coordinates is not a slope up or slope down, will return -1.
	 */
//	public int getHeightOfHill(int x, int y, int z) {
//		Block camouflage = this.worldObj.getBlock(x, y, z);
//
//		if (!(camouflage instanceof BlockTrackBase)) {
//			return -1;
//		} else {
//			if (((BlockTrackBase) camouflage).track_type != TrackHandler.findTrackType("slope_up") && ((BlockTrackBase) camouflage).track_type != TrackHandler.findTrackType("slope_down")) {
//				return -1;
//			} else {
//				int meta = this.getWorldObj().getBlockMetadata(x, y, z);
//				boolean isDummy = meta > 11;
//				meta = isDummy ? meta - 10 : meta;
//
//			}
//		}
//
//		return -1;
//	}

	/*@Optional.Method(modid = "PneumaticCraft")
	@Override
	public IAirHandler getAirHandler() {
		if(airHandler == null) {
			airHandler = AirHandlerSupplier.getAirHandler(5, 7, 50, 2000);
		}
		return airHandler;
	}

	@Optional.Method(modid = "PneumaticCraft")
	@Override
	public boolean isConnectedTo(ForgeDirection side) {
		if(this.extra != null && this.extra instanceof TrackExtraAirLauncher) {
			return true;
		}
		else {
			return false;
		}
	}*/

	/*@Override
	public void updateEntity() {
		super.updateEntity();
		getAirHandler().updateEntityI();
	}

	@Override
	public void validate() {
		super.validate();
		getAirHandler().validateI(this);
	}*/
}
