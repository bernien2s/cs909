import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.StringReader;
import java.io.StreamTokenizer;

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
	
	public Tokenizer(List<ReutersDocument> documentSet) {
	
		this.documentSet = documentSet;
	
	}
	
	public List<ReutersDocument> tokenizeDocumentSet() {
	
		//variables for temporary storage of tokenized info.
		String title;
		String dateLine;
		String body;
		String[] tokenArray;
		List<String> tokenList = new ArrayList<String>();
	
		for (ReutersDocument document : documentSet) {
			
			//get body text from current document
			body = document.getText();
			
			try {
					
				//enter text into a tokenizer object
				tokenizerObject = new StreamTokenizer(new StringReader(body));
			
				while(tokenizerObject.nextToken() != StreamTokenizer.TT_EOF){

					if(tokenizerObject.ttype == StreamTokenizer.TT_WORD) {
						System.out.println(tokenizerObject.sval);
						tokenList.add(tokenizerObject.sval);
					} else if(tokenizerObject.ttype == StreamTokenizer.TT_EOL) {
						System.out.println();	
					}
						
				}
			
			} catch (Exception e) {
			
				e.toString();
			
			}
		
			tokenArray = (String[]) tokenList.toArray(new String[0]);
			document.setbodyTokens(tokenArray);
		
		}
	
		return documentSet;
		
	}

}
