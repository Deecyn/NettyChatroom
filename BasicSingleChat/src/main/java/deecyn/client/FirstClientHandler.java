package deecyn.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端写出数据...");

        for (int i = 10; i < 100; i++) {
            ByteBuf byteBuf = getByteBuf(ctx);
            ctx.channel().writeAndFlush(byteBuf);
        }
    }

    int count = 0;
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        ByteBuf buffer = ctx.alloc().buffer();
        byte[] bytes = ("你好，我是客户端！--" + count + " 次").getBytes(CharsetUtil.UTF_8);
        count++;
        buffer.writeBytes(bytes);
        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 客户端读到数据 --> " + byteBuf.toString(CharsetUtil.UTF_8));
    }
}
