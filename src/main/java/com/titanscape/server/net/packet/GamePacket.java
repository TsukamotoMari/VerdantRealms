package com.titanscape.server.net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * Represents a network packet with an opcode and payload.
 */
public final class GamePacket {

    // Opcodes
    public static final int LOGIN_REQUEST = 1;
    public static final int LOGIN_RESPONSE = 2;
    public static final int CHAT_MESSAGE = 3;
    public static final int COMMAND = 4;
    public static final int MOVEMENT = 5;
    public static final int ATTACK_NPC = 6;
    public static final int EQUIP_ITEM = 7;
    public static final int SERVER_MESSAGE = 8;
    public static final int PLAYER_UPDATE = 9;
    public static final int NPC_UPDATE = 10;
    public static final int INVENTORY_UPDATE = 11;
    public static final int SKILL_UPDATE = 12;
    public static final int WORLD_STATE = 13;

    private final int opcode;
    private final ByteBuf buffer;

    public GamePacket(int opcode) {
        this.opcode = opcode;
        this.buffer = Unpooled.buffer(256);
    }

    public GamePacket(int opcode, ByteBuf buffer) {
        this.opcode = opcode;
        this.buffer = buffer;
    }

    public int getOpcode() {
        return opcode;
    }

    public ByteBuf getBuffer() {
        return buffer;
    }

    public void writeByte(int value) {
        buffer.writeByte(value);
    }

    public void writeShort(int value) {
        buffer.writeShort(value);
    }

    public void writeInt(int value) {
        buffer.writeInt(value);
    }

    public void writeLong(long value) {
        buffer.writeLong(value);
    }

    public void writeString(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        buffer.writeShort(bytes.length);
        buffer.writeBytes(bytes);
    }

    public int readByte() {
        return buffer.readByte();
    }

    public int readShort() {
        return buffer.readShort();
    }

    public int readInt() {
        return buffer.readInt();
    }

    public long readLong() {
        return buffer.readLong();
    }

    public String readString() {
        int length = buffer.readShort();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public int readableBytes() {
        return buffer.readableBytes();
    }
}
