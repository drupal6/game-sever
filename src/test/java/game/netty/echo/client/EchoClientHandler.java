package game.netty.echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	
	 private final ByteBuf firstMessage;
	 
	 public EchoClientHandler(int firstMessageSize) {
		 if(firstMessageSize <= 0) {
			 throw new IllegalArgumentException("firstMessageSize:" + firstMessageSize);
		 }
		 firstMessage = Unpooled.buffer(firstMessageSize);
			for (int i = 0; i < firstMessage.capacity(); i ++) {
			firstMessage.writeByte((byte) i);
		 }
	 }
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		System.out.println("EchoClientHandler  channelRegistered");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("EchoClientHandler  channelActive");
		ctx.writeAndFlush(firstMessage);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("EchoClientHandler  channelInactive");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		super.channelRead(ctx, msg);
		System.out.println("EchoClientHandler  channelRead");
		ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		System.out.println("EchoClientHandler  channelReadComplete");
		ctx.flush();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		super.userEventTriggered(ctx, evt);
		System.out.println("EchoClientHandler  userEventTriggered");
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx)
			throws Exception {
		super.channelWritabilityChanged(ctx);
		System.out.println("EchoClientHandler  channelWritabilityChanged");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
		ctx.close();
	}
}
