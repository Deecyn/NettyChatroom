package deecyn.client.handler;

import deecyn.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();

        System.out.println("\n" + fromUserName + "（" + fromUserId + "）--> " + messageResponsePacket.getMessage());
    }
}
