package game.netty.echo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	
	public static void main(String[] arg) {
		EchoClient c = new EchoClient();
		c.run("127.0.0.1", 8080, 100);
	}
	
	public void run(String host, int port, final int size) {
		EventLoopGroup loopGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(loopGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel paramC) throws Exception {
					paramC.pipeline().addLast(new EchoClientHandler(size));
				}
			});
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			loopGroup.shutdownGracefully();
		}
		
	}

}
