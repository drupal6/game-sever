package game.quoteofmonet.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class QuoteOfTheMonetClient {

	public static void main(String[] arg) {
		QuoteOfTheMonetClient client = new QuoteOfTheMonetClient();
		client.run("192.168.1.129", 8083);
	}
	
	public void run(String inetHost, int inetPort) {
		EventLoopGroup workeEventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workeEventLoopGroup)
			.channel(NioDatagramChannel.class)
			.option(ChannelOption.SO_BROADCAST, true)
			.handler(new QuoteOfTheMonetClientHandler());
			Channel channel = b.bind(0).sync().channel();
			channel.writeAndFlush(new DatagramPacket(
					Unpooled.copiedBuffer("QOTM?", CharsetUtil.UTF_8),
					new InetSocketAddress("255.255.255.255", inetPort))).sync();
			if (!channel.closeFuture().await(5000)) {
                 System.err.println("QOTM request timed out.");
            }
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workeEventLoopGroup.shutdownGracefully();
		}
	}
}