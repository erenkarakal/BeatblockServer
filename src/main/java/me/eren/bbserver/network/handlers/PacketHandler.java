package me.eren.bbserver.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.eren.bbserver.connection.ClientConnection;
import me.eren.bbserver.network.ChannelAttributes;
import me.eren.bbserver.network.packets.BeatblockPacket;

public class PacketHandler extends SimpleChannelInboundHandler<BeatblockPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BeatblockPacket packet) {
        ClientConnection connection = ctx.channel().attr(ChannelAttributes.CONNECTION).get();
        packet.handle(connection);
    }

}
