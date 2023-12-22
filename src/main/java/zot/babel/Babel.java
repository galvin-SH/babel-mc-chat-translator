package zot.babel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.kyori.adventure.platform.fabric.PlayerLocales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.cdimascio.dotenv.Dotenv;
import net.minecraft.util.Identifier;

public class Babel implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	public static final String MOD_ID = "babel";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier LOCALE_UPDATED = new Identifier(MOD_ID, "locale_updated");

	private FabricServerAudiences adventure;

	public FabricServerAudiences adventure() {
		if (this.adventure == null) {
			throw new IllegalStateException("Tried to access adventure before it was initialized");
		}
		return this.adventure;
	}

	@Override
	public void onInitialize() {
		// Log successful initialization
		LOGGER.info("Babel Initialized");

		// Load environment variables from .env file
		// Access them anywhere with System.getenv("VAR_NAME")
		Dotenv dotenv = Dotenv.configure().systemProperties().load();
		if (dotenv != null) {
			LOGGER.info("Loaded .env file");
		} else {
			LOGGER.info("No .env file found");
		}

		// Register event listeners for player locale changes
		PlayerLocales.CHANGED_EVENT.register((player, locale) -> {
			// Get the player's name and locale
			PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
			playerState.playerName = player.getName().getString();
			playerState.playerLocale = locale.toLanguageTag();
			LOGGER.info("Player " + playerState.playerName + " changed locale to " + locale.toLanguageTag());
		});

		// Register event listeners for server lifecycle events
		ServerLifecycleEvents.SERVER_STARTING.register(server -> this.adventure = FabricServerAudiences.of(server));
		ServerLifecycleEvents.SERVER_STOPPED.register(server -> this.adventure = null);
	}
}