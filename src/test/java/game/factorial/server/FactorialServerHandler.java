package game.factorial.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;
import java.util.Formatter;

public class FactorialServerHandler extends SimpleChannelInboundHandler<BigInteger> {
  
      private BigInteger lastMultiplier = new BigInteger("1");
      private BigInteger factorial = new BigInteger("1");
  
      @Override
      public void channelRead0(ChannelHandlerContext ctx, BigInteger msg) throws Exception {
          // Calculate the cumulative factorial and send it to the client.
          lastMultiplier = msg;
          factorial = factorial.multiply(msg);
          ctx.writeAndFlush(factorial);
      }
  
      @Override
      public void channelInactive(ChannelHandlerContext ctx) throws Exception {
          Formatter fmt = new Formatter();
          fmt.close();
      }
  
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
          ctx.close();
      }
  }
