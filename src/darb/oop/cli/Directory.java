package darb.oop.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Directory {
	private Path path;
	
	public Directory() {
		this.path = getHomePath();
	}
	
	protected Path getHomePath() {
		return Paths.get(System.getProperty("user.home"));
	}
	
	public String getPathBasename() {
		//Avoid going before root
		try {
			if(this.path.equals(this.getHomePath())) {
				return "~";
			}
			return path.getFileName().toString();
		}
		catch(Exception x) {
			return "/";
		}
	}
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return path;
	}
	
	@Override
	public String toString() {
		return this.path.toString();		
	}
}
 