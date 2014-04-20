import java.io.File;
import java.util.Scanner;

public class Rule {

	String	eventType;
	String	arg1, arg2, arg3, arg4, arg5;
	String	time;
	String	place;

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("rules.txt"));

		while (sc.hasNext()) {
			String ruleStr = sc.nextLine();
			if (ruleStr.charAt(0) == '#')
				continue;
			System.out.println(ruleStr);

			String[] arguments = ruleStr.split(",\\s?|\\+");
			Rule rule = new Rule();
			rule.eventType = arguments[0];
			rule.arg1 = 
			
			
			
			System.out.println("end");
		}
	}

}
