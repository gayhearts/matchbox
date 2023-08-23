package xyz.sunrose.matchbox.blocks;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.sunrose.matchbox.Matchbox;

public class MatchboxBlocks {
    private static final Identifier MESH_ID = new Identifier(Matchbox.MODID, "mesh");
    public static final Block MESH = Registry.register(
            Registries.BLOCK, MESH_ID,
            new MeshBlock(FabricBlockSettings.copyOf(Blocks.CHAIN).strength(4,6).nonOpaque())
    );
    public static final Item MESH_ITEM = Registry.register(
            Registries.ITEM, MESH_ID,
            new BlockItem(MESH, new FabricItemSettings())
    );

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(MESH_ITEM));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(MESH_ITEM));
    }

    public static void clientInit() {
        BlockRenderLayerMap.INSTANCE.putBlock(MESH, RenderLayer.getCutoutMipped());
    }
}
