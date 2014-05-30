package mspacket;

public abstract class MsPacketCreater {
	
	public abstract MsPacket create(int actId, Object object);
	
	public abstract MsPacket create(int actId, Object object, Runnable run);
}
