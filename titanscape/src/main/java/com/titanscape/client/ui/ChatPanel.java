package com.titanscape.client.ui;

import com.titanscape.client.TitanClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Chat panel at the bottom of the game window.
 */
public final class ChatPanel extends JPanel {

    private final List<String> messages = new ArrayList<>();
    private final JTextArea chatArea;
    private final JTextField inputField;
    private final JScrollPane scrollPane;

    public ChatPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 150));
        setBackground(new Color(15, 15, 30));

        // Chat display
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(new Color(10, 10, 25));
        chatArea.setForeground(new Color(200, 200, 220));
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setMargin(new Insets(5, 5, 5, 5));

        scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 80)));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Input bar
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(20, 20, 45));

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        inputField.setBackground(new Color(30, 30, 60));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 100)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        JButton sendButton = new JButton("Send");
        sendButton.setBackground(new Color(40, 100, 40));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Actions
        ActionListener sendAction = e -> sendInput();
        inputField.addActionListener(sendAction);
        sendButton.addActionListener(sendAction);

        addMessage("[TitanScape] Welcome! Type ::help for commands.");
    }

    private void sendInput() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;

        inputField.setText("");

        if (text.startsWith("::")) {
            String cmd = text.substring(2);
            addMessage("[Command] ::" + cmd);
            if (TitanClient.getInstance().getConnection() != null) {
                TitanClient.getInstance().getConnection().sendCommand(cmd);
            }
        } else {
            addMessage("[" + TitanClient.getInstance().getUsername() + "] " + text);
            if (TitanClient.getInstance().getConnection() != null) {
                TitanClient.getInstance().getConnection().sendChat(text);
            }
        }
    }

    public void addMessage(String message) {
        messages.add(message);
        chatArea.append(message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }
}
