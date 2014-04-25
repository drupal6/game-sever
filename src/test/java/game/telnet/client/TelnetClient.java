package game.telnet.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TelnetClient {

	public static void main(String[] arg) {
		TelnetClient client = new TelnetClient();
		client.run("192.168.1.128", 8082);
	}
	
	public void run(String inetHost, int inetPort) {
		EventLoopGroup woEventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(woEventLoopGroup)
			.channel(NioSocketChannel.class)
			.handler(new TelnetClientInitialier());
			Channel channel = b.connect(inetHost, inetPort).sync().channel();
			channel.writeAndFlush("test>>>>");
			ChannelFuture future = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			for(;;) {
				String line = reader.readLine();
				if(line == null) {
					break;
				}
				future = channel.writeAndFlush(line);
				if(line.equalsIgnoreCase("bye")) {
					channel.closeFuture().sync();
					break;
				}
				if(future != null) {
					future.sync();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			woEventLoopGroup.shutdownGracefully();
		}
	}
}
