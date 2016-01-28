package dockmaster.agent.adapter;

import java.util.List;

import dockmaster.agent.Container;
import dockmaster.agent.Dockmaster;

public class LoggerMaster implements Dockmaster {

	@Override
	public void synchronize(String id, List<Container> containers) {
		System.out.println("");
		System.out.println("++++++++++++++++ SYNCH ++++++++++++");
		System.out.println("AGENT: " + id);
		System.out.println(containers);
		System.out.println("+++++++++++++++++++++++++++++++++++");
		System.out.println("");
	}

}
