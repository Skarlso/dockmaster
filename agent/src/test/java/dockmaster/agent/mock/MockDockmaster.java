package dockmaster.agent.mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dockmaster.agent.Container;
import dockmaster.agent.Dockmaster;

public class MockDockmaster implements Dockmaster{
	
	Set<ContainerDTO> set = new HashSet<>();
	
	public List<Container> getContainers(String agentId) {
		ArrayList<Container> result = new ArrayList<>();
		for(ContainerDTO dto : set){
			if(dto.getAgentId().equals(agentId)){
				result.add(dto.toContainer());
			}
		}
		return result;
	}

	@Override
	public void synchronize(String id, List<Container> containers) {
		for(Container c : containers){
			set.add(new ContainerDTO(id, c));
		}
	}
	
	
}
