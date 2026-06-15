package com.titanscape.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The main game rendering viewport. Displays the isometric game world.
 */
public final class GamePanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private int playerX = 3222;
    private int playerY = 3218;
    private int cameraX = 0;
    private int cameraY = 0;
    private int hoveredTileX = -1;
    private int hoveredTileY = -1;

    private static final int TILE_SIZE = 32;
    private static final int VIEW_TILES = 20;
    private static final Color GRASS_COLOR_1 = new Color(34, 139, 34);
    private static final Color GRASS_COLOR_2 = new Color(40, 155, 40);
    private static final Color PATH_COLOR = new Color(139, 119, 101);
    private static final Color WATER_COLOR = new Color(30, 80, 160);
    private static final Color PLAYER_COLOR = new Color(255, 215, 0);
    private static final Color NPC_COLOR = new Color(0, 200, 255);

    public GamePanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(640, 480));
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        Timer repaintTimer = new Timer(16, e -> repaint());
        repaintTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Draw ground tiles
        for (int dx = -VIEW_TILES; dx <= VIEW_TILES; dx++) {
            for (int dy = -VIEW_TILES; dy <= VIEW_TILES; dy++) {
                int tileX = playerX + dx;
                int tileY = playerY + dy;
                int screenX = centerX + dx * TILE_SIZE - cameraX;
                int screenY = centerY - dy * TILE_SIZE - cameraY;

                Color tileColor = getTileColor(tileX, tileY);
                g2.setColor(tileColor);
                g2.fillRect(screenX - TILE_SIZE / 2, screenY - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                g2.setColor(tileColor.darker());
                g2.drawRect(screenX - TILE_SIZE / 2, screenY - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);

                // Highlight hovered tile
                if (tileX == hoveredTileX && tileY == hoveredTileY) {
                    g2.setColor(new Color(255, 255, 255, 60));
                    g2.fillRect(screenX - TILE_SIZE / 2, screenY - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Draw some sample NPCs
        drawNpc(g2, centerX, centerY, 3225, 3220, "Guard");
        drawNpc(g2, centerX, centerY, 3218, 3215, "Shop Keeper");
        drawNpc(g2, centerX, centerY, 3230, 3225, "Titan Warrior");

        // Draw player
        g2.setColor(PLAYER_COLOR);
        g2.fillOval(centerX - 8, centerY - 8, 16, 16);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        FontMetrics fm = g2.getFontMetrics();
        String name = "You";
        g2.drawString(name, centerX - fm.stringWidth(name) / 2, centerY - 14);

        // Draw minimap indicator
        drawMinimap(g2);

        // Draw coordinates
        g2.setColor(new Color(255, 255, 255, 180));
        g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g2.drawString("Pos: " + playerX + ", " + playerY, 10, getHeight() - 10);
    }

    private void drawNpc(Graphics2D g2, int cx, int cy, int npcX, int npcY, String name) {
        int dx = npcX - playerX;
        int dy = npcY - playerY;
        int sx = cx + dx * TILE_SIZE - cameraX;
        int sy = cy - dy * TILE_SIZE - cameraY;

        g2.setColor(NPC_COLOR);
        g2.fillOval(sx - 6, sy - 6, 12, 12);
        g2.setColor(Color.CYAN);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(name, sx - fm.stringWidth(name) / 2, sy - 10);
    }

    private void drawMinimap(Graphics2D g2) {
        int mmSize = 120;
        int mmX = getWidth() - mmSize - 10;
        int mmY = 10;

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillOval(mmX, mmY, mmSize, mmSize);
        g2.setColor(new Color(100, 100, 100));
        g2.drawOval(mmX, mmY, mmSize, mmSize);

        // Player dot
        g2.setColor(Color.WHITE);
        g2.fillRect(mmX + mmSize / 2 - 2, mmY + mmSize / 2 - 2, 4, 4);

        g2.setColor(new Color(200, 200, 200));
        g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
        g2.drawString("N", mmX + mmSize / 2 - 3, mmY + 12);
    }

    private Color getTileColor(int x, int y) {
        if ((x + y) % 17 == 0) return WATER_COLOR;
        if (Math.abs(x - 3222) <= 1) return PATH_COLOR;
        if (Math.abs(y - 3218) <= 1) return PATH_COLOR;
        return (x + y) % 2 == 0 ? GRASS_COLOR_1 : GRASS_COLOR_2;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int dx = (e.getX() - centerX + cameraX + TILE_SIZE / 2) / TILE_SIZE;
        int dy = -(e.getY() - centerY + cameraY + TILE_SIZE / 2) / TILE_SIZE;
        playerX += dx;
        playerY += dy;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        hoveredTileX = playerX + (e.getX() - centerX + cameraX + TILE_SIZE / 2) / TILE_SIZE;
        hoveredTileY = playerY - (e.getY() - centerY + cameraY + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> playerY++;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> playerY--;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> playerX--;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> playerX++;
        }
        repaint();
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    public int getPlayerX() { return playerX; }
    public int getPlayerY() { return playerY; }
}
