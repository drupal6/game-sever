package game.netty.filestatic.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpStaticFileServer {

	
	public static void main(String[] arg) {
		HttpStaticFileServer server = new HttpStaticFileServer();
		server.run(8087);
	}
	
	public void run(int inetPort) {
		EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1);
		EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossEventLoopGroup, workEventLoopGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new FileStaticServerInitilizer());
			server.bind(inetPort).sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossEventLoopGroup.shutdownGracefully();
			workEventLoopGroup.shutdownGracefully();
		}
	}
}
