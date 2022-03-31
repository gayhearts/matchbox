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
            new MatchboxTool(new FabricItemSettings().maxDamage(64).group(ItemGroup.TOOLS))
    );

    public static Item DETACHER = Registry.register(
            Registry.ITEM, new Identifier(Matchbox.MODID, "detacher"),
            new DetacherTool(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS))
    );

    public static void init () {

    }
}
