package com.titanscape.server.model.entity.player;

/**
 * Manages a player's skill levels and experience.
 */
public final class Skills {

    public static final int MAX_LEVEL = 99;
    public static final double MAX_EXPERIENCE = 200_000_000;
    public static final double XP_RATE = 50.0;

    private final int[] levels = new int[SkillType.SKILL_COUNT];
    private final double[] experience = new double[SkillType.SKILL_COUNT];

    public Skills() {
        for (int i = 0; i < SkillType.SKILL_COUNT; i++) {
            levels[i] = 1;
            experience[i] = 0;
        }
        levels[SkillType.HITPOINTS.getId()] = 10;
        experience[SkillType.HITPOINTS.getId()] = 1154;
    }

    public int getLevel(SkillType skill) {
        return levels[skill.getId()];
    }

    public void setLevel(SkillType skill, int level) {
        levels[skill.getId()] = Math.max(1, Math.min(level, MAX_LEVEL));
    }

    public double getExperience(SkillType skill) {
        return experience[skill.getId()];
    }

    public void addExperience(SkillType skill, double amount) {
        double xp = experience[skill.getId()];
        xp = Math.min(xp + amount * XP_RATE, MAX_EXPERIENCE);
        experience[skill.getId()] = xp;
        int newLevel = levelForExperience(xp);
        if (newLevel > levels[skill.getId()]) {
            levels[skill.getId()] = newLevel;
        }
    }

    public int getCombatLevel() {
        double base = 0.25 * (getLevel(SkillType.DEFENCE) + getLevel(SkillType.HITPOINTS)
                + Math.floor(getLevel(SkillType.PRAYER) / 2.0));
        double melee = 0.325 * (getLevel(SkillType.ATTACK) + getLevel(SkillType.STRENGTH));
        double ranged = 0.325 * (Math.floor(getLevel(SkillType.RANGED) / 2.0) + getLevel(SkillType.RANGED));
        double magic = 0.325 * (Math.floor(getLevel(SkillType.MAGIC) / 2.0) + getLevel(SkillType.MAGIC));
        return (int) (base + Math.max(melee, Math.max(ranged, magic)));
    }

    public int getTotalLevel() {
        int total = 0;
        for (int level : levels) {
            total += level;
        }
        return total;
    }

    public long getTotalExperience() {
        long total = 0;
        for (double xp : experience) {
            total += (long) xp;
        }
        return total;
    }

    public int[] getLevels() {
        return levels;
    }

    public double[] getExperienceArray() {
        return experience;
    }

    public static int levelForExperience(double xp) {
        int level = 1;
        double total = 0;
        for (int i = 1; i <= MAX_LEVEL; i++) {
            total += Math.floor(i + 300 * Math.pow(2, i / 7.0));
            if (xp >= Math.floor(total / 4.0)) {
                level = i + 1;
            }
        }
        return Math.min(level, MAX_LEVEL);
    }
}
