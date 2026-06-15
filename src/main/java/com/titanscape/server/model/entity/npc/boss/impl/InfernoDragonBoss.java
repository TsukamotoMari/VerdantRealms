package com.titanscape.server.model.entity.npc.boss.impl;

import com.titanscape.server.model.entity.npc.boss.Boss;
import com.titanscape.server.model.entity.npc.boss.BossDefinition;
import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.item.Item;
import com.titanscape.server.model.world.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * INFERNO DRAGON — A massive dragon boss deep in the Molten Caverns.
 *
 * Phase 1: Claw swipe melee + fire pools on the ground.
 * Phase 2: Takes flight — only rangeable/mageable, rains fireballs.
 * Phase 3: Lands with earthquake, lava eruptions around arena.
 * Enrage: Permanent fire aura damages nearby players every tick.
 */
public final class InfernoDragonBoss extends Boss {

    private static final Logger logger = LogManager.getLogger(InfernoDragonBoss.class);

    public static final int NPC_ID = 10003;
    private boolean isFlying;

    public InfernoDragonBoss(Position position, BossDefinition def) {
        super(NPC_ID, position, def);
    }

    @Override
    public void performSpecialAttack() {
        switch (getCurrentPhase()) {
            case PHASE_1 -> firePools();
            case PHASE_2 -> fireballRain();
            case PHASE_3 -> lavaEruption();
            case ENRAGE -> infernalNova();
        }
    }

    private void firePools() {
        for (Player p : getAttackers()) {
            if (p != null && !p.isDead()) {
                if (Math.random() < 0.5) {
                    int hit = 8 + (int) (Math.random() * 12);
                    p.damage(hit);
                    p.sendMessage("You stand in a pool of dragonfire! (-" + hit + ")");
                }
            }
        }
    }

    private void fireballRain() {
        isFlying = true;
        for (Player p : getAttackers()) {
            if (p != null && !p.isDead()) {
                int hit = 20 + (int) (Math.random() * 30);
                p.damage(hit);
                p.sendMessage("Fireballs rain from above! (-" + hit + ")");
            }
        }
    }

    private void lavaEruption() {
        isFlying = false;
        for (Player p : getAttackers()) {
            if (p != null && getPosition().isWithinDistance(p.getPosition(), 4)) {
                int hit = 25 + (int) (Math.random() * 20);
                p.damage(hit);
                p.sendMessage("Lava erupts from the ground! (-" + hit + ")");
            }
        }
    }

    private void infernalNova() {
        for (Player p : getAttackers()) {
            if (p != null && !p.isDead()) {
                int hit = 30 + (int) (Math.random() * 35);
                p.damage(hit);
                p.sendMessage("The Inferno Dragon unleashes a devastating nova! (-" + hit + ")");
                heal(hit / 5);
            }
        }
    }

    @Override
    public void onPhaseChange(Phase oldPhase, Phase newPhase) {
        String msg = switch (newPhase) {
            case PHASE_2 -> "The Inferno Dragon takes flight! Use ranged or magic!";
            case PHASE_3 -> "The dragon crashes down with a massive earthquake!";
            case ENRAGE -> "The Inferno Dragon is engulfed in flames: 'BURN WITH ME!'";
            default -> null;
        };
        if (msg != null) {
            for (Player p : getAttackers()) {
                if (p != null) p.sendMessage(msg);
            }
        }
    }

    @Override
    public void rollDrops(Player killer) {
        double roll = Math.random();
        if (roll < 0.008) {
            killer.getInventory().add(new Item(20003)); // Dragonfire Lance
            killer.sendMessage("@red@EXTREMELY RARE DROP: Dragonfire Lance!");
        } else if (roll < 0.03) {
            killer.getInventory().add(new Item(20007)); // Infernal Wings
            killer.sendMessage("@red@RARE DROP: Infernal Wings!");
        } else if (roll < 0.10) {
            killer.getInventory().add(new Item(20008)); // Dragon Heart
            killer.sendMessage("@pur@UNCOMMON DROP: Dragon Heart!");
        } else if (roll < 0.25) {
            killer.getInventory().add(new Item(1616, 5 + (int) (Math.random() * 10))); // Dragonstone
            killer.sendMessage("You receive some dragonstones.");
        } else {
            killer.getInventory().add(new Item(995, 400000 + (int) (Math.random() * 600000)));
            killer.sendMessage("You receive a pile of gold coins.");
        }
    }

    public boolean isFlying() {
        return isFlying;
    }
}
