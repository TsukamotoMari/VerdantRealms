package com.titanscape.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class GamePanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private int playerX = 3222;
    private int playerY = 3218;
    private int cameraX = 0;
    private int cameraY = 0;
    private int hoveredTileX = -1;
    private int hoveredTileY = -1;

    private static final int TILE_SIZE = 32;
    private static final int VIEW_TILES = 20;

    // OSRS-authentic earth tones
    private static final Color GRASS_1 = new Color(76, 120, 42);
    private static final Color GRASS_2 = new Color(68, 112, 38);
    private static final Color GRASS_3 = new Color(82, 126, 48);
    private static final Color GRASS_DARK = new Color(58, 96, 32);
    private static final Color PATH_COLOR = new Color(145, 125, 90);
    private static final Color PATH_EDGE = new Color(130, 112, 78);
    private static final Color WATER_DEEP = new Color(28, 65, 120);
    private static final Color WATER_SHALLOW = new Color(42, 85, 140);
    private static final Color SAND_COLOR = new Color(190, 170, 120);
    private static final Color PLAYER_COLOR = new Color(255, 255, 255);
    private static final Color NPC_COLOR_BODY = new Color(200, 180, 40);
    private static final Color NPC_COLOR_HEAD = new Color(230, 180, 130);
    private static final Color TEXT_YELLOW = new Color(255, 255, 0);
    private static final Color TEXT_CYAN = new Color(0, 255, 255);
    private static final Color MINIMAP_BG = new Color(62, 53, 41);
    private static final Color BORDER_GOLD = new Color(152, 128, 51);

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
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

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

                // Subtle grid lines (OSRS style)
                g2.setColor(new Color(0, 0, 0, 20));
                g2.drawLine(screenX - TILE_SIZE / 2, screenY - TILE_SIZE / 2,
                           screenX + TILE_SIZE / 2, screenY - TILE_SIZE / 2);
                g2.drawLine(screenX - TILE_SIZE / 2, screenY - TILE_SIZE / 2,
                           screenX - TILE_SIZE / 2, screenY + TILE_SIZE / 2);

                // Highlight hovered tile with yellow OSRS cursor
                if (tileX == hoveredTileX && tileY == hoveredTileY) {
                    g2.setColor(new Color(255, 255, 0, 40));
                    g2.fillRect(screenX - TILE_SIZE / 2, screenY - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
                    g2.setColor(new Color(255, 255, 0, 100));
                    g2.drawRect(screenX - TILE_SIZE / 2, screenY - TILE_SIZE / 2, TILE_SIZE - 1, TILE_SIZE - 1);
                }
            }
        }

        // Draw trees (small decorations)
        drawTree(g2, centerX, centerY, 3226, 3222);
        drawTree(g2, centerX, centerY, 3219, 3221);
        drawTree(g2, centerX, centerY, 3215, 3216);
        drawTree(g2, centerX, centerY, 3228, 3214);
        drawTree(g2, centerX, centerY, 3217, 3225);

        // Draw NPCs
        drawNpc(g2, centerX, centerY, 3225, 3220, "Guard", new Color(160, 50, 50));
        drawNpc(g2, centerX, centerY, 3218, 3215, "Shop Keeper", new Color(80, 60, 150));
        drawNpc(g2, centerX, centerY, 3230, 3225, "Titan Warrior", new Color(180, 150, 40));

        // Draw player character
        drawPlayer(g2, centerX, centerY);

        // Draw minimap
        drawMinimap(g2);

        // Draw coordinates (OSRS style)
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        String posText = "Tile: (" + playerX + ", " + playerY + ")";
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(posText, 11, getHeight() - 9);
        g2.setColor(TEXT_YELLOW);
        g2.drawString(posText, 10, getHeight() - 10);
    }

    private void drawPlayer(Graphics2D g2, int cx, int cy) {
        // Body
        g2.setColor(new Color(0, 100, 200));
        g2.fillRect(cx - 5, cy - 4, 10, 14);
        // Head
        g2.setColor(new Color(230, 180, 130));
        g2.fillOval(cx - 5, cy - 12, 10, 10);
        // Outline
        g2.setColor(Color.BLACK);
        g2.drawRect(cx - 5, cy - 4, 9, 13);
        g2.drawOval(cx - 5, cy - 12, 9, 9);
        // Name above
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        FontMetrics fm = g2.getFontMetrics();
        String name = "code187";
        int tw = fm.stringWidth(name);
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(cx - tw / 2 - 4, cy - 28, tw + 8, 15, 4, 4);
        g2.setColor(TEXT_YELLOW);
        g2.drawString(name, cx - tw / 2, cy - 17);
    }

    private void drawNpc(Graphics2D g2, int cx, int cy, int npcX, int npcY, String name, Color bodyColor) {
        int dx = npcX - playerX;
        int dy = npcY - playerY;
        int sx = cx + dx * TILE_SIZE - cameraX;
        int sy = cy - dy * TILE_SIZE - cameraY;

        // Body
        g2.setColor(bodyColor);
        g2.fillRect(sx - 5, sy - 4, 10, 14);
        // Head
        g2.setColor(NPC_COLOR_HEAD);
        g2.fillOval(sx - 5, sy - 12, 10, 10);
        // Outline
        g2.setColor(Color.BLACK);
        g2.drawRect(sx - 5, sy - 4, 9, 13);
        g2.drawOval(sx - 5, sy - 12, 9, 9);
        // Name (OSRS cyan)
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(name);
        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRoundRect(sx - tw / 2 - 3, sy - 26, tw + 6, 13, 3, 3);
        g2.setColor(TEXT_CYAN);
        g2.drawString(name, sx - tw / 2, sy - 16);
    }

    private void drawTree(Graphics2D g2, int cx, int cy, int treeX, int treeY) {
        int dx = treeX - playerX;
        int dy = treeY - playerY;
        int sx = cx + dx * TILE_SIZE - cameraX;
        int sy = cy - dy * TILE_SIZE - cameraY;

        // Trunk
        g2.setColor(new Color(100, 70, 35));
        g2.fillRect(sx - 3, sy - 8, 6, 14);
        // Canopy
        g2.setColor(new Color(30, 90, 25));
        g2.fillOval(sx - 12, sy - 22, 24, 18);
        g2.setColor(new Color(45, 110, 35));
        g2.fillOval(sx - 9, sy - 25, 18, 16);
        // Outline
        g2.setColor(new Color(20, 60, 15));
        g2.drawOval(sx - 12, sy - 22, 23, 17);
    }

    private void drawMinimap(Graphics2D g2) {
        int mmSize = 130;
        int mmX = getWidth() - mmSize - 8;
        int mmY = 8;

        // OSRS-style stone frame around minimap
        g2.setColor(MINIMAP_BG);
        g2.fillRoundRect(mmX - 6, mmY - 6, mmSize + 12, mmSize + 12, 8, 8);
        g2.setColor(BORDER_GOLD);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(mmX - 6, mmY - 6, mmSize + 12, mmSize + 12, 8, 8);
        g2.setStroke(new BasicStroke(1));

        // Minimap circle background
        g2.setColor(new Color(50, 85, 40));
        g2.fillOval(mmX, mmY, mmSize, mmSize);

        // Mini tile rendering
        int mmScale = 3;
        for (int mdx = -mmSize / (2 * mmScale); mdx <= mmSize / (2 * mmScale); mdx++) {
            for (int mdy = -mmSize / (2 * mmScale); mdy <= mmSize / (2 * mmScale); mdy++) {
                int tx = playerX + mdx;
                int ty = playerY + mdy;
                int px = mmX + mmSize / 2 + mdx * mmScale;
                int py = mmY + mmSize / 2 - mdy * mmScale;
                int dist = mdx * mdx + mdy * mdy;
                if (dist > (mmSize / (2 * mmScale)) * (mmSize / (2 * mmScale))) continue;
                Color c = getTileColor(tx, ty);
                g2.setColor(c);
                g2.fillRect(px, py, mmScale, mmScale);
            }
        }

        // Minimap circle border
        g2.setColor(new Color(30, 25, 18));
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(mmX, mmY, mmSize, mmSize);
        g2.setStroke(new BasicStroke(1));

        // Player dot (white)
        g2.setColor(Color.WHITE);
        g2.fillRect(mmX + mmSize / 2 - 2, mmY + mmSize / 2 - 2, 5, 5);

        // NPC dots on minimap
        drawMinimapDot(g2, mmX, mmY, mmSize, mmScale, 3225, 3220, TEXT_CYAN);
        drawMinimapDot(g2, mmX, mmY, mmSize, mmScale, 3218, 3215, TEXT_CYAN);
        drawMinimapDot(g2, mmX, mmY, mmSize, mmScale, 3230, 3225, TEXT_CYAN);

        // Compass N
        g2.setColor(new Color(200, 30, 30));
        g2.setFont(new Font("Serif", Font.BOLD, 12));
        g2.drawString("N", mmX + mmSize / 2 - 4, mmY + 14);
    }

    private void drawMinimapDot(Graphics2D g2, int mmX, int mmY, int mmSize, int mmScale,
                                 int npcX, int npcY, Color color) {
        int mdx = npcX - playerX;
        int mdy = npcY - playerY;
        int dist = mdx * mdx + mdy * mdy;
        if (dist > (mmSize / (2 * mmScale)) * (mmSize / (2 * mmScale))) return;
        int px = mmX + mmSize / 2 + mdx * mmScale;
        int py = mmY + mmSize / 2 - mdy * mmScale;
        g2.setColor(color);
        g2.fillRect(px - 1, py - 1, 3, 3);
    }

    private Color getTileColor(int x, int y) {
        // Water features
        if ((x + y) % 17 == 0) return WATER_DEEP;
        if ((x + y) % 17 == 1 || (x + y) % 17 == 16) return WATER_SHALLOW;

        // Paths (cross pattern through spawn)
        boolean onPathX = Math.abs(x - 3222) <= 1;
        boolean onPathY = Math.abs(y - 3218) <= 1;
        if (onPathX || onPathY) {
            if ((onPathX && Math.abs(x - 3222) == 1) || (onPathY && Math.abs(y - 3218) == 1)) {
                return PATH_EDGE;
            }
            return PATH_COLOR;
        }

        // Sand patches near water
        if ((x + y) % 17 == 2 || (x + y) % 17 == 15) return SAND_COLOR;

        // Varied grass
        int hash = ((x * 73856093) ^ (y * 19349669)) & 0x7FFFFFFF;
        int variant = hash % 4;
        return switch (variant) {
            case 0 -> GRASS_1;
            case 1 -> GRASS_2;
            case 2 -> GRASS_3;
            default -> GRASS_DARK;
        };
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
