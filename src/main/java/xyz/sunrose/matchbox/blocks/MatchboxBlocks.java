package xyz.sunrose.matchbox.blocks;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.sunrose.matchbox.Matchbox;

public class MatchboxBlocks {
    /*public static final Block FAKE_REDSTONE = Registry.register(
            Registry.BLOCK, new Identifier(Matchbox.MODID, "fake_redstone"),
            new FakeRedstoneWireBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_WIRE))
    );*/

    private static final Identifier MESH_ID = new Identifier(Matchbox.MODID, "mesh");
    public static final Block MESH = Registry.register(
            Registry.BLOCK, MESH_ID,
            new MeshBlock(FabricBlockSettings.copyOf(Blocks.CHAIN).strength(4,6))
    );
    public static final Item MESH_ITEM = Registry.register(
            Registry.ITEM, MESH_ID,
            new BlockItem(MESH, new FabricItemSettings().group(ItemGroup.REDSTONE))
    );

    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(MESH, RenderLayer.getCutoutMipped());
    }
}
