package deecyn.server;

import deecyn.protocol.Packet;
import deecyn.protocol.PacketCodeC;
import deecyn.protocol.request.LoginRequestPacket;
import deecyn.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
@Deprecated
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("服务端收到登录请求...");
        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodeC.SINGLE_INSTANCE.decode(requestByteBuf);

        // 校验登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            // 登录校验
            if (valid(loginRequestPacket)) {
                // 校验成功
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + "：客户登录成功！");
            } else {
                // 校验失败
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + ": 客户登录失败!");
            }

            // 登录响应
//            ByteBuf responseByteBuf = PacketCodeC.SINGLE_INSTANCE.encode(ctx.alloc(), loginResponsePacket);
//            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

}
