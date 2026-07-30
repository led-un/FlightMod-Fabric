/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.KeyMapping$Category
 *  net.minecraft.client.Minecraft
 */
package com.github.flightmod;

import com.github.flightmod.FlightMod;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class KeyHandler {
    private final Minecraft mc = Minecraft.getInstance();
    private KeyMapping flightKey;
    private KeyMapping noFallKey;
    private boolean fPressed = false;
    private boolean nPressed = false;

    public void registerKeys() {
        this.flightKey = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("key.flightmod.flight", InputConstants.Type.KEYSYM, 74, KeyMapping.Category.MISC));
        this.noFallKey = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("key.flightmod.nofall", InputConstants.Type.KEYSYM, 75, KeyMapping.Category.MISC));
    }

    public void onClientTick(Minecraft client) {
        if (this.mc.player == null || this.mc.screen != null) {
            return;
        }
        if (this.flightKey != null && this.flightKey.isDown()) {
            if (!this.fPressed) {
                this.fPressed = true;
                FlightMod.getFlight().toggle();
            }
        } else {
            this.fPressed = false;
        }
        if (this.noFallKey != null && this.noFallKey.isDown()) {
            if (!this.nPressed) {
                this.nPressed = true;
                FlightMod.getNoFall().toggle();
            }
        } else {
            this.nPressed = false;
        }
    }
}
