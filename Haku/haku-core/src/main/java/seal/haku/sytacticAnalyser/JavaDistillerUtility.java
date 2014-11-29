package seal.haku.sytacticAnalyser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import ch.uzh.ifi.seal.changedistiller.JavaChangeDistillerModule;
import ch.uzh.ifi.seal.changedistiller.ast.java.Comment;
import ch.uzh.ifi.seal.changedistiller.ast.java.JavaASTNodeTypeConverter;
import ch.uzh.ifi.seal.changedistiller.ast.java.JavaCompilation;
import ch.uzh.ifi.seal.changedistiller.ast.java.JavaCompilationUtils;
import ch.uzh.ifi.seal.changedistiller.ast.java.JavaMethodBodyConverter;
import ch.uzh.ifi.seal.changedistiller.distilling.Distiller;
import ch.uzh.ifi.seal.changedistiller.distilling.DistillerFactory;
import ch.uzh.ifi.seal.changedistiller.model.classifiers.SourceRange;
import ch.uzh.ifi.seal.changedistiller.model.classifiers.java.JavaEntityType;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeEntity;
import ch.uzh.ifi.seal.changedistiller.model.entities.StructureEntityVersion;
import ch.uzh.ifi.seal.changedistiller.treedifferencing.Node;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class JavaDistillerUtility {
	protected static final Injector sInjector;
	protected static JavaMethodBodyConverter sMethodBodyConverter;
	protected static JavaASTNodeTypeConverter sNodeConverter;
	static {
		sInjector = Guice.createInjector(new JavaChangeDistillerModule());
		sMethodBodyConverter = sInjector
				.getInstance(JavaMethodBodyConverter.class);
		sNodeConverter = sInjector.getInstance(JavaASTNodeTypeConverter.class);
	}

	static Distiller getDistiller(StructureEntityVersion structureEntity) {
		return sInjector.getInstance(DistillerFactory.class).create(
				structureEntity);
	}


	public static List<SourceCodeChange> diffBlock(Node aRootOld, Node aRootNew) {
		StructureEntityVersion structureEntity = new StructureEntityVersion(
				JavaEntityType.METHOD, "Method", 0);
		Distiller distiller = getDistiller(structureEntity);
		distiller.extractClassifiedSourceCodeChanges(aRootOld, aRootNew);
		return structureEntity.getSourceCodeChanges();
	}

	public static List<SourceCodeChange> diffBlock(MethodDeclaration aRootOld,
			MethodDeclaration aRootNew) {
		StructureEntityVersion structureEntity = new StructureEntityVersion(
				JavaEntityType.METHOD, "Method", 0);

		Distiller distiller = getDistiller(structureEntity);
		distiller.extractClassifiedSourceCodeChanges(
				convertMethodBody(aRootOld),
				convertMethodBody(aRootNew));
		return structureEntity.getSourceCodeChanges();
	}

	private static Node convertMethodBody(MethodDeclaration method) {
		String methodName = method.getName().toString();
		Node root = new Node(JavaEntityType.METHOD, methodName);
		root.setEntity(new SourceCodeEntity(methodName, JavaEntityType.METHOD,
				new SourceRange(method.getStartPosition(), method.getLength()
						+ method.getStartPosition())));

		return root;
	}

}