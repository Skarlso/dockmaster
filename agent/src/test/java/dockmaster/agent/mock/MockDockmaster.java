package dockmaster.agent.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dockmaster.agent.Container;
import dockmaster.agent.Dockmaster;

public class MockDockmaster implements Dockmaster{
	
	Map<String,List<Container>> map = new HashMap<>();
	
	public List<Container> getContainers(String agentId) {
		return new ArrayList<>(map.get(agentId));
	}

	@Override
	public void synchronize(String id, List<Container> containers) {
		map.put(id, containers);
	}
	
	
}
