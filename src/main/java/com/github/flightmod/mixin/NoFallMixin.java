package com.github.flightmod.mixin;

import com.github.flightmod.FlightMod;
import com.github.flightmod.modules.NoFall;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class NoFallMixin {

    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"))
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (!(packet instanceof ServerboundMovePlayerPacket)) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        if (mc.player.getAbilities().instabuild) {
            return;
        }
        if (!NoFall.isNoFallActive()) {
            return;
        }
        NoFall noFall = FlightMod.getNoFall();
        if (noFall == null || !noFall.isEnabled()) {
            return;
        }
        if (mc.player.getDeltaMovement().y > 0.0) {
            return;
        }
        if (mc.player.onGround()) {
            return;
        }

        // Use Accessor Mixin to set onGround
        ((ServerboundMovePlayerPacketAccessor) packet).setOnGround(true);
    }
}
