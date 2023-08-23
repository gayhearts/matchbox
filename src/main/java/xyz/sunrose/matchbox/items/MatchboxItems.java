package xyz.sunrose.matchbox.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.sunrose.matchbox.Matchbox;

public class MatchboxItems {
    public static Item MATCHBOX = Registry.register(
            Registries.ITEM, new Identifier(Matchbox.MODID, "matchbox"),
            new MatchboxToolItem(new FabricItemSettings().maxDamage(64))
    );

    public static Item DETACHER = Registry.register(
            Registries.ITEM, new Identifier(Matchbox.MODID, "detacher"),
            new DetacherToolItem(new FabricItemSettings().maxCount(1))
    );

    public static Item WOOD_GLUE = Registry.register(
            Registries.ITEM, new Identifier(Matchbox.MODID, "wood_glue"),
            new WoodGlueItem(new FabricItemSettings().maxDamage(64))
    );

    /*public static Item REDSTONE_TOOL = Registry.register(
            Registry.ITEM, new Identifier(Matchbox.MODID, "redstone_tool"),
            new RedstoneToolItem(new FabricItemSettings().maxCount(1).group(ItemGroup.REDSTONE))
    );*/

    public static void init () {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(MATCHBOX);
            entries.add(DETACHER);
            entries.add(WOOD_GLUE);
        });
    }
}
