package seal.haku.lexicalAnalyser.model;
public enum RelationType {
	INVOCATION(1),INHERITANCE(2),DEPENDENCE(3),CONTAINS(4),ASSIGNMENT(5);
	
	private int num;
	
	RelationType(int num) {
		this.num = num;
	}
	public int getType() {
		return num;
	}
}