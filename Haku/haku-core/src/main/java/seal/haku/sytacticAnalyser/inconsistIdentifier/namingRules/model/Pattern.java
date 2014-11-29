package seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules.model;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
private List<IdentifierName> types = new ArrayList<IdentifierName>();

public List<IdentifierName> getTypes() {
	return types;
}

public void setTypes(List<IdentifierName> types) {
	this.types = types;
}

}
