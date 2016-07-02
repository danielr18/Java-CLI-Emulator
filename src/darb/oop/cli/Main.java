package darb.oop.cli;
public class Main {

	public static void main(String[] args) {
		String OS = System.getProperty("os.name");
		if(OS.startsWith("Mac")) {
			UNIX_Terminal terminal = new UNIX_Terminal();
			terminal.start();
		}
		else if(OS.startsWith("Windows")) {
			DOS_Terminal terminal = new DOS_Terminal();
			terminal.start();
		}
		else {
			System.out.println("Sorry, this software is not compatible with your operating system.");
		}		
	}

}
