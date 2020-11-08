package deecyn.client.test_handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class InBoundHandlerA extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("InBoundHandlerA: A=======");

        // 调用父类的 channelActive() 方法，而这里父类的 channelActive() 方法会自动调用到下一个 inBoundHandler
        //    的 channelActive() 方法，并且会把当前 inBoundHandler 里处理完毕的对象传递到下一个 inBoundHandler。
        // 如果不调用父类的 channelActive() 方法，则不会调用到后续的 inBoundHandler。
        super.channelActive(ctx);
    }
}
