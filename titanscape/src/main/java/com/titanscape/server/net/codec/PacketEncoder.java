package com.titanscape.server.net.codec;

import com.titanscape.server.net.packet.GamePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Encodes GamePacket objects into bytes for transmission.
 * Protocol: [opcode:1byte][length:2bytes][payload:N bytes]
 */
public final class PacketEncoder extends MessageToByteEncoder<GamePacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, GamePacket packet, ByteBuf out) {
        ByteBuf payload = packet.getBuffer();
        int length = payload.readableBytes();

        out.writeByte(packet.getOpcode());
        out.writeShort(length);
        out.writeBytes(payload);
    }
}
