package dockmaster.agent;

import java.util.List;

public interface Dockmaster {

	void synchronize(String id, List<Container> containers);
	
}
