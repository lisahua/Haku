package seal.haku.empirical.logProcessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @Author Lisa
 * @Date: Jan 21, 2015
 */
public class AnomalyCalculator {
	private String[] keyword = { "fix", "error", "bug", "issue", "mistake",
			"incorrect", "fault", "defect", "flaw" };
	private HashSet<String> dictBuggy = new HashSet<String>(
			Arrays.asList(keyword));

	/**
	 * read upi file
	 */
	public void readFile(String file) {
		String commitNo = "";
		int buggy = 0;
		boolean isMsg = false;
		String fileName = "";
		int addedLine = 0;
		int deletedLine = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			PrintWriter writer = new PrintWriter(file + ".msg");
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line.equals(""))
					continue;
				if (line.startsWith("commit ")) {
					if (fileName.length() > 0)
						writer.println(commitNo + "," + fileName + "," + buggy+","+addedLine+","+deletedLine);
					fileName = "";
					commitNo = line.split("commit ")[1].trim().substring(0, 10);
					buggy = 0;

				} else if (line.startsWith("Date: "))
					isMsg = true;
				else if (line.startsWith("diff "))
					isMsg = false;
				else if (isMsg && buggy == 0) {

					for (String s : dictBuggy) {
						if (line.contains(s))
							buggy = 1;
					}
				} else if (line.startsWith("+++")) {
					if (!fileName.equals(""))
						writer.println(commitNo + "," + fileName + ","
								+ buggy+","+addedLine+","+deletedLine);
					fileName = line.substring(6);
					addedLine =0;
					deletedLine = 0;
					
				} else if (line.startsWith("+ "))
					addedLine++;
				else if (line.startsWith("- "))
					deletedLine++;
			}
			writer.println(commitNo + "," + fileName + ","
					+ buggy+","+addedLine+","+deletedLine);
			reader.close();
			writer.close();
			System.out.println("finish "+file+".msg");
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

}
