package server;

import java.util.ArrayList;
import java.util.List;

public class ServerManager {

	List<Server> servers = new ArrayList<Server>();
	
	public boolean start() {
		return true;
	}
	
	
	public void stop() {
		for(Server server : servers) {
			server.stop();
		}
	}
}
