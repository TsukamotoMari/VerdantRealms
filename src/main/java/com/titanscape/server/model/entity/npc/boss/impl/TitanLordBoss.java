package com.titanscape.server.model.entity.npc.boss.impl;

import com.titanscape.server.model.entity.npc.boss.Boss;
import com.titanscape.server.model.entity.npc.boss.BossDefinition;
import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.item.Item;
import com.titanscape.server.model.world.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TITAN LORD — The ultimate boss of TitanScape.
 *
 * Phase 1 (100%-66%): Standard melee attacks, occasional ground slam AoE.
 * Phase 2 (66%-33%): Summons minions, uses ranged fire breath.
 * Phase 3 (33%-10%): Teleports randomly, uses magic shadow bolts.
 * Enrage (<10%): All attacks combined, double speed, heals from damage dealt.
 */
public final class TitanLordBoss extends Boss {

    private static final Logger logger = LogManager.getLogger(TitanLordBoss.class);

    public static final int NPC_ID = 10001;

    public TitanLordBoss(Position position, BossDefinition def) {
        super(NPC_ID, position, def);
    }

    @Override
    public void performSpecialAttack() {
        switch (getCurrentPhase()) {
            case PHASE_1 -> groundSlam();
            case PHASE_2 -> fireBreath();
            case PHASE_3 -> shadowBolt();
            case ENRAGE -> enragedCombo();
        }
    }

    private void groundSlam() {
        for (Player p : getAttackers()) {
            if (p != null && getPosition().isWithinDistance(p.getPosition(), 3)) {
                int hit = 15 + (int) (Math.random() * 20);
                p.damage(hit);
                p.sendMessage("The Titan Lord slams the ground beneath you! (-" + hit + ")");
            }
        }
    }

    private void fireBreath() {
        for (Player p : getAttackers()) {
            if (p != null && getPosition().isWithinDistance(p.getPosition(), 6)) {
                int hit = 20 + (int) (Math.random() * 25);
                p.damage(hit);
                p.sendMessage("The Titan Lord breathes scorching fire! (-" + hit + ")");
            }
        }
    }

    private void shadowBolt() {
        Position pos = getPosition();
        int dx = (int) (Math.random() * 10) - 5;
        int dy = (int) (Math.random() * 10) - 5;
        setPosition(pos.translate(dx, dy));

        for (Player p : getAttackers()) {
            if (p != null) {
                int hit = 25 + (int) (Math.random() * 30);
                p.damage(hit);
                p.sendMessage("A shadow bolt strikes you from the darkness! (-" + hit + ")");
            }
        }
    }

    private void enragedCombo() {
        for (Player p : getAttackers()) {
            if (p != null && !p.isDead()) {
                int hit1 = 10 + (int) (Math.random() * 15);
                int hit2 = 15 + (int) (Math.random() * 20);
                int hit3 = 20 + (int) (Math.random() * 25);
                p.damage(hit1 + hit2 + hit3);
                p.sendMessage("The Titan Lord unleashes an enraged combo! (-" + (hit1 + hit2 + hit3) + ")");
                heal((hit1 + hit2 + hit3) / 4);
            }
        }
    }

    @Override
    public void onPhaseChange(Phase oldPhase, Phase newPhase) {
        String msg = switch (newPhase) {
            case PHASE_2 -> "The Titan Lord roars: 'You dare challenge me?! Feel my flames!'";
            case PHASE_3 -> "The Titan Lord becomes shrouded in darkness: 'The shadows consume all!'";
            case ENRAGE -> "The Titan Lord screams: 'I WILL NOT FALL! ENRAGE MODE ACTIVATED!'";
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
            killer.getInventory().add(new Item(20001)); // Titanforge Blade
            killer.sendMessage("@red@EXTREMELY RARE DROP: Titanforge Blade!");
        } else if (roll < 0.05) {
            killer.getInventory().add(new Item(20005)); // Titan Lord's Crown
            killer.sendMessage("@red@RARE DROP: Titan Lord's Crown!");
        } else if (roll < 0.15) {
            killer.getInventory().add(new Item(20010)); // Titan Essence
            killer.sendMessage("@pur@UNCOMMON DROP: Titan Essence x1");
        } else {
            killer.getInventory().add(new Item(995, 500000 + (int) (Math.random() * 500000)));
            killer.sendMessage("You receive a pile of gold coins.");
        }
    }
}
