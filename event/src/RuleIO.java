import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Extraction and printing of rules.
 * 
 * @author morteza
 * 
 */
public class RuleIO {

	static List<Rule>	listRules;

	public static List<Rule> getRuleList() throws FileNotFoundException {
		if (listRules != null) {
			return listRules;
		} else {
			listRules = new LinkedList<Rule>();
			Scanner sc = new Scanner(new File("rules.txt"));

			while (sc.hasNext()) {
				String ruleStr = sc.nextLine();
				if (ruleStr.charAt(0) == '#')
					continue;
			//	System.out.println(ruleStr);

				TwoArgRule twoArgRule = new TwoArgRule(ruleStr);
				listRules.add(twoArgRule);
				// System.out.println("end");
			}
		}
		return listRules;
	}

	public static void main(String[] args) throws FileNotFoundException {

	}

}
