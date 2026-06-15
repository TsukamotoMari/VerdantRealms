package com.titanscape.server.model.combat.strategy;

import com.titanscape.server.model.entity.Entity;
import com.titanscape.server.model.entity.player.Player;

/**
 * Interface for pluggable combat strategies (used by NPCs/bosses).
 */
public interface CombatStrategy {

    int getAttackRange();

    int getAttackSpeed();

    int calculateMaxHit(Entity attacker);

    void executeAttack(Entity attacker, Entity defender);

    boolean canAttack(Entity attacker, Entity defender);
}
