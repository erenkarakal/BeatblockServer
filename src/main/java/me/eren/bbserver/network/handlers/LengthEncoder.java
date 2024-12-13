package me.eren.bbserver.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class LengthEncoder extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        if (msg.readableBytes() > 2) {
            throw new IllegalArgumentException("Packet is way too big!");
        }
        out.writeShort(msg.readableBytes());
        out.writeBytes(msg);
    }

}
