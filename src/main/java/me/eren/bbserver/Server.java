package me.eren.bbserver;

import me.eren.bbserver.network.BeatblockServer;
import me.eren.bbserver.network.packets.BeatblockPacket;
import org.machinemc.paklet.PacketEncoder;
import org.machinemc.paklet.PacketFactory;
import org.machinemc.paklet.PacketFactoryImpl;
import org.machinemc.paklet.SerializerProviderImpl;
import org.machinemc.paklet.serialization.SerializerProvider;
import org.machinemc.paklet.serialization.catalogue.DefaultSerializationRules;
import org.machinemc.paklet.serialization.catalogue.DefaultSerializers;

public class Server {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        BeatblockServer.initialize(port, createPacketFactory()).start();
    }

    private static PacketFactory createPacketFactory() {
        SerializerProvider serializerProvider = new SerializerProviderImpl();
        serializerProvider.addSerializers(DefaultSerializers.class);
        serializerProvider.addSerializationRules(DefaultSerializationRules.class);

        PacketFactory packetFactory = new PacketFactoryImpl(PacketEncoder.varInt(), serializerProvider);
        packetFactory.addPackets(BeatblockPacket.class);

        return packetFactory;
    }

}