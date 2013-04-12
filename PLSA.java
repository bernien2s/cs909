import java.util.List;

import weka.attributeSelection.LatentSemanticAnalysis;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class PLSA {
	
	LatentSemanticAnalysis lsa;
	Ranker ranker;
	
	public PLSA() {
	
		this.lsa = new LatentSemanticAnalysis();
		this.ranker = new Ranker();
	}
	
	public void performLSA(List<ReutersDocument> collection) {

		String[] lsaoptions = {"-A", "-1","-R","0"};
		
		try 
        {
            lsa.setOptions(lsaoptions);
            //lsa.buildEvaluator(collection.get(0));
            //ranker.search(lsa, lsa.transformedData(collection.get(0)));
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
	}
}