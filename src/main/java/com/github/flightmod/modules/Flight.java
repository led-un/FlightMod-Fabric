/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket
 *  net.minecraft.world.entity.player.Abilities
 */
package com.github.flightmod.modules;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.world.entity.player.Abilities;

public class Flight {
    private final Minecraft mc = Minecraft.getInstance();
    private boolean enabled = false;
    private float speed = 0.1f;
    private static Field flyingSpeedField = null;

    private static void setFlySpeed(Abilities abilities, float speed) {
        if (flyingSpeedField != null) {
            try {
                flyingSpeedField.setFloat(abilities, speed);
            }
            catch (Exception exception) {
                // empty catch block
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
            player.sendSystemMessage((Component)Component.literal((String)"\u00a7a[Flight] \u00a7f\u5df2\u5f00\u542f"));
        } else {
            this.onDeactivate(player);
            player.sendSystemMessage((Component)Component.literal((String)"\u00a7c[Flight] \u00a7f\u5df2\u5173\u95ed"));
        }
    }

    private void onActivate(LocalPlayer player) {
        if (!player.isCreative()) {
            Abilities abilities = player.getAbilities();
            abilities.mayfly = true;
            if (!abilities.instabuild) {
                abilities.flying = true;
            }
            Flight.setFlySpeed(abilities, this.speed);
            this.sendAbilitiesPacket();
        }
    }

    private void onDeactivate(LocalPlayer player) {
        if (!player.isCreative()) {
            Abilities abilities = player.getAbilities();
            abilities.mayfly = false;
            Flight.setFlySpeed(abilities, 0.05f);
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
            Flight.setFlySpeed(player.getAbilities(), this.speed);
        }
    }

    private void sendAbilitiesPacket() {
        if (this.mc.player != null && this.mc.getConnection() != null) {
            this.mc.getConnection().send((Packet)new ServerboundPlayerAbilitiesPacket(this.mc.player.getAbilities()));
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

    static {
        try {
            flyingSpeedField = Abilities.class.getDeclaredField("flyingSpeed");
            flyingSpeedField.setAccessible(true);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}
