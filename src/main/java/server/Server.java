package server;


public interface Server {
	
	public enum TransmissionProtocol{
		TCP,
		UDP,
		;
	}
	
	public TransmissionProtocol getTransmissionProtocol();
	
	public boolean start();
	
	public boolean stop();
	
	public void setPort(int port);
	
}