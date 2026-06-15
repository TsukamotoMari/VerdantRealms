package com.titanscape.client.ui;

import com.titanscape.client.TitanClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public final class ChatPanel extends JPanel {

    private final List<String> messages = new ArrayList<>();
    private final JTextArea chatArea;
    private final JTextField inputField;
    private final JScrollPane scrollPane;

    private static final Color STONE_DARK = new Color(62, 53, 41);
    private static final Color STONE_MED = new Color(79, 68, 52);
    private static final Color CHAT_BG = new Color(43, 37, 27);
    private static final Color BORDER_GOLD = new Color(152, 128, 51);
    private static final Color TEXT_LIGHT = new Color(210, 195, 160);
    private static final Color TEXT_GOLD = new Color(255, 215, 0);
    private static final Color INPUT_BG = new Color(35, 30, 22);

    public ChatPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 150));
        setBackground(STONE_DARK);
        setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, BORDER_GOLD));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(CHAT_BG);
        chatArea.setForeground(TEXT_LIGHT);
        chatArea.setFont(new Font("Serif", Font.PLAIN, 12));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setMargin(new Insets(5, 8, 5, 8));
        chatArea.setSelectionColor(new Color(100, 85, 55));
        chatArea.setSelectedTextColor(TEXT_GOLD);

        scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(90, 77, 55)));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(CHAT_BG);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(STONE_MED);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        inputField = new JTextField();
        inputField.setFont(new Font("Serif", Font.PLAIN, 13));
        inputField.setBackground(INPUT_BG);
        inputField.setForeground(TEXT_LIGHT);
        inputField.setCaretColor(TEXT_GOLD);
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_GOLD, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        JButton sendButton = new JButton("Send") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(110, 85, 40));
                } else {
                    g2.setColor(new Color(80, 65, 35));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2.setColor(BORDER_GOLD);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                g2.setColor(TEXT_LIGHT);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
            }
        };
        sendButton.setFont(new Font("Serif", Font.BOLD, 12));
        sendButton.setContentAreaFilled(false);
        sendButton.setBorderPainted(false);
        sendButton.setFocusPainted(false);
        sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

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
