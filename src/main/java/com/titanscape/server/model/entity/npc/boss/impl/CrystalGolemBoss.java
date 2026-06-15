package com.titanscape.server.model.entity.npc.boss.impl;

import com.titanscape.server.model.entity.npc.boss.Boss;
import com.titanscape.server.model.entity.npc.boss.BossDefinition;
import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.item.Item;
import com.titanscape.server.model.world.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CRYSTAL GOLEM — A slow but devastatingly powerful boss in the Crystal Caverns.
 *
 * Phase 1: Rock throw + crystal shield (damage reduction).
 * Phase 2: Crystal shards explode outward from body.
 * Phase 3: Encases players in crystal (stun mechanic).
 * Enrage: Shatters, dealing massive AoE then reforms with 25% HP.
 */
public final class CrystalGolemBoss extends Boss {

    private static final Logger logger = LogManager.getLogger(CrystalGolemBoss.class);

    public static final int NPC_ID = 10004;
    private boolean hasReformed;
    private int shieldReduction = 0;

    public CrystalGolemBoss(Position position, BossDefinition def) {
        super(NPC_ID, position, def);
    }

    @Override
    public void performSpecialAttack() {
        switch (getCurrentPhase()) {
            case PHASE_1 -> rockThrow();
            case PHASE_2 -> crystalShatter();
            case PHASE_3 -> crystalEncase();
            case ENRAGE -> shatterReform();
        }
    }

    private void rockThrow() {
        shieldReduction = 5;
        if (getTarget() != null && !getTarget().isDead() && getTarget() instanceof Player p) {
            int hit = 20 + (int) (Math.random() * 15);
            p.damage(hit);
            p.sendMessage("The Crystal Golem hurls a boulder at you! (-" + hit + ")");
        }
    }

    private void crystalShatter() {
        shieldReduction = 0;
        for (Player p : getAttackers()) {
            if (p != null && getPosition().isWithinDistance(p.getPosition(), 5)) {
                int hit = 15 + (int) (Math.random() * 25);
                p.damage(hit);
                p.sendMessage("Crystal shards explode outward! (-" + hit + ")");
            }
        }
    }

    private void crystalEncase() {
        for (Player p : getAttackers()) {
            if (p != null && !p.isDead() && Math.random() < 0.3) {
                int hit = 5 + (int) (Math.random() * 10);
                p.damage(hit);
                p.sendMessage("You are encased in crystal! You can't move! (-" + hit + ")");
                p.setAttribute("stunned", true);
                p.setAttribute("stun_ticks", 5);
            }
        }
    }

    private void shatterReform() {
        if (!hasReformed) {
            for (Player p : getAttackers()) {
                if (p != null && !p.isDead()) {
                    int hit = 40 + (int) (Math.random() * 30);
                    p.damage(hit);
                    p.sendMessage("The Crystal Golem SHATTERS! Massive crystal explosion! (-" + hit + ")");
                }
            }
            setHitpoints(getMaxHitpoints() / 4);
            setDead(false);
            hasReformed = true;
            for (Player p : getAttackers()) {
                if (p != null) p.sendMessage("The Crystal Golem reforms from the shards!");
            }
        } else {
            crystalShatter();
            crystalEncase();
        }
    }

    @Override
    public void onPhaseChange(Phase oldPhase, Phase newPhase) {
        String msg = switch (newPhase) {
            case PHASE_2 -> "Cracks appear across the Crystal Golem's body!";
            case PHASE_3 -> "The Crystal Golem glows with imprisoning energy!";
            case ENRAGE -> "The Crystal Golem begins to vibrate violently!";
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
        if (roll < 0.01) {
            killer.getInventory().add(new Item(20004)); // Crystal Maul
            killer.sendMessage("@red@EXTREMELY RARE DROP: Crystal Maul!");
        } else if (roll < 0.04) {
            killer.getInventory().add(new Item(20009)); // Crystal Armor Shard
            killer.sendMessage("@red@RARE DROP: Crystal Armor Shard!");
        } else if (roll < 0.15) {
            killer.getInventory().add(new Item(20012)); // Golem Core
            killer.sendMessage("@pur@UNCOMMON DROP: Golem Core!");
        } else {
            killer.getInventory().add(new Item(995, 350000 + (int) (Math.random() * 450000)));
            killer.sendMessage("You receive a pile of gold coins.");
        }
    }

    public int getShieldReduction() {
        return shieldReduction;
    }
}
