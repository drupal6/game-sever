package server.mina;

import mspacket.CodecUtil;
import mspacket.MsPacket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;


public class MinaPacketEncoder implements MessageEncoder<MsPacket> {
	
	/**
	 * length+checkSum+actId+data
	 */
	@Override
	public void encode(IoSession paramIoSession, MsPacket msPacket,
			ProtocolEncoderOutput out) throws Exception {
         IoBuffer out_buffer;
         int length = msPacket.getLength();
         int checkSum = CodecUtil.getCheckSum(length, msPacket.getBuff(), 0, length);
         CodecUtil.encodeBuffer(msPacket.getBuff(), 0, length);
         out_buffer = IoBuffer.allocate(length + 4 + 2 + 2 + 2);
         out_buffer.putInt(length + 10); // +4
         out_buffer.putShort((short) checkSum); // +2
         out_buffer.putShort((short)(msPacket.getActId())); // +2
         out_buffer.putShort((short)(msPacket.getModId())); // +2
         out_buffer.put(msPacket.getBuff());
         out_buffer.flip();
         out.write(out_buffer);
	}


}
