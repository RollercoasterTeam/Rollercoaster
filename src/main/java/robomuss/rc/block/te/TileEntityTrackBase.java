package robomuss.rc.block.te;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.chat.ChatHandler;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.piece.TrackPiece;
import net.minecraft.block.Block;
import robomuss.rc.track.style.TrackStyle;

/*@Optional.InterfaceList(
       @Optional.Interface(iface = "pneumaticCraft.api.tileentity.IPneumaticMachine", modid = "PneumaticCraft")
)*/
public class TileEntityTrackBase extends TileEntity {
	//private IAirHandler airHandler;

	public BlockTrackBase track;
	public int trackMeta;
//    public ForgeDirection direction;

	public boolean converted = false;
	public boolean isDummy = false;
//	public boolean isDummy;
	public TrackExtra extra;
	public int colour = 0;
	public TrackStyle style;

	private TrackPiece trackPieceListHolder;
	private String trackPieceListID;
	private int trackPieceListIndex = -1;

	public TileEntityTrackBase() {}

	public TileEntityTrackBase(World world, int trackMeta, BlockTrackBase track) {
		this.worldObj = world;
		this.trackMeta = trackMeta;
		this.track = track;
//		this.isDummy = false;
//		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
//		this.markDirty();
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
//		System.out.println("Colour: " + this.colour);

		this.converted = compound.getBoolean("converted");
//		System.out.println("Converted: " + this.converted);

		this.isDummy = compound.getBoolean("dummy");

		for (TrackStyle style : TrackHandler.styles) {
			if (style.getId().contains(compound.getString("styleName"))) {
				this.style = style;
//				System.out.println("Style: " + this.style.name);
			}
		}

		int extraID = compound.getInteger("extraID");
//		System.out.println("ExtraID: " + extraID);

		if (extraID == -1) {
			this.extra = null;
//			System.out.println("extra null");
		} else {
			this.extra = TrackHandler.extras.get(extraID);
//			System.out.println("Extra: " + this.extra.name);
		}

		this.track = (BlockTrackBase) this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);

		this.trackMeta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (this.extra != null) {
			compound.setInteger("extraID", this.extra.id);
		}

		if (!this.converted || this.style == null) {
			this.style = TrackHandler.findTrackStyle("corkscrew");
			this.converted = true;
		}

		compound.setInteger("colour", this.colour);
		compound.setString("styleName", this.style.getId());
		compound.setBoolean("converted", this.converted);
		compound.setBoolean("dummy", this.isDummy);

		if (this.extra != null) {
			compound.setInteger("extraID", this.extra.id);
		} else {
			compound.setInteger("extraID", -1);
		}
	}

	@Override
	public Packet getDescriptionPacket() {
//		findTrack();
//		System.out.println("getDescriptionPacket!");
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
//		findTrack();
//		ChatHandler.broadcastChatMessageToPlayers("packet received!");
		if (this.getWorldObj().getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) > 11) {
			this.isDummy = true;
		}
		readFromNBT(packet.func_148857_g());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		Block block = this.getWorldObj().getBlock(xCoord, yCoord, zCoord);
		TrackPiece type = TrackHandler.findTrackTypeFull(block.getUnlocalizedName());
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

	public void placeDummy() {
		if (!this.worldObj.isRemote) {
//			System.out.println("world not remote");
			this.track = (BlockTrackBase) this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
			this.trackMeta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);

			switch (this.trackMeta) {
				case 2:
					System.out.println("north");
					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord - 1, this.track.track_type.block);
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord - 1, trackMeta + 10, 2);
					((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1)).isDummy = true;
					break;
				case 3:
					System.out.println("south");
					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord + 1, this.track.track_type.block);
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord + 1, trackMeta + 10, 2);
					((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1)).isDummy = true;
					break;
				case 4:
					System.out.println("west");
					this.worldObj.setBlock(this.xCoord - 1, this.yCoord, this.zCoord, this.track.track_type.block);
					this.worldObj.setBlockMetadataWithNotify(this.xCoord - 1, this.yCoord, this.zCoord, trackMeta + 10, 2);
					((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord)).isDummy = true;
					break;
				case 5:
					System.out.println("east");
					this.worldObj.setBlock(this.xCoord + 1, this.yCoord, this.zCoord, this.track.track_type.block);
					this.worldObj.setBlockMetadataWithNotify(this.xCoord + 1, this.yCoord, this.zCoord, trackMeta + 10, 2);
					((TileEntityTrackBase) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord)).isDummy = true;
					break;
			}
		}
	}

	public void rotate() {
		int currentFacing = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);

		currentFacing = currentFacing > 11 ? currentFacing - 10 : currentFacing;
		BlockSnapshot snapshot;
		if (TrackManager.isSloped(TrackManager.getTrackType(this.track))) {
			if (!this.isDummy) {
				if (currentFacing == 2) {   //N->E
					Block blockEast = this.worldObj.getBlock(this.xCoord + 1, this.yCoord, this.zCoord);
					int metaEast = this.worldObj.getBlockMetadata(this.xCoord + 1, this.yCoord, this.zCoord);
					snapshot = new BlockSnapshot(this.worldObj, this.xCoord + 1, this.yCoord, this.zCoord, blockEast, metaEast);

					if (!snapshot.world.isAirBlock(snapshot.x, snapshot.y, snapshot.z)) {
						this.unRotate();
						snapshot.restore();
					} else {
						snapshot.world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 5, 2);
						snapshot.world.setBlockToAir(this.xCoord, this.yCoord, this.zCoord - 1);
						snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track.track_type.block);
//					snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track);
						snapshot.world.setBlockMetadataWithNotify(snapshot.x, snapshot.y, snapshot.z, 5, 2);
//					((TileEntityTrackBase) snapshot.getTileEntity()).isDummy = true;
						((TileEntityTrackBase) snapshot.world.getTileEntity(snapshot.x, snapshot.y, snapshot.z)).isDummy = true;
					}
				} else if (currentFacing == 3) {   //S->W
					Block blockWest = this.worldObj.getBlock(this.xCoord - 1, this.yCoord, this.zCoord);
					int metaWest = this.worldObj.getBlockMetadata(this.xCoord - 1, this.yCoord, this.zCoord);
					snapshot = new BlockSnapshot(this.worldObj, this.xCoord - 1, this.yCoord, this.zCoord, blockWest, metaWest);

					if (!snapshot.world.isAirBlock(snapshot.x, snapshot.y, snapshot.z)) {
						this.unRotate();
						snapshot.restore();
					} else {
						snapshot.world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 4, 2);
						snapshot.world.setBlockToAir(this.xCoord, this.yCoord, this.zCoord + 1);
						snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track.track_type.block);
