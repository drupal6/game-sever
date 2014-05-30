package game.netty.objectecho.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ObjectEchoServerHandler extends ChannelInboundHandlerAdapter{
	 @Override
	       public void channelRead(
	               ChannelHandlerContext ctx, Object msg) throws Exception {
	           // Echo back the received object to the client.
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
