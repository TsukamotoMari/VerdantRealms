package com.titanscape.client.net;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Manages the TCP connection to the game server.
 */
public final class ClientConnection {

    private final String host;
    private final int port;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private volatile boolean connected;

    public ClientConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(10000);
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            connected = true;
            return true;
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            return false;
        }
    }

    public String login(String username, String password) {
        try {
            sendPacket(1, encodeLogin(username, password));
            return readResponse();
        } catch (IOException e) {
            System.err.println("Login failed: " + e.getMessage());
            return null;
        }
    }

    public void sendChat(String message) {
        try {
            sendPacket(3, encodeString(message));
        } catch (IOException e) {
            System.err.println("Failed to send chat: " + e.getMessage());
        }
    }

    public void sendCommand(String command) {
        try {
            sendPacket(4, encodeString(command));
        } catch (IOException e) {
            System.err.println("Failed to send command: " + e.getMessage());
        }
    }

    public void sendMovement(int x, int y) {
        try {
            ByteBuffer buf = ByteBuffer.allocate(8);
            buf.putInt(x);
            buf.putInt(y);
            sendPacket(5, buf.array());
        } catch (IOException e) {
            System.err.println("Failed to send movement: " + e.getMessage());
        }
    }

    private void sendPacket(int opcode, byte[] payload) throws IOException {
        out.writeByte(opcode);
        out.writeShort(payload.length);
        out.write(payload);
        out.flush();
    }

    private byte[] encodeLogin(String username, String password) {
        byte[] userBytes = username.getBytes(StandardCharsets.UTF_8);
        byte[] passBytes = password.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buf = ByteBuffer.allocate(4 + userBytes.length + passBytes.length);
        buf.putShort((short) userBytes.length);
        buf.put(userBytes);
        buf.putShort((short) passBytes.length);
        buf.put(passBytes);
        return buf.array();
    }

    private byte[] encodeString(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buf = ByteBuffer.allocate(2 + bytes.length);
        buf.putShort((short) bytes.length);
        buf.put(bytes);
        return buf.array();
    }

    private String readResponse() throws IOException {
        int opcode = in.readByte() & 0xFF;
        int length = in.readShort() & 0xFFFF;
        byte[] data = new byte[length];
        in.readFully(data);

        ByteBuffer buf = ByteBuffer.wrap(data);
        int strLen = buf.getShort() & 0xFFFF;
        byte[] strBytes = new byte[strLen];
        buf.get(strBytes);
        String result = new String(strBytes, StandardCharsets.UTF_8);

        if (result.equals("SUCCESS") && buf.remaining() >= 4) {
            int rights = buf.getInt();
            return "SUCCESS," + rights;
        }
        return result;
    }

    public void disconnect() {
        connected = false;
        try {
            if (socket != null) socket.close();
        } catch (IOException ignored) {}
    }

    public boolean isConnected() {
        return connected && socket != null && !socket.isClosed();
    }
}
