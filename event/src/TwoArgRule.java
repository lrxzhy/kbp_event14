import java.io.File;
import java.util.Scanner;

public class TwoArgRule extends Rule {
	
	String ruleStr;

	String	eventType;
	String	lookupWord;			// synonym of the term to find
	String	extractionTitle;
	String	direction;				// 'left|right
	String	extractionType;

	public TwoArgRule(String ruleStr) {
		this.ruleStr = ruleStr;
		String[] arguments = ruleStr.split(",\\s?|\\+");
		this.eventType = arguments[0];
		this.extractionTitle = arguments[1];
		this.lookupWord = arguments[2];
		this.direction = arguments[3];
		this.extractionType = arguments[4];
	}

	public static void main(String[] args) throws Exception {
		RuleIO.getRuleList();
	}

	@Override
	public String toString() {
		return ruleStr;
	}
}
