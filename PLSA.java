import java.util.List;

import weka.attributeSelection.LatentSemanticAnalysis;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.classifiers.Classifier;
import weka.filters.unsupervised.attribute.Remove;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.Evaluation;
import java.util.Random;

public class PLSA implements CustomModel {
	
	private LatentSemanticAnalysis lsa;
	private Ranker ranker;
	private StringToWordVector swv;
	private Remove rm;
	private FilteredClassifier fc;
	private int noOfClasses;
	
	public PLSA() {
	
		//LSA specific constructors
		this.lsa = new LatentSemanticAnalysis();
		this.ranker = new Ranker();
		
		//Text specific constructors
		this.swv = new StringToWordVector(); 
		this.rm = new Remove();
		this.fc = new FilteredClassifier();
		this.noOfClasses = 75;
		
	}
	
	public void runFilteredClassifier(Instances data, Instances test, Classifier classifier, String cName) {
	
		//data is what is given to the lsa evaluator.
		
		//Apply LSA evaluator options
		String[] lsaoptions = {"-A", "-1","-R","0"};
		
		//Cross validation fold and random seed
		int folds = 5;
		Random rand = new Random(1);
		
		try {
			
			for (int i = 1; i < this.noOfClasses; i++) {
		
				System.out.println("(RemoveFilter): Selected class attribute " + i);
		
				//**Remove all attribute classes not relevant this pass**
				this.rm.setAttributeIndicesArray(new int[] {0,i});
				this.rm.setInvertSelection(true);
				this.rm.setInputFormat(data);
				Instances removedData = Filter.useFilter(data, this.rm);
				
				//**Apply StringToWordVector filter**
				removedData.setClassIndex(1);
				this.fc.setFilter(this.swv);
				System.out.println("(STWFilter): Appled StringToWordVector");
				
				/*
				
				
				
				//Run LSA Stuff here?
				
				//lsa.setOptions(lsaoptions);
            	//lsa.buildEvaluator(data);
            	//ranker.search(lsa, lsa.transformedData(data));
				
				
				
				*/
				
				
				//**Build classifier on filtered data**
				this.fc.buildClassifier(removedData);
			
				//**Run Classifier**
				System.out.println("(TFIDFModel): Running evaluation of " + cName + " on TFIDF Model");
				Evaluation eval = new Evaluation(removedData);
				//eval.evaluateModel(this.fc, removedData);
				eval.crossValidateModel(this.fc, removedData, folds, rand);
				
			}
			
		} catch (Exception err) {
		
			err.printStackTrace(System.out);
		
		}
		
	
	}
	
	public void performLSA(List<ReutersDocument> collection) {

		/*String[] lsaoptions = {"-A", "-1","-R","0"};
		
		try 
        {
            //lsa.setOptions(lsaoptions);
            //lsa.buildEvaluator(collection.get(0));
            //ranker.search(lsa, lsa.transformedData(collection.get(0)));
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }*/
	}
	
	//deprecated but interface still contains it so safer to leave for now
	public Instances[] runModel(Instances data, Instances data2) { return null; }
}