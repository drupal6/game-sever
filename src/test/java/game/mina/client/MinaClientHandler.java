package game.mina.client;

import mspacket.CodecUtil;
import mspacket.MsPacket;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import until.ParamObject;

public class MinaClientHandler extends IoHandlerAdapter {
	
	private final static Logger logger = LoggerFactory.getLogger(MinaClientHandler.class);
	
	/**
	 * length+checkSum+actId+data
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if(message instanceof MsPacket) {
			MsPacket msPacket = (MsPacket) message;
			int actId = msPacket.getActId();
			int modId = msPacket.getModId();
			
			//分流
			CodecUtil.decodeBuffer(msPacket.getBuff(), 0, msPacket.getLength());
			int calcsum = CodecUtil.getCheckSum(msPacket.getLength(), msPacket.getBuff(), 0, msPacket.getLength());
			if(calcsum != msPacket.getCheckSum()) {
				logger.error("checkSum error!");
				session.close(true);
				return;
			}
			ParamObject params = msPacket.readObject();
		}
	}

}
