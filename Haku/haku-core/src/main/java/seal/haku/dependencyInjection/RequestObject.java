package seal.haku.dependencyInjection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestObject {

	private String checkApproach = "";
	private String sourceCodeDir = "";
	private boolean writeToDB = false;
	private String writeToPath = "";
	public String getCheckApproach() {
		return checkApproach;
	}
	public void setCheckApproach(String checkApproach) {
		this.checkApproach = checkApproach;
	}
	public String getSourceCodeDir() {
		return sourceCodeDir;
	}
	public void setSourceCodeDir(String sourceCodeDir) {
		this.sourceCodeDir = sourceCodeDir;
	}
	public boolean isWriteToDB() {
		return writeToDB;
	}
	public void setWriteToDB(boolean writeToDB) {
		this.writeToDB = writeToDB;
	}
	public String getWriteToPath() {
		return writeToPath;
	}
	public void setWriteToPath(String writeToPath) {
		this.writeToPath = writeToPath;
	}
	
}
