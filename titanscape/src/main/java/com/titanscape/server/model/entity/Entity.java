package com.titanscape.server.model.entity;

import com.titanscape.server.model.world.Position;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base class for all game entities (players, NPCs).
 */
public abstract class Entity {

    private int index = -1;
    private Position position;
    private int hitpoints;
    private int maxHitpoints;
    private boolean isDead;
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();

    public Entity(Position position, int maxHitpoints) {
        this.position = position;
        this.maxHitpoints = maxHitpoints;
        this.hitpoints = maxHitpoints;
    }

    public abstract void process();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = Math.max(0, Math.min(hitpoints, maxHitpoints));
        if (this.hitpoints <= 0 && !isDead) {
            isDead = true;
            onDeath();
        }
    }

    public void heal(int amount) {
        setHitpoints(hitpoints + amount);
    }

    public void damage(int amount) {
        setHitpoints(hitpoints - amount);
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(int maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key, T defaultValue) {
        Object val = attributes.get(key);
        return val != null ? (T) val : defaultValue;
    }

    protected abstract void onDeath();
}
