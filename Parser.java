import java.io.*;
import java.util.*;
//keeping dom parser, could use sax in future
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

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
	
	public static void main (String[] args) {
		
		//this main will eventually be abstracted out to a handler class
		
		//file path for reuters files
		dirpath = "reuters21578/reut2-0";
		//number of sgm files
		sgmCount = 21;
		//call file load
		loadSGMFile("01");
	}
	
	public static void loadSGMFile(String sgmNumber) {
	
		try {
	
			File sgmFile = new File(dirpath+sgmNumber+".sgm");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(sgmFile);
			System.out.println("Document " + sgmNumber + " loaded");
		
		} catch (Exception e) {
			
			System.out.println(e.toString());
		
		}
	
	}

}