package dockmaster.agent.mock;

import dockmaster.agent.Container;

class ContainerDTO {

	private String id;
	private String agentId;

	public ContainerDTO(String agentId, Container container) {
		this.id = container.getId();
		this.agentId = agentId;
	}
	
	public String getAgentId() {
		return agentId;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentId == null) ? 0 : agentId.hashCode());
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
		if (agentId == null) {
			if (other.agentId != null)
				return false;
		} else if (!agentId.equals(other.agentId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Container toContainer() {
		return new Container(id);
	}

}
