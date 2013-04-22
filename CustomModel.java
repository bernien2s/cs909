import weka.core.Instances;

interface CustomModel {
	
	Instances[] runModel(Instances data, Instances data2); 
	void runFilteredClassifier(Instances data, String classifier);

}