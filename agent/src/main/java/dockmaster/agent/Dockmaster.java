package dockmaster.agent;

import java.util.List;

public interface Dockmaster {

	void synchronize(String id, Integer expire, List<Container> containers);
	
}
