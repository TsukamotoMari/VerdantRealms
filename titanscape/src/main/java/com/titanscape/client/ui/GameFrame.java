package com.titanscape.client.ui;

import com.titanscape.client.TitanClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main game window with the game viewport, chat, minimap, and inventory panels.
 */
public final class GameFrame extends JFrame {

    private final String username;
    private final int rights;
    private final GamePanel gamePanel;
    private final ChatPanel chatPanel;
    private final SidePanel sidePanel;

    public GameFrame(String username, int rights) {
        super(TitanClient.TITLE + " - " + username);
        this.username = username;
        this.rights = rights;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(20, 20, 40));
        topBar.setPreferredSize(new Dimension(0, 35));

        JLabel titleLabel = new JLabel("  TitanScape  |  " + username
            + (rights > 0 ? " [" + getRightsName() + "]" : ""));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        topBar.add(titleLabel, BorderLayout.WEST);

        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 3));
        topButtons.setOpaque(false);
        addTopButton(topButtons, "World Map");
        addTopButton(topButtons, "Settings");
        addTopButton(topButtons, "Logout");
        topBar.add(topButtons, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // Game viewport (center)
        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // Side panel (inventory, equipment, skills, etc)
        sidePanel = new SidePanel();
        add(sidePanel, BorderLayout.EAST);

        // Chat panel (bottom)
        chatPanel = new ChatPanel();
        add(chatPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (TitanClient.getInstance().getConnection() != null) {
                    TitanClient.getInstance().getConnection().disconnect();
                }
            }
        });
    }

    private void addTopButton(JPanel panel, String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 11));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            if ("Logout".equals(text)) {
                if (TitanClient.getInstance().getConnection() != null) {
                    TitanClient.getInstance().getConnection().disconnect();
                }
                dispose();
                TitanClient.getInstance().launch();
            }
        });
        panel.add(btn);
    }

    public void addChatMessage(String message) {
        chatPanel.addMessage(message);
    }

    private String getRightsName() {
        return switch (rights) {
            case 1 -> "Moderator";
            case 2 -> "Administrator";
            case 3 -> "Owner";
            case 4 -> "Developer";
            default -> "Player";
        };
    }

    public GamePanel getGamePanel() { return gamePanel; }
    public ChatPanel getChatPanel() { return chatPanel; }
    public SidePanel getSidePanel() { return sidePanel; }
}
