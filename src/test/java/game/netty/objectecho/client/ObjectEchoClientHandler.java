package game.netty.objectecho.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {
	
    private final List<Integer> firstMessage;
  
      /**
       * Creates a client-side handler.
       */
      public ObjectEchoClientHandler(int firstMessageSize) {
          if (firstMessageSize <= 0) {
              throw new IllegalArgumentException(
                      "firstMessageSize: " + firstMessageSize);
          }
          firstMessage = new ArrayList<Integer>(firstMessageSize);
          for (int i = 0; i < firstMessageSize; i ++) {
              firstMessage.add(Integer.valueOf(i));
          }
      }
  
      @Override
      public void channelActive(ChannelHandlerContext ctx) throws Exception {
          // Send the first message if this handler is a client-side handler.
          ctx.writeAndFlush(firstMessage);
      }
  
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
          // Echo back the received object to the server.
          ctx.write(msg);
      }
  
      @Override
      public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
          ctx.flush();
      }
  
      @Override
      public void exceptionCaught(
              ChannelHandlerContext ctx, Throwable cause) throws Exception {
          ctx.close();
      }
}
