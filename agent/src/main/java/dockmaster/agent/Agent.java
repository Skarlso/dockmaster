package dockmaster.agent;

import java.util.List;

public class Agent {

	private final Docker docker;
	private final Dockmaster master;
	private final String id;
	
	public Agent(String id, Docker docker, Dockmaster dockmaster) {
		this.docker = docker;
		this.master = dockmaster;
		this.id = id;
	}

	public void publishContainers() {
		List<Container> containers = docker.getContainers();
		if(containers == null){
			return;
		}
		master.synchronize(id, containers);
	}

}
