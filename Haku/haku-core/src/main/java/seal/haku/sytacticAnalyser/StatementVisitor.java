package seal.haku.sytacticAnalyser;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;

import seal.haku.lexicalAnalyser.model.RelationAnalyzer;
import seal.haku.lexicalAnalyser.tokenizer.CamelCaseSplitter;
import seal.haku.lexicalAnalyser.tokenizer.POSTagger;

public class StatementVisitor extends ASTVisitor {
	HashSet<RelationAnalyzer> esList = new HashSet<RelationAnalyzer>();
	ReturnStatement rs;

	public boolean visit(ReturnStatement rs) {
		this.rs = rs;
		return true;
	}

	public boolean visit(ExpressionStatement es) {
//		String[] stats = es.getExpression().toString().split("\\.");
//		if (stats.length > 1)
//			stats = stats[stats.length - 1].split("\\(|\\)");
//		else
//			stats = es.getExpression().toString().split("\\(|\\)");
		String name = es.getExpression().toString();
		String[] tokens = CamelCaseSplitter.getInstance().executeSingleName(
				name);
//		esList.add(new PreprocessNameExecutor(name, POSTagger.getInstance()
//				.executeSingleName(tokens)));
		return true;
	}

	public ReturnStatement getReturnStatement() {
		return rs;
	}

	public HashSet<RelationAnalyzer> getExpressionStatement() {
		return esList;
	}
}
