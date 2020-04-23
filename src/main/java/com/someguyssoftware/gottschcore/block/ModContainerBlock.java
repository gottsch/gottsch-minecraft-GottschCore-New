/**
 * 
 */
package com.someguyssoftware.gottschcore.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/**
 * This class replaces BlockContainer so that it can extend ModBlock
 * 
 * @author Mark Gottschling onJan 2, 2018
 *
 */
public abstract class ModContainerBlock extends ModBlock implements ITileEntityProvider {

	/**
	 * 
	 * @param modID
	 * @param name
	 * @param properties
	 */
	public ModContainerBlock(String modID, String name, Block.Properties properties) {
		super(modID, name, properties);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return super.createTileEntity(state, world);
	}

	/**
	 * 
	 * @param worldIn
	 * @param pos
	 * @param facing
	 * @return
	 */
	protected boolean isInvalidNeighbor(World worldIn, BlockPos pos, Direction facing) {
		return worldIn.getBlockState(pos.offset(facing)).getMaterial() == Material.CACTUS;
	}

	/**
	 * 
	 * @param worldIn
	 * @param pos
	 * @return
	 */
//	protected boolean hasInvalidNeighbor(World worldIn, BlockPos pos) {
//		return this.isInvalidNeighbor(worldIn, pos, Direction.NORTH)
//				|| this.isInvalidNeighbor(worldIn, pos, Direction.SOUTH)
//				|| this.isInvalidNeighbor(worldIn, pos, Direction.WEST)
//				|| this.isInvalidNeighbor(worldIn, pos, Direction.EAST);
//	}

	/**
	 * The type of render function called. MODEL for mixed tesr and static model,
	 * MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids, INVISIBLE to
	 * skip all rendering. Since this class is abstract, default to no rendering.
	 */
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but
	 * before the Tile Entity is updated
	 */
//	@Override
//	public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
//		super.breakBlock(worldIn, pos, state);
//		worldIn.removeTileEntity(pos);
//	}

	/**
	 * Spawns the block's drops in the world. By the time this is called the Block
	 * has possibly been set to air via Block.removedByPlayer
	 */
//	@Override
//	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state,
//			@Nullable TileEntity te, ItemStack stack) {
//		if (te instanceof IWorldNameable && ((IWorldNameable) te).hasCustomName()) {
//			player.addStat(StatList.getBlockStats(this));
//			player.addExhaustion(0.005F);
//
//			if (worldIn.isRemote) {
//				return;
//			}
//
//			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
//			Item item = this.getItemDropped(state, worldIn.rand, i);
//
//			if (item == Items.AIR) {
//				return;
//			}
//
//			ItemStack itemstack = new ItemStack(item, this.quantityDropped(worldIn.rand));
//			itemstack.setStackDisplayName(((IWorldNameable) te).getName());
//			spawnAsEntity(worldIn, pos, itemstack);
//		} else {
//			super.harvestBlock(worldIn, player, pos, state, (TileEntity) null, stack);
//		}
//	}

	/**
	 * Called on server when World#addBlockEvent is called. If server returns true,
	 * then also called on the client. On the Server, this may perform additional
	 * changes to the world, like pistons replacing the block with an extended base.
	 * On the client, the update may involve replacing tile entities or effects such
	 * as sounds or particles
	 */
//	@SuppressWarnings("deprecation")
//	@Override
//	public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
//		super.eventReceived(state, worldIn, pos, id, param);
//		TileEntity tileentity = worldIn.getTileEntity(pos);
//		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
//	}
}
