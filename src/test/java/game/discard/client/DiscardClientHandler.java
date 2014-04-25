package game.discard.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardClientHandler extends ChannelInboundHandlerAdapter {
	
	private final int firstMessageSize;
	private ChannelHandlerContext ctx;
	private ByteBuf content;
	
	public DiscardClientHandler(int firstMessageSize) {
		 if(firstMessageSize <= 0) {
			 throw new IllegalArgumentException("firstMessageSize:" + firstMessageSize);
		 }
		 this.firstMessageSize = firstMessageSize;
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		System.out.println("EchoClientHandler  channelRegistered");
		this.ctx = ctx;
		content = ctx.alloc().directBuffer(firstMessageSize).writeZero(firstMessageSize);
	}

	private void generateTraffic() {
		ctx.writeAndFlush(content.duplicate().retain()).addListener(trafficGenerator);
    }
	
	private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
         @Override
         public void operationComplete(ChannelFuture future) throws Exception {
         if (future.isSuccess()) {
             generateTraffic();
         }
      }
    };
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("EchoClientHandler  channelActive");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("EchoClientHandler  channelInactive");
		content.release();
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
