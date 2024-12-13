package me.eren.bbserver.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.machinemc.paklet.Packet;
import org.machinemc.paklet.PacketFactory;
import org.machinemc.paklet.netty.NettyDataVisitor;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private final PacketFactory packetFactory;

    public PacketEncoder(PacketFactory packetFactory) {
        this.packetFactory = packetFactory;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) {
        packetFactory.write(msg, Packet.DEFAULT, new NettyDataVisitor(out));
    }

}
