package seal.haku.syntacticAnalyser.usagePattern.inconsistency;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;

import seal.haku.syntacticAnalyser.parser.UsagePatternFileReader;

/**
 * @Author Lisa
 * @Date: Jan 19, 2015
 */
public class UPInconsistentProcessor extends UsagePatternFileReader {
	PrintWriter writer;

	public UPInconsistentProcessor(String outputPath) {
		try {
			writer = new PrintWriter(outputPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void processMethod(String usagePattern) {
		String[] nameUsage = usagePattern.split("-->");
		if (nameUsage.length > 1) {
			HashSet<String> usageSet = new HashSet<String>(
					Arrays.asList(nameUsage[1].split(",")));
			usageSet.remove("");
			for (String usage : usageSet) {
				if (usage.contains("2:")) {
					usage = usage.substring(2);
					writer.println(generateWriteLine(usage) + ","
							+ nameUsage[0] + ","
							+ processingClass+","+filePath);
				}
			}

		}

	}

	private String generateWriteLine(String usage) {
		String method = "";
		String type = "";
		String name = "";
		// int dotIndex = usage.indexOf(".");
		// if (dotIndex < 1)
		// return usage;
		// String value = usage.substring(0, dotIndex).trim();
		if (usage.contains("[")) {
			int leftPara = usage.indexOf("[");
			int rightPara = usage.lastIndexOf("]");
			type = usage.substring(0, leftPara);
			method = usage.substring(leftPara + 1, rightPara);
			name = usage.substring(leftPara + 1, rightPara);
			return type + "." + method + "," + name;
		} else if (usage.contains(".")) {
			int dotIndex = usage.indexOf(".");
			if (usage.charAt(0) >= 'A' && usage.charAt(0) <= 'Z')
				return usage + ",";
			name = usage.substring(0, dotIndex);
			if (name.equals("this"))
				type = processingClass;
			else if (fieldTypeMap.containsKey(name))
				type = fieldTypeMap.get(name);
			else
				type = processingClass;
			return type + usage.substring(dotIndex) + "," + name;
		} else {
			return processingClass + "." + usage + ",";
		}

	}
}
