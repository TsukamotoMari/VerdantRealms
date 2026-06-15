package com.titanscape.server.net;

import com.titanscape.server.net.codec.PacketDecoder;
import com.titanscape.server.net.codec.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Netty-based network server that accepts game client connections.
 */
public final class NetworkServer {

    private static final Logger logger = LogManager.getLogger(NetworkServer.class);

    private final int port;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel channel;

    public NetworkServer(int port) {
        this.port = port;
    }

    public void bind() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new PacketDecoder());
                        pipeline.addLast("encoder", new PacketEncoder());
                        pipeline.addLast("handler", new GameChannelHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(port).sync();
            channel = future.channel();
            logger.info("Network server bound to port {}", port);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Failed to bind network server", e);
        }
    }

    public void shutdown() {
        if (channel != null) {
            channel.close();
        }
        if (bossGroup != null) bossGroup.shutdownGracefully();
        if (workerGroup != null) workerGroup.shutdownGracefully();
        logger.info("Network server shut down.");
    }
}
