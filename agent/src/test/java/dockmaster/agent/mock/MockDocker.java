package dockmaster.agent.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dockmaster.agent.Container;
import dockmaster.agent.Docker;

public class MockDocker implements Docker{

	private final Map<String, Container> containers = new HashMap<>();
	
	public void add(Container c) {
		containers.put(c.getId(), c);
	}

	@Override
	public List<Container> getContainers() {
		return new ArrayList<>(containers.values());
	}

}
