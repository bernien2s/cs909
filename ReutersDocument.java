import java.util.*;

//Represents a reuters document.

public class ReutersDocument {

	private int docid;
	private String TOPICS;
	private String LEWISSPLIT;
	private String CGISPLIT;
	private String OLDID;
	private String NEWID;
	private String TITLE;
	private String DATELINE;
	private String TEXT;
	private String[] TITLETOKENS;
	private String[] BODYTOKENS;
	private Boolean TESTTRAINING;
	
	//Constructor for initial parsing.

	public ReutersDocument (String topics, String lewissplit, String cgisplit, String oldid, String newid, String title, String dateline, String text) {

		this.TOPICS = topics;
		this.LEWISSPLIT = lewissplit;
		this.CGISPLIT = cgisplit;
		this.OLDID = oldid;
		this.NEWID = newid;
		this.TITLE = title;
		this.DATELINE = dateline;
		this.TEXT = text;

	}
	
	//Constructor for tokenized document.
	
	public ReutersDocument (String lewissplit, String cgisplit, String[] titleTokens, String[] bodyTokens) {
		
		this.TITLETOKENS = titleTokens;
		this.BODYTOKENS = bodyTokens;
	
	}
	
	public ReutersDocument() {}
	
	public void settitleTokens(String[] titleTokens) {
	
		TITLETOKENS = titleTokens;
	
	}
	
	public void setbodyTokens(String[] bodyTokens) {
	
		BODYTOKENS = bodyTokens;
	
	}
	
	public void setTopics(String topics) {
	
		TOPICS = topics;
		
	}
	
	public void setLewis(String lewis) {
	
		LEWISSPLIT = lewis;
	
	}
	
	public void setCgi(String cgi) {
	
		CGISPLIT = cgi;
	
	}
	
	public void setoldid(String old) {
	
		OLDID = old;
	
	}

	public void setnewid(String newid) {
	
		NEWID = newid;
	
	}
	
	public void setTitle(String title) {
	
		TITLE = title;
	
	}
	
	public void setDateline(String dateline) {
	
		DATELINE = dateline;
	
	}
	
	public void setText(String text) {
	
		TEXT = text;
	
	}
	
	public String getTopics() {
	
		return TEXT;
	
	}
	
	public String getLewis() {
	
		return LEWISSPLIT;
	
	}
	
	public String getCgi() {
	
		return CGISPLIT;
	
	}
	
	public String getoldid() {
	
		return OLDID;
	
	}

	public String getnewid() {
	
		return NEWID;
	
	}
	
	public String getTitle() {
	
		return TITLE;
	
	}
	
	public String getDateline() {
	
		return DATELINE;
	
	}
	
	public String getText() {
	
		return TEXT;
	
	}
	
	public String[] gettitleTokens() {
	
		return TITLETOKENS;
	
	}
	
	public String[] getbodyTokens() {
	
		return BODYTOKENS;
	
	}
}





