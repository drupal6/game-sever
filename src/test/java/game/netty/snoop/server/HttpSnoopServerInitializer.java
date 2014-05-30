package game.netty.snoop.server;

import game.netty.securechat.server.SecureChatSslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class HttpSnoopServerInitializer extends
		ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();

		// Uncomment the following line if you want HTTPS
//		 SSLEngine engine =
//		 SecureChatSslContextFactory.getServerContext().createSSLEngine();
//		 engine.setUseClientMode(false);
//		 p.addLast("ssl", new SslHandler(engine));

		p.addLast("decoder", new HttpRequestDecoder());
		// Uncomment the following line if you don't want to handle HttpChunks.
		// p.addLast("aggregator", new HttpObjectAggregator(6));
		p.addLast("encoder", new HttpResponseEncoder());
		// Remove the following line if you don't want automatic content
		// compression.
		// p.addLast("deflater", new HttpContentCompressor());
		p.addLast("handler", new HttpSnoopServerHandler());
	}
}
