package xyz.sunrose.matchbox.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.sunrose.matchbox.mixin.AccessorRedstoneWireBlock;

public class FakeRedstoneWireBlock extends RedstoneWireBlock {
    public static final IntProperty UPDATE_TIME = IntProperty.of("update_time", 0, 2);
    public FakeRedstoneWireBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateWithProperties(super.getDefaultState()).with(UPDATE_TIME, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(UPDATE_TIME);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (!((AccessorRedstoneWireBlock)this).getWiresGivePower() || direction == Direction.DOWN) {
            return 0;
        }
        int i = state.get(POWER);
        if (i == 0) {
            return 0;
        }
        if (direction == Direction.UP || (((AccessorRedstoneWireBlock)this).invokeGetPlacementState(world, state, pos).get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction.getOpposite()))).isConnected()) {
            return i;
        }
        return 0;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
