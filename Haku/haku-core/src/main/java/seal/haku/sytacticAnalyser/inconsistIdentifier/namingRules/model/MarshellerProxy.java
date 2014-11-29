package seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules.model;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class MarshellerProxy {

	public void marshell(List<NamingRule> rules) throws Exception {
		JAXBContext jc = JAXBContext.newInstance("haku.seal.edu.utexas.ece");
		
		Marshaller m = jc.createMarshaller();

		OutputStream os = new FileOutputStream("nosferatu.xml");
		m.marshal(rules, os);
	}
}
