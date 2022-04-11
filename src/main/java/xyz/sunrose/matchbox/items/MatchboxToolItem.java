package xyz.sunrose.matchbox.items;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class MatchboxToolItem extends Item {
    public MatchboxToolItem(Settings settings) {
        super(settings);
    }


    public ActionResult useOnBlock(ItemUsageContext context) {
        // basically copied from flint ant steel
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState)) {
            // TODO custom sound
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
            world.setBlockState(blockPos, (BlockState)blockState.with(Properties.LIT, true),
                    Block.REDRAW_ON_MAIN_THREAD | Block.NOTIFY_LISTENERS | Block.NOTIFY_NEIGHBORS
            );
            world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);
            if (playerEntity != null) {
                context.getStack().damage(1, playerEntity, (p) -> {
                    p.sendToolBreakStatus(context.getHand());
                });
            }

            return ActionResult.success(world.isClient());
        }
        return ActionResult.FAIL;
    }
}
