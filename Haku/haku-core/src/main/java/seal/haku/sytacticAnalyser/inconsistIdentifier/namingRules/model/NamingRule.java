package seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules.model;

import java.util.ArrayList;
import java.util.List;

public class NamingRule {
	private List<Pattern> source = new ArrayList<Pattern>();
	private List<Pattern> target = new ArrayList<Pattern>();
	public List<Pattern> getSource() {
		return source;
	}
	public void setSource(List<Pattern> source) {
		this.source = source;
	}
	public List<Pattern> getTarget() {
		return target;
	}
	public void setTarget(List<Pattern> target) {
		this.target = target;
	}
}
