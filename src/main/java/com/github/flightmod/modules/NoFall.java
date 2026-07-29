package com.github.flightmod.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class NoFall {
    private final Minecraft mc = Minecraft.getInstance();
    private boolean enabled = false;
    private static boolean noFallActive = false;

    public void toggle() {
        noFallActive = this.enabled = !this.enabled;
        LocalPlayer player = this.mc.player;
        if (player == null) {
            return;
        }
        if (this.enabled) {
            player.displayClientMessage(Component.literal("§a[NoFall] §f已开启"), true);
        } else {
            player.displayClientMessage(Component.literal("§c[NoFall] §f已关闭"), true);
        }
    }

    public void onClientTick(Minecraft client) {
        // NoFall works entirely through the Mixin, no tick logic needed
    }

    public boolean shouldForceOnGround() {
        if (!this.enabled) {
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
        // Trigger whenever falling (not ascending), not only at high speed
        return player.getDeltaMovement().y < 0.0;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public static boolean isNoFallActive() {
        return noFallActive;
    }
}
