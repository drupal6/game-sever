package game.mina.client;

import java.net.ConnectException;
import java.net.InetSocketAddress;

import mspacket.MsPacket;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import server.mina.MinaPacketDecoder;
import server.mina.MinaPacketEncoder;
import until.ObjectUtil;
import until.ParamObject;

public class MinaClient {

	public SocketConnector socketConnector;

	ConnectFuture cf;
	
	public MinaClient() {
		init();
	}

	public void init() {
		socketConnector = new NioSocketConnector();
		socketConnector.getSessionConfig().setKeepAlive(true);
		DemuxingProtocolCodecFactory codecFactory = new DemuxingProtocolCodecFactory();
		codecFactory.addMessageDecoder(new MinaPacketDecoder());
		codecFactory.addMessageEncoder(MsPacket.class, new MinaPacketEncoder());
		socketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecFactory));
		socketConnector.setHandler(new MinaClientHandler());
		InetSocketAddress addr = new InetSocketAddress("192.168.1.128", 8080);
		cf = socketConnector.connect(addr);
	}

	public void sendMessage(MsPacket msPacket) {
		try {
			cf.awaitUninterruptibly();
			cf.getSession().write(msPacket);
		} catch (RuntimeIoException e) {
			if (e.getCause() instanceof ConnectException) {
				try {
					if (cf.isConnected()) {
						cf.getSession().close(true);
					}
				} catch (RuntimeIoException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		MinaClient client = new MinaClient();
		MsPacket msPacket = new MsPacket();
		msPacket.setActId(33);
		msPacket.setModId(123);
		ParamObject paramObject = new ParamObject();
		paramObject.put("test", "helloWorld");
		msPacket.setBuff(ObjectUtil.toByteArray(paramObject));
		client.sendMessage(msPacket);
	}


}
