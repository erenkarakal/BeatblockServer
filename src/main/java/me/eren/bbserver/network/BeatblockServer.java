package me.eren.bbserver.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.eren.bbserver.network.handlers.LengthDecoder;
import me.eren.bbserver.network.handlers.PacketDecoder;
import me.eren.bbserver.network.handlers.PacketEncoder;
import me.eren.bbserver.network.handlers.PacketHandler;
import me.eren.bbserver.connection.ClientConnection;
import org.machinemc.paklet.PacketFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeatblockServer {

    private static BeatblockServer instance;
    private final int port;
    private final PacketFactory packetFactory;
    private final Set<ClientConnection> connections = ConcurrentHashMap.newKeySet();

    private BeatblockServer(int port, PacketFactory packetFactory) {
        this.port = port;
        this.packetFactory = packetFactory;
    }

    public static BeatblockServer initialize(int port, PacketFactory packetFactory) {
        if (instance != null) {
            instance.stop();
        }
        instance = new BeatblockServer(port, packetFactory);
        return instance;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new Initializer());
            ChannelFuture f = b.bind(port).sync();
            System.out.println("Server started on port " + port);
            f.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        instance.stop();
    }

    public final class Initializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) {
            ch.pipeline()
                    .addLast("LengthDecoder", new LengthDecoder())
                    .addLast("PacketDecoder", new PacketDecoder(packetFactory))
                    .addLast("PacketHandler", new PacketHandler())
                    .addLast("PacketEncoder", new PacketEncoder(packetFactory))
                    .addLast("LengthEncoder", new LengthDecoder());
            connections.add(new ClientConnection(ch, packetFactory));
        }
    }
}
