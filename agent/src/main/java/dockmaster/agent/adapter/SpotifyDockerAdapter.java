package dockmaster.agent.adapter;

import java.util.ArrayList;
import java.util.List;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;

import dockmaster.agent.Container;
import dockmaster.agent.Docker;

public class SpotifyDockerAdapter implements Docker {

	public static final String DEFAULT_URL = "unix:///var/run/docker.sock";

	@Override
	public List<Container> getContainers() {
		ArrayList<Container> result = new ArrayList<>();
		try (DockerClient docker = new DefaultDockerClient(DEFAULT_URL)) {
			for (com.spotify.docker.client.messages.Container dockerContainer : docker.listContainers()) {
				result.add(toContainer(dockerContainer));
			}
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Container toContainer(com.spotify.docker.client.messages.Container dockerContainer) {
		return new Container(dockerContainer.id());
	}

}
