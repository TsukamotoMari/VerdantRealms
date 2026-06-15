package com.titanscape.server.model.content.minigame;

import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.item.Item;
import com.titanscape.server.model.world.Position;

/**
 * TITAN ARENA — A wave-based survival minigame.
 * Players fight increasingly difficult waves of monsters.
 * Rewards scale with waves survived.
 */
public final class TitanArena extends Minigame {

    public static final Position ENTRANCE = new Position(3000, 3000);
    private int currentWave;
    private int monstersRemaining;

    public TitanArena() {
        super("Titan Arena", 1, 4);
    }

    @Override
    protected void start() {
        setActive(true);
        currentWave = 0;
        for (Player p : getParticipants()) {
            p.sendMessage("=== TITAN ARENA BEGINS ===");
            p.sendMessage("Survive as many waves as you can!");
        }
        nextWave();
    }

    @Override
    protected void end() {
        setActive(false);
        for (Player p : getParticipants()) {
            giveRewards(p);
            p.setPosition(ENTRANCE);
            p.sendMessage("Titan Arena ended! You survived " + currentWave + " waves.");
        }
    }

    @Override
    protected void tick() {
        if (monstersRemaining <= 0 && getTickCounter() % 10 == 0) {
            nextWave();
        }
    }

    @Override
    protected void onPlayerDeath(Player player) {
        player.sendMessage("You have fallen in the Titan Arena!");
        leave(player);
        giveRewards(player);
    }

    private void nextWave() {
        currentWave++;
        monstersRemaining = 3 + currentWave * 2;
        for (Player p : getParticipants()) {
            p.sendMessage("--- Wave " + currentWave + " --- (" + monstersRemaining + " monsters)");
        }
    }

    private void giveRewards(Player player) {
        int coins = currentWave * 25000;
        player.getInventory().add(new Item(995, coins));
        player.sendMessage("Arena reward: " + coins + " coins!");
        if (currentWave >= 10) {
            player.getInventory().add(new Item(20013)); // Arena Champion Token
            player.sendMessage("@red@You earned an Arena Champion Token!");
        }
        if (currentWave >= 25) {
            player.getInventory().add(new Item(20014)); // Titan Arena Cape
            player.sendMessage("@red@You earned the Titan Arena Cape!");
        }
    }

    public int getCurrentWave() {
        return currentWave;
    }
}
