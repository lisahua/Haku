package seal.haku.dependencyInjection;

import seal.haku.lexicalAnalyser.extractName.PoorNameDetector;
import seal.haku.lexicalAnalyser.extractName.PurityASTVisitor;
import seal.haku.lexicalAnalyser.extractName.SlicingASTVisitor;

public class ConfigMaintainer {
	private static RequestObject config = new RequestObject();

	public static void setConfig(RequestObject obj) {
		config = obj;
	}
	
	public static PoorNameDetector getCheckApproach() {
		if (config.getCheckApproach().equals("usage"))
			return new SlicingASTVisitor();
		else if (config.getCheckApproach().equals("purity")) {
			return new PurityASTVisitor();
		}
		return new SlicingASTVisitor();
	}
	
	public static String getSourceCodeDir() {
		return config.getSourceCodeDir();
	}

	public static boolean isWriteToDB() {
		return config.isWriteToDB();
	}

	public static String getWriteToPath() {
		return config.getWriteToPath();
	}

}
