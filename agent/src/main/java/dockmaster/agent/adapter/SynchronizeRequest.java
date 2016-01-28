package dockmaster.agent.adapter;

import java.util.ArrayList;
import java.util.List;

class SynchronizeRequest {
	
	private List<ContainerDTO> containers;
	
	private Integer expireAfterSeconds;
	
	private String agentid;
	
	public SynchronizeRequest(String agentid, Integer expireAfterSeconds,List<ContainerDTO> containers) {
		this.agentid = agentid;
		this.expireAfterSeconds = expireAfterSeconds;
		this.containers = new ArrayList<>(containers);
	}

	public List<ContainerDTO> getContainers() {
		return java.util.Collections.unmodifiableList(containers);
	}

	public String getAgentid() {
		return agentid;
	}
	
	public Integer getExpireAfterSeconds() {
		return expireAfterSeconds;
	}

}
