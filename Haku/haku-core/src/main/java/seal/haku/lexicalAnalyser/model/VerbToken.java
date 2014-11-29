package seal.haku.lexicalAnalyser.model;

public class VerbToken {
	private String verb;
	private String fullName;
	private int frequency;
	private int renameCount;
	private boolean isFullWord = true;

	public VerbToken (String v) {
		verb = v;
	}
	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getRenameCount() {
		return renameCount;
	}

	public void setRenameCount(int renameCount) {
		this.renameCount = renameCount;
	}

	public boolean isFullWord() {
		return isFullWord;
	}

	public void setFullWord(boolean isFullWord) {
		this.isFullWord = isFullWord;
	}

	public int hashCode() {
		return 0;
	}

	public boolean equals(Object o) {
		if (o instanceof VerbToken) {
			VerbToken node = (VerbToken) o;
			return (!node.isFullWord) ? (node.getFullName().equals(verb) || node
					.getFullName().equals(fullName)) : (node.getVerb()
					.equals(verb)) || node.getVerb().equals(fullName);
		}
		return false;
	}
}
