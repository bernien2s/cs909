import java.util.List;

import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.classifiers.bayes.NaiveBayesMultinomial;

public class SWM implements CustomModel {
	
	private StringToWordVector swv; 
	
	public SWM (){
	
		this.swv = new StringToWordVector();  
	}
	
	public Instances runModel(Instances is) {
	
		Instances wordVectors = null;
	
		//Set filter options
		String swvOptions[] = {"-L"};
		
		System.out.println("(SWM Model): Applying...");
		
		try {
		
			this.swv.setInputFormat(is);
			
			//Set attribute indices
			this.swv.setAttributeIndicesArray(new int[] { 1 });
			
			this.swv.setOptions(swvOptions);
		
			wordVectors = this.swv.useFilter(is, this.swv);
			
			System.out.println("\n\n(SWM Model): Text Model Created");

		
		} catch (Exception err) {
		
			System.out.println("(SWM Model)(Error): " + err.toString());
		
		}
		
		return wordVectors;
	
	}
}