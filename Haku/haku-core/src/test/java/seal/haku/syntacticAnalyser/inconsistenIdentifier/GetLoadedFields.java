package seal.haku.syntacticAnalyser.inconsistenIdentifier;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.ibm.wala.classLoader.CodeScanner;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.types.FieldReference;
import com.ibm.wala.util.collections.HashMapFactory;
import com.ibm.wala.util.config.AnalysisScopeReader;

public class GetLoadedFields {

  private final static ClassLoader MY_CLASSLOADER = GetLoadedFields.class.getClassLoader();

  /**
   * Use the 'GetLoadedFields' launcher to run this program with the appropriate classpath
   * @throws InvalidClassFileException
   */
  public static void main(String[] args) throws IOException, ClassHierarchyException, InvalidClassFileException {
    // build an analysis scope representing the standard libraries, excluding no classes
    AnalysisScope scope = AnalysisScopeReader.readJavaScope("primordial.txt", null, MY_CLASSLOADER);

    // build a class hierarchy
    System.err.print("Build class hierarchy...");
    IClassHierarchy cha = ClassHierarchy.make(scope);
    System.err.println("Done");

    int nMethods = 0;
    int nFields = 0;

    Map<IMethod, Collection<FieldReference>> method2Field = HashMapFactory.make();
    for (IClass c : cha) {
      for (IMethod m : c.getDeclaredMethods()) {
        nMethods++;
        Collection<FieldReference> fields = CodeScanner.getFieldsRead(m);
        nFields += fields.size();
        method2Field.put(m, fields);
      }
    }
    
    System.out.println(nMethods + " methods");
    System.out.println((float)nFields/(float)nMethods + " fields read per method");
  }
}