package darb.oop.cli;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class UNIX_Commands {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001b[38;5;210m";
	public static final String ANSI_PINK = "\u001b[38;5;200m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001b[38;5;27m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static void execute(String command, String[] args, Directory dir) {
		switch(command) {
			case "help":
				System.out.print(""+
						"Type `help' to see this list.\n"+
						"Commands Available:\n"+
						"cp <source> <target>: Copy a file (source) to target\n"+
						"mv <source> <target>: Move a file (source) to target\n"+
						"rm <file>: Delete a file or directory(it must be empty)\n"+
						"mkdir <directory>: Create a new directory\n"+
						"echo <message>: Print message in cli\n"+
						"echo <message> >> <file>: Append message in target file\n"+
						"ls: Lists the files in the current working directory.\n"+
						"cd <target>: Change the current working directory to target.\n"+
						"exit: Exit cli\n"
						);
				break;
			case "cp":
				if(args!=null && args.length==2) {
					Path sourcePath = UNIX_Commands.parsePath(args[0], dir);
					Path targetPath = UNIX_Commands.parsePath(args[1], dir);	
					Commands.copy(sourcePath, targetPath);
				}
				else {
					System.out.println("The syntax of the command is incorrect");							
				}
				break;
			case "mv":
				if(args!=null && args.length==2) {
					Path sourcePath = UNIX_Commands.parsePath(args[0], dir);
					Path targetPath = UNIX_Commands.parsePath(args[1], dir);	
					Commands.move(sourcePath, targetPath);
				}
				else {
					System.out.println("The syntax of the command is incorrect");							
				}
				break;
			case "rm":
				if(args!=null && args.length==1) {
					Path file = UNIX_Commands.parsePath(args[0], dir);
					Commands.delete(file);
				}
				else {
					System.out.println("The syntax of the command is incorrect");							
				}
				break;
			case "mkdir":
				if(args!=null && args.length==1) {
					Path newDir = UNIX_Commands.parsePath(args[0], dir);
					Commands.newDirectory(newDir);
				}
				else {
					System.out.println("The syntax of the command is incorrect");
				}
				break;
			case "echo":
				if(args!=null) {
					String arg = String.join(" ", args);
					if(arg.contains(">>")) {
						String[] echoArgs = arg.split(">>(\\s)*");
						if(echoArgs.length==2) {								
							Path target = UNIX_Commands.parsePath(echoArgs[1], dir);
							Commands.echo(echoArgs[0], target);
						}
					}
					else {
						Commands.echo(arg);
					}
				}
				break;
			case "ls":
				try (DirectoryStream<Path> dirs = Files.newDirectoryStream(dir.getPath())) {
					int i = 0;
				    for (Path entry: dirs) {
				    	i++;
				    	if(i%5==0) {
				    		System.out.println();
				    	}
				    	if(Files.isDirectory(entry)) {
				    		System.out.print(ANSI_BLUE);
				    	}
				    	else if(Files.isRegularFile(entry)) {
				    		System.out.print(ANSI_RED);
				    	}
				        System.out.format("%-25s"+ANSI_RESET, entry.getFileName());
				    }
				} catch (IOException | DirectoryIteratorException x) {
				    // IOException can never be thrown by the iteration.
				    // In this snippet, it can only be thrown by newDirectoryStream.
				    System.err.println(x);
				}
	    		System.out.println();
				break;
			case "cd":
				if(args!=null && args.length==1) {
					Path targetPath = UNIX_Commands.parsePath(args[0], dir);
					Commands.changeDirectory(targetPath, dir);
				}
				else {
					System.out.println("The syntax of the command is incorrect");
				}
				break;
			case "exit":
				break;
			default:
				System.out.println(command + ": command not found");
				break;					
		}
	}
	
	private static Path parsePath(String path, Directory dir) {
		Path parsedPath;
		if(Pattern.compile("^/").matcher(path).find()) {									
			parsedPath = Paths.get(path);
		}
		else if(path.equals("..")) {
			//Avoid going before root
			try {
				parsedPath = Paths.get(dir.getPath().getParent().toString());
			}
			catch(Exception x) {
				parsedPath = dir.getPath();
			}
		}
		else if(path.equals(".")) {
			parsedPath = dir.getPath();
		}
		else {
			parsedPath = Paths.get(dir+"/"+path);
		}
		return parsedPath;
	}
}
