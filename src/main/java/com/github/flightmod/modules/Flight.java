package com.github.flightmod.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.world.entity.player.Abilities;

import java.lang.reflect.Field;

public class Flight {
    private final Minecraft mc = Minecraft.getInstance();
    private boolean enabled = false;
    private float speed = 0.1f;

    private static Field flyingSpeedField = null;

    static {
        try {
            flyingSpeedField = Abilities.class.getDeclaredField("flyingSpeed");
            flyingSpeedField.setAccessible(true);
        } catch (Exception ignored) {
        }
    }

    private static void setFlySpeed(Abilities abilities, float speed) {
        if (flyingSpeedField != null) {
            try {
                flyingSpeedField.setFloat(abilities, speed);
            } catch (Exception ignored) {
            }
        }
    }

    public void toggle() {
        this.enabled = !this.enabled;
        LocalPlayer player = this.mc.player;
        if (player == null) {
            return;
        }
        if (this.enabled) {
            this.onActivate(player);
            player.sendSystemMessage(Component.literal("§a[Flight] §f已开启"));
        } else {
            this.onDeactivate(player);
            player.sendSystemMessage(Component.literal("§c[Flight] §f已关闭"));
        }
    }

    private void onActivate(LocalPlayer player) {
        if (!player.isCreative()) {
            Abilities abilities = player.getAbilities();
            abilities.mayfly = true;
            if (!abilities.instabuild) {
                abilities.flying = true;
            }
            setFlySpeed(abilities, this.speed);
            this.sendAbilitiesPacket();
        }
    }

    private void onDeactivate(LocalPlayer player) {
        if (!player.isCreative()) {
            Abilities abilities = player.getAbilities();
            abilities.mayfly = false;
            setFlySpeed(abilities, 0.05f);
            if (!abilities.instabuild) {
                abilities.flying = false;
            }
            this.sendAbilitiesPacket();
        }
    }

    public void onClientTick(Minecraft client) {
        if (!this.enabled || this.mc.player == null) {
            return;
        }
        LocalPlayer player = this.mc.player;

        if (!player.isCreative()) {
            player.getAbilities().mayfly = true;
            setFlySpeed(player.getAbilities(), this.speed);
        }
    }

    private void sendAbilitiesPacket() {
        if (this.mc.player != null && this.mc.getConnection() != null) {
            this.mc.getConnection().send(new ServerboundPlayerAbilitiesPacket(this.mc.player.getAbilities()));
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
