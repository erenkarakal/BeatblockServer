package me.eren.bbserver.network.packets;

import me.eren.bbserver.connection.ClientConnection;

public interface BeatblockPacket {
    void handle(ClientConnection playerConnection);
}
