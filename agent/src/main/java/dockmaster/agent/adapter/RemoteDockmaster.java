package dockmaster.agent.adapter;

import java.io.Closeable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.hk2.api.MultiException;
import org.glassfish.jersey.filter.LoggingFilter;

import dockmaster.agent.Container;
import dockmaster.agent.Dockmaster;

public final class RemoteDockmaster implements Dockmaster, Closeable {
    private static final Logger LOGGER = Logger.getLogger(RemoteDockmaster.class.getName());

	private final Client client;
	private final URI uri;

	public RemoteDockmaster(URI uri) {
		this.uri = uri;
		this.client = ClientBuilder.newClient();
		LoggingFilter filter = new LoggingFilter(LOGGER,true);
		this.client.register(filter);
	}

	@Override
	public void synchronize(String id, List<Container> containers) {
		WebTarget target = client.target(uri);

		post(target.path("api").path("1").path("add"), toDTO(id,containers));
	}

	private List<ContainerDTO> toDTO(String id, List<Container> containers) {
		ArrayList<ContainerDTO> result = new ArrayList<>();
		for(Container c : containers){
			result.add(new ContainerDTO(id, c));
		}
		return result;
	}

	private String message(Response response) {
		return "Error code: " + response.getStatus();
	}

	private boolean isOK(Response response) {
		return Status.fromStatusCode(response.getStatus()) == Status.OK;
	}

	private <T> void post(final WebTarget target, T entity) {
		try {
			Builder request = target.request(MediaType.APPLICATION_JSON);
			Response response = request.async().post(Entity.json(entity)).get();
			if (!isOK(response)) {
				throw new RuntimeException(message(response));
			}
		} catch (ExecutionException | MultiException | InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(target.getUri().toString());
		}
	}

	@Override
	public void close() {
		client.close();
	}

}
