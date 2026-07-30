/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Connection
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ServerboundMovePlayerPacket
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.github.flightmod.mixin;

import com.github.flightmod.FlightMod;
import com.github.flightmod.mixin.ServerboundMovePlayerPacketAccessor;
import com.github.flightmod.modules.NoFall;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Connection.class})
public class NoFallMixin {
    @Inject(method={"send(Lnet/minecraft/network/protocol/Packet;)V"}, at={@At(value="HEAD")})
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
        if (noFall.getMode() != NoFall.Mode.PACKET) {
            return;
        }
        if (mc.player.getDeltaMovement().y > 0.0) {
            return;
        }
        if (mc.player.onGround()) {
            return;
        }
        ((ServerboundMovePlayerPacketAccessor)packet).setOnGround(true);
    }
}
