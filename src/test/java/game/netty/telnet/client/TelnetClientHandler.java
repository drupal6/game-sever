package game.netty.telnet.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TelnetClientHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(
			ChannelHandlerContext paramChannelHandlerContext, String paramI)
			throws Exception {
		System.err.println(paramI);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
}
