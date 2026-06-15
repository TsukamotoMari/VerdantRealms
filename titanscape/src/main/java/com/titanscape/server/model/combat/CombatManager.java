package com.titanscape.server.model.combat;

import com.titanscape.server.model.entity.Entity;
import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.entity.player.Equipment;
import com.titanscape.server.model.entity.player.SkillType;
import com.titanscape.server.model.entity.npc.Npc;
import com.titanscape.server.model.entity.npc.boss.Boss;

import java.util.Random;

/**
 * Handles combat calculations between entities.
 */
public final class CombatManager {

    private static final Random random = new Random();

    private CombatManager() {}

    public static int calculatePlayerMaxHit(Player player, CombatStyle style) {
        int effectiveLevel;
        int bonus;

        switch (style.getType()) {
            case "melee" -> {
                effectiveLevel = player.getSkills().getLevel(SkillType.STRENGTH);
                bonus = player.getEquipment().getStrengthBonus();
            }
            case "ranged" -> {
                effectiveLevel = player.getSkills().getLevel(SkillType.RANGED);
                bonus = player.getEquipment().getRangedBonus();
            }
            case "magic" -> {
                effectiveLevel = player.getSkills().getLevel(SkillType.MAGIC);
                bonus = player.getEquipment().getMagicBonus();
            }
            default -> {
                effectiveLevel = 1;
                bonus = 0;
            }
        }

        double base = 0.5 + effectiveLevel * (bonus + 64.0) / 640.0;
        return (int) Math.floor(base);
    }

    public static int calculateAttackRoll(Player player, CombatStyle style) {
        int effectiveLevel;
        int bonus;

        switch (style.getType()) {
            case "melee" -> {
                effectiveLevel = player.getSkills().getLevel(SkillType.ATTACK);
                bonus = player.getEquipment().getAttackBonus();
            }
            case "ranged" -> {
                effectiveLevel = player.getSkills().getLevel(SkillType.RANGED);
                bonus = player.getEquipment().getRangedBonus();
            }
            case "magic" -> {
                effectiveLevel = player.getSkills().getLevel(SkillType.MAGIC);
                bonus = player.getEquipment().getMagicBonus();
            }
            default -> {
                effectiveLevel = 1;
                bonus = 0;
            }
        }

        return effectiveLevel * (bonus + 64);
    }

    public static int calculateDefenceRoll(Entity defender) {
        if (defender instanceof Player player) {
            int defLevel = player.getSkills().getLevel(SkillType.DEFENCE);
            int defBonus = player.getEquipment().getDefenceBonus();
            return defLevel * (defBonus + 64);
        } else if (defender instanceof Npc npc) {
            int defBonus = npc.getDefinition() != null ? npc.getDefinition().getDefenceBonus() : 0;
            return 1 * (defBonus + 64);
        }
        return 64;
    }

    public static boolean doesHitLand(int attackRoll, int defenceRoll) {
        double accuracy;
        if (attackRoll > defenceRoll) {
            accuracy = 1.0 - (defenceRoll + 2.0) / (2.0 * (attackRoll + 1.0));
        } else {
            accuracy = attackRoll / (2.0 * (defenceRoll + 1.0));
        }
        return random.nextDouble() < accuracy;
    }

    public static int rollDamage(int maxHit) {
        return random.nextInt(maxHit + 1);
    }

    public static void processAttack(Player attacker, Entity defender, CombatStyle style) {
        int attackRoll = calculateAttackRoll(attacker, style);
        int defenceRoll = calculateDefenceRoll(defender);

        if (doesHitLand(attackRoll, defenceRoll)) {
            int maxHit = calculatePlayerMaxHit(attacker, style);
            int damage = rollDamage(maxHit);
            defender.damage(damage);
            attacker.sendMessage("You hit a " + damage + ".");

            grantCombatXp(attacker, style, damage);

            if (defender instanceof Boss boss) {
                boss.addAttacker(attacker);
            }
        } else {
            attacker.sendMessage("You miss.");
        }
    }

    private static void grantCombatXp(Player player, CombatStyle style, int damage) {
        double xp = damage * 4.0;
        switch (style.getType()) {
            case "melee" -> {
                player.getSkills().addExperience(SkillType.ATTACK, xp / 3);
                player.getSkills().addExperience(SkillType.STRENGTH, xp / 3);
                player.getSkills().addExperience(SkillType.DEFENCE, xp / 3);
            }
            case "ranged" -> player.getSkills().addExperience(SkillType.RANGED, xp);
            case "magic" -> player.getSkills().addExperience(SkillType.MAGIC, xp);
        }
        player.getSkills().addExperience(SkillType.HITPOINTS, damage * 1.33);
    }
}
