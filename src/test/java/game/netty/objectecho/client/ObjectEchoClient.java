package game.netty.objectecho.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ObjectEchoClient {
	public static void main(String[] arg) {
		ObjectEchoClient client = new ObjectEchoClient();
		client.run("192.168.1.128", 8085);
	}
	public void run(String inetHost, int inetPort) {
		EventLoopGroup work = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(work)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel paramC) throws Exception {
					paramC.pipeline().addLast(new ObjectEncoder()
							,new ObjectDecoder(ClassResolvers.cacheDisabled(null))
							,new ObjectEchoClientHandler(111));
				}
			});
			b.connect(inetHost, inetPort).sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			work.shutdownGracefully();
		}
	}
}
