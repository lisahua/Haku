package seal.haku.syntacticAnalyser.inconsistenIdentifier;

import com.ibm.wala.ipa.callgraph.AnalysisCache;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.CallGraphBuilder;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.util.config.AnalysisScopeReader;

public class TestWala {
	private final static ClassLoader MY_CLASSLOADER = GetLoadedFields.class
			.getClassLoader();


	public static void main(String[] args) throws Exception {
		// represents code to be analyzed
		AnalysisScope scope = AnalysisScopeReader.makeJavaBinaryAnalysisScope(
				"/Users/jinruhua/Documents/Study/2014/nameExample/snapshots/Hello/bin/Hello.class", null);
		IClassHierarchy cha = ClassHierarchy.make(scope);
		System.err.print(cha.getNumberOfClasses());
		// what are the call graph entrypoints?
		Iterable<Entrypoint> e = Util.makeMainEntrypoints(scope, cha);
		// encapsulates various analysis options
		AnalysisOptions o = new AnalysisOptions(scope, e);
		// builds call graph via pointer analysis
		CallGraphBuilder builder = Util.makeZeroCFABuilder(o,
				new AnalysisCache(), cha, scope);
//		CallGraph cg = builder.makeCallGraph(o, null);
		System.err.println("Done");

//		int nMethods = 0;
//		int nFields = 0;
//
//		Map<IMethod, Collection<FieldReference>> method2Field = HashMapFactory
//				.make();
//		for (IClass c : cha) {
//			for (IMethod m : c.getDeclaredMethods()) {
//				nMethods++;
//				Collection<FieldReference> fields = CodeScanner
//						.getFieldsRead(m);
//				nFields += fields.size();
//				method2Field.put(m, fields);
//			}
//		}
//
//		System.out.println(nMethods + " methods");
//		System.out.println((float) nFields / (float) nMethods
//				+ " fields read per method");
	}

	public void testSoot() {

	}

	public void testChord() {

	}
}
