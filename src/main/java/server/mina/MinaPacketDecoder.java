package server.mina;

import mspacket.MsPacket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaPacketDecoder implements MessageDecoder {
	
	private final static Logger logger = LoggerFactory.getLogger(MinaPacketDecoder.class);

	@Override
	public MessageDecoderResult decodable(IoSession session,
			IoBuffer in) {
		int length = in.getInt();
		if(length < 10) {
			logger.warn("message error, session haved close. length:{}", length);
			session.close(true);
			return MessageDecoderResult.NEED_DATA;
		}
		return MessageDecoderResult.OK;
	}

	/**
	 * length+checkSum+actId+data
	 */
	@Override
	public MessageDecoderResult decode(IoSession session,
			IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		int length = in.getInt();
		int checkSum = in.getShort() & 0xFFFF;
		int actId = in.getShort() & 0xFFFF;
		int modId = in.getShort() & 0xFFFF;
		int buffDataLength = length - 10;
		byte[] buffData = new byte[buffDataLength];
		in.get(buffData, 0, buffDataLength);
		MsPacket msPacket = new MsPacket();
		msPacket.setCheckSum(checkSum);
		msPacket.setBuff(buffData);
		msPacket.setActId(actId);
		msPacket.setModId(modId);
		out.write(msPacket);
		return MessageDecoderResult.OK;
	}

	@Override
	public void finishDecode(IoSession paramIoSession,
			ProtocolDecoderOutput paramProtocolDecoderOutput) throws Exception {
	}

}
