package game.quoteofmonet.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class QuoteOfTheMonetServer {

	public static void main(String[] arg) {
		QuoteOfTheMonetServer server = new QuoteOfTheMonetServer();
		server.run(8083);
	}
	
	public void run(int inetPort) {
		EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workEventLoopGroup)
			.channel(NioDatagramChannel.class)
			.option(ChannelOption.SO_BROADCAST, true)
			.handler(new QuoteOfTheMonetServerHandler());
			b.bind(inetPort).sync().channel().closeFuture().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			workEventLoopGroup.shutdownGracefully();
		}
	}
}
