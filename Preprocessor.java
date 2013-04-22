import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Preprocessor {

	//private modifier makes this a "static" class 
	private Preprocessor() {
 		//do nothing
	}
		
	//preprocess w/ default options
	public static Instances preprocess(Instances data) {
		//options for the swv
		String[] opt = {"-C", "-L"};
	
		//create new swv 
		StringToWordVector swv = new StringToWordVector();
		
		try{
			swv.setOptions(opt);
		} catch (Exception e) {
			System.err.println("(Preprocessor): Unable to set options in SWV");
		}
		
		return data;
	} 
	
	//try to be a bit controversial and use unusual options
	public static Instances preprocess(Instances data, String options) {
		return data; 
	} 

}