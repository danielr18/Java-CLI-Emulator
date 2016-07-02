package darb.oop.cli;

public class DOS_Terminal extends Terminal {
	@Override
	protected void printPrompt() {
		System.out.print(this.dir+">");
	}
	
	@Override
	protected void executeCommand(String command, String[] arguments, Directory dir) {
		DOS_Commands.execute(command, arguments, dir);
	}
}
