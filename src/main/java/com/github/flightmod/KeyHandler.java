package com.github.flightmod;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {
    private final Minecraft mc = Minecraft.getInstance();
    private KeyMapping flightKey;
    private KeyMapping noFallKey;

    private boolean fPressed = false;
    private boolean nPressed = false;

    public void registerKeys() {
        KeyMapping.Category cat = new KeyMapping.Category(
            ResourceLocation.fromNamespaceAndPath("flightmod", "main")
        );

        flightKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.flightmod.flight",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                cat
        ));

        noFallKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.flightmod.nofall",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                cat
        ));
    }

    public void onClientTick(Minecraft client) {
        if (mc.player == null || mc.screen != null) {
            return;
        }

        if (flightKey != null && flightKey.isDown()) {
            if (!fPressed) {
                fPressed = true;
                FlightMod.getFlight().toggle();
            }
        } else {
            fPressed = false;
        }

        if (noFallKey != null && noFallKey.isDown()) {
            if (!nPressed) {
                nPressed = true;
                FlightMod.getNoFall().toggle();
            }
        } else {
            nPressed = false;
        }
    }
}
