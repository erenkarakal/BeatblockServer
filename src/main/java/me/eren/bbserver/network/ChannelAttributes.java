package me.eren.bbserver.network;

import io.netty.util.AttributeKey;
import me.eren.bbserver.connection.ClientConnection;

public class ChannelAttributes {
    /**
     * Player instance
     */
    public static final AttributeKey<ClientConnection> CONNECTION = AttributeKey.newInstance("connection");
}
