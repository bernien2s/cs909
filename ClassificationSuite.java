import java.util.List;
import java.util.Collection;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.lazy.IBk;

public class ClassificationSuite  {
	
	/*
	*	Bayes Classification Algorithm
	*/
	
	public static void runNaiveBayes(Instances train, Instances test) {
	
		//Set counts
		System.out.println("Training set size: " + train.numInstances());
		System.out.println("Test set size: " + test.numInstances());
		
		//Set index
		train.setClassIndex(0);
		test.setClassIndex(0);
		
		try {
		
			//Create Classifier
			Classifier nbModel = (Classifier) new NaiveBayes();
			nbModel.buildClassifier(train);
		
			//Test Classifier
			Evaluation nbTest = new Evaluation(train);
			nbTest.evaluateModel(nbModel, test);
		
			returnResults(nbTest);
		
		} catch (Exception err) {
			
			err.printStackTrace(System.out);
		
		}
		
	}
	
	/*
	*	Decision Tree Classification Algorithm
	*/
	
	public static void runJ48(Instances train, Instances test) {
		try {
		
			//Set counts
			System.out.println("Training set size: " + train.numInstances());
			System.out.println("Test set size: " + test.numInstances());
			
			train.setClassIndex(0);
			test.setClassIndex(0);
	
			//Train classifier
			Classifier j48Model = (Classifier) new J48();
			j48Model.buildClassifier(train);
		
			//Test Classifier
			Evaluation j48Test = new Evaluation(train);
			j48Test.evaluateModel(j48Model, test);
		
			returnResults(j48Test);
			
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
	*	Nearest-Neighbour Classification Algorithm
	*/
	
	public static void runKNN(Instances train, Instances test) {
		
		//Set counts
		System.out.println("Training set size: " + train.numInstances());
		System.out.println("Test set size: " + test.numInstances());
		
		//Set index
		train.setClassIndex(0);
		test.setClassIndex(0);
		
		try {
		
			//Create Classifier
			Classifier nbModel = (Classifier) new IBk(1);
			nbModel.buildClassifier(train);
		
			//Test Classifier
			Evaluation nbTest = new Evaluation(train);
			nbTest.evaluateModel(nbModel, test);
		
			returnResults(nbTest);
		
		} catch (Exception err) {
			
			err.printStackTrace(System.out);
		
		}
	
		
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