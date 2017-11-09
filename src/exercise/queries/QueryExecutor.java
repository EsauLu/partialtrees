package exercise.queries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exercise.bean.Axis;
import exercise.bean.Node;
import exercise.bean.PartialTree;
import exercise.bean.Step;

public class QueryExecutor {
	
	
	//Algorithm 1
	public static List<List<Node>> query(List<Step> steps, List<PartialTree> pts){

		int p=pts.size();
		List<List<Node>> resultList=new ArrayList<>();
		
		for(int i=0;i<p;i++) {
			List<Node> tem = new ArrayList<>();
			tem.add(pts.get(i).getRoot());
			resultList.add(tem);
		}

		int size=steps.size();
		for(int i=0;i<size;i++) {
			List<List<Node>> inputLists=resultList;
			resultList=new ArrayList<>();
			Step step = steps.get(i);
			for(int j=0;j<p;j++) {
				
				List<Node> result = new ArrayList<>();
				result.addAll( queryByAixs( pts.get(j), inputLists.get(j), step ) );
				
				String predicate = step.getPredicate();
				if(predicate!=null) {
					// querying predicate
				}
				
				resultList.add(result);
			}
		}
		
		return resultList;
	}
	
	public static List<Node> queryByAixs(PartialTree pt, List<Node> inputList, Step step){
		Axis axis=step.getAxis();
		
		if(Axis.CHILD.equals(axis)) {
			return queryChid(pt, inputList, step.getNameTest());
		}
		
		return null;
	}
	
	public static List<Node> queryChid(PartialTree pt, List<Node> inputList, String test){
	    List<Node> outputList=new ArrayList<>();
	    
	    Map<Integer, Node> nodeMap=pt.getNodeMap();
	    for(int i=0;i<inputList.size();i++) {
	    	    Node inputNode = inputList.get(i);
	    	    Node originNode = nodeMap.get(inputNode.getUid());
	    	    for(Node ch: originNode.getChildList()) {
	    	    	    if(ch.getTagName().equals(test)) {
	    	    	    	    outputList.add(ch);
	    	    	    }
	    	    }
	    }
	    
		return outputList;
	}

}














































