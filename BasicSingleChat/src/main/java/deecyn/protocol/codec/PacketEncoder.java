package deecyn.protocol.codec;

import deecyn.protocol.Packet;
import deecyn.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Deecyn
 * @version 0.1
 * Description: Java 消息的编码
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.SINGLE_INSTANCE.encode(out, msg);
    }
}
