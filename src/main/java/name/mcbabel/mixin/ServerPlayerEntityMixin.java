package name.mcbabel.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    // Fired when the server broadcasts a chat message to all players
    @Inject(at = @At("HEAD"), method = "sendChatMessage")
    private void onSendChatMessage(CallbackInfo info) {
        // This code is injected into the start of
        // ServerPlayerEntity.sendChatMessage(Lnet/minecraft/network/message/SentMessage;)V
        System.out.println("A player sent a chat message!");
    }
}