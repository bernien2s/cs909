import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Parser {

	/*Suggested structure:
	* - Parser class which performs initial streaming in of sgm files
	* - Tokenizer class which performs stem removal/other text processing to give root words
	* - Classes that then represent each text mining model
	* - Classes that run classification algorithms on the document representations
	* - (optional) implementation of the mapreduce framework via apache hadoop.
	*/
	
	private static String dirpath;
	private static int sgmCount;
	private static List<ReutersDocument> completedCollection;
	
	public Parser () {}
	
	public static void main (String[] args) {
		
		//this main will eventually be abstracted out to a handler class
		
		//file path for reuters files
		dirpath = "reuters21578/reut2-0";

		//has this file been converted from SGML to our preferred XML yet? 
		File f = new File(dirpath + args[0] + ".xml");
		if(!f.exists()) { 
			loadSGMFile(args[0]);
		}
		
		//call file load
		parseXMLFile(args[0]);
		
		System.out.println("(Parser): Parsing complete.");
		
		//pass to tokenizer for now
		Tokenizer tk = new Tokenizer(completedCollection);
		completedCollection = tk.tokenizeDocumentSet();
		
		System.out.println("(Tokenizer): Tokenization complete, " + completedCollection.size() + "documents.");
	
		
		//pass to lemmatizer? (more accurate classification)
		//better than stemming but more lookup involved (http://en.wikipedia.org/wiki/Lemmatisation)
		//uses lucene.
		

	}
	
	public static void parseXMLFile(String filenum) {
	
		//Loads the requested XML file and splits out the desired tags that we are
		//interested in. This will eventually go to a tokenizer and is stored as
		//in a document object.
		
		List<ReutersDocument> documentCollection = new ArrayList<ReutersDocument>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		
		//SAX Handler callback functions. This needs to be changed into a non anon class.
		SAXDefaultHandler handler = new SAXDefaultHandler(documentCollection);
	
		try {
	
			SAXParser parser = factory.newSAXParser();
			parser.parse(dirpath+filenum+".xml", handler);
			documentCollection = handler.getCompletedCollection();
			System.out.println("(ReutersDocument): Last documented to be added");
			System.out.println("(ReutersDocument): PROPERTIES:" + documentCollection.remove(documentCollection.size()-1).getTopics() + "/" + documentCollection.remove(documentCollection.size()-1).getLewis() + "/" + documentCollection.remove(documentCollection.size()-1).getCgi() + "/" + documentCollection.remove(documentCollection.size()-1).getoldid() + "/" + documentCollection.remove(documentCollection.size()-1).getnewid());
			System.out.println("(ReutersDocument): TITLE: " + documentCollection.remove(documentCollection.size()-1).getTitle());
			System.out.println("(ReutersDocument): DATELINE: " + documentCollection.remove(documentCollection.size()-1).getDateline());
			System.out.println("(ReutersDocument): BODY: " + documentCollection.remove(documentCollection.size()-1).getText());
			System.out.println("(PARSER): Parsing complete");
			System.out.println("(PARSER): Documents in collection, " + documentCollection.size());
			completedCollection = documentCollection;
		
		} catch (Exception e) {
		
			System.out.println(e.toString());
		
		}
	
	
	}
	
	public static void loadSGMFile(String sgmNumber) {
	
		//Loads the requested SGM file, removing noise and converting into
		//a well formed XML file that exists in the reuters21578 subdirectory.
		//Essentially a deprecated method now that we have clean xml files.
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		DefaultHandler handler = new DefaultHandler();
	
		try {
			
			//1) First add a root node as file isn't well formed.
			String ln;
		    SAXParser parser = factory.newSAXParser();
			
			//a)Temp file
			File file = new File(dirpath+sgmNumber+".xml");
			file.createNewFile();
			
			//b)Load sgm file
			File sgmFile = new File(dirpath+sgmNumber+".sgm");
			BufferedReader br = new BufferedReader(new FileReader(dirpath+sgmNumber+".sgm"));
			
			//c)Write to file
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<ReutersRoot>");
			
			while ((ln = br.readLine()) != null) {
				if(!ln.contains("DOCTYPE")) {
					bw.write(ln.replaceAll("&#","")+"\n");
				} 
			}
			
			bw.write("</ReutersRoot>");
		
			bw.flush();
			bw.close();
			
			parser.parse(dirpath+sgmNumber+".xml", handler);
			System.out.println("(PARSER): Parsing complete");
			
		
		} catch (Exception e) {
			
			System.out.println(e.toString());
		
		}
	
	}
	
	public static List<ReutersDocument> getCompletedCollection() {
	
		return completedCollection;
	
	}

}

class SAXDefaultHandler extends DefaultHandler {

	private List<ReutersDocument> collection;
	private boolean title = false;
	private boolean d = false; 
	private boolean places = false; 
	private boolean dateline = false;
	private boolean topics = false;
	private boolean body = false;
	private boolean endofdoc = false;
	private boolean endoffile = false;
	private String bodytext;
			
	public ReutersDocument document;

	public SAXDefaultHandler(List<ReutersDocument> collection) {
	
		this.collection = collection;
	
	}
	
	public SAXDefaultHandler() {}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("reuters")) {
		
			//create new reuters document.
			document = new ReutersDocument();
			document.setTopics(attributes.getValue("TOPICS"));
			document.setLewis(attributes.getValue("LEWISSPLIT"));
			document.setCgi(attributes.getValue("CGISPLIT"));
			document.setoldid(attributes.getValue("OLDID"));
			document.setnewid(attributes.getValue("NEWID"));
			endofdoc = false;
		}

		if (qName.equalsIgnoreCase("title")) {
			title = true;
		}

		if (qName.equalsIgnoreCase("dateline")) {
			dateline = true;
		}

		if (qName.equalsIgnoreCase("body")) {
			body = true;
		}
		
		if (qName.equalsIgnoreCase("topics")) {
			topics = true;
		}
		
		//"d" contains the names of topics
		if (qName.equalsIgnoreCase("d")) {
			d = true;
		}
		
		//we do not care about places, but we are using this to prevent places from occurring in the topic list
		if (qName.equalsIgnoreCase("places")) {
			topics = false; 
			places = true;
		}

	}	

	public void characters(char ch[], int start, int length) throws SAXException {

		if (title) {
			document.setTitle(new String(ch, start, length));
			title = false;
		}

		if (dateline) {
			document.setDateline(new String(ch, start, length));
			dateline = false;
		}
		
		//we don't actually do anything here... this method can be removed
		if (topics) {
			//document.setTopicList(new String(ch, start, length));
			//topics = false;
		}
		
		//dispose of places
		if (places) {
			places = false;
		}
		
		//"d" contains the names of topics 
		if (d && topics) {
			document.addTopic(new String(ch, start, length));
			d = false; 
		}

		if (body) {
			bodytext = bodytext + new String(ch, start, length);
		}

	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase("reuters")) {
			endofdoc = true;
			collection.add(document);
		}
		
		if (qName.equalsIgnoreCase("body")) {
			document.setText(bodytext);
			body = false;
			bodytext="";
		}

		if (qName.equalsIgnoreCase("topics")) {
			topics = false;
		}
		
	}
	
	public List<ReutersDocument> getCompletedCollection() {
	
		return collection;
	
	}

}
