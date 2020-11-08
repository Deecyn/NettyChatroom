package deecyn.client.test_handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class InBoundHandlerC extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("InBoundHandlerC: C=======");
        super.channelActive(ctx);
    }
}
