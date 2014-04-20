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

	public OutputRecord() {
		responseId = random.nextInt();
		realisLabel = "ACTUAL";
		confidencecore = 0;

	}

	public static void main(String[] args) throws Exception {


	}

}
