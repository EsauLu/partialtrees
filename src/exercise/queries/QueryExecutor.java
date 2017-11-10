package exercise.queries;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
				result.addAll( queryWithAixs( pts.get(j), inputLists.get(j), step ) );
				
				String predicate = step.getPredicate();
				if(predicate!=null) {
					// querying predicate
				}
				
				resultList.add(result);
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
	
	public static List<Node> queryWithAixs(PartialTree pt, List<Node> inputList, Step step){
		Axis axis=step.getAxis();
		
		if(Axis.CHILD.equals(axis)) {
			return queryChid(pt, inputList, step.getNameTest());
		}
		
		if(Axis.DESCENDANT.equals(axis)) {
			return queryDescendant(pt, inputList, step.getNameTest());
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
	
	public static List<Node> queryDescendant(PartialTree pt, List<Node> inputList, String test){

	    List<Node> outputList=new ArrayList<>();
		setIsChecked(pt, false);

		Map<Integer, Node> nodeMap=pt.getNodeMap();
		for (int i = 0; i < inputList.size(); i++) {
			
			Node node=nodeMap.get(inputList.get(i).getUid());
			if(node==null) {
				continue;
			}
			
		    Deque<Node> stack=new ArrayDeque<>();
			stack.push(node);
			
			while(!stack.isEmpty()) {
				Node nt=stack.pop();
				if (nt.isChecked()) {
					continue;
				}
				nt.setChecked(true);
				List<Node> childList = nt.getChildList();
				for(int j=0; j<childList.size(); j++) {
					Node ch=childList.get(j);
					if(ch.getTagName().equals(test)) {
						outputList.add(ch);
						stack.push(ch);
					}
				}
			}
			
		}
		
		return outputList;
	}
	
	private static void setIsChecked(PartialTree pt, boolean isChecked) {
		
		Map<Integer, Node> nodeMap=pt.getNodeMap();
		for(Integer uid: nodeMap.keySet()) {
			Node node = nodeMap.get(uid);
			node.setChecked(isChecked);
		}
		
	}
	
	public static List<Node> queryParent() {
		return null;
	}
	
	public List<Node> shareNodes(PartialTree pt, List<Node> nodeList){
		return null;
	}

}














































