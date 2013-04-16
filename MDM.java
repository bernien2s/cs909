import java.util.List;

import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.classifiers.bayes.NaiveBayesMultinomial;

public class MDM implements CustomModel {
	
	//this can probs be abstracted out at some point... 
	private StringToWordVector swv; 
	
	private NaiveBayesMultinomial nbm;
	
	public MDM (){
	
		this.swv = new StringToWordVector();  
		this.nbm = new NaiveBayesMultinomial();
	}
	
	public void runModel(Instances is) {
		
		String swvOptions; 
		String nbmOptions; 
	}	

}