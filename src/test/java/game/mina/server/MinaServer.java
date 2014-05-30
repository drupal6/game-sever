package game.mina.server;

import server.mina.MinaAbstractServer;

public class MinaServer {

	
	public static void main(String[] arg) {
		MinaAbstractServer server = new MinaAbstractServer();
		server.setPort(8080);
		server.start();
	}
}
