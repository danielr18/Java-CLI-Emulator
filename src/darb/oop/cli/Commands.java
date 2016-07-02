package darb.oop.cli;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedOutputStream;
import java.io.IOException;
import static java.nio.file.StandardOpenOption.*;
import java.io.OutputStream;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Commands {
	
	protected static void copy(Path source, Path target) {
		if(Files.exists(source)) {
			try {
				Files.copy(source, target, REPLACE_EXISTING);
			} catch (IOException e) {
				System.out.println("Access denied exception");
			}
		}
		else {
			System.out.println("The system cannot find the file specified.");
		}
	}
	
	protected static void move(Path source, Path target) {
		if(Files.exists(source)) {
			try {
				Files.move(source, target, REPLACE_EXISTING);
			} catch (IOException e) {
				System.out.println("Access denied exception");
			}
		}
		else {
			System.out.println("The system cannot find the file specified.");
		}
	}
	
	protected static void delete(Path file) {
		try {
			Files.delete(file);
		}
		catch(NoSuchFileException x) {
			System.out.println(file + " no such file or directory.");
		}
		catch(DirectoryNotEmptyException x) {
			System.out.println(file + " is not empty.");
		}
		catch(IOException x) {
			System.out.println(x);
		}	
	}
	
	protected static void listFiles(Directory dir) {
		try (DirectoryStream<Path> dirs = Files.newDirectoryStream(dir.getPath())) {
			System.out.println("Directory of " + dir);
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
	}
	
	protected static void changeDirectory(Path target, Directory dir) {		
		if(Files.exists(target)) {
			dir.setPath(target);
		}
		else {
			System.out.println("The system cannot find the path specified.");
		}
	}
	
	protected static void newDirectory(Path newDir) {
		try {
			Files.createDirectory(newDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	
	protected static void echo(String message) {
		System.out.println(message);
	}
	
	protected static void echo(String message, Path target) {
		byte data[] = message.getBytes();
		try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(target, CREATE, APPEND))) {
			out.write(data, 0, data.length);
	    }
		catch (IOException x) {
			System.err.println(x);
	    }
	}
	
}
