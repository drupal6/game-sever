package game.netty.telnet.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TelnetServer {

	public static void main(String[] arg) {
		TelnetServer server = new TelnetServer();
		server.run("192.168.1.128", 8082);
	}
	
	public void run(String inetHost, int inetPort) {
		EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1);
		EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossEventLoopGroup, workEventLoopGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new TelnetServerInitializer());
			b.bind(inetHost, inetPort).sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossEventLoopGroup.shutdownGracefully();
			workEventLoopGroup.shutdownGracefully();
		}
	}
	
}
