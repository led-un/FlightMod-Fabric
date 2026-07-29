package com.github.flightmod;

import com.github.flightmod.modules.Flight;
import com.github.flightmod.modules.NoFall;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlightMod implements ClientModInitializer {
    public static final String MOD_ID = "flightmod";
    public static final Logger LOGGER = LoggerFactory.getLogger("flightmod");

    private static Flight flight;
    private static NoFall noFall;
    private static KeyHandler keyHandler;

    @Override
    public void onInitializeClient() {
        flight = new Flight();
        noFall = new NoFall();
        keyHandler = new KeyHandler();

        // Register tick events
        ClientTickEvents.END_CLIENT_TICK.register(flight::onClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(noFall::onClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(keyHandler::onClientTick);

        // Register key bindings
        keyHandler.registerKeys();

        LOGGER.info("FlightMod loaded! J=Flight, K=NoFall");
    }

    public static Flight getFlight() {
        return flight;
    }

    public static NoFall getNoFall() {
        return noFall;
    }
}
