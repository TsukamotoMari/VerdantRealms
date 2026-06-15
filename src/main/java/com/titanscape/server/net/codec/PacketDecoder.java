package com.titanscape.server.net.codec;

import com.titanscape.server.net.packet.GamePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Decodes incoming bytes into GamePacket objects.
 * Protocol: [opcode:1byte][length:2bytes][payload:N bytes]
 */
public final class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 3) {
            return;
        }

        in.markReaderIndex();
        int opcode = in.readByte() & 0xFF;
        int length = in.readShort() & 0xFFFF;

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }

        ByteBuf payload = in.readBytes(length);
        out.add(new GamePacket(opcode, payload));
    }
}
