package com.titanscape.client.ui;

import com.titanscape.client.TitanClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Login screen with username/password fields and server connection.
 */
public final class LoginScreen extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JTextField hostField;
    private final JButton loginButton;
    private final JLabel statusLabel;

    public LoginScreen() {
        super(TitanClient.TITLE + " - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(450, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(15, 15, 35),
                        0, getHeight(), new Color(30, 30, 70));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("TitanScape", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 215, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Custom RSPS v" + TitanClient.VERSION, SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(180, 180, 200));
        gbc.gridy = 1;
        mainPanel.add(subtitleLabel, gbc);

        // Spacer
        gbc.gridy = 2;
        mainPanel.add(Box.createVerticalStrut(15), gbc);

        // Host
        gbc.gridwidth = 1;
        JLabel hostLabel = styledLabel("Server:");
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(hostLabel, gbc);

        hostField = styledTextField(TitanClient.DEFAULT_HOST + ":" + TitanClient.DEFAULT_PORT);
        gbc.gridx = 1;
        mainPanel.add(hostField, gbc);

        // Username
        JLabel userLabel = styledLabel("Username:");
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(userLabel, gbc);

        usernameField = styledTextField("");
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        // Password
        JLabel passLabel = styledLabel("Password:");
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(passLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 15, 5, 15);
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setBackground(new Color(40, 120, 40));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(loginButton, gbc);

        // Status
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 15, 5, 15);
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setForeground(new Color(255, 100, 100));
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
        statusLabel.setText("Connecting...");
        statusLabel.setForeground(new Color(200, 200, 100));

        String finalHost = host;
        int finalPort = port;
        new Thread(() -> {
            boolean success = TitanClient.getInstance().connect(finalHost, finalPort, username, password);
            SwingUtilities.invokeLater(() -> {
                if (success) {
                    statusLabel.setText("Login successful!");
                    statusLabel.setForeground(new Color(100, 255, 100));
                    dispose();
                    TitanClient.getInstance().openGame();
                } else {
                    statusLabel.setText("Connection failed. Check server address.");
                    statusLabel.setForeground(new Color(255, 100, 100));
                    loginButton.setEnabled(true);
                }
            });
        }).start();
    }

    private JLabel styledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(200, 200, 220));
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return label;
    }

    private JTextField styledTextField(String defaultText) {
        JTextField field = new JTextField(defaultText);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 30));
        return field;
    }
}
