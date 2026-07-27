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
    private int tickCounter = 0;
    private boolean flip = false;
    private float lastYaw = 0.0f;

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
            player.displayClientMessage(Component.literal("§a[Flight] §f已开启"), true);
        } else {
            this.onDeactivate(player);
            player.displayClientMessage(Component.literal("§c[Flight] §f已关闭"), true);
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

        ++this.tickCounter;
        if (this.tickCounter >= 20) {
            this.tickCounter = 0;
            if (player.fallDistance >= 3.0f) {
                float currentYaw = player.getYRot();
                if (currentYaw == this.lastYaw) {
                    player.setYRot(currentYaw + (float) (this.flip ? 1 : -1));
                    this.flip = !this.flip;
                }
                this.lastYaw = player.getYRot();
            }
        }

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
