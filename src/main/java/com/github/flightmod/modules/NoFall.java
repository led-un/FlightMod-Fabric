/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 */
package com.github.flightmod.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class NoFall {
    private final Minecraft mc = Minecraft.getInstance();
    private boolean enabled = false;
    private Mode mode = Mode.PACKET;
    private static boolean noFallActive = false;

    public void toggle() {
        this.enabled = !this.enabled;
        noFallActive = this.enabled;
        LocalPlayer player = this.mc.player;
        if (player == null) {
            return;
        }
        if (this.enabled) {
            player.sendSystemMessage((Component)Component.literal((String)("\u00a7a[NoFall] \u00a7f\u5df2\u5f00\u542f (" + this.mode.name() + ")")));
        } else {
            player.sendSystemMessage((Component)Component.literal((String)"\u00a7c[NoFall] \u00a7f\u5df2\u5173\u95ed"));
        }
    }

    public void onClientTick(Minecraft client) {
        if (!this.enabled || this.mc.player == null) {
            return;
        }
        LocalPlayer player = this.mc.player;
        if (this.mode == Mode.SIMPLE && player.fallDistance > 2.5) {
            player.fallDistance = 0.0;
        }
    }

    public boolean shouldForceOnGround() {
        if (!this.enabled || this.mode != Mode.PACKET) {
            return false;
        }
        LocalPlayer player = this.mc.player;
        if (player == null) {
            return false;
        }
        if (player.onGround()) {
            return false;
        }
        if (player.getAbilities().instabuild) {
            return false;
        }
        return player.getDeltaMovement().y < 0.0;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public static boolean isNoFallActive() {
        return noFallActive;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public static enum Mode {
        PACKET,
        SIMPLE;

    }
}
