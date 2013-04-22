import java.util.List;
import weka.core.Instances;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.classifiers.bayes.NaiveBayesMultinomial;

/*TFIDF Model with Multiple Classifier Implementations
  - Uses FilteredClassifier
*/

public class TFIDF implements CustomModel {
	
	private StringToWordVector swv; 
	private Remove rm;
	private FilteredClassifier fc;
	private double averagedIncorrect;
	private double averagedCorrect;
	private double noOfInstances;
	private int noOfClasses;
	
	public TFIDF (){
	
		this.swv = new StringToWordVector(); 
		this.rm = new Remove();
		this.fc = new FilteredClassifier();
		this.noOfClasses = 75;
		
	}
	
	public void runFilteredClassifier(Instances data, String classifier) {

		//Apply StringToWordVector TFIDF Option
		String swvoptions[] = {"-W 100", "-I", "-L", "-M 1"};
		String classifierOpts[] = {"-W " + classifier};
		
		//Set input formats.
		
		try {
			
			for (int i = 1; i < this.noOfClasses; i++) {
			
				//More spaf
				this.swv.setInputFormat(data);
				this.swv.setOptions(swvoptions);
				this.fc.setOptions(classifierOpts);
				
				//Remove specific stuff
				this.rm.setAttributeIndicesArray(new int[] {0,i});
				this.rm.setInvertSelection(true);
				this.rm.setInputFormat(data);
				
				//Apply options to remove filter and apply
				Instances removedData = Filter.useFilter(data, this.rm);
				System.out.println("(RemoveFilter): Left attribute " + i);
				
				//System.out.println(removedData);
				
				//Apply string to word vector filter and builds classifier
				removedData.setClassIndex(1);
				this.fc.setFilter(this.swv);
				
				this.fc.buildClassifier(removedData);
			
				System.out.println("(Class " + i +"): " + this.fc);
			
				//Evaluate classifer
				//Evaluation nbTest = new Evaluation(data);
				//nbTest.evaluateModel(this.fc, data);
			
				//Print results
				//System.out.print("(Class " + i + "): " );
				//System.out.println(eval.toSummaryString());
			
			}
			
		} catch (Exception err) {
		
			err.printStackTrace(System.out);
		
		}
	
	
	}
	
	
	
	
	/*
	*	Potentially deprecated method
	*	Do not change at present.
	*/
	
	public Instances[] runModel(Instances is, Instances is2) {
		
		//wordvector instances
		Instances[] wordVectors = new Instances[2];
		
		//Set filter options
		String swvOptions[] = {"-W 100","-I","-L", "-M 1"};
		
		System.out.println("(TFIDF Model): Applying...");
		
		try {
		
			System.out.println("Instances before processing: " + is.firstInstance());
			
			is.setClassIndex(1);
			this.swv.setInputFormat(is);
					
			//Set attribute indices
			this.swv.setAttributeIndices("0");
			
			//Do not operate on per class basis
			this.swv.setWordsToKeep(100);
			
			this.swv.setOptions(swvOptions);
		
			wordVectors[0] = this.swv.useFilter(is, this.swv);
			wordVectors[1] = this.swv.useFilter(is2, this.swv);
			
			System.out.println("(TFIDF Model): Text Model Created");
			
			System.out.println("Instances after processing: " + wordVectors[0].firstInstance());

		
		} catch (Exception err) {
		
			System.out.println("(TFIDF Model)(Error): " + err.toString());
		
		}
		
		return wordVectors;
		
	}
	
	

}