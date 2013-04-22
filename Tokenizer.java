import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.io.StringReader;
import java.io.StreamTokenizer;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;

/*Weka imports*/
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class Tokenizer {

	/*Suggested structure:
	* - This tokenizer class will deal with the content of the ReutersDocument objects.
	* - methods will be used for tokenizing input and then applying preprocessing methods
	* - suggested preprocessing methods: 
	* -- case normalization (will be performed at tkn stg), smart detection on acronyms
	* -- remove stopwords, stemming (remove prefixes/suffixes)
	* -- these remaining words are then put back in the object to be used 
	*/

	private List<ReutersDocument> documentSet;
	
	private List<ReutersDocument> testSet;
	private List<ReutersDocument> trainingSet;
	
	private StreamTokenizer	tokenizerObject;
	private File stopwords;
	private Hashtable<String, String> stopSet;
	private Hashtable<String, String> usedLabels;		//list of topics that have been used at some point
	private Hashtable<String, String> pointlessTopics;  //list of topics that never occur in the training set
	private BufferedReader bReader;
	
	//two different modes of operation depending on which classification method is used
	private boolean multiTopic = true; 
	
	public Tokenizer(List<ReutersDocument> documentSet) {
	
		this.documentSet = documentSet;
		stopSet = new Hashtable<String,String>();
		usedLabels = new Hashtable<String,String>();
	
	}
		
	public List<ReutersDocument> tokenizeDocumentSet() {
	
		//variables for temporary storage of tokenized info.
		String title;
		String dateLine;
		String body;
		String[] tokenArray;
		String tmpLine = "";
		List<String> tokenList = new ArrayList<String>();
		
		List<ReutersDocument> trainingSet = new ArrayList<ReutersDocument>();
		List<ReutersDocument> testSet = new ArrayList<ReutersDocument>();
		
		System.out.println("(Tokenizer): Tokenizing document set");
		
		//pull in stopwords and place in hash table to be used later.
		try { 
		
			stopwords = new File("reuters21578/stopwords.txt");
			bReader = new BufferedReader(new FileReader(stopwords));
			
			while((tmpLine = bReader.readLine())!=null) {
				
				String[] dataArray = tmpLine.split(",");
						
				for (String item:dataArray) { 
					stopSet.put(item,item);
				}
			
			}
			
			bReader.close();
			
			
		} catch (FileNotFoundException err) {
		
			System.out.println(err.toString());
			
		} catch (IOException err) {
		
			System.out.println(err.toString());
		
		}
		
		//proceed to tokenize each document
		
		for (ReutersDocument document : documentSet) {
		
			//if the document is in the training set we need to do a bit more processing to check what topics are used
			if(document.getLewis().equals("train")){
				//get list of topics from document 
				List<String> docTopicList  = document.getTopicArrayList(); 
				//iterate over the document topic list comparing each value to the overall topic list
				for (String top : docTopicList) {
					if(!usedLabels.containsKey(top)) {
						usedLabels.put(top, top);
					}
				}
			}
			
			//get body text from current document
			if (document.getText()!=null) {
			
				body = document.getText();
			
			} else if (document.getTitle()!=null) {
			
				body = document.getTitle();
			
			} else {
				
				body = "";
				
			}
			
				try {
					
					//enter text into a tokenizer object
					tokenizerObject = new StreamTokenizer(new StringReader(body));
				
					while(tokenizerObject.nextToken() != StreamTokenizer.TT_EOF){
				
						if(tokenizerObject.ttype == StreamTokenizer.TT_WORD) {
					
							//normalize case of words.
							if(!isStopWord(tokenizerObject.sval.toLowerCase())) {		
								//pass to stem words method.
								tokenList.add(stemWord(tokenizerObject.sval.toLowerCase()));
							}
						
						} else if(tokenizerObject.ttype == StreamTokenizer.TT_EOL) {
							System.out.println();	
						}
						
					}
					
				} catch (Exception e) {
			
					System.out.println("(TOKENIZER): Tokenizer failed");
					System.out.println(e.toString());
			
				}
			
			//set body tokens
			tokenArray = (String[]) tokenList.toArray(new String[0]);
			document.setbodyTokens(tokenArray);
			
			//dispose contents of token list
			tokenList.clear();
			tokenArray = null;
			
		
		}
		
		//stem documentset
		
		//split documentSet into training and test set
		for(ReutersDocument d : documentSet) {
			//determine where to put the data item
			if(d.getLewis().equals("train")) {
				trainingSet.add(d);
			} else {
				testSet.add(d);
			}
		}
		
		System.out.println("(Tokenizer): Training set size is " + trainingSet.size()); 
		System.out.println("(Tokenizer): Test set size is " + testSet.size());
		
		//output to separate arff files 
		outputCollectionToArff(trainingSet, "train");
		outputCollectionToArff(testSet, "test");
	
		return documentSet;
		
	}
	
	public boolean isStopWord(String word) {
	
		//checks existence in stopwords hashmap.
		
		if (stopSet.containsKey(word)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public String stemWord(String word) {
	
		String stemmed = "";
		Stemmer stemmer = new Stemmer();
		stemmer.add(word.toCharArray(),word.toCharArray().length);
		stemmer.stem();
		stemmed = stemmer.toString();
		
		return stemmed;
		
	}
	
	public void outputCollectionToArff(List<ReutersDocument> docSet, String type) {
	
		//Create arff file from XML file for StringToWordVector representation.
		//This is appropriate for Vector Space and Topic Based Vector Space Model.
		//Convert to CSV and then to ARFF.
		
		System.out.println("(Tokenizer): Outputting to Arff");
		
		
		File file;		
		File topicsfile = new File("reuters21578/all-topics-strings.lc.txt");
		String tmpLine = "";
		String topicString = "";
		//multiTopic = false;
		
		List<String> topicList = new ArrayList<String>();
		
		//depending on the type of file, save to training or test file 
		if(type.equals("train")) {
			file = new File("reuters21578/arff/reut2-train.arff");
		} else {
			file = new File("reuters21578/arff/reut2-test.arff");
		}
	
		try {
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	 
	 		//create writer object for file
			BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			BufferedReader reader = new BufferedReader(new FileReader(topicsfile));
			
			//read and store topics list
			while((tmpLine = reader.readLine())!=null) {
				
				System.out.println(tmpLine);
				
				if (topicString=="") {
					topicString = null;
				} else {
					topicString = topicString + "," + tmpLine;
					topicList.add(tmpLine);
				}
			
			}
			
			reader.close();
			
			//write arff headers to File
			writer.write("@RELATION 'documents'\n\n");
			writer.write("@ATTRIBUTE words string\n");
			
			//if we are using multiple topics, write each topic out 
			//as a boolean attribute otherwise just use the first attribute 
			if (!multiTopic) {
				writer.write("@ATTRIBUTE class-att {"+topicString+"}\n\n");
			} else {
				//iterate over each and every topic, spaffing to the arff file
				for (String topic : topicList) {
					if(usedLabels.containsKey(topic)){
						writer.write("@ATTRIBUTE " + topic.replaceAll("\\s","") + "-class" + " {false, true} \n");
					}
				}
				writer.write("\n");
			}
			
			writer.write("@DATA\n");
			
			for (ReutersDocument doc : docSet) {
				
				writer.write("'");
	
				//write out string	
				for (int i = 0; i < doc.getbodyTokens().length; i++) {
				
					if (doc.getbodyTokens()[i]!=null) {
						writer.write(doc.getbodyTokens()[i].replace("\n","") + ":");
					}
				
				}
				
				//write out topics for class (null if n/a)				
				writer.write("'");
				
				//if it's a multitopic we will spam with boolean vals, otherwise now 
				if (!multiTopic) {
				
					//write out a single class (the first one available)
					if (!(doc.getTopicList()=="")) {
						writer.write(",'" + doc.getTopicList() + "'");
					}
					else {
						writer.write("," + "null");
					}
				} else {
					writer.write(",");
					//for every possible class try to match with the topics in the document
					if (!(null == doc.getTopicList())) {
						int j = 0; 
						for (String topic : topicList) {
							//hack: only add if in the "used" set
							if(usedLabels.containsKey(topic)) {
								if (j!=0) {
									writer.write(",");
								}
								if (doc.getTopicArrayList().contains(topic)) {
									writer.write("true");
								} else {
									writer.write("false"); 
								}
							}
							j++;
						}
					} else {
						int j = 0;
						for (String topic : topicList) {
							//hack: only add if in the "used set"
							if(usedLabels.containsKey(topic)) {
								if (j!=0) {
									writer.write(",");
								}
							
								writer.write("false");
							}
							j++;
						}
					}
				}
				
				writer.newLine();
				writer.flush();
			
			}
			
			writer.close();
		
		} catch (Exception e) {
		
			System.out.println(e.toString());
		
		}
	}
	
	public void setMultiTopic(boolean mt) {
		multiTopic = mt; 
	}

}
