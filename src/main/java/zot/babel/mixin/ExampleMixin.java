package zot.babel.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mixin(MinecraftServer.class)
public class ExampleMixin {
	private static final Logger LOGGER = LoggerFactory.getLogger("babel");

	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
		// This code is injected into the start of MinecraftServer.loadWorld()V
		LOGGER.info("This line is printed by ExampleMixin!");
		LOGGER.info("The World is loading!");
	}

	@Inject(at = @At("RETURN"), method = "loadWorld")
	private void init2(CallbackInfo info) {
		// This code is injected into the end of MinecraftServer.loadWorld()V
		LOGGER.info("The World has finished loading!");
	}
}