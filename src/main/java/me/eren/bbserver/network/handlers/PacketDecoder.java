package me.eren.bbserver.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.eren.bbserver.network.packets.BeatblockPacket;
import org.machinemc.paklet.Packet;
import org.machinemc.paklet.PacketFactory;
import org.machinemc.paklet.netty.NettyDataVisitor;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private final PacketFactory packetFactory;

    public PacketDecoder(PacketFactory factory) {
        this.packetFactory = factory;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (!in.isReadable())
            return;
        BeatblockPacket packet = packetFactory.create(Packet.DEFAULT, new NettyDataVisitor(in));
        out.add(packet);
    }

}
