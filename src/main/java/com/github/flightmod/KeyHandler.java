/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
 *  net.minecraft.class_304
 *  net.minecraft.class_310
 *  net.minecraft.class_3675$class_307
 */
package com.github.flightmod;

import com.github.flightmod.FlightMod;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_3675;

public class KeyHandler {
    private final class_310 mc = class_310.method_1551();
    private class_304 flightKey;
    private class_304 noFallKey;
    private boolean fPressed = false;
    private boolean nPressed = false;

    public void registerKeys() {
        this.flightKey = KeyBindingHelper.registerKeyBinding((class_304)new class_304("key.flightmod.flight", class_3675.class_307.field_1668, 74, "category.flightmod"));
        this.noFallKey = KeyBindingHelper.registerKeyBinding((class_304)new class_304("key.flightmod.nofall", class_3675.class_307.field_1668, 75, "category.flightmod"));
    }

    public void onClientTick(class_310 client) {
        if (this.mc.field_1724 == null || this.mc.field_1755 != null) {
            return;
        }
        if (this.flightKey != null && this.flightKey.method_1434()) {
            if (!this.fPressed) {
                this.fPressed = true;
                FlightMod.getFlight().toggle();
            }
        } else {
            this.fPressed = false;
        }
        if (this.noFallKey != null && this.noFallKey.method_1434()) {
            if (!this.nPressed) {
                this.nPressed = true;
                FlightMod.getNoFall().toggle();
            }
        } else {
            this.nPressed = false;
        }
    }
}
