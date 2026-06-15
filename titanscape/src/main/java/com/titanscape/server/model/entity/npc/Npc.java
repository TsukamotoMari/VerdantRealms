package com.titanscape.server.model.entity.npc;

import com.titanscape.server.definition.NpcDefinitionLoader;
import com.titanscape.server.model.entity.Entity;
import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.world.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a non-player character in the game world.
 */
public class Npc extends Entity {

    private static final Logger logger = LogManager.getLogger(Npc.class);

    private final int npcId;
    private final NpcDefinition definition;
    private final Position spawnPosition;
    private Entity target;
    private int respawnTicks;
    private int respawnCountdown;
    private boolean aggressive;
    private int wanderRadius;

    public Npc(int npcId, Position position) {
        super(position, 10);
        this.npcId = npcId;
        this.definition = NpcDefinitionLoader.get(npcId);
        this.spawnPosition = position;
        if (definition != null) {
            setMaxHitpoints(definition.getHitpoints());
            setHitpoints(definition.getHitpoints());
            this.aggressive = definition.isAggressive();
            this.respawnTicks = definition.getRespawnTicks();
            this.wanderRadius = definition.getWanderRadius();
        }
    }

    @Override
    public void process() {
        if (isDead()) {
            if (respawnCountdown > 0) {
                respawnCountdown--;
            }
            if (respawnCountdown <= 0) {
                respawn();
            }
            return;
        }

        if (target != null) {
            processCombat();
        } else if (wanderRadius > 0) {
            processWander();
        }
    }

    protected void processCombat() {
        if (target == null || target.isDead()) {
            target = null;
            return;
        }
        if (!getPosition().isWithinDistance(target.getPosition(), getAttackRange())) {
            moveTowards(target.getPosition());
        }
    }

    protected void processWander() {
        if (Math.random() < 0.1) {
            int dx = (int) (Math.random() * (wanderRadius * 2 + 1)) - wanderRadius;
            int dy = (int) (Math.random() * (wanderRadius * 2 + 1)) - wanderRadius;
            Position newPos = spawnPosition.translate(dx, dy);
            setPosition(newPos);
        }
    }

    protected void moveTowards(Position target) {
        int dx = Integer.compare(target.getX(), getPosition().getX());
        int dy = Integer.compare(target.getY(), getPosition().getY());
        setPosition(getPosition().translate(dx, dy));
    }

    @Override
    protected void onDeath() {
        logger.debug("NPC {} (id={}) has died.", getName(), npcId);
        respawnCountdown = respawnTicks;
        target = null;
    }

    public void respawn() {
        setPosition(spawnPosition);
        setHitpoints(getMaxHitpoints());
        setDead(false);
        target = null;
    }

    public int getAttackRange() {
        return definition != null ? definition.getAttackRange() : 1;
    }

    public int getNpcId() {
        return npcId;
    }

    public String getName() {
        return definition != null ? definition.getName() : "Unknown";
    }

    public NpcDefinition getDefinition() {
        return definition;
    }

    public Position getSpawnPosition() {
        return spawnPosition;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }
}
