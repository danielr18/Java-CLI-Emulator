package darb.oop.cli;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class DOS_Commands {
	
	public static void execute(String command, String[] args, Directory dir) {
		switch(command) {
			case "help":
				System.out.print(""+
						"Type `help' to see this list.\n"+
						"Commands Available:\n"+
						"copy <source> <target>: Copy a file (source) to target\n"+
						"move <source> <target>: Move a file (source) to target\n"+
						"del <file>: Delete a file or directory(it must be empty)\n"+
						"mkdir <directory>: Create a new directory\n"+
						"echo <message>: Print message in cli\n"+
						"echo <message> >> <file>: Append message in target file\n"+
						"dir: Lists the files in the current working directory.\n"+
						"cd <target>: Change the current working directory to target.\n"+
						"exit: Exit cli\n"
						);
				break;
			case "copy":
				if(args!=null && args.length==2) {
					Path sourcePath = DOS_Commands.parsePath(args[0], dir);
					Path targetPath = DOS_Commands.parsePath(args[1], dir);	
					Commands.copy(sourcePath, targetPath);
				}
				else {
					System.out.println("The syntax of the command is incorrect");							
				}
				break;
			case "move":
				if(args!=null && args.length==2) {
					Path sourcePath = DOS_Commands.parsePath(args[0], dir);
					Path targetPath = DOS_Commands.parsePath(args[1], dir);	
					Commands.move(sourcePath, targetPath);
				}
				else {
					System.out.println("The syntax of the command is incorrect");							
				}
				break;
			case "del":
				if(args!=null && args.length==1) {
					Path file = DOS_Commands.parsePath(args[0], dir);
					Commands.delete(file);
				}
				else {
					System.out.println("The syntax of the command is incorrect");							
				}
				break;
			case "mkdir":
				if(args!=null && args.length==1) {
					Path newDir = DOS_Commands.parsePath(args[0], dir);
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
							Path target = DOS_Commands.parsePath(echoArgs[1], dir);
							Commands.echo(echoArgs[0], target);
						}
					}
					else {
						Commands.echo(arg);
					}
				}
				break;
			case "dir":
				try (DirectoryStream<Path> dirs = Files.newDirectoryStream(dir.getPath())) {
				    for (Path entry: dirs) {
				    	if(Files.isDirectory(entry)) {
				    		System.out.print("<DIR> ");
				    	}
				    	else if(Files.isRegularFile(entry)) {
				    		System.out.print("<FILE> ");
				    	}
				        System.out.println(entry.getFileName());
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
					Path targetPath = DOS_Commands.parsePath(args[0], dir);
					Commands.changeDirectory(targetPath, dir);
				}
				else {
					System.out.println("The syntax of the command is incorrect");
				}
				break;
			case "exit":
				break;
			default:
				System.out.println("'" + command + "' is not recognized as an internal or external command, operable program or batch file.");
				break;					
		}
	}
	
	private static Path parsePath(String path, Directory dir) {
		Path parsedPath;
		if(Pattern.compile("^\\w:\\\\").matcher(path).find()) {									
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
			parsedPath = Paths.get(dir+"\\"+path);
		}
		return parsedPath;
	}
}
