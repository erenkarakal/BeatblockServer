package me.eren.bbserver.connection;

import io.netty.channel.Channel;
import me.eren.bbserver.network.packets.BeatblockPacket;
import org.machinemc.paklet.Packet;
import org.machinemc.paklet.PacketFactory;
import org.machinemc.paklet.netty.NettyDataVisitor;

public class ClientConnection {

    private final Channel channel;
    private final PacketFactory packetFactory;
    private ConnectionStatus status = ConnectionStatus.LOGIN;

    public ClientConnection(Channel channel, PacketFactory packetFactory) {
        this.channel = channel;
        this.packetFactory = packetFactory;
    }

    public Channel getChannel() {
        return channel;
    }

    public ConnectionStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }

    public void sendPacket(BeatblockPacket packet) {
        packetFactory.write(packet, Packet.DEFAULT, new NettyDataVisitor(channel.alloc().buffer()));
    }

}
