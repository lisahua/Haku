package seal.haku.syntacticAnalyser.usagePattern.inconsistency;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import seal.haku.syntacticAnalyser.parser.UsagePatternFileReader;

/**
 * @Author Lisa
 * @Date: Jan 20, 2015
 */
public class UPInconsistencyCluster extends UsagePatternFileReader {

public UPInconsistencyCluster (String outputPath) {
	try {
		writer = new PrintWriter(outputPath);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	@Override
	public void processMethod(String usagePattern) {
		

	}
	public void readUsagePatternFile(String file) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = reader.readLine()) != null) {
				processMethod(line);
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
