package deecyn.server;

import deecyn.common.LifeCyCleTestHandler;
import deecyn.protocol.codec.PacketDecoder;
import deecyn.protocol.codec.PacketEncoder;
import deecyn.protocol.codec.PacketSplitter;
import deecyn.server.handler.AuthHandler;
import deecyn.server.handler.LoginRequestHandler;
import deecyn.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class NettyServer {

    private static final int INITIAL_SERVER_PORT = 9001;

    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)  // 1. 指定线程模型
                .channel(NioServerSocketChannel.class)  // 2. 指定 IO 模型
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 3. 指定每个连接的读写处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(
                                new PacketSplitter(),
                                new PacketDecoder(),
                                new LoginRequestHandler(),
                                new AuthHandler(),
                                new MessageRequestHandler(),
                                new PacketEncoder()
                        );
                    }
                });

        bind(serverBootstrap, INITIAL_SERVER_PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功！");
                } else {
                    System.out.println("端口[" + port + "]绑定失败！");
                    // 异步循环连接
//                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
