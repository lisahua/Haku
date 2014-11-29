package seal.haku.lexicalAnalyser.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import seal.haku.lexicalAnalyser.model.nameNode.ClassIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.FieldIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.MethodIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.NameToken;
import seal.haku.lexicalAnalyser.model.nameNode.ParameterIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.VariableIdentifierNode;

public class DBEntity {
	private static Connection conn = null;
	private static String relationTableName = "Haku_elasticsearch_0190_usage";
	private static String nodeTableName = "Haku_NodeTable_elasticsearch_0190";

	private static Statement stmt = null;
	private static PrintWriter writer;

	public static void initDatabase() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/test");
			stmt = conn.createStatement();
		} catch (Exception ex) {
		}
	}

	public static int calRelatedBug(String path) {
		String sql = "select count(*) from elasticsearch_commit_file_0190_100 where  files like \"%"
				+ path + "%\"; ";
	//	System.out.println(sql);
		try {
			ResultSet set = stmt.executeQuery(sql);
//			if (set.getMetaData().getColumnCount() > 0)
			while(set.next())
				return set.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// public static void insertRelationEdge(RelationObject obj) {
	//
	// String sql = "insert into " + relationTableName + " VALUES ("
	// + relationToDBQuery(obj) + ");";
	// System.out.println(sql);
	// // try {
	// // stmt.executeUpdate(sql);
	// // } catch (SQLException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// }

	// public static void insertNameNode(IdentifierNode node) {
	// String sql = "insert into " + nodeTableName + " VALUES ("
	// + nameNodeToDBQuery(node) + ");";
	// System.out.println(sql);
	// // try {
	// // stmt.executeUpdate(sql);
	// // } catch (SQLException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// }

	private static String relationToDBQuery(RelationObject obj) {
		return obj.getType() + "\",\"" + obj.getSource().getName() + "\",\""
				+ obj.getTarget().getName() + "\",\"" + obj.getFilePath()
				+ "\"";
	}

	private static String nameNodeToDBQuery(IdentifierNode node) {
		int type = 0;
		if (node instanceof ClassIdentifierNode) {
			type = 1;
		} else if (node instanceof MethodIdentifierNode) {
			type = 2;

		} else if (node instanceof FieldIdentifierNode) {
			type = 3;
		} else if (node instanceof ParameterIdentifierNode) {
			type = 4;
		} else if (node instanceof VariableIdentifierNode) {
			type = 5;
		}
		NameToken[] tokens = node.getNameToken();
		// VB, VBD, VBG, VBN, VBP, VBZ,
		ArrayList<NameToken> verbTkns = new ArrayList<NameToken>();
		ArrayList<NameToken> nameTkns = new ArrayList<NameToken>();
		String subString = "";
		for (NameToken token : tokens) {
			switch (token.getType()) {
			case VB:
			case VBD:
			case VBG:
			case VBP:
			case VBZ:
			case TO:
			case IN:
				verbTkns.add(token);
				break;
			default:
				nameTkns.add(token);
			}
		}
		switch (verbTkns.size()) {
		case 0:
			subString += "\"\",\"\",";
		case 1:
			subString += "\"" + verbTkns.get(0) + "\",\"\",";
		case 2:
			subString += "\"" + verbTkns.get(0) + "\",\"" + verbTkns.get(1)
					+ "\",";
		}
		switch (nameTkns.size()) {
		case 0:
			subString += "\"\",\"\",\"\",\"\",\"\"";
		case 1:
			subString += "\"" + nameTkns.get(0) + "\",\"\",\"\",\"\",\"\"";
		case 2:
			subString += "\"" + nameTkns.get(0) + "\",\"" + nameTkns.get(1)
					+ "\",\"\",\"\",\"\"";
		case 3:
			subString += "\"" + nameTkns.get(0) + "\",\"" + nameTkns.get(1)
					+ "\",\"" + nameTkns.get(2) + "\",\"\",\"\"";
		case 4:
			subString += "\"" + nameTkns.get(0) + "\",\"" + nameTkns.get(1)
					+ "\",\"" + nameTkns.get(2) + "\",\"" + nameTkns.get(3)
					+ "\",\"\"";
		case 5:
			subString += "\"" + nameTkns.get(0) + "\",\"" + nameTkns.get(1)
					+ "\",\"" + nameTkns.get(2) + "\",\"" + nameTkns.get(3)
					+ "\",\"" + nameTkns.get(4) + "\"";
		}
		return type + "," + node.getModifier() + ",\"" + node.getType()
				+ "\",\"" + node.getFilePath() + "\"," + subString;
	}

	static String outputPath = "";

	public DBEntity(String output) {
		outputPath = output;
		try {
			writer = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setOutputPath(String path) {
		outputPath = path;
		try {
			writer = new PrintWriter(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeUsage(String path, String type, String name,
			String method, String usage) {
//		if (writer == null) {
//			try {
//				writer = new PrintWriter(outputPath);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}

		insertUsage("\""+path + "\",\"" + type + "\",\"" + name + "\",\"" + method + "\",\""
				+ usage+"\"");
		// System.out.println(path + "," + type + "," + name + "," + method +
		// ","
		// + usage);
		// insertUsage("\""+path + "\",\"" + type + "\",\"" + name + "\",\"" +
		// method + "\",\"" + usage+"\"");
	}

	public static void insertUsage(String obj) {

		String sql = "insert into " + relationTableName + " VALUES (" + obj
				+ ");";
		System.out.println(sql);
		if (stmt == null)
			initDatabase();
//		try {
//			stmt.executeUpdate(sql);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
