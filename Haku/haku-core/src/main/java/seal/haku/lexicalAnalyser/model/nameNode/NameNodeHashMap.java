package seal.haku.lexicalAnalyser.model.nameNode;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class NameNodeHashMap {
	private static HashMap<String, Integer> nameNodeMap = new HashMap<String, Integer>();
	private static HashMap<String, String> containsMap = new HashMap<String, String>();
	// private static HashMap<String, String> invocationMap = new
	// HashMap<String, String>();
	private static HashMap<String, String> inheritanceMap = new HashMap<String, String>();
	// private static HashMap<String, String> assignmentMap = new
	// HashMap<String, String>();
	private static HashMap<String, String> nameTypeMap = new HashMap<String, String>();
	private static int count = 1;
	private static PrintWriter invocationWriter;
	private static PrintWriter assignmentWriter;
	public static String getTypeInName(String name) {
		return nameTypeMap.get(name);
	}

	public static void parseAndAddStatement(String statement, String classType) {

		/*
		 * private String generalizeMtdInv(String invokeS) { if
		 * (!invokeS.contains(".") && !invokeS.contains("(")) return invokeS;
		 * else if (!invokeS.contains(".") && invokeS.contains("(")) { return
		 * "<"++">." + invokeS; } else { // TODO: complext nest function call
		 * using stack String[] tkns = invokeS.split("\\."); String caller =
		 * NameNodeHashMap.getTypeInName(tkns[0]); if (caller != null) return
		 * "<" + caller + ">." + tkns[1].split("(")[0]; } return invokeS;
		 * 
		 * }
		 */
		// invocation
		if (!statement.contains("=")) {
			if (!statement.contains("."))
				return;
			String[] tokens = statement.split("\\.");
			String caller = getTypeInName(tokens[0]);
			if (caller != null)
				addInvocationRelation(caller + "." + tokens[1].split("\\(")[0].split("<")[0],
						tokens[0],classType);
		}
		// assignment
		else {
			String[] tokens = statement.split("=");
			String value = tokens[0];
			String[] temp = value.split(" ");
			value = temp[temp.length - 1];
			if (value.contains("this."))
				value = value.split("this.")[1];

			// caller
			String apis = tokens[1].split("\\(")[0].replace("\n", "").replace(
					";", "").split("{")[0];
			if (!apis.contains(".")) {
				// String[] tmp = tokens[1].split("\\(");
				String source = getTypeInName(apis);
				if (source == null) {
					try {
						Double.parseDouble(apis);
						return;
					} catch (Exception e) {
						source = classType;
					}
				}

				// TODO wrong for override function
				addAssignmentRelation(source + "." + apis, value,classType);
			} else {
				String[] tkns = apis.split("\\.");
				String caller = getTypeInName(tkns[0]);
				if (caller == null)
					return;
				if (tkns.length<2) return ;
				addAssignmentRelation(caller + "." + tkns[1], value,classType);
				addInvocationRelation(caller + "." + tkns[1], tkns[0],classType);
			}
		}
	}

	public static void addNameTypeMap(String name, String type) {
		nameTypeMap.put(name.trim(), type.trim());
	}

	public static void clearNameTypeMap() {
		nameTypeMap.clear();
	}

	public static void put(IdentifierNode node) {
		if (!nameNodeMap.containsKey(node.toString())) {
			nameNodeMap.put(node.toString().trim(), count++);
			// Not initializer
			if (node.getType() != null)
				nameTypeMap.put(node.getName().trim(), node.getType()
						.toString().trim());
		}
	}

	public static void getID(IdentifierNode node) {
		nameNodeMap.get(node.toString());
	}

	public static void addContainsRelation(IdentifierNode source,
			IdentifierNode target) {
		containsMap.put(source.toString(), target.toString());
	}

	public static void addInvocationRelation(String source, String target,String clazz) {
		if (invocationWriter == null)
			try {
				invocationWriter = new PrintWriter(
						"/Users/jinruhua/Documents/Study/2014/nameExample/snapshots/elasticsearch-0190-invocation.csv");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		invocationWriter.println(source+"\t"+target+"\t"+clazz);
	}

	public static void addInheritanceRelation(IdentifierNode source,
			IdentifierNode target) {
		int sc = nameNodeMap.get(source);
		int tgt = nameNodeMap.get(target);
		if (sc != 0 && tgt != 0)
			inheritanceMap.put(source.toString(), target.toString());
	}

	public static void addAssignmentRelation(String source, String target,String clazz) {
		if (assignmentWriter == null)
			try {
				assignmentWriter = new PrintWriter(
						"/Users/jinruhua/Documents/Study/2014/nameExample/snapshots/elasticsearch-0190-assignment.csv");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		assignmentWriter.println(source+"\t"+target+"\t"+clazz);
	}

}
