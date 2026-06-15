package com.titanscape.client.ui;

import com.titanscape.client.TitanClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class LoginScreen extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JTextField hostField;
    private final JButton loginButton;
    private final JLabel statusLabel;

    private static final Color BG_DARK = new Color(54, 46, 34);
    private static final Color BG_MED = new Color(71, 61, 46);
    private static final Color BG_LIGHT = new Color(93, 80, 60);
    private static final Color BORDER_DARK = new Color(40, 34, 24);
    private static final Color BORDER_GOLD = new Color(152, 128, 51);
    private static final Color TEXT_GOLD = new Color(255, 215, 0);
    private static final Color TEXT_LIGHT = new Color(210, 195, 160);
    private static final Color FIELD_BG = new Color(30, 26, 18);
    private static final Color FIELD_FG = new Color(230, 220, 190);
    private static final Color BTN_NORMAL = new Color(100, 70, 25);
    private static final Color BTN_HOVER = new Color(130, 95, 35);

    public LoginScreen() {
        super(TitanClient.TITLE + " - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(480, 420);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_DARK);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Stone border frame
                g2.setColor(BORDER_GOLD);
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(8, 8, getWidth() - 16, getHeight() - 16);
                g2.setColor(BORDER_DARK);
                g2.setStroke(new BasicStroke(1));
                g2.drawRect(6, 6, getWidth() - 12, getHeight() - 12);
                g2.drawRect(11, 11, getWidth() - 22, getHeight() - 22);
                // Inner panel bg
                g2.setColor(BG_MED);
                g2.fillRect(15, 15, getWidth() - 30, getHeight() - 30);
                // Top decorative line
                g2.setColor(BORDER_GOLD);
                g2.fillRect(20, 85, getWidth() - 40, 2);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("TitanScape", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 38));
        titleLabel.setForeground(TEXT_GOLD);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Old School Custom RSPS", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Serif", Font.ITALIC, 13));
        subtitleLabel.setForeground(new Color(180, 160, 120));
        gbc.gridy = 1;
        mainPanel.add(subtitleLabel, gbc);

        gbc.gridy = 2;
        mainPanel.add(Box.createVerticalStrut(20), gbc);

        // Server
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(osrsLabel("Server:"), gbc);
        hostField = osrsTextField(TitanClient.DEFAULT_HOST + ":" + TitanClient.DEFAULT_PORT);
        gbc.gridx = 1;
        mainPanel.add(hostField, gbc);

        // Username
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(osrsLabel("Username:"), gbc);
        usernameField = osrsTextField("");
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(osrsLabel("Password:"), gbc);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBackground(FIELD_BG);
        passwordField.setForeground(FIELD_FG);
        passwordField.setCaretColor(TEXT_GOLD);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_GOLD, 1),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.insets = new Insets(18, 60, 5, 60);
        loginButton = new JButton("Login to TitanScape") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(BTN_HOVER);
                } else {
                    g2.setColor(BTN_NORMAL);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(BORDER_GOLD);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 8, 8);
                g2.setColor(TEXT_GOLD);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
            }
        };
        loginButton.setFont(new Font("Serif", Font.BOLD, 16));
        loginButton.setForeground(TEXT_GOLD);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 42));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(loginButton, gbc);

        // Status
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 20, 5, 20);
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setForeground(new Color(200, 60, 60));
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        mainPanel.add(statusLabel, gbc);

        loginButton.addActionListener(e -> attemptLogin());
        passwordField.addActionListener(e -> attemptLogin());

        setContentPane(mainPanel);
        setVisible(true);
        usernameField.requestFocusInWindow();
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String hostStr = hostField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter username and password.");
            return;
        }

        String host = TitanClient.DEFAULT_HOST;
        int port = TitanClient.DEFAULT_PORT;
        if (hostStr.contains(":")) {
            String[] parts = hostStr.split(":");
            host = parts[0];
            port = Integer.parseInt(parts[1]);
        } else if (!hostStr.isEmpty()) {
            host = hostStr;
        }

        loginButton.setEnabled(false);
        statusLabel.setText("Connecting to TitanScape...");
        statusLabel.setForeground(TEXT_GOLD);

        String finalHost = host;
        int finalPort = port;
        new Thread(() -> {
            boolean success = TitanClient.getInstance().connect(finalHost, finalPort, username, password);
            SwingUtilities.invokeLater(() -> {
                if (success) {
                    statusLabel.setText("Welcome to TitanScape!");
                    statusLabel.setForeground(new Color(80, 200, 80));
                    dispose();
                    TitanClient.getInstance().openGame();
                } else {
                    statusLabel.setText("Connection failed. Check server address.");
                    statusLabel.setForeground(new Color(200, 60, 60));
                    loginButton.setEnabled(true);
                }
            });
        }).start();
    }

    private JLabel osrsLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_LIGHT);
        label.setFont(new Font("Serif", Font.BOLD, 14));
        return label;
    }

    private JTextField osrsTextField(String defaultText) {
        JTextField field = new JTextField(defaultText);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 30));
        field.setBackground(FIELD_BG);
        field.setForeground(FIELD_FG);
        field.setCaretColor(TEXT_GOLD);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_GOLD, 1),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        return field;
    }
}
