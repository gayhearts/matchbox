package xyz.sunrose.matchbox.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import xyz.sunrose.matchbox.blocks.MatchboxBlocks;

public class RedstoneToolItem extends Item {
    public RedstoneToolItem(Settings settings) {
        super(settings);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 16;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    /*public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        BlockHitResult hit = raycast(world, (PlayerEntity) user, RaycastContext.FluidHandling.NONE);
        BlockPos pos = hit.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if(state.isOf(Blocks.REDSTONE_WIRE)) {
            BlockState newState = MatchboxBlocks.FAKE_REDSTONE.getStateWithProperties(state).with(RedstoneWireBlock.POWER, 15);
            world.setBlockState(
                    pos, newState, Block.REDRAW_ON_MAIN_THREAD | Block.NOTIFY_LISTENERS | Block.NOTIFY_NEIGHBORS | Block.FORCE_STATE
            );
        }
        return stack;
    }*/
}
