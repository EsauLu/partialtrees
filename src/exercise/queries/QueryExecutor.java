package exercise.queries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exercise.bean.Axis;
import exercise.bean.Node;
import exercise.bean.NodeType;
import exercise.bean.PartialTree;
import exercise.bean.Step;

public class QueryExecutor {
	
	
	//Algorithm 1
	public static List<List<Node>> query(List<Step> steps, List<PartialTree> pts){

		int p=pts.size();
		List<List<Node>> resultList=new ArrayList<List<Node>>();
		String string="";
		string.hashCode();
		for(int i=0;i<p;i++) {
			List<Node> tem = new ArrayList<>();
			tem.add(pts.get(i).getRoot());
			resultList.add(tem);
		}

		int size=steps.size();
		for(int i=0;i<size;i++) {
			
			Step step = steps.get(i);
			resultList = queryWithAixs(step.getAxis(), pts, resultList, step.getNameTest() );
			
			String predicate = step.getPredicate();
			if(predicate!=null) {
				// querying predicate
			}
			
			System.out.println();
			System.out.println("Step"+i+" : "+step);
			System.out.println();
			for(int j=0; j<p; j++) {
				List<Node> result=resultList.get(j);
				System.out.print("  pt"+j+" : ");
				
				for(Node node: result) {
					System.out.print(node);
				}

				System.out.println();
			}			
			System.out.println();
			System.out.println("---------------------------------------------------------------------");
			
		}
		
		return resultList;
	}
	
	public static List<List<Node>> queryWithAixs(Axis axis, List<PartialTree> pts, List<List<Node>> inputLists, String test){
		
		if(Axis.CHILD.equals(axis)) {
			return queryChid(pts, inputLists, test);
		}
		
		if(Axis.DESCENDANT.equals(axis)) {
			return queryDescendant(pts, inputLists, test);
		}
		
		if(Axis.PARENT.equals(axis)) {
            return queryParent(pts, inputLists, test);
		}
		
		return null;
	}
	
	public static List<List<Node>> queryChid(List<PartialTree> pts, List<List<Node>> inputLists, String test){
		List<List<Node>> outputList=new ArrayList<>();
		
		int p=pts.size();
		for(int i=0;i<p;i++) {
			
			PartialTree pt=pts.get(i);
			
			List<Node> result = pt.findChilds(inputLists.get(i), test);
			
			outputList.add(result);
			
		}
	    
		return outputList;
	}
	
	public static List<List<Node>> queryDescendant(List<PartialTree> pts, List<List<Node>> inputLists, String test){
		List<List<Node>> outputList=new ArrayList<>();
		
		int p=pts.size();
		for(int i=0;i<p;i++) {
			
			PartialTree pt=pts.get(i);
			
			List<Node> result = pt.findDescendants(inputLists.get(i), test);
			
			outputList.add(result);
			
		}
	    
		return outputList;		
	}
	
	public static List<List<Node>> queryParent(List<PartialTree> pts, List<List<Node>> inputLists, String test) {

		List<List<Node>> outputList=new ArrayList<>();
		
		int p=pts.size();
		for(int i=0;i<p;i++) {
			
			PartialTree pt=pts.get(i);
			
			List<Node> result = pt.findParents(inputLists.get(i), test);
			
			outputList.add(result);
			
		}
	    
		return outputList;		
		
	}
	
	public List<List<Node>> shareNodes(List<PartialTree> pts, List<List<Node>> nodeLists){
		
		List<Node> toBeShare=new ArrayList<Node>();
		
		int p=pts.size();
		for(int i=0;i<p;i++) {
			for(Node node: nodeLists.get(i)) {
				if(!NodeType.CLOSED_NODE.equals(node.getType())) {
					toBeShare.add(node);
					
				}
			}
		}
		
		return null;
	}

}














































