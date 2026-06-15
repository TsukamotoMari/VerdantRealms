package com.titanscape.server.model.entity.npc.boss;

import com.titanscape.server.model.entity.Entity;
import com.titanscape.server.model.entity.npc.Npc;
import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.world.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for custom bosses with advanced AI and phase mechanics.
 */
public abstract class Boss extends Npc {

    private static final Logger logger = LogManager.getLogger(Boss.class);

    public enum Phase {
        PHASE_1, PHASE_2, PHASE_3, ENRAGE
    }

    private Phase currentPhase = Phase.PHASE_1;
    private int specialAttackCooldown;
    private int tickCounter;
    private final List<Player> attackers = new ArrayList<>();
    private final BossDefinition bossDefinition;

    public Boss(int npcId, Position position, BossDefinition bossDefinition) {
        super(npcId, position);
        this.bossDefinition = bossDefinition;
        if (bossDefinition != null) {
            setMaxHitpoints(bossDefinition.getHitpoints());
            setHitpoints(bossDefinition.getHitpoints());
        }
    }

    @Override
    public void process() {
        if (isDead()) {
            super.process();
            return;
        }

        tickCounter++;
        updatePhase();

        if (specialAttackCooldown > 0) {
            specialAttackCooldown--;
        }

        if (getTarget() != null && !getTarget().isDead()) {
            if (shouldUseSpecialAttack()) {
                performSpecialAttack();
                specialAttackCooldown = getSpecialCooldownTicks();
            } else {
                processCombat();
            }
        } else {
            scanForTargets();
        }
    }

    private void updatePhase() {
        double hpPercent = (double) getHitpoints() / getMaxHitpoints();
        Phase newPhase;
        if (hpPercent <= 0.10) {
            newPhase = Phase.ENRAGE;
        } else if (hpPercent <= 0.33) {
            newPhase = Phase.PHASE_3;
        } else if (hpPercent <= 0.66) {
            newPhase = Phase.PHASE_2;
        } else {
            newPhase = Phase.PHASE_1;
        }

        if (newPhase != currentPhase) {
            Phase old = currentPhase;
            currentPhase = newPhase;
            onPhaseChange(old, newPhase);
        }
    }

    protected void scanForTargets() {
        // Subclasses can override for custom aggro logic
    }

    protected boolean shouldUseSpecialAttack() {
        return specialAttackCooldown <= 0 && tickCounter % 10 == 0;
    }

    @Override
    protected void onDeath() {
        logger.info("Boss '{}' has been defeated!", getName());
        for (Player attacker : attackers) {
            if (attacker != null && !attacker.isDead()) {
                onBossKill(attacker);
            }
        }
        attackers.clear();
        super.onDeath();
    }

    protected void onBossKill(Player killer) {
        killer.sendMessage("Congratulations! You have defeated " + getName() + "!");
        rollDrops(killer);
    }

    public abstract void performSpecialAttack();
    public abstract void onPhaseChange(Phase oldPhase, Phase newPhase);
    public abstract void rollDrops(Player killer);

    public int getSpecialCooldownTicks() {
        return bossDefinition != null ? bossDefinition.getSpecialCooldown() : 15;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public int getTickCounter() {
        return tickCounter;
    }

    public List<Player> getAttackers() {
        return attackers;
    }

    public void addAttacker(Player player) {
        if (!attackers.contains(player)) {
            attackers.add(player);
        }
    }

    public BossDefinition getBossDefinition() {
        return bossDefinition;
    }
}
