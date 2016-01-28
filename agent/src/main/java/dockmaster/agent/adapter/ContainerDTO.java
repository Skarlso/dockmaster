package dockmaster.agent.adapter;

import dockmaster.agent.Container;

class ContainerDTO {

	private String id;

	private String name;

	private String command;

	private String port;
	
	public ContainerDTO(Container container) {
		this.id = container.getId();
		this.name = container.getName();
		this.command = container.getCommand();
		this.port = container.getPort();
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
