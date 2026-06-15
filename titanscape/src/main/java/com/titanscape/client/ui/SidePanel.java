package com.titanscape.client.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Right-side panel with inventory, equipment, skills, and prayer tabs.
 */
public final class SidePanel extends JPanel {

    private final JTabbedPane tabbedPane;

    public SidePanel() {
        setPreferredSize(new Dimension(220, 0));
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 40));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("SansSerif", Font.PLAIN, 10));

        tabbedPane.addTab("Inv", createInventoryPanel());
        tabbedPane.addTab("Equip", createEquipmentPanel());
        tabbedPane.addTab("Skills", createSkillsPanel());
        tabbedPane.addTab("Prayer", createPrayerPanel());
        tabbedPane.addTab("Magic", createMagicPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 4, 2, 2));
        panel.setBackground(new Color(30, 30, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        for (int i = 0; i < 28; i++) {
            JPanel slot = new JPanel();
            slot.setPreferredSize(new Dimension(40, 40));
            slot.setBackground(new Color(45, 45, 65));
            slot.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 80)));
            slot.setToolTipText("Slot " + (i + 1));
            panel.add(slot);
        }
        return panel;
    }

    private JPanel createEquipmentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        String[][] layout = {
            {null, "Helmet", null},
            {"Cape", "Amulet", "Arrows"},
            {null, "Weapon", "Shield"},
            {null, "Body", null},
            {null, "Legs", null},
            {"Gloves", "Boots", "Ring"}
        };

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                String name = layout[row][col];
                gbc.gridx = col; gbc.gridy = row;
                if (name != null) {
                    JPanel slot = createEquipSlot(name);
                    panel.add(slot, gbc);
                } else {
                    panel.add(Box.createRigidArea(new Dimension(50, 45)), gbc);
                }
            }
        }
        return panel;
    }

    private JPanel createEquipSlot(String name) {
        JPanel slot = new JPanel(new BorderLayout());
        slot.setPreferredSize(new Dimension(50, 45));
        slot.setBackground(new Color(45, 45, 65));
        slot.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 120)));
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setForeground(new Color(150, 150, 170));
        label.setFont(new Font("SansSerif", Font.PLAIN, 8));
        slot.add(label, BorderLayout.SOUTH);
        slot.setToolTipText(name);
        return slot;
    }

    private JPanel createSkillsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 2, 2));
        panel.setBackground(new Color(30, 30, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] skills = {
            "Attack", "Strength", "Defence", "Hitpoints", "Ranged",
            "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching",
            "Fishing", "Firemaking", "Crafting", "Smithing", "Mining",
            "Herblore", "Agility", "Thieving", "Slayer", "Farming",
            "Runecrafting", "Construction", "Hunter",
            "Summoning", "Dungeoneering", "Titan Forging"
        };

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(null);

        for (String skill : skills) {
            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(new Color(40, 40, 60));
            row.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

            JLabel nameLabel = new JLabel(skill);
            nameLabel.setForeground(new Color(200, 200, 220));
            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

            JLabel levelLabel = new JLabel("1/99", SwingConstants.RIGHT);
            levelLabel.setForeground(new Color(255, 215, 0));
            levelLabel.setFont(new Font("SansSerif", Font.BOLD, 11));

            row.add(nameLabel, BorderLayout.WEST);
            row.add(levelLabel, BorderLayout.EAST);
            panel.add(row);
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createPrayerPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 3, 3, 3));
        panel.setBackground(new Color(30, 30, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] prayers = {
            "Thick Skin", "Burst of Str", "Clarity",
            "Sharp Eye", "Mystic Will", "Rock Skin",
            "Superhuman Str", "Improved Ref", "Rapid Restore",
            "Rapid Heal", "Protect Item", "Hawk Eye",
            "Mystic Lore", "Steel Skin", "Ultimate Str",
            "Incredible Ref", "Protect Melee", "Protect Range",
            "Protect Mage", "Retribution", "Redemption",
            "Smite", "Chivalry", "Piety"
        };

        for (String prayer : prayers) {
            JButton btn = new JButton("<html><center><font size=1>" + prayer + "</font></center></html>");
            btn.setBackground(new Color(45, 45, 65));
            btn.setForeground(new Color(180, 180, 200));
            btn.setFont(new Font("SansSerif", Font.PLAIN, 8));
            btn.setPreferredSize(new Dimension(55, 40));
            btn.setFocusPainted(false);
            panel.add(btn);
        }
        return panel;
    }

    private JPanel createMagicPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 4, 2, 2));
        panel.setBackground(new Color(30, 30, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] spells = {
            "Wind Strike", "Water Strike", "Earth Strike", "Fire Strike",
            "Wind Bolt", "Water Bolt", "Earth Bolt", "Fire Bolt",
            "Wind Blast", "Water Blast", "Earth Blast", "Fire Blast",
            "Wind Wave", "Water Wave", "Earth Wave", "Fire Wave",
            "Teleport Home", "Varrock TP", "Lumby TP", "Falador TP",
            "Camelot TP", "Ardougne TP", "Titan TP", "Void TP"
        };

        for (String spell : spells) {
            JButton btn = new JButton("<html><center><font size=1>" + spell + "</font></center></html>");
            btn.setBackground(new Color(35, 35, 65));
            btn.setForeground(new Color(100, 180, 255));
            btn.setFont(new Font("SansSerif", Font.PLAIN, 7));
            btn.setPreferredSize(new Dimension(45, 35));
            btn.setFocusPainted(false);
            panel.add(btn);
        }
        return panel;
    }
}
