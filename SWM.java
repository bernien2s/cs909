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
	
	public void runModel(Instances is) {
	
		//Set filter options
		String swvOptions[] = {"-L"};
		
		System.out.println("(SWM Model): Applying...");
		
		try {
		
			this.swv.setInputFormat(is);
			this.swv.setOptions(swvOptions);
		
			Instances wordVectors = this.swv.useFilter(is, this.swv);
			
			System.out.println("\n\n(SWM Model): Word Vectors -\n\n " + wordVectors);

		
		} catch (Exception err) {
		
			System.out.println("(SWM Model)(Error): " + err.toString());
		
		}
	
	}
}