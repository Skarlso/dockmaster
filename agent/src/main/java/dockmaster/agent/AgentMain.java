package dockmaster.agent;

import java.net.URI;
import java.net.URISyntaxException;

import dockmaster.agent.adapter.RemoteDockmaster;
import dockmaster.agent.adapter.SpotifyDockerAdapter;

public class AgentMain {

	public static void main(String[] args) throws URISyntaxException {
		String id = "007";
		int millis = 5000;
		String url = "";
		
		URI uri = new URI(url);
		RemoteDockmaster dockmaster = new RemoteDockmaster(uri);
		Runtime.getRuntime().addShutdownHook(closeConnection(dockmaster));

		Docker docker = new SpotifyDockerAdapter();
		Agent agent = new Agent(id, docker, dockmaster);

		while (true) {
			agent.publishContainers();
			sleep(millis);
		}
	}

	private static Thread closeConnection(final RemoteDockmaster dockmaster) {
		return new Thread() {
			public void run() {
				dockmaster.close();
			}
		};
	}

	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
