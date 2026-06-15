package com.titanscape.client.ui;

import com.titanscape.client.TitanClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class GameFrame extends JFrame {

    private final String username;
    private final int rights;
    private final GamePanel gamePanel;
    private final ChatPanel chatPanel;
    private final SidePanel sidePanel;

    private static final Color STONE_DARK = new Color(62, 53, 41);
    private static final Color STONE_MED = new Color(79, 68, 52);
    private static final Color BORDER_GOLD = new Color(152, 128, 51);
    private static final Color TEXT_GOLD = new Color(255, 215, 0);
    private static final Color TEXT_WHITE = new Color(230, 220, 190);

    public GameFrame(String username, int rights) {
        super(TitanClient.TITLE + " - " + username);
        this.username = username;
        this.rights = rights;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // OSRS-style top bar
        JPanel topBar = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint grad = new GradientPaint(0, 0, STONE_MED, 0, getHeight(), STONE_DARK);
                g2.setPaint(grad);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(BORDER_GOLD);
                g2.fillRect(0, getHeight() - 2, getWidth(), 2);
            }
        };
        topBar.setPreferredSize(new Dimension(0, 36));

        JLabel titleLabel = new JLabel("  TitanScape  |  " + username
            + (rights > 0 ? "  [" + getRightsName() + "]" : ""));
        titleLabel.setForeground(TEXT_GOLD);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 14));
        topBar.add(titleLabel, BorderLayout.WEST);

        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        topButtons.setOpaque(false);
        addTopButton(topButtons, "World Map");
        addTopButton(topButtons, "Settings");
        addTopButton(topButtons, "Logout");
        topBar.add(topButtons, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        sidePanel = new SidePanel();
        add(sidePanel, BorderLayout.EAST);

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
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(110, 85, 40));
                } else {
                    g2.setColor(new Color(80, 65, 35));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.setColor(BORDER_GOLD);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);
                g2.setColor(TEXT_WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
            }
        };
        btn.setFont(new Font("Serif", Font.PLAIN, 11));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(75, 26));
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
