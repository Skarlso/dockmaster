package dockmaster.agent;

import dockmaster.agent.adapter.SpotifyDockerAdapter;

public class AgentMain {

	public static void main(String[] args) {
		String id = "007";
		int millis = 5000;
		
		Dockmaster dockmaster = new LoggerMaster();
		Docker docker = new SpotifyDockerAdapter();
		Agent agent = new Agent(id, docker, dockmaster);

		while (true) {
			agent.publishContainers();
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
