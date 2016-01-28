package dockmaster.agent;

import static org.junit.Assert.*;


import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

import org.junit.Test;

import dockmaster.agent.Agent;
import dockmaster.agent.Container;
import dockmaster.agent.mock.MockDocker;
import dockmaster.agent.mock.MockDockmaster;


public class AgentAT {

	private static final String OTHER_CONTAINER_ID = "other.container.id";
	private static final String AGENT_ID = "agent.id";
	private static final String CONTAINER_ID = "arbitary.container.id";

	@Test
	public void agentShouldReadDockerAndPublishAllContainersTo() {
		MockDocker docker = new MockDocker();
		Container container = new Container(CONTAINER_ID);
		docker.add(container);
		Container other_container = new Container(OTHER_CONTAINER_ID);
		docker.add(other_container);
		MockDockmaster dockmaster = new MockDockmaster();
		
		Agent agent = new Agent(AGENT_ID,docker, dockmaster);
		agent.publishContainers();
		
		assertThat(dockmaster.getContainers(AGENT_ID),hasItems(container,other_container));
	}

}
