package com.titanscape.server.task;

import com.titanscape.server.TitanScape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The main game engine that runs the 600ms game tick loop.
 */
public final class GameEngine {

    private static final Logger logger = LogManager.getLogger(GameEngine.class);

    private final TitanScape server;
    private Thread gameThread;
    private volatile boolean running;

    public GameEngine(TitanScape server) {
        this.server = server;
    }

    public void start() {
        running = true;
        gameThread = new Thread(this::loop, "GameEngine");
        gameThread.setDaemon(true);
        gameThread.start();
        logger.info("Game engine started ({}ms cycle).", TitanScape.CYCLE_RATE_MS);
    }

    private void loop() {
        while (running) {
            long startTime = System.currentTimeMillis();
            try {
                server.getWorld().process();
            } catch (Exception e) {
                logger.error("Error in game cycle", e);
            }
            long elapsed = System.currentTimeMillis() - startTime;
            long sleepTime = TitanScape.CYCLE_RATE_MS - elapsed;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } else {
                logger.warn("Game cycle took {}ms (over budget by {}ms)", elapsed, -sleepTime);
            }
        }
    }

    public void stop() {
        running = false;
        if (gameThread != null) {
            gameThread.interrupt();
        }
        logger.info("Game engine stopped.");
    }

    public boolean isRunning() {
        return running;
    }
}
