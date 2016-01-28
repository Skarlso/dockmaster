package dockmaster.agent.adapter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.Test;

import dockmaster.agent.Container;

public class RemoteDockmasterIT {

	private static final String URL = "http://10.32.0.32:8989";
	private static final String CONTAINER_ID = "container.id";
	private static final String AGENT_ID = "007";

	@Test
	public void testCallRemoteDockermaster() throws URISyntaxException {
		URI uri = new URI(URL);
		
		try(RemoteDockmaster master = new RemoteDockmaster(uri)){
			master.synchronize(AGENT_ID, 10, Arrays.asList(new Container(CONTAINER_ID,"","","")));
		}
		
	}

}
