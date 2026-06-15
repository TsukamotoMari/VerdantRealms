package com.titanscape.client.ui;

import javax.swing.*;
import java.awt.*;

public final class SidePanel extends JPanel {

    private final JTabbedPane tabbedPane;

    private static final Color STONE_DARK = new Color(62, 53, 41);
    private static final Color STONE_MED = new Color(79, 68, 52);
    private static final Color STONE_LIGHT = new Color(93, 80, 60);
    private static final Color SLOT_BG = new Color(50, 43, 32);
    private static final Color SLOT_BORDER = new Color(110, 93, 60);
    private static final Color BORDER_GOLD = new Color(152, 128, 51);
    private static final Color TEXT_GOLD = new Color(255, 215, 0);
    private static final Color TEXT_LIGHT = new Color(210, 195, 160);
    private static final Color TEXT_DIM = new Color(160, 145, 110);

    public SidePanel() {
        setPreferredSize(new Dimension(220, 0));
        setLayout(new BorderLayout());
        setBackground(STONE_DARK);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Serif", Font.BOLD, 10));
        tabbedPane.setBackground(STONE_MED);
        tabbedPane.setForeground(TEXT_LIGHT);

        tabbedPane.addTab("Skills", createSkillsPanel());
        tabbedPane.addTab("Prayer", createPrayerPanel());
        tabbedPane.addTab("Magic", createMagicPanel());
        tabbedPane.addTab("Inv", createInventoryPanel());
        tabbedPane.addTab("Equip", createEquipmentPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 4, 2, 2));
        panel.setBackground(STONE_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        for (int i = 0; i < 28; i++) {
            JPanel slot = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(SLOT_BG);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    g2.setColor(SLOT_BORDER);
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                }
            };
            slot.setPreferredSize(new Dimension(40, 40));
            slot.setOpaque(false);
            slot.setToolTipText("Slot " + (i + 1));
            panel.add(slot);
        }
        return panel;
    }

    private JPanel createEquipmentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(STONE_DARK);
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
                    panel.add(createEquipSlot(name), gbc);
                } else {
                    panel.add(Box.createRigidArea(new Dimension(50, 45)), gbc);
                }
            }
        }
        return panel;
    }

    private JPanel createEquipSlot(String name) {
        JPanel slot = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(SLOT_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.setColor(SLOT_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);
            }
        };
        slot.setPreferredSize(new Dimension(50, 45));
        slot.setOpaque(false);
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setForeground(TEXT_DIM);
        label.setFont(new Font("Serif", Font.PLAIN, 8));
        slot.add(label, BorderLayout.SOUTH);
        slot.setToolTipText(name);
        return slot;
    }

    private JPanel createSkillsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 1, 1));
        panel.setBackground(STONE_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

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
        scroll.getViewport().setBackground(STONE_DARK);

        for (String skill : skills) {
            JPanel row = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(STONE_MED);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    g2.setColor(new Color(90, 77, 55));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                }
            };
            row.setOpaque(false);
            row.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));

            JLabel nameLabel = new JLabel(skill);
            nameLabel.setForeground(TEXT_LIGHT);
            nameLabel.setFont(new Font("Serif", Font.PLAIN, 11));

            JLabel levelLabel = new JLabel("1/99", SwingConstants.RIGHT);
            levelLabel.setForeground(TEXT_GOLD);
            levelLabel.setFont(new Font("Serif", Font.BOLD, 11));

            row.add(nameLabel, BorderLayout.WEST);
            row.add(levelLabel, BorderLayout.EAST);
            panel.add(row);
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(STONE_DARK);
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createPrayerPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 3, 3, 3));
        panel.setBackground(STONE_DARK);
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
            JButton btn = new JButton("<html><center><font size=1>" + prayer + "</font></center></html>") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    if (getModel().isRollover()) {
                        g2.setColor(STONE_LIGHT);
                    } else {
                        g2.setColor(SLOT_BG);
                    }
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    g2.setColor(SLOT_BORDER);
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                    super.paintComponent(g);
                }
            };
            btn.setForeground(TEXT_LIGHT);
            btn.setFont(new Font("Serif", Font.PLAIN, 8));
            btn.setPreferredSize(new Dimension(55, 40));
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            panel.add(btn);
        }
        return panel;
    }

    private JPanel createMagicPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 4, 2, 2));
        panel.setBackground(STONE_DARK);
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
            JButton btn = new JButton("<html><center><font size=1>" + spell + "</font></center></html>") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    if (getModel().isRollover()) {
                        g2.setColor(new Color(55, 50, 70));
                    } else {
                        g2.setColor(new Color(40, 36, 52));
                    }
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    g2.setColor(new Color(80, 70, 110));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                    super.paintComponent(g);
                }
            };
            btn.setForeground(new Color(130, 190, 255));
            btn.setFont(new Font("Serif", Font.PLAIN, 7));
            btn.setPreferredSize(new Dimension(45, 35));
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            panel.add(btn);
        }
        return panel;
    }
}
