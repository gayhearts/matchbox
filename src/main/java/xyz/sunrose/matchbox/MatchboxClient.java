package xyz.sunrose.matchbox;

import net.fabricmc.api.ClientModInitializer;
import xyz.sunrose.matchbox.blocks.MatchboxBlocks;

public class MatchboxClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MatchboxBlocks.clientInit();
    }
}
