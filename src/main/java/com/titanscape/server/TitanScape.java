package com.titanscape.server;

import com.titanscape.server.definition.ItemDefinitionLoader;
import com.titanscape.server.definition.NpcDefinitionLoader;
import com.titanscape.server.definition.BossDefinitionLoader;
import com.titanscape.server.definition.ShopDefinitionLoader;
import com.titanscape.server.definition.DropTableLoader;
import com.titanscape.server.definition.AreaDefinitionLoader;
import com.titanscape.server.model.world.World;
import com.titanscape.server.net.NetworkServer;
import com.titanscape.server.task.GameEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TitanScape — A custom RuneScape Private Server featuring unique items,
 * bosses with advanced AI mechanics, custom areas, minigames, and more.
 */
public final class TitanScape {

    private static final Logger logger = LogManager.getLogger(TitanScape.class);

    public static final String SERVER_NAME = "TitanScape";
    public static final int REVISION = 317;
    public static final int PORT = 43594;
    public static final int CYCLE_RATE_MS = 600;

    private static TitanScape instance;

    private final World world;
    private final GameEngine gameEngine;
    private final NetworkServer networkServer;

    private volatile boolean running;

    private TitanScape() {
        this.world = new World();
        this.gameEngine = new GameEngine(this);
        this.networkServer = new NetworkServer(PORT);
    }

    public static TitanScape getInstance() {
        if (instance == null) {
            instance = new TitanScape();
        }
        return instance;
    }

    public void start() {
        logger.info("Starting {} v{} on port {}...", SERVER_NAME, REVISION, PORT);
        long startTime = System.currentTimeMillis();

        loadDefinitions();
        world.init();
        gameEngine.start();
        networkServer.bind();

        running = true;
        long elapsed = System.currentTimeMillis() - startTime;
        logger.info("{} is now online! Startup took {}ms.", SERVER_NAME, elapsed);
    }

    private void loadDefinitions() {
        logger.info("Loading game definitions...");
        ItemDefinitionLoader.load("data/items/");
        NpcDefinitionLoader.load("data/npcs/");
        BossDefinitionLoader.load("data/bosses/");
        ShopDefinitionLoader.load("data/shops/");
        DropTableLoader.load("data/drops/");
        AreaDefinitionLoader.load("data/areas/");
        logger.info("All definitions loaded.");
    }

    public void shutdown() {
        logger.info("Shutting down {}...", SERVER_NAME);
        running = false;
        world.saveAll();
        gameEngine.stop();
        networkServer.shutdown();
        logger.info("{} has been shut down.", SERVER_NAME);
    }

    public World getWorld() {
        return world;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public boolean isRunning() {
        return running;
    }

    public static void main(String[] args) {
        try {
            TitanScape server = TitanScape.getInstance();
            server.start();

            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        } catch (Exception e) {
            logger.fatal("Failed to start TitanScape!", e);
            System.exit(1);
        }
    }
}
