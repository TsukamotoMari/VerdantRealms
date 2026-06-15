package com.titanscape.server.model.content.minigame;

import com.titanscape.server.model.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for custom minigames.
 */
public abstract class Minigame {

    private final String name;
    private final int minPlayers;
    private final int maxPlayers;
    private final List<Player> participants = new ArrayList<>();
    private boolean active;
    private int tickCounter;

    protected Minigame(String name, int minPlayers, int maxPlayers) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public boolean join(Player player) {
        if (participants.size() >= maxPlayers) {
            player.sendMessage("The " + name + " is full!");
            return false;
        }
        participants.add(player);
        player.sendMessage("You have joined " + name + "!");
        if (participants.size() >= minPlayers && !active) {
            start();
        }
        return true;
    }

    public void leave(Player player) {
        participants.remove(player);
        player.sendMessage("You have left " + name + ".");
        if (participants.size() < minPlayers && active) {
            end();
        }
    }

    public void process() {
        if (!active) return;
        tickCounter++;
        tick();
    }

    protected abstract void start();
    protected abstract void end();
    protected abstract void tick();
    protected abstract void onPlayerDeath(Player player);

    public String getName() { return name; }
    public List<Player> getParticipants() { return participants; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public int getTickCounter() { return tickCounter; }
}
