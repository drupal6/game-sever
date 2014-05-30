package game.netty.factorial.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

public class FactorialServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	      public void initChannel(SocketChannel ch) throws Exception {
	          ChannelPipeline pipeline = ch.pipeline();
	  
	          // Enable stream compression (you can remove these two if unnecessary)
	          pipeline.addLast("deflater", ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
	          pipeline.addLast("inflater", ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
	  
	          // Add the number codec first,
	          pipeline.addLast("decoder", new BigIntegerDecoder());
	          pipeline.addLast("encoder", new NumberEncoder());
	  
	          // and then business logic.
	          // Please note we create a handler for every new channel
	          // because it has stateful properties.
	          pipeline.addLast("handler", new FactorialServerHandler());
	      }
}
