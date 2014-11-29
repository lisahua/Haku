package seal.haku.syntacticAnalyser.inconsistenIdentifier;

import soot.Scene;
import soot.SootClass;

public class TestSoot {

//	public static void main(String[] args) {
//		  PackManager.v().getPack("jtp").add(
//		    new Transform("jtp.myTransform", new BodyTransformer() {
//		      protected void internalTransform(Body body, String phase, Map options) {
//		        new LocalMustNotAliasAnalysis(new ExceptionalUnitGraph(body));
//		      }
//		    }));
//		  soot.Main.main(args);
//		  
//		}
	private static SootClass loadClass(String name,
			boolean main) {
			SootClass c = Scene.v().loadClassAndSupport(name);
			c.setApplicationClass();
			if (main) Scene.v().setMainClass(c);
			return c;
			}
			public static void main(String[] args) {
			
			SootClass c = loadClass("Hello",true);
//			loadClass("Hello",false);
	
			}

}
