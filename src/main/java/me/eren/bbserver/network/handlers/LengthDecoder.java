package me.eren.bbserver.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class LengthDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // skip if the channel is closed
        if (!ctx.channel().isActive()) {
            in.skipBytes(in.readableBytes());
            return;
        }

        short length = in.readShort();
        in.markReaderIndex();

        // wait until there is enough data to read
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }

        out.add(in.readBytes(length));
    }

}
