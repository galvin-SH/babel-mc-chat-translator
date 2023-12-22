package zot.babel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.kyori.adventure.platform.fabric.PlayerLocales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.cdimascio.dotenv.Dotenv;

public class Babel implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	//
	public static final String MOD_ID = "babel";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private FabricServerAudiences adventure;

	public FabricServerAudiences adventure() {
		if (this.adventure == null) {
			throw new IllegalStateException("Tried to access adventure before it was initialized");
		}
		return this.adventure;
	}

	@Override
	public void onInitialize() {

		// Load environment variables from .env file
		// Access them anywhere with System.getenv("VAR_NAME")
		Dotenv dotenv = Dotenv.configure().systemProperties().load();
		if (dotenv != null) {
			LOGGER.info("Loaded .env file");
		} else {
			LOGGER.info("No .env file found");
		}

		// Log successful initialization
		LOGGER.info("Babel Initialized");

		PlayerLocales.CHANGED_EVENT.register((player, locale) -> {
			LOGGER.info("Player " + player.getName().getString() + " changed locale to " + locale.toLanguageTag());
		});

		// Register event listeners for server lifecycle events
		ServerLifecycleEvents.SERVER_STARTING.register(server -> this.adventure = FabricServerAudiences.of(server));
		ServerLifecycleEvents.SERVER_STOPPED.register(server -> this.adventure = null);
	}
}