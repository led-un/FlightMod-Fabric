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
        noFallActive = this.enabled = !this.enabled;
        LocalPlayer player = this.mc.player;
        if (player == null) {
            return;
        }
        if (this.enabled) {
            player.displayClientMessage(Component.literal("§a[NoFall] §f已开启 (" + this.mode.name() + ")"), true);
        } else {
            player.displayClientMessage(Component.literal("§c[NoFall] §f已关闭"), true);
        }
    }

    public void onClientTick(Minecraft client) {
        if (!this.enabled || this.mc.player == null) {
            return;
        }
        LocalPlayer player = this.mc.player;
        if (this.mode == Mode.SIMPLE && player.fallDistance > 2.5f) {
            player.fallDistance = 0.0f;
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
        return player.getDeltaMovement().y < -0.5;
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

    public enum Mode {
        PACKET,
        SIMPLE
    }
}
