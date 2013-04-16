import java.util.List;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.Evaluation;

public class ClassificationSuite  {
	
	/*
	*	Bayes Classification Algorithm
	*/
	
	public static void runNaiveBayes(Instances train, Instances test) {
	
		//Train Classifier
		
		//Set index
		train.setClassIndex(3);
		
		try {
		
		//Create Classifier
		Classifier nbModel = (Classifier) new NaiveBayes();
		nbModel.buildClassifier(train);
		
		//Test Classifier
		Evaluation nbTest = new Evaluation(train);
		nbTest.evaluateModel(nbModel, test);
		
		returnResults(nbTest);
		
		} catch (Exception err) {
			
			System.out.println(err.toString());
		
		}
		
	}
	
	/*
	*	Neural Network Classification Algorithm
	*/
	
	public static void runMLP(Instances train, Instances test) {
		
	}
	
	/*
	*	Decision Tree Classification Algorithm
	*/
	
	public static void runJ48(Instances train, Instances test) {
		
	}
	
	/*
	*	Nearest-Neighbour Classification Algorithm
	*/
	
	public static void runKNN(Instances train, Instances test) {
		
	}	
	
	/*
	* 	Return results of classification algorithm
	*/
	
	public static void returnResults(Evaluation eval) {
	
		//Print results
		System.out.print("(Classifier): " );
		System.out.println(eval.toSummaryString());
		
		//Get confusion matrix
		double[][] confMatrix = eval.confusionMatrix();
		
		//Print confusion matrix
		
		for (int i =0; i < confMatrix.length; i++) {
		
			for (int j = 0; j < confMatrix[i].length; j++) {
			
				System.out.print(" " + confMatrix[i][j]);
			}
			
			System.out.println("");
		}
	
	}

}