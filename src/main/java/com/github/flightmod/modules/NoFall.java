/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 */
package com.github.flightmod.modules;

import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_746;

public class NoFall {
    private final class_310 mc = class_310.method_1551();
    private boolean enabled = false;
    private Mode mode = Mode.PACKET;
    private static boolean noFallActive = false;

    public void toggle() {
        this.enabled = !this.enabled;
        noFallActive = this.enabled;
        class_746 player = this.mc.field_1724;
        if (player == null) {
            return;
        }
        if (this.enabled) {
            player.method_7353((class_2561)class_2561.method_43470((String)("\u00a7a[NoFall] \u00a7f\u5df2\u5f00\u542f (" + this.mode.name() + ")")), true);
        } else {
            player.method_7353((class_2561)class_2561.method_43470((String)"\u00a7c[NoFall] \u00a7f\u5df2\u5173\u95ed"), true);
        }
    }

    public void onClientTick(class_310 client) {
        if (!this.enabled || this.mc.field_1724 == null) {
            return;
        }
        class_746 player = this.mc.field_1724;
        if (this.mode == Mode.SIMPLE && player.field_6017 > 2.5) {
            player.field_6017 = 0.0;
        }
    }

    public boolean shouldForceOnGround() {
        if (!this.enabled || this.mode != Mode.PACKET) {
            return false;
        }
        class_746 player = this.mc.field_1724;
        if (player == null) {
            return false;
        }
        if (player.method_24828()) {
            return false;
        }
        if (player.method_31549().field_7477) {
            return false;
        }
        return player.method_18798().field_1351 < 0.0;
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
