package zot.babel.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.network.message.SentMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    // Fired when the server broadcasts a chat message to all players
    @Inject(at = @At("HEAD"), method = "sendChatMessage")
    private void onSendChatMessage(CallbackInfo info) {
        // This code is injected into the start of
        // ServerPlayerEntity.sendChatMessage(Lnet/minecraft/network/message/SentMessage;)V
        System.out.println("A player sent a chat message!");
    }

    @Inject(at = @At("TAIL"), method = "sendChatMessage")
    private void injected(CallbackInfo ci, @Local(ordinal = 0) SentMessage message) {
        // This code is injected into the end of
        // ServerPlayerEntity.sendChatMessage(Lnet/minecraft/network/message/SentMessage;)V
        System.out.println("The message was: " + message.getContent().getString());
    }
}