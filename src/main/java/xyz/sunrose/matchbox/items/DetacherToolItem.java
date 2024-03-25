package xyz.sunrose.matchbox.items;

import net.minecraft.block.*;
import net.minecraft.block.enums.WallShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import xyz.sunrose.matchbox.Matchbox;

public class DetacherToolItem extends Item {
    public DetacherToolItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if(blockState.getBlock() instanceof HorizontalConnectingBlock) { // TODO maybe check for side properties instead of this
            Direction side = hitSide(context);
            if (  blockState.get(ConnectingBlock.FACING_PROPERTIES.get(side)) ) { // only execute if the relevant side has a connection
                //detach the relevant side
                BlockState finalState = detachSide(world, blockState, blockPos, side);
                world.setBlockState(blockPos, finalState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE); //flags to not update neighbors

                world.playSound(
                        player, blockPos, Matchbox.SAW_SOUND,
                        SoundCategory.BLOCKS, 1f, 0.8f + 0.01f * world.random.nextFloat()
                );

                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                return ActionResult.success(world.isClient());
            }
        }

        else if(blockState.getBlock() instanceof WallBlock) {
            Direction side = hitSide(context);
            EnumProperty<WallShape> sideToCheck = null;
            switch (side) {
                case DOWN, UP -> throw new IllegalArgumentException("Matchbox getting invalid side input in wall's useOnBlock");
                case NORTH -> sideToCheck = WallBlock.NORTH_SHAPE;
                case SOUTH -> sideToCheck = WallBlock.SOUTH_SHAPE;
                case WEST -> sideToCheck = WallBlock.WEST_SHAPE;
                case EAST -> sideToCheck = WallBlock.EAST_SHAPE;
            }
            if (  blockState.get(sideToCheck) != WallShape.NONE) { // only execute if the relevant side has a connection
                //detach the relevant side
                BlockState finalState = detachWallSide(world, blockState, blockPos, side, sideToCheck);
                world.setBlockState(blockPos, finalState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE); //flags to not update neighbors

                world.playSound(
                        player, blockPos, Matchbox.SAW_SOUND,
                        SoundCategory.BLOCKS, 1f, 0.8f + 0.01f * world.random.nextFloat()
                );

                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
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

    private BlockState detachWallSide(World world, BlockState state, BlockPos pos, Direction dir, EnumProperty<WallShape> side) {
        //TODO fix vertical stuff

        // disconnect the wall on our side...
        BlockState newState = state.with(side, WallShape.NONE).with(WallBlock.UP, true);
        BlockState neighbor = world.getBlockState(pos.offset(dir));
        if (neighbor.getBlock() instanceof WallBlock) {
            world.setBlockState(pos.offset(dir), neighbor.with(oppositeSide(dir), WallShape.NONE).with(WallBlock.UP, true), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        }
        return newState;
    }

    private EnumProperty<WallShape> oppositeSide(Direction side) {
        switch (side) {
            case NORTH -> {
                return WallBlock.SOUTH_SHAPE;
            }
            case SOUTH -> {
                return WallBlock.NORTH_SHAPE;
            }
            case WEST -> {
                return WallBlock.EAST_SHAPE;
            }
            case EAST -> {
                return WallBlock.WEST_SHAPE;
            }
            default -> throw new IllegalArgumentException("Matchbox getting invalid input to oppositeSide");
        }
    }

    private Direction hitSide(ItemUsageContext context) {
        Vec3d pos = context.getHitPos();
        //position relative to center of block
        double relX = MathHelper.fractionalPart(pos.x) - 0.5;
        double relZ = MathHelper.fractionalPart(pos.z) - 0.5;
        double absX = MathHelper.sign(relX) * relX;
        double absZ = MathHelper.sign(relZ) * relZ;

        if (relX < 0 && absX >= absZ) { //negative X - West
            return Direction.WEST;
        }
        else if (relX >= 0 && absX >= absZ) { //positive X - East
            return Direction.EAST;
        }
        else if (relZ < 0 && absZ > absX) { //negative Z - North
            return Direction.NORTH;
        }
        else if (relZ >= 0 && absZ > absX) { //positive Z - South
            return Direction.SOUTH;
        }
        else { // ????
            Matchbox.LOGGER.debug("Invalid hit location, defaulting to north");
            return Direction.NORTH;
        }

    }
}
