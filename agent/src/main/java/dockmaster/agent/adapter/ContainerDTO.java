package dockmaster.agent.adapter;

import dockmaster.agent.Container;

public class ContainerDTO {

	private String id;

	private String name;

	private String command;

	private String port;
	
	private String agentid;

	public ContainerDTO(String agentId, Container container) {
		this.id = container.getId();
		this.name = container.getName();
		this.command = container.getCommand();
		this.port = container.getPort();
		this.agentid = agentId;
	}

	public String getAgentid() {
		return agentid;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCommand() {
		return command;
	}

	public String getPort() {
		return port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentid == null) ? 0 : agentid.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContainerDTO other = (ContainerDTO) obj;
		if (agentid == null) {
			if (other.agentid != null)
				return false;
		} else if (!agentid.equals(other.agentid))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Container toContainer() {
		return new Container(id,name,command,port);
	}

}
