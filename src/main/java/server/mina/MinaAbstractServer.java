package server.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import mspacket.MsPacket;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaAbstractServer implements MinaServer {
	private final static Logger logger = LoggerFactory.getLogger(MinaAbstractServer.class);
	
	SocketAcceptor acceptor;
	Integer port;
	
	public TransmissionProtocol getTransmissionProtocol(){
		return TransmissionProtocol.TCP;
	}
	
	@Override
	public boolean start() {
		if(port == null) {
			logger.error("start MinaAbstractServer error. port is null.");
			return false;
		}
		acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setTcpNoDelay(true);
		acceptor.getSessionConfig().setReuseAddress(true);
	    acceptor.getSessionConfig().setSoLinger(0);
	    DemuxingProtocolCodecFactory codecFactory = new DemuxingProtocolCodecFactory();
	    codecFactory.addMessageDecoder(new MinaPacketDecoder());
	    codecFactory.addMessageEncoder(MsPacket.class, new MinaPacketEncoder());
	    acceptor.getFilterChain().addLast("coder", new ProtocolCodecFilter(codecFactory));
	    acceptor.setHandler(new MinaServerHandler()); 
	    try {
			acceptor.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			logger.error("start MinaAbstractServer error:{}", e);
			return false;
		}
	    logger.info(this.toString());
		return true;
	} 
	
	@Override
	public boolean stop() {
		acceptor.dispose();
		return true;
	}
	
	@Override
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String toString() {
		return "MinaAbstractServer start succ. port:" + port;
	}
}
