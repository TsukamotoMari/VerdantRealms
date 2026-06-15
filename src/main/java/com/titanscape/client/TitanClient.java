package com.titanscape.client;

import com.titanscape.client.net.ClientConnection;
import com.titanscape.client.ui.LoginScreen;
import com.titanscape.client.ui.GameFrame;

import javax.swing.*;

/**
 * TitanScape Client — launches the game window.
 * Double-click Start-Client.bat on Windows to run.
 */
public final class TitanClient {

    public static final String TITLE = "TitanScape";
    public static final String VERSION = "1.0.0";
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 43594;

    private static TitanClient instance;
    private ClientConnection connection;
    private GameFrame gameFrame;
    private String username;
    private int rights;

    public static TitanClient getInstance() {
        if (instance == null) {
            instance = new TitanClient();
        }
        return instance;
    }

    public void launch() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new LoginScreen();
        });
    }

    public boolean connect(String host, int port, String username, String password) {
        this.username = username;
        connection = new ClientConnection(host, port);
        if (connection.connect()) {
            String result = connection.login(username, password);
            if (result != null && result.startsWith("SUCCESS")) {
                String[] parts = result.split(",");
                if (parts.length > 1) {
                    rights = Integer.parseInt(parts[1].trim());
                }
                return true;
            }
        }
        return false;
    }

    public void openGame() {
        SwingUtilities.invokeLater(() -> {
            gameFrame = new GameFrame(username, rights);
            gameFrame.setVisible(true);
        });
    }

    public ClientConnection getConnection() {
        return connection;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public String getUsername() {
        return username;
    }

    public int getRights() {
        return rights;
    }

    public static void main(String[] args) {
        TitanClient.getInstance().launch();
    }
}
