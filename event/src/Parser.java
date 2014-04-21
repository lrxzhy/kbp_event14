import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * NER's found:
 * 
 * PERSON DATE TIME DURATION ORGANIZATION NUMBER LOCATION MONEY MISC PERCENT ORDINAL SET
 * 
 */
public class Parser {

	HashSet<String>					verbs						= new HashSet<String>();
	HashSet<String>					ner							= new HashSet<String>();
	HashSet<String>					sentenceList		= new HashSet<String>();
	static String[]					conjunctionList	= { "for", "and", "nor", "but", "or", "yet", "so", "after", "although",
			"because,", "before,", "if", "lest", "once", "since", "than", "that", "though", "till", "unless", "until",
			"when", "whenever", "where", "wherever", "while", "both", "either", "neither", "whether", "eventhough" };
	static HashSet<String>	conjunction			= new HashSet<String>();

	StanfordCoreNLP					pipeline;

	// props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");

	void parseDocument(File file) throws Exception {

		// if (!file.getName().contains("bolt-eng-DF-200-192446-3810563"))
		// return;

		String docId = file.getName().substring(0, file.getName().length() - 4);

		PrintWriter printWriter;
		// printWriter = new PrintWriter(new File("./output/" + docId) );
		// printWriter = new PrintWriter(new OutputStreamWriter(
		// csocket.getOutputStream(), StandardCharsets.UTF_8), true);

		printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream("./output/" + docId, false), "UTF-8"));

		printWriter.print('\uFEFF'); // BOM
		
		// printWriter.println("# -----");
		printWriter.flush();

		System.out.println(docId);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		StringBuilder textTemp = new StringBuilder();
		while ((line = br.readLine()) != null) {
			textTemp.append(line);
			// System.out.println(line);
		}
		br.close();
		String text = textTemp.toString();

		// creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and
		// coreference resolution

		// read some text in the text variable

		// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);

		// run all Annotators on this text
		pipeline.annotate(document);

		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and has values with custom
		// types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) { // for each sentence

			// for each rule
			for (Rule rule : RuleIO.getRuleList()) {

				// traversing the words in the current sentence
				// a CoreLabel is a CoreMap with additional token-specific methods
				StringBuilder temp = new StringBuilder();
				String prev = "";
				// for (int i = 0; i < sentence.get(TokensAnnotation.class).size(); i++) { // for each token
				// // CoreLabel token
				// CoreLabel token = sentence.get(TokensAnnotation.class).get(i);
				// // this is the text of the token
				// String word = token.get(TextAnnotation.class);
				// // this is the POS tag of the token
				// String pos = token.get(PartOfSpeechAnnotation.class);
				// // this is the NER label of the token
				// String ner = token.get(NamedEntityTagAnnotation.class);
				// if (!ner.equals("O")) { // it is not named entity
				// if (!prev.equals(ner))
				// temp.append(" " + ner);
				// prev = ner;
				// } else if (pos.startsWith("VB") || conjunction.contains(word.toLowerCase())) {
				// temp.append(" " + word);
				// } else if (pos.equals(word)) {// punctuation
				// temp.append(word);
				// } else if (pos.startsWith("NN")) {
				// if (!prev.startsWith("NN"))
				// temp.append(" NP");
				// prev = "NN";
				// } else
				// prev = "";
				//
				// }

				match(printWriter, docId, sentence, (TwoArgRule) rule);

				sentenceList.add(file.getName() + "\t" + temp.toString().trim() + "\t" + sentence.toString());
			}
		}
		printWriter.close();

	}

	public void match(PrintWriter printWriter, String docId, CoreMap sentence, TwoArgRule tar) {
		List<CoreLabel> listTokens = sentence.get(TokensAnnotation.class);
		for (int i = 0; i < listTokens.size(); i++) { // for each token
			CoreLabel token = listTokens.get(i);
			// System.out.println(token);
			String word = token.get(TextAnnotation.class);

			if (word.equalsIgnoreCase(tar.lookupWord)) {
				CoreLabel cl = scan(sentence, i, tar);
				if (cl != null) {
					System.out.println("#==> " + cl);
					OutputRecord or = new OutputRecord(docId, tar.eventType, tar.extractionTitle, cl.word(),
							sentenceToStr(sentence), tar);
					// System.out.println(or);
					// sentenceToStr(sentence);
				//	printWriter.println("# __________");
					printWriter.println(or.toString());
					// System.out.println(or.toString());
				}
			}
		}
		// System.out.println("============================================");
	}

	private String sentenceToStr(CoreMap sentence) {
		List<CoreLabel> listTokens = sentence.get(TokensAnnotation.class);
		String tempStr = "";
		for (int i = 0; i < listTokens.size(); i++) { // for each token
			CoreLabel token = listTokens.get(i);
			// System.out.println(token);
			String word = token.get(TextAnnotation.class);
			// this is the POS tag of the token
			String pos = token.get(PartOfSpeechAnnotation.class);
			// this is the NER label of the token
			String ner = token.get(NamedEntityTagAnnotation.class);
			tempStr += word + "[" + ner + "_" + pos + "] ";
		}
		return tempStr;
	}

	// scan left or right from index according to rule
	private CoreLabel scan(CoreMap sentence, int index, TwoArgRule tar) {
		List<CoreLabel> listTokens = sentence.get(TokensAnnotation.class);
		int beginIndex = index;
		int endIndex = (tar.direction.equals(Rule.LEFT)) ? 0 : listTokens.size();

		int tempIndex = beginIndex;
		boolean found = false;
		while (tempIndex != endIndex && !found) {
			if (tempIndex != beginIndex) {
				// skip first index (anchor word itself)
				CoreLabel token = listTokens.get(tempIndex);

				String pos = token.get(PartOfSpeechAnnotation.class);
				String ner = token.get(NamedEntityTagAnnotation.class);

				if (ner.contains(tar.extractionType) || pos.contains(tar.extractionType))
					return token;
			}

			if (tar.direction.equals(Rule.LEFT))
				tempIndex--;
			else
				tempIndex++;
		}

		return null;
	}

	public static void main(String[] args) throws Exception {
		// System.setProperty("file.encoding", "UTF8");
		for (String conj : conjunctionList) {
			conjunction.add(conj);
		}

		Parser parser = new Parser();

		// parser.parseDocument(new File("temp.txt"));
		parser
				.parseDocumentList(new File("/Users/morteza/Dropbox/workspaces/eclipse/kepler/kbp_event14/event/corpus/data/"));
		// parser.readRules("event-rules.txt");

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("sentences.txt")));
		System.out.println("Printing all Scentences...");
		for (String scen : parser.sentenceList) {
			bw.write(scen);
			bw.newLine();
		}
		bw.close();
	}

	public Parser() {
		Properties props = new Properties();
		props.put("annotators", "tokenize,cleanxml, ssplit, pos,lemma,ner");
		pipeline = new StanfordCoreNLP(props);
	}

	// recursively parse all text files in a directory
	public void parseDocumentList(final File folder) throws Exception {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				parseDocumentList(fileEntry);
			} else {
				parseDocument(fileEntry);
			}
		}
	}
}
