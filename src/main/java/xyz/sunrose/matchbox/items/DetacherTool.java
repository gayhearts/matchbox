package xyz.sunrose.matchbox.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.HorizontalConnectingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class DetacherTool extends Item {
    public DetacherTool(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if(blockState.getBlock() instanceof HorizontalConnectingBlock block) {
            if (  blockState.get(ConnectingBlock.NORTH)
                ||blockState.get(ConnectingBlock.EAST)
                ||blockState.get(ConnectingBlock.SOUTH)
                ||blockState.get(ConnectingBlock.WEST)) {

                //this is a mess lol but basically just detach all the sides
                //this would be so much easier in haskell smh
                BlockState finalState = detachSide(
                        world, detachSide(
                                world, detachSide(
                                        world, detachSide(
                                                world, blockState, blockPos, Direction.NORTH
                                        ), blockPos, Direction.EAST
                                ), blockPos, Direction.SOUTH
                        ), blockPos, Direction.WEST
                );
                world.setBlockState(blockPos, finalState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE); //flags to not update?
                // TODO custom sound
                BlockSoundGroup soundGroup = block.getSoundGroup(blockState);
                world.playSound(
                        player, blockPos, soundGroup.getBreakSound(),
                        SoundCategory.BLOCKS, 0.9f, soundGroup.getPitch() * 0.8f
                );
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                if (player != null) {
                    context.getStack().damage(1, player, (p) -> {
                        p.sendToolBreakStatus(context.getHand());
                    });
                }
                return ActionResult.success(world.isClient());
            }
        }

        return super.useOnBlock(context);
    }

    private BlockState detachSide(World world, BlockState state, BlockPos pos, Direction dir){
        // set the state to false
        BlockState newState = state.with(ConnectingBlock.FACING_PROPERTIES.get(dir), false);
        // set the neightbor's opposite-direction state to false if applicable
        BlockState neighbor = world.getBlockState(pos.offset(dir));
        if (neighbor.getBlock() instanceof HorizontalConnectingBlock) {
            Direction opposite = dir.getOpposite();
            world.setBlockState(pos.offset(dir), neighbor.with(ConnectingBlock.FACING_PROPERTIES.get(opposite), false), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        }
        return newState;
    }
}