//					snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track);
						snapshot.world.setBlockMetadataWithNotify(snapshot.x, snapshot.y, snapshot.z, 4, 2);
//					((TileEntityTrackBase) snapshot.getTileEntity()).isDummy = true;
						((TileEntityTrackBase) snapshot.world.getTileEntity(snapshot.x, snapshot.y, snapshot.z)).isDummy = true;
					}
				} else if (currentFacing == 4) {   //W->N
					Block blockNorth = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord - 1);
					int metaNorth = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord - 1);
					snapshot = new BlockSnapshot(this.worldObj, this.xCoord, this.yCoord, this.zCoord - 1, blockNorth, metaNorth);

					if (!snapshot.world.isAirBlock(snapshot.x, snapshot.y, snapshot.z)) {
						this.unRotate();
						snapshot.restore();
					} else {
						snapshot.world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 2, 2);
						snapshot.world.setBlockToAir(this.xCoord - 1, this.yCoord, this.zCoord);
						snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track.track_type.block);
//					snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track);
						snapshot.world.setBlockMetadataWithNotify(snapshot.x, snapshot.y, snapshot.z, 2, 2);
//					((TileEntityTrackBase) snapshot.getTileEntity()).isDummy = true;
						((TileEntityTrackBase) snapshot.world.getTileEntity(snapshot.x, snapshot.y, snapshot.z)).isDummy = true;
					}
				} else if (currentFacing == 5) {   //E->S
					Block blockSouth = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord + 1);
					int metaSouth = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord + 1);
					snapshot = new BlockSnapshot(this.worldObj, this.xCoord, this.yCoord, this.zCoord + 1, blockSouth, metaSouth);

					if (!snapshot.world.isAirBlock(snapshot.x, snapshot.y, snapshot.z)) {
						this.unRotate();
						snapshot.restore();
					} else {
						snapshot.world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 3, 2);
						snapshot.world.setBlockToAir(this.xCoord + 1, this.yCoord, this.zCoord);
						snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track.track_type.block);
//					snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track);
						snapshot.world.setBlockMetadataWithNotify(snapshot.x, snapshot.y, snapshot.z, 3, 2);
