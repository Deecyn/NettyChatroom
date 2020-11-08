package deecyn.client;

import deecyn.client.handler.LoginResponseHandler;
import deecyn.client.handler.MessageResponseHandler;
import deecyn.protocol.codec.PacketDecoder;
import deecyn.protocol.codec.PacketEncoder;
import deecyn.protocol.codec.PacketSplitter;
import deecyn.protocol.request.LoginRequestPacket;
import deecyn.protocol.request.MessageRequestPacket;
import deecyn.server.session.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class NettyClient {

    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 9001;
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(
                                new PacketSplitter(),
                                new PacketDecoder(),
                                new LoginResponseHandler(),
                                new MessageResponseHandler(),
                                new PacketEncoder()
                        );
//                      channel.pipeline().addLast(new InBoundHandlerA(), new InBoundHandlerB(), new InBoundHandlerC());
                    }
                });

        connectServer(bootstrap, SERVER_HOST, SERVER_PORT, 5);
    }

    private static void connectServer(final Bootstrap bootstrap, final String host, final int port, int retry){

        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + " 连接服务器成功，启动控制台线程...");

                Channel channel =((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0){
                System.err.println("重连次数已用完，放弃连接 ！");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connectServer(bootstrap, host, port, retry - 1),
                        delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    System.out.print("输入用户名登录：");
                    String username = scanner.nextLine();
                    loginRequestPacket.setUserName(username);
                    // 使用默认的密码
                    loginRequestPacket.setPassword("abc123");

                    channel.writeAndFlush(loginRequestPacket);
                    // 等待登录
                    waitForResponse();
                } else {
                    System.out.print("请输入对方的 ID：");
                    String toUserId = scanner.nextLine();
                    System.out.print("请输入待发送的消息：");
                    String message = scanner.nextLine();
                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                }
            }
        }).start();
    }

    private static void waitForResponse() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // 忽略
        }
    }

    private static void waitForResponse10() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            // 忽略
        }
    }
}
