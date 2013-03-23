import java.util.*;

//Represents a reuters document.

public class ReutersDocument {

	private String TOPICS;
	private String LEWISSPLIT;
	private String CGISPLIT;
	private String OLDID;
	private String NEWID;
	private String TITLE;
	private String DATELINE;
	private String TEXT;

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
	
	public ReutersDocument() {}
	
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
}





