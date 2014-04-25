package game.telnet.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class TelnetClientInitialier extends ChannelInitializer<SocketChannel> {

	private final StringDecoder DECODER = new StringDecoder();
	private final StringEncoder ENCODER = new StringEncoder();
	
	@Override
	protected void initChannel(SocketChannel paramC) throws Exception {
		ChannelPipeline pipeline = paramC.pipeline();
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", DECODER);
		pipeline.addLast("encoder", ENCODER);
		pipeline.addLast("handler", new TelnetClientHandler());
	}

}
