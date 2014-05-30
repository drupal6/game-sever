package game.netty.factorial.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class FactorialClient {
	  private final String host;
	        private final int port;
	        private final int count;
	    
	        public FactorialClient(String host, int port, int count) {
	            this.host = host;
	            this.port = port;
	            this.count = count;
	        }
	    
	        public void run() throws Exception {
	            EventLoopGroup group = new NioEventLoopGroup();
	            try {
	                Bootstrap b = new Bootstrap();
	                b.group(group)
	                 .channel(NioSocketChannel.class)
	                 .handler(new FactorialClientInitializer(count));
	    
	                // Make a new connection.
	                ChannelFuture f = b.connect(host, port).sync();
	    
	                // Get the handler instance to retrieve the answer.
	                FactorialClientHandler handler =
	                    (FactorialClientHandler) f.channel().pipeline().last();
	    
	                // Print out the answer.
	                System.err.format(
	                        "Factorial of %,d is: %,d", count, handler.getFactorial());
	            } finally {
	                group.shutdownGracefully();
	            }
	        }
	    
	        public static void main(String[] args) throws Exception {
	    
	            new FactorialClient("192.168.1.128", 8086, 100).run();
	        }
}
