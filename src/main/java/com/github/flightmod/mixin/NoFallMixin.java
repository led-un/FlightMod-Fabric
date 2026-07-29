package com.github.flightmod.mixin;

import com.github.flightmod.FlightMod;
import com.github.flightmod.modules.NoFall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class NoFallMixin {

    @Shadow
    @Final
    private Connection connection;

    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true)
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

        // Cancel the original packet and send a modified one with onGround=true
        ci.cancel();

        ServerboundMovePlayerPacket original = (ServerboundMovePlayerPacket) packet;
        ServerboundMovePlayerPacket modified;

        if (original.hasPosition() && original.hasRotation()) {
            modified = new ServerboundMovePlayerPacket.PosRot(
                    original.getX(0), original.getY(0), original.getZ(0),
                    original.getYRot(0), original.getXRot(0),
                    true  // onGround = true
            );
        } else if (original.hasPosition()) {
            modified = new ServerboundMovePlayerPacket.Pos(
                    original.getX(0), original.getY(0), original.getZ(0),
                    true  // onGround = true
            );
        } else if (original.hasRotation()) {
            modified = new ServerboundMovePlayerPacket.Rot(
                    original.getYRot(0), original.getXRot(0),
                    true  // onGround = true
            );
        } else {
            modified = new ServerboundMovePlayerPacket.StatusOnly(
                    true  // onGround = true
            );
        }

        this.connection.send(modified);
    }
}
