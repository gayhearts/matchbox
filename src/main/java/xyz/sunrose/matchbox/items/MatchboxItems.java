package xyz.sunrose.matchbox.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.sunrose.matchbox.Matchbox;

public class MatchboxItems {
    public static Item MATCHBOX = Registry.register(
            Registry.ITEM, new Identifier(Matchbox.MODID, "matchbox"),
            new MatchboxToolItem(new FabricItemSettings().maxDamage(64).group(ItemGroup.TOOLS))
    );

    public static Item DETACHER = Registry.register(
            Registry.ITEM, new Identifier(Matchbox.MODID, "detacher"),
            new DetacherToolItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS))
    );

    public static Item WOOD_GLUE = Registry.register(
            Registry.ITEM, new Identifier(Matchbox.MODID, "wood_glue"),
            new WoodGlueItem(new FabricItemSettings().maxDamage(64).group(ItemGroup.TOOLS))
    );

    /*public static Item REDSTONE_TOOL = Registry.register(
            Registry.ITEM, new Identifier(Matchbox.MODID, "redstone_tool"),
            new RedstoneToolItem(new FabricItemSettings().maxCount(1).group(ItemGroup.REDSTONE))
    );*/

    public static void init () {

    }
}
