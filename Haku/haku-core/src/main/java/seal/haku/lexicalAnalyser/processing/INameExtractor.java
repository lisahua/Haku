package seal.haku.lexicalAnalyser.processing;

import java.io.File;
import java.util.ArrayList;

import seal.haku.lexicalAnalyser.model.IdentifierNode;

/**
 * @Author Lisa
 * @Date: Jan 16, 2015
 */
public interface INameExtractor {
	public ArrayList<IdentifierNode> getNameInDir(String dirPath);
	public ArrayList<IdentifierNode> getNameInFile(File file);
}
