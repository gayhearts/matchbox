package xyz.sunrose.matchbox;

import net.fabricmc.api.ClientModInitializer;
import xyz.sunrose.matchbox.blocks.MatchboxBlocks;
import xyz.sunrose.matchbox.items.MatchboxItems;

public class MatchboxClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MatchboxBlocks.clientInit();
        MatchboxItems.clientInit();
    }
}
