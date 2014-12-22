package common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Project {
	
	String name, projectPath;

	public Project(String name, String projectPath) {
		super();
		setName(name);
		setProjectPath(projectPath);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		try {
			String converted = URLDecoder.decode(projectPath,"UTF-8");
			this.projectPath = converted;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
