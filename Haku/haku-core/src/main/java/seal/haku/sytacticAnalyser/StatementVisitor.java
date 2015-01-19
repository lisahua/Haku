package seal.haku.sytacticAnalyser;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;

import seal.haku.lexicalAnalyser.model.IdentifierNameModel;
import seal.haku.lexicalAnalyser.tokenizer.CamelCaseSplitter;
import seal.haku.lexicalAnalyser.tokenizer.POSTagger;

public class StatementVisitor extends ASTVisitor {
	HashSet<IdentifierNameModel> esList = new HashSet<IdentifierNameModel>();
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
		esList.add(new IdentifierNameModel(name, POSTagger.getInstance()
				.executeSingleName(tokens)));
		return true;
	}

	public ReturnStatement getReturnStatement() {
		return rs;
	}

	public HashSet<IdentifierNameModel> getExpressionStatement() {
		return esList;
	}
}
