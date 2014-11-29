package seal.haku.lexicalAnalyser.extractName;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;

public class StatementAnalyser {
	String[] verbs = new String[] { "select", "run", "handle", "build",
			"process", "invoke", "generate", "execute", "parse", "register",
			"make", "reset", "copy", "stop", "extract", "write", "initialize",
			"update", "append", "convert", "push", "start", "insert", "apply",
			"send", "save", "set", "inc", "dec", "touch", "apply", "write",
			"do", "merge", "execute", "perform", "run", "inject", "clear",
			"reset", "fill", "put", "generate", "resolve", "create", "delete",
			"remove", "sort", "activate" };
	HashSet<String> impureVerb = new HashSet<String>(Arrays.asList(verbs));

	public void parseStatement(String csvPath, String output) {

		try {
			PrintWriter writer = new PrintWriter(output);
			BufferedReader reader = new BufferedReader(new FileReader(csvPath));
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				if (tokens.length < 5)
					continue;
				String method = tokens[3];
				String usage = tokens[4];
				// static, collection
				if (method.charAt(0) <= 'Z' && method.charAt(0) >= 'A')
					continue;
				if (tokens[1].contains("<") && !tokens[1].contains(">"))
					continue;
				if (containImpureVerb(method.toLowerCase()))
					continue;
				if (containImpureVerb(usage))
					writer.println(line);
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseFieldStatement(String csvPath, String output) {

		try {
			PrintWriter writer = new PrintWriter(output);
			BufferedReader reader = new BufferedReader(new FileReader(csvPath));
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				if (tokens.length < 3)
					continue;
				String method = tokens[1];
				String usage = tokens[2];
				// static, collection
				// if (method.charAt(0) <= 'Z' && method.charAt(0) >= 'A')
				// continue;
				if (tokens[1].contains("<") && !tokens[1].contains(">"))
					continue;
				if (containImpureVerb(method.toLowerCase()))
					continue;
				if (containImpureVerb(usage))
					writer.println(line);
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean containImpureVerb(String name) {
		for (String verb : impureVerb) {
			if (name.contains(verb))
				return true;
		}
		return false;
	}

	public void getBugFixesOnFile(String filePath) {

	}

	public void getSameUsage() {
		String dir = "/Users/jinruhua/Documents/Study/2014/workspace/Haku-git/Haku/";
		String file1 = dir + "elasticsearch-100-impure.csv";
		String file2 = dir + "elasticsearch-0190-impure.csv";
		HashSet<String> existingStatement = new HashSet<String>();
		HashSet<String> commonStats = new HashSet<String>();
		HashSet<String> distinctName = new HashSet<String>();
		HashSet<String> distinctCommonName = new HashSet<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file2));
			String line = "";
			int countFileOld = 0;
			while ((line = reader.readLine()) != null) {
				// if(existingStatement.contains(line)) continue;
//				line = line.substring(line.indexOf(","));
				existingStatement.add(line);
				countFileOld++;
				distinctName.add(line.split(",")[1]);
			}
			System.out.println("distinct name "+distinctName.size());
				
			reader.close();
			reader = new BufferedReader(new FileReader(file1));
			int count = 0;
			int countFileNew = 0;
			PrintWriter writer = new PrintWriter("elasticsearch-0190-remain-after-100.csv");
			while ((line = reader.readLine()) != null) {
				countFileNew++;
				if (line.startsWith("rg")) 
					line = "o"+line;
				else 
					line = "j"+line;
				if (existingStatement.contains(line)) {
					count++;
					commonStats.add(line);
				}
			}
			
			for (String oldLines: existingStatement) {
				if (!commonStats.contains(oldLines))
//					writer.println(oldLines);
					distinctCommonName.add(oldLines.split(",")[1]);
			}
			System.out.println("remaining distinct names: "+distinctCommonName.size());
			reader.close();
			writer.close();
			System.out.println("count file old: " + countFileOld
					+ " count file new " + countFileNew + " common records "
					+ count);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
