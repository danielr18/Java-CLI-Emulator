package darb.oop.cli;

import java.util.Scanner;

public abstract class Terminal {
	protected Directory dir;
	
	public Terminal() {
		this.dir = new Directory();
	}
	
	public void start() {
		Scanner scanner = new Scanner(System.in);
		String input;
		do {
			this.printPrompt();
			input = scanner.nextLine();
			this.readCommand(input);
		} while(!input.equals("exit"));
		scanner.close();
	}
	
	protected void executeCommand(String command, String[] arguments, Directory dir) {}
	
	private void readCommand(String input) {
		String[] parts = input.split(" ", 2);
		if(parts.length > 0) {
			String command = parts[0];
			String[] arguments = null;
			if(parts.length > 1) {
				 arguments = parts[1].split(" ");
			}
			this.executeCommand(command, arguments, this.dir);
		}	
	}

	protected void printPrompt() {}
	
}




