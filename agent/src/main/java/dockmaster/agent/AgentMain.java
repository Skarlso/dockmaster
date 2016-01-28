package dockmaster.agent;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import dockmaster.agent.adapter.RemoteDockmaster;
import dockmaster.agent.adapter.SpotifyDockerAdapter;

public class AgentMain {

	private static final String HOST_OPT = "h";
	private static final String NAME_OPT = "n";
	private static final String HELP_OPT = "help";

	public static void main(String[] args) throws URISyntaxException {
		CommandLine commandLine;
		try {
			commandLine = new DefaultParser().parse(options(), args);
			String id = commandLine.getOptionValue(NAME_OPT);
			String url = commandLine.getOptionValue(HOST_OPT);
			AgentMain main = new AgentMain();
			main.setUrl(url);
			main.setId(id);
			main.run();
		} catch (ParseException| RuntimeException e) {
			System.out.println(e.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("Dockmaster Agent", options());
		}
	}

	private static Options options() {
		Options opts = new Options();
		opts.addOption(NAME_OPT, "name", true, "The agents name must be unique");
		opts.addOption(HOST_OPT, "host", true, "Host url eg.: http://localhost:8080 must be well formed");
		opts.addOption(HELP_OPT, HELP_OPT, false, "Prints this message");
		return opts;
	}

	private static Thread closeConnection(final RemoteDockmaster dockmaster) {
		return new Thread() {
			public void run() {
				dockmaster.close();
			}
		};
	}

	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int seconds;
	private URI uri;
	private String id;

	public AgentMain() {
		this.seconds = 5;
	}

	private void run() {
		RemoteDockmaster dockmaster = new RemoteDockmaster(uri);
		Runtime.getRuntime().addShutdownHook(closeConnection(dockmaster));
		Agent agent = new Agent(id, new SpotifyDockerAdapter(), dockmaster);
		while (true) {
			agent.publishContainers(seconds*2);
			sleep(seconds*1000);
		}
	}

	private void setUrl(String url) {
		if(url == null){
			throw new RuntimeException("Host not set!");
		}
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Host url not well formed!");
		}
	}

	public void setId(String id) {
		if(id == null){
			throw new RuntimeException("Agent name not set!");
		}
		this.id = id;
	}

	public void setMillis(int millis) {
		this.seconds = millis;
	}

}
