import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class OutputRecord {

	int						responseId;
	String				docId;
	String				eventType;
	String				role;
	String				canonicalArgumentString;
	String				canonicalArgumentStringOffset;
	String				predicateJustification;
	String				baseFilter;
	String				additionalArgumentJustification;
	String				realisLabel;
	double				confidencecore	= 0;
	static Random	random					= new Random();

	String				comment;
	private Rule	rule;

	public OutputRecord(String docId, String eventType, String role, String canonicalArgumentString, String comment,
			Rule rule) {

		responseId = random.nextInt();
		this.docId = docId;
		this.eventType = eventType;
		this.role = role;
		this.canonicalArgumentString = canonicalArgumentString;

		canonicalArgumentStringOffset = "2-9"; //"1758-1760";
		predicateJustification = "1-10";//"1667-1821";
		baseFilter = "2-9";//"1758-1760";
		additionalArgumentJustification = "NIL";

		realisLabel = "Actual";
		confidencecore = 0;

		this.comment = comment;
		this.rule = rule;
	}

	@Override
	public String toString() {
		return responseId + "\t" + docId + "\t" + eventType + "\t" + role + "\t" + canonicalArgumentString + "\t"
				+ canonicalArgumentStringOffset + "\t" + predicateJustification + "\t" + baseFilter + "\t"
				+ additionalArgumentJustification + "\t" + realisLabel + "\t" + confidencecore 
			//	+ 
			//	"\n# " + comment +
				//"\n#"				+ rule
				;
	}

	public static void main(String[] args) throws Exception {

	}

}