//					((TileEntityTrackBase) snapshot.getTileEntity()).isDummy = true;
						((TileEntityTrackBase) snapshot.world.getTileEntity(snapshot.x, snapshot.y, snapshot.z)).isDummy = true;
					}
				}
			} else {
				currentFacing = currentFacing > 11 ? currentFacing - 10 : currentFacing;
//			System.out.println("dummy");
				if (currentFacing == 2) {   //N->E
					Block blockWest = this.getWorldObj().getBlock(this.xCoord - 1, this.yCoord, this.zCoord);
					int metaWest = this.getWorldObj().getBlockMetadata(this.xCoord - 1, this.yCoord, this.zCoord);
					snapshot = new BlockSnapshot(this.worldObj, this.xCoord - 1, this.yCoord, this.zCoord, blockWest, metaWest);

//				Block blockSouth = this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord + 1);
//				int metaSouth = this.getWorldObj().getBlockMetadata(this.xCoord, this.yCoord, this.zCoord + 1);
//				snapshot = new BlockSnapshot(this.worldObj, this.xCoord, this.yCoord, this.zCoord + 1, blockSouth, metaSouth);

					if (!snapshot.world.isAirBlock(snapshot.x, snapshot.y, snapshot.z)) {
						this.unRotate();
						snapshot.restore();
					} else {
						snapshot.world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 5, 2);
						snapshot.world.setBlockToAir(this.xCoord, this.yCoord, this.zCoord + 1);
						snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track.track_type.block);
//					snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track);
						snapshot.world.setBlockMetadataWithNotify(snapshot.x, snapshot.y, snapshot.z, 5, 2);
					}
				} else if (currentFacing == 3) {   //S->W
					Block blockEast = this.getWorldObj().getBlock(this.xCoord + 1, this.yCoord, this.zCoord);
					int metaEast = this.getWorldObj().getBlockMetadata(this.xCoord + 1, this.yCoord, this.zCoord);
					snapshot = new BlockSnapshot(this.worldObj, this.xCoord + 1, this.yCoord, this.zCoord, blockEast, metaEast);

//				Block blockNorth = this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord - 1);
//				int metaNorth = this.getWorldObj().getBlockMetadata(this.xCoord, this.yCoord, this.zCoord - 1);
//				snapshot = new BlockSnapshot(this.worldObj, this.xCoord, this.yCoord, this.zCoord - 1, blockNorth, metaNorth);

					if (!snapshot.world.isAirBlock(snapshot.x, snapshot.y, snapshot.z)) {
						this.unRotate();
						snapshot.restore();
					} else {
						snapshot.world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 4, 2);
						snapshot.world.setBlockToAir(this.xCoord, this.yCoord, this.zCoord - 1);
						snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track.track_type.block);
//					snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track);
						snapshot.world.setBlockMetadataWithNotify(snapshot.x, snapshot.y, snapshot.z, 4, 2);
					}
				} else if (currentFacing == 4) {   //W->N
					Block blockSouth = this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord + 1);
					int metaSouth = this.getWorldObj().getBlockMetadata(this.xCoord, this.yCoord, this.zCoord + 1);
					snapshot = new BlockSnapshot(this.worldObj, this.xCoord, this.yCoord, this.zCoord + 1, blockSouth, metaSouth);

//				Block blockEast = this.getWorldObj().getBlock(this.xCoord + 1, this.yCoord, this.zCoord);
//				int metaEast = this.getWorldObj().getBlockMetadata(this.xCoord + 1, this.yCoord, this.zCoord);
//				snapshot = new BlockSnapshot(this.worldObj, this.xCoord + 1, this.yCoord, this.zCoord, blockEast, metaEast);

					if (!snapshot.world.isAirBlock(snapshot.x, snapshot.y, snapshot.z)) {
						this.unRotate();
						snapshot.restore();
					} else {
						snapshot.world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 2, 2);
						snapshot.world.setBlockToAir(this.xCoord + 1, this.yCoord, this.zCoord);
						snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track.track_type.block);
//					snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track);
						snapshot.world.setBlockMetadataWithNotify(snapshot.x, snapshot.y, snapshot.z, 2, 2);
					}
				} else if (currentFacing == 5) {   //E->S
					Block blockNorth = this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord - 1);
					int metaNorth = this.getWorldObj().getBlockMetadata(this.xCoord, this.yCoord, this.zCoord - 1);
					snapshot = new BlockSnapshot(this.worldObj, this.xCoord, this.yCoord, this.zCoord - 1, blockNorth, metaNorth);

//				Block blockWest = this.getWorldObj().getBlock(this.xCoord - 1, this.yCoord, this.zCoord);
//				int metaWest = this.getWorldObj().getBlockMetadata(this.xCoord - 1, this.yCoord, this.zCoord);
//				snapshot = new BlockSnapshot(this.worldObj, this.xCoord - 1, this.yCoord, this.zCoord, blockWest, metaWest);

					if (!snapshot.world.isAirBlock(snapshot.x, snapshot.y, snapshot.z)) {
						this.unRotate();
						snapshot.restore();
					} else {
						snapshot.world.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 3, 2);
						snapshot.world.setBlockToAir(this.xCoord - 1, this.yCoord, this.zCoord);
						snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track.track_type.block);
//					snapshot.world.setBlock(snapshot.x, snapshot.y, snapshot.z, this.track);
						snapshot.world.setBlockMetadataWithNotify(snapshot.x, snapshot.y, snapshot.z, 3, 2);
					}
				}
			}
		} else {
			if (currentFacing == 2) {
				this.getWorldObj().setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 5, 2);
			} else if (currentFacing == 3) {
				this.getWorldObj().setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 4, 2);
			} else if (currentFacing == 4) {
				this.getWorldObj().setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 2, 2);
			} else if (currentFacing == 5) {
				this.getWorldObj().setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 3, 2);
			}
		}
	}

	public void unRotate() {
		int currentFacing = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);

//		if (!this.isDummy) {
//			if (currentFacing == 2) {
//				this.getWorldObj().setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 4, 2);
//			}
//		}
	}

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
