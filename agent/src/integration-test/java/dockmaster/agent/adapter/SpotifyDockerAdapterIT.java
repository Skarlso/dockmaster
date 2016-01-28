package dockmaster.agent.adapter;

import org.junit.Test;

public class SpotifyDockerAdapterIT {

	@Test
	public void listContainers(){
		SpotifyDockerAdapter docker=  new SpotifyDockerAdapter();
		
		System.out.println(docker.getContainers());
	}
	
}
