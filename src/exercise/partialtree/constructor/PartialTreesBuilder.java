package exercise.partialtree.constructor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import exercise.bean.Node;
import exercise.bean.NodeType;
import exercise.bean.PartialTree;
import exercise.bean.Tag;
import exercise.bean.TagType;
import exercise.partialtree.factory.NodeFactory;

public class PartialTreesBuilder {
		
	private static PartialTreesBuilder instance=new PartialTreesBuilder();
	
	private PartialTreesBuilder() {
		
	}
    
	public static PartialTreesBuilder getInstance(){
		
		if(instance==null) {
			instance=new PartialTreesBuilder();
		}
		
		return instance;
		
	}
	
	public List<PartialTree> createPartialTreesByXML(String xmlDoc, int treeNum){
		return createPartialTreesByXML(xmlDoc, getPos(xmlDoc, treeNum));
	}
	
	public List<PartialTree> createPartialTreesByXML(String xmlDoc, int[] pos){
		
		List<List<Tag>> chunkList=getChunksByXML(xmlDoc, fixPos(xmlDoc, pos));
		
		List<List<Node>> subTreeLists=buildSubTrees(chunkList);
		
		List<Node> roots=getPrePath(subTreeLists);
		
		List<PartialTree> pts=computeRanges(roots);
		
		return pts;
		
	}
	
	private int[] getPos(String xmlDoc, int chunkNum) {

		int[] pos=new int[chunkNum+1];
		int t=xmlDoc.length()/chunkNum+1;
		for(int i=0; i<chunkNum; i++) {
			pos[i]=i*t;
		}
		pos[chunkNum]=xmlDoc.length();		
		return fixPos(xmlDoc, pos);
	}
	
	private int[] fixPos(String xmlDoc, int[] pos) {
		long len=xmlDoc.length();
		for(int i=0; i<pos.length; i++) {		
			if(pos[i]>=len) {
				pos[i]=(int)len;
				continue;
			}
			while(xmlDoc.charAt(pos[i])!='<') {
				pos[i]--;
			}
		}
		return pos;
	}
	
	private List<PartialTree> computeRanges(List<Node> roots){
		
		List<Map<Integer, Node>> nodeMapList=new ArrayList<>();
		List<PartialTree> pts=new ArrayList<>();

		int p=roots.size();
		for(int i=0;i<p;i++) {
			PartialTree pt=new PartialTree();
			pt.setPid(i);
			pt.setRoot(roots.get(i));
			nodeMapList.add(pt.getNodeMap());
			pts.add(pt);
		}
		
		for(int st=0; st<p; st++) {
			Map<Integer, Node> map=nodeMapList.get(st);
			for(Integer uid: map.keySet()) {
			    Node node = map.get(uid);
			    if(NodeType.RIGHT_OPEN_NODE.equals(node.getType())) {
			    	List<Node> crpNodes=new ArrayList<>();
			    	crpNodes.add(node);
                    int en=st+1;
                    while(en<p) {
                    	Node crpNode=nodeMapList.get(en).get(uid);
                    	crpNodes.add(crpNode);
                    	if(NodeType.LEFT_OPEN_NODE.equals(crpNode.getType())) {
                    		break;
                    	}
                    	en++;
                    }
                    for(int k=0;k<crpNodes.size();k++) {
                    	Node crpNode=crpNodes.get(k);
                    	crpNode.setStart(st);
                    	crpNode.setEnd(en);
                    }
			    }
			}
		}

		return pts;
	}
	
	
	
	private List<Node> getPrePath(List<List<Node>> sts){

		List<List<Node>> lls=new ArrayList<>();
		List<List<Node>> rls=new ArrayList<>();
		
		// open nodes in LLS or RLS are arranged in top-bottom order
		int p=sts.size();
		for(int i=0; i<p; i++) {
			lls.add(selectLeftOpenNode(sts.get(i)));
			rls.add(selectRightOpenNode(sts.get(i)));
		}
				
		// Prepath-computation and collecting matching nodes
		ArrayList<Node> auxList=new ArrayList<Node>();
		List<List<Node>> pps=new ArrayList<List<Node>>();
		pps.add(new ArrayList<Node>());

		for(int i=0;i<p-1;i++) {
			
			List<Node> rl=rls.get(i);
			for(int j=0;j<rl.size();j++) {
				auxList.add(rl.get(j));
			}
			
			int size=auxList.size()-lls.get(i+1).size();
			for(int j=auxList.size()-1; j>=size; j--) {
				auxList.remove(j);
			}
			
			List<Node> pp=new ArrayList<Node>();
			for(int j=0; j<auxList.size(); j++) {
				Node node=auxList.get(j);
				Node prenode=NodeFactory.createNode(node.getTagName(), NodeType.PRE_NODE, node.getUid());
				pp.add(prenode);
			}
			pps.add(pp);
			
		}
		
		// add pre-nodes to substrees
		List<Node> pts=new ArrayList<Node>();
		for(int i=0; i<p; i++) {
			List<Node> pp=pps.get(i);
			if(pp.size()>0) {
				int j=0;
				for(j=0; j<pp.size()-1; j++) {
					pp.get(j).getChildList().add(pp.get(j+1));
				}
				List<Node> subtrees=sts.get(i);
				List<Node> childList=pp.get(j).getChildList();
				for(j=0; j<subtrees.size(); j++) {
					childList.add(subtrees.get(j));
				}
				pts.add(pp.get(0));
			}else {
				pts.add(sts.get(i).get(0));
			}
		}
		
		return pts;
	}
	
