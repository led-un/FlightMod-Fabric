/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2535
 *  net.minecraft.class_2596
 *  net.minecraft.class_2828
 *  net.minecraft.class_310
 *  net.minecraft.class_7648
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.github.flightmod.mixin;

import com.github.flightmod.FlightMod;
import com.github.flightmod.mixin.ServerboundMovePlayerPacketAccessor;
import com.github.flightmod.modules.NoFall;
import net.minecraft.class_2535;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import net.minecraft.class_310;
import net.minecraft.class_7648;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_2535.class})
public class NoFallMixin {
    @Inject(method={"send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;Z)V"}, at={@At(value="HEAD")})
    private void onSendPacket(class_2596<?> packet, class_7648 listener, boolean flush, CallbackInfo ci) {
        if (!(packet instanceof class_2828)) {
            return;
        }
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null) {
            return;
        }
        if (mc.field_1724.method_31549().field_7477) {
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
        if (mc.field_1724.method_18798().field_1351 > -0.5) {
            return;
        }
        if (mc.field_1724.method_24828()) {
            return;
        }
        ((ServerboundMovePlayerPacketAccessor)packet).setOnGround(true);
    }
}
