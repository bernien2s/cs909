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

		TOPICS = topics;
		LEWISSPLIT = lewissplit;
		CGISPLIT = cgisplit;
		OLDID = oldid;
		NEWID = newid;
		TITLE = title;
		DATELINE = dateline;
		TEXT = text;

	}

}





