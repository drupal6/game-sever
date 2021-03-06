package game.netty.quoteofmonet.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class QuoteOfTheMonetClientHandler extends
		SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	protected void channelRead0(
			ChannelHandlerContext ctx,
			DatagramPacket msg) throws Exception {
		String response = msg.content().toString(CharsetUtil.UTF_8);
		if (response.startsWith("QOTM: ")) {
			System.out.println("Quote of the Moment: " + response.substring(6));
			ctx.close();
		}
	}
}
