package seal.haku.lexicalAnalyser.model.usageNode;

import java.util.ArrayList;

public class ActionNode extends UsageEdge {
	private String statement;
	private ArrayList<DataNode> parameters = new ArrayList<DataNode>();
	private DataNode typeValue ;	
	private DataNode returnValue;
	private String type;
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public ArrayList<DataNode> getParameters() {
		return parameters;
	}
	//need more methods
	public void setParameters(ArrayList<DataNode> parameters) {
		this.parameters = parameters;
	}
	public DataNode getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(DataNode typeValue) {
		this.typeValue = typeValue;
	}
	public DataNode getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(DataNode returnValue) {
		this.returnValue = returnValue;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
