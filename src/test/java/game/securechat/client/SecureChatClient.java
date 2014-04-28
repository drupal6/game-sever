package game.securechat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SecureChatClient {

	public static void main(String[] arg) {
		SecureChatClient client = new SecureChatClient();
		client.run("192.168.1.128", 8084);
	}
	
	public void run(String inetHost, int inetPort) {
		EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workEventLoopGroup)
			.channel(NioSocketChannel.class)
			.handler(new SecureChatClientInitializer());
			b.connect(inetHost, inetPort).sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workEventLoopGroup.shutdownGracefully();
		}
	}
}