package xyz.sunrose.matchbox;

import net.fabricmc.api.ModInitializer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.sunrose.matchbox.blocks.MatchboxBlocks;
import xyz.sunrose.matchbox.items.MatchboxItems;

public class Matchbox implements ModInitializer {
	public static final String MODID = "matchbox";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Identifier SAW_SOUND_ID = new Identifier(MODID, "saw");
	public static SoundEvent SAW_SOUND = Registry.register(
			Registry.SOUND_EVENT, SAW_SOUND_ID, new SoundEvent(SAW_SOUND_ID)
	);

	@Override
	public void onInitialize() {
		MatchboxItems.init();
		MatchboxBlocks.init();
	}
}
