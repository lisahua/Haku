package seal.haku.syntacticAnalyser.purityAnalysis;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

import seal.haku.empirical.compare.ConfigUtility;
import seal.haku.syntacticAnalyser.parser.UsagePatternFileReader;

/**
 * @Author Lisa
 * @Date: Jan 19, 2015
 */
public class PurityAnalysisProcessor extends UsagePatternFileReader {
	private Set<String> pureVerbs;

	public PurityAnalysisProcessor(String bugOutputPath) {
		pureVerbs = ConfigUtility.getPURITY_VERBS();
		try {
			writer = new PrintWriter(bugOutputPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void processMethod(String usagePattern) {
		if (!isPureMethod(usagePattern))
			if (pureVerbs.contains(getVerbFromUsagePattern(usagePattern))) {
				writer.println(usagePattern);
			}
	}
}
