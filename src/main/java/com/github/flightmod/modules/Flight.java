/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1656
 *  net.minecraft.class_2561
 *  net.minecraft.class_2596
 *  net.minecraft.class_2842
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 */
package com.github.flightmod.modules;

import java.lang.reflect.Field;
import net.minecraft.class_1656;
import net.minecraft.class_2561;
import net.minecraft.class_2596;
import net.minecraft.class_2842;
import net.minecraft.class_310;
import net.minecraft.class_746;

public class Flight {
    private final class_310 mc = class_310.method_1551();
    private boolean enabled = false;
    private float speed = 0.1f;
    private static Field flyingSpeedField = null;

    private static void setFlySpeed(class_1656 abilities, float speed) {
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
        class_746 player = this.mc.field_1724;
        if (player == null) {
            return;
        }
        if (this.enabled) {
            this.onActivate(player);
            player.method_7353((class_2561)class_2561.method_43470((String)"\u00a7a[Flight] \u00a7f\u5df2\u5f00\u542f"), true);
        } else {
            this.onDeactivate(player);
            player.method_7353((class_2561)class_2561.method_43470((String)"\u00a7c[Flight] \u00a7f\u5df2\u5173\u95ed"), true);
        }
    }

    private void onActivate(class_746 player) {
        if (!player.method_68878()) {
            class_1656 abilities = player.method_31549();
            abilities.field_7478 = true;
            if (!abilities.field_7477) {
                abilities.field_7479 = true;
            }
            Flight.setFlySpeed(abilities, this.speed);
            this.sendAbilitiesPacket();
        }
    }

    private void onDeactivate(class_746 player) {
        if (!player.method_68878()) {
            class_1656 abilities = player.method_31549();
            abilities.field_7478 = false;
            Flight.setFlySpeed(abilities, 0.05f);
            if (!abilities.field_7477) {
                abilities.field_7479 = false;
            }
            this.sendAbilitiesPacket();
        }
    }

    public void onClientTick(class_310 client) {
        if (!this.enabled || this.mc.field_1724 == null) {
            return;
        }
        class_746 player = this.mc.field_1724;
        if (!player.method_68878()) {
            player.method_31549().field_7478 = true;
            Flight.setFlySpeed(player.method_31549(), this.speed);
        }
    }

    private void sendAbilitiesPacket() {
        if (this.mc.field_1724 != null && this.mc.method_1562() != null) {
            this.mc.method_1562().method_52787((class_2596)new class_2842(this.mc.field_1724.method_31549()));
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
            flyingSpeedField = class_1656.class.getDeclaredField("flyingSpeed");
            flyingSpeedField.setAccessible(true);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}