	private List<Node> selectLeftOpenNode(List<Node> pt){
		
		Node p=pt.get(0);
		
		List<Node> ll=new ArrayList<>();
		
		while(p!=null&&NodeType.LEFT_OPEN_NODE.equals(p.getType())) {
			ll.add(p);
			List<Node> childList=p.getChildList();
			if(childList.size()>0) {
				p=p.getChildList().get(0);
			}else {
				p=null;
			}
		}
		
		return ll;
	}
	
	private List<Node> selectRightOpenNode(List<Node> pt){	
		
		Node p=pt.get(pt.size()-1);
		
		List<Node> rl=new ArrayList<>();
		
		while(p!=null&&NodeType.RIGHT_OPEN_NODE.equals(p.getType())) {
			rl.add(p);
			List<Node> childList=p.getChildList();
			if(childList.size()>0) {
				p=childList.get(childList.size()-1);
			}else {
				p=null;
			}
		}
		
		return rl;
	}
	
	private List<List<Node>> buildSubTrees( List<List<Tag>> chunkList ){
		
		List<List<Node>> subTreeLists=new ArrayList<List<Node>>();
		for(int i=0;i<chunkList.size();i++) {
			List<Node> subTrees = buildTreesByTags(chunkList.get(i));
			subTreeLists.add(subTrees);
		}
		
		
		return subTreeLists;
	}
	
	private List<Node> buildTreesByTags(List<Tag> chunk) {
		
		Deque<Node> stack = new ArrayDeque<Node>();
		
		stack.push(NodeFactory.createNode("\0", NodeType.CLOSED_NODE, 0));
		
		for(int i=0;i<chunk.size();i++) {
			
			Tag tag = chunk.get(i);
			
			if(TagType.START.equals(tag.getType())) {
				
				Node node=NodeFactory.createNode(tag.getName(), NodeType.CLOSED_NODE, tag.getTid());
				stack.push(node);
				
			}else {
				
				Node node=stack.peek();
				
				if(node.getTagName().equals(tag.getName())) {
					stack.pop();
					stack.peek().getChildList().add(node);
				}else {
					Node temNode=NodeFactory.createNode(tag.getName(), NodeType.LEFT_OPEN_NODE, tag.getTid());
					temNode.getChildList().addAll(node.getChildList());
				    node.setChildList(new ArrayList<>());
				    node.getChildList().add(temNode);
				}
				
			}
		}
		
		while(stack.size()>1) {
			Node node=stack.pop();
			node.setType(NodeType.RIGHT_OPEN_NODE);
			stack.peek().getChildList().add(node);
		}
		
		return stack.pop().getChildList();
	}
	
	private List<List<Tag>> getChunksByXML(String xmlDoc, int[] pos){

		List<List<Tag>> chunkList=new ArrayList<>();
		int chunkNum=pos.length-1;
		Deque<Tag> allTags=new ArrayDeque<>();
		for(int i=0;i<chunkNum;i++) {
			List<Tag> list = getTagsByXML( xmlDoc.substring(pos[i], pos[i+1]) );
            chunkList.add( list );
            for(int j=0; j<list.size(); j++) {
            	    allTags.addLast(list.get(j));
            }
		}

		Tag startRoot=new Tag("Root",TagType.START);
		List<Tag> tem=chunkList.get(0);
		if(tem!=null) {
			startRoot.setTid(Integer.MIN_VALUE);
			tem.add(0, startRoot);
		}
		
		Tag endRoot=new Tag("Root",TagType.END);
		tem=chunkList.get(chunkList.size()-1);
		if(tem!=null) {
			endRoot.setTid(Integer.MIN_VALUE);
			tem.add(endRoot);
		}

		Deque<Tag> stack=new ArrayDeque<>();
		int tid=0;
		while(!allTags.isEmpty()) {
			Tag tag=allTags.removeFirst();
			if(TagType.START.equals(tag.getType())) {
				tag.setTid(tid++);
				stack.push(tag);
			}else {
				tag.setTid(stack.pop().getTid());
			}
		}
		
		return chunkList;
	}

	private List<Tag> getTagsByXML(String chunkStr){
		
		List<Tag> tagList=new ArrayList<Tag>();

		String tem=chunkStr.substring(1, chunkStr.length()-1);
		String[] tagsArr=tem.split("><");
		
		for(int i=0; i<tagsArr.length; i++) {
			
			String s=tagsArr[i].trim();
			Tag tag=new Tag();
			
			if(s.contains("/")) {
				tag.setName(s.substring(s.indexOf('/')+1).trim());
				tag.setType(TagType.END);
			}else {
				tag.setName(s);
				tag.setType(TagType.START);
			}
			
			tagList.add(tag);
			
		}
		
		return tagList;
		
	}
	
	
}


