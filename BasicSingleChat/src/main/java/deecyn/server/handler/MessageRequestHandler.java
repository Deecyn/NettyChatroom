package deecyn.server.handler;

import deecyn.protocol.request.MessageRequestPacket;
import deecyn.protocol.response.MessageResponsePacket;
import deecyn.server.session.Session;
import deecyn.server.session.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        // 根据连接拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 根据消息发送方的会话信息构造要转发的信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(msg.getMessage());

        System.out.println("（" + session.getUserName() + " --> " + msg.getToUserId() + "）: " + msg.getMessage());

        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + msg.getToUserId() + "] 不在线，发送失败!");
        }
    }
}
