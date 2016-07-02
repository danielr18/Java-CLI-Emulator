package darb.oop.cli;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UNIX_Terminal extends Terminal {		
	private String hostname;
	private String user;

	public UNIX_Terminal() {
		try {
			this.hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.user = System.getProperty("user.name");		
	}
	
	@Override
	protected void printPrompt() {
		System.out.print(this.hostname + ":" + this.dir.getPathBasename() + " " + this.user + "$ ");	
	}
	
	@Override
	protected void executeCommand(String command, String[] arguments, Directory dir) {
		UNIX_Commands.execute(command, arguments, dir);
	}
	
}
