package game.netty.factorial.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FactorialClientHandler extends SimpleChannelInboundHandler<BigInteger> {
  
  
      private ChannelHandlerContext ctx;
      private int receivedMessages;
      private int next = 1;
      private final int count;
      final BlockingQueue<BigInteger> answer = new LinkedBlockingQueue<BigInteger>();
  
      public FactorialClientHandler(int count) {
          this.count = count;
      }
  
      public BigInteger getFactorial() {
          boolean interrupted = false;
          for (;;) {
              try {
                  BigInteger factorial = answer.take();
                  if (interrupted) {
                      Thread.currentThread().interrupt();
                  }
                  return factorial;
              } catch (InterruptedException e) {
                  interrupted = true;
              }
          }
      }
  
      @Override
      public void channelActive(ChannelHandlerContext ctx) {
          this.ctx = ctx;
          sendNumbers();
      }
  
      @Override
      public void channelRead0(ChannelHandlerContext ctx, final BigInteger msg) {
          receivedMessages ++;
          if (receivedMessages == count) {
              // Offer the answer after closing the connection.
              ctx.channel().close().addListener(new ChannelFutureListener() {
                  @Override
                  public void operationComplete(ChannelFuture future) {
                      boolean offered = answer.offer(msg);
                      assert offered;
                  }
              });
          }
      }
  
      @Override
      public void exceptionCaught(
              ChannelHandlerContext ctx, Throwable cause) throws Exception {
          ctx.close();
      }
  
      private void sendNumbers() {
          // Do not send more than 6 numbers.
          ChannelFuture future = null;
          for (int i = 0; i < 6 && next <= count; i++) {
             future = ctx.write(Integer.valueOf(next));
             next++;
         }
         if (next <= count) {
             assert future != null;
             future.addListener(numberSender);
         }
         ctx.flush();
     }
 
     private final ChannelFutureListener numberSender = new ChannelFutureListener() {
         @Override
         public void operationComplete(ChannelFuture future) throws Exception {
             if (future.isSuccess()) {
                 sendNumbers();
             }
         }
     };
 }