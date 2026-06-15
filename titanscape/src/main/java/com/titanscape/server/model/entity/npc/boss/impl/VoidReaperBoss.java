package com.titanscape.server.model.entity.npc.boss.impl;

import com.titanscape.server.model.entity.npc.boss.Boss;
import com.titanscape.server.model.entity.npc.boss.BossDefinition;
import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.item.Item;
import com.titanscape.server.model.world.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * VOID REAPER — A spectral boss that phases through dimensions.
 *
 * Phase 1: Invisible strikes — random damage from unseen attacks.
 * Phase 2: Soul drain — steals HP from players to heal itself.
 * Phase 3: Void rift — teleports players to random positions.
 * Enrage: Summons void clones that mirror its attacks.
 */
public final class VoidReaperBoss extends Boss {

    private static final Logger logger = LogManager.getLogger(VoidReaperBoss.class);

    public static final int NPC_ID = 10002;

    public VoidReaperBoss(Position position, BossDefinition def) {
        super(NPC_ID, position, def);
    }

    @Override
    public void performSpecialAttack() {
        switch (getCurrentPhase()) {
            case PHASE_1 -> invisibleStrike();
            case PHASE_2 -> soulDrain();
            case PHASE_3 -> voidRift();
            case ENRAGE -> voidMirror();
        }
    }

    private void invisibleStrike() {
        for (Player p : getAttackers()) {
            if (p != null && Math.random() < 0.6) {
                int hit = 10 + (int) (Math.random() * 20);
                p.damage(hit);
                p.sendMessage("An unseen force strikes you! (-" + hit + ")");
            }
        }
    }

    private void soulDrain() {
        int totalDrained = 0;
        for (Player p : getAttackers()) {
            if (p != null && !p.isDead()) {
                int drain = 5 + (int) (Math.random() * 15);
                p.damage(drain);
                totalDrained += drain;
                p.sendMessage("The Void Reaper drains your soul! (-" + drain + ")");
            }
        }
        heal(totalDrained / 2);
    }

    private void voidRift() {
        for (Player p : getAttackers()) {
            if (p != null && !p.isDead()) {
                int dx = (int) (Math.random() * 8) - 4;
                int dy = (int) (Math.random() * 8) - 4;
                p.setPosition(p.getPosition().translate(dx, dy));
                int hit = 10 + (int) (Math.random() * 10);
                p.damage(hit);
                p.sendMessage("A void rift teleports you! (-" + hit + ")");
            }
        }
    }

    private void voidMirror() {
        for (Player p : getAttackers()) {
            if (p != null && !p.isDead()) {
                int hit1 = 15 + (int) (Math.random() * 20);
                int hit2 = 15 + (int) (Math.random() * 20);
                p.damage(hit1 + hit2);
                p.sendMessage("The Void Reaper and its clone strike together! (-" + (hit1 + hit2) + ")");
            }
        }
    }

    @Override
    public void onPhaseChange(Phase oldPhase, Phase newPhase) {
        String msg = switch (newPhase) {
            case PHASE_2 -> "The Void Reaper whispers: 'Your soul... it sustains me...'";
            case PHASE_3 -> "Reality tears apart! The Void Reaper opens dimensional rifts!";
            case ENRAGE -> "The Void Reaper splits into multiple copies: 'WE ARE LEGION!'";
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
            killer.getInventory().add(new Item(20002)); // Voidreaper Scythe
            killer.sendMessage("@red@EXTREMELY RARE DROP: Voidreaper Scythe!");
        } else if (roll < 0.05) {
            killer.getInventory().add(new Item(20006)); // Spectral Cloak
            killer.sendMessage("@red@RARE DROP: Spectral Cloak!");
        } else if (roll < 0.20) {
            killer.getInventory().add(new Item(20011)); // Void Shard
            killer.sendMessage("@pur@UNCOMMON DROP: Void Shard x1");
        } else {
            killer.getInventory().add(new Item(995, 300000 + (int) (Math.random() * 400000)));
            killer.sendMessage("You receive a pile of gold coins.");
        }
    }
}
