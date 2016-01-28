package dockmaster.agent;

public class Container {

	private final String id;
	
	private final String name;
	
	private final String command;
	
	private final String port;
	
	
	public Container(String id, String name, String command, String port) {
		this.id = id;
		this.name = name;
		this.command = command;
		this.port = port;
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
		Container other = (Container) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Container [id=" + id + "]";
	}

}
