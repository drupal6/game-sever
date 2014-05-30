package game.netty.factorial.client;

import game.netty.factorial.server.BigIntegerDecoder;
import game.netty.factorial.server.NumberEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

public class FactorialClientInitializer extends ChannelInitializer<SocketChannel> {
  
      private final int count;
  
      public FactorialClientInitializer(int count) {
          this.count = count;
      }
  
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
          pipeline.addLast("handler", new FactorialClientHandler(count));
      }
  }
