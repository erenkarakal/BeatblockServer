package me.eren.bbserver.network.packets;

import me.eren.bbserver.connection.ClientConnection;
import org.machinemc.paklet.Packet;

/**
 *  A packet that echoes the byte it receives
 */
@Packet(id = 0x00, catalogue = BeatblockPacket.class)
public class PingPacket implements BeatblockPacket {

    private byte random;

    @Override
    public void handle(ClientConnection connection) {
        connection.sendPacket(this);
    }

}
