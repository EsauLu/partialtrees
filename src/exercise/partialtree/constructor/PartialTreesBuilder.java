package exercise.partialtree.constructor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import exercise.partialtree.bean.Node;
import exercise.partialtree.bean.NodeType;
import exercise.partialtree.bean.Tag;
import exercise.partialtree.bean.TagType;
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
	
	public List<Node> createPartialTreesByXML(String xmlDoc, int treeNum){
		
		List<List<Tag>> chunkList=getChunksByXML(xmlDoc, treeNum);
		
		List<List<Node>> subTreeLists=buildSubTrees(chunkList);
		
		return null;
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
		
		Deque<Node> auxList=new ArrayDeque<>();
		
		for(int i=0;i<p-1;i++) {
			List<Node> rl=rls.get(i);
			for(int j=0;j<rl.size();j++) {
				
			}
		}
		
		
		return null;
	}
	
	private List<Node> selectLeftOpenNode(List<Node> pt){
		
		Node p=pt.get(0);
		
		List<Node> ll=new ArrayList<>();
		
		while(p!=null&&NodeType.LEFT_OPEN_NODE.equals(p.getType())) {
			ll.add(p);
			System.out.print(" +"+p.getTagName()+p.getUid()+" ");
			p=p.getChildList().get(0);
		}
		
		return ll;
	}
	
	private List<Node> selectRightOpenNode(List<Node> pt){	
		
		Node p=pt.get(pt.size()-1);
		
		List<Node> rl=new ArrayList<>();
		
		while(p!=null&&NodeType.RIGHT_OPEN_NODE.equals(p.getType())) {
			rl.add(p);
			System.out.print(" +"+p.getTagName()+p.getUid()+" ");
			List<Node> childList=p.getChildList();
			p=childList.get(childList.size()-1);
		}
		
		return rl;
	}
	
	private List<List<Node>> buildSubTrees( List<List<Tag>> chunkList ){
		
		List<List<Node>> subTreeLists=new ArrayList<List<Node>>();
		for(int i=0;i<chunkList.size();i++) {
			List<Node> subTrees = buildTreesByTags(chunkList.get(i));
			subTreeLists.add(subTrees);
			System.out.println("pt "+i+" : ");
			for(Node root: subTrees) {
				bfs(root);
			}
			System.out.println();
		}
		
		return null;
	}
	
	private void bfs(Node root) {
		
		if(root==null) {
			return;
		}
		
		Deque<Node> que=new ArrayDeque<>();
		que.addLast(root);
		
		while(!que.isEmpty()) {
			
			Node node=que.removeFirst();
			
			if(NodeType.LEFT_OPEN_NODE.equals(node.getType())) {
				System.out.print(" +"+node.getTagName()+node.getUid()+" ");
			}else if(NodeType.RIGHT_OPEN_NODE.equals(node.getType())) {
				System.out.print(" "+node.getTagName()+node.getUid()+"+ ");
			}else if(NodeType.PRE_NODE.equals(node.getType())) {
				System.out.print(" +"+node.getTagName()+node.getUid()+"+ ");
			}else {
				System.out.print(" "+node.getTagName()+node.getUid()+" ");
			}
			
			for(Node nd: node.getChildList()) {
				que.addLast(nd);
			}
			
		}
		
		System.out.println();
		
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

	private List<List<Tag>> getChunksByXML(String xmlDoc, int chunkNum){
		
		List<List<Tag>> chunkList=new ArrayList<>();
		
		int[] pos=new int[chunkNum+1];
		int t=xmlDoc.length()/chunkNum+1;
		for(int i=0; i<chunkNum; i++) {
			pos[i]=i*t;
			while(xmlDoc.charAt(pos[i])!='<') {
				pos[i]--;
			}
		}
		pos[chunkNum]=xmlDoc.length();
		
		Deque<Tag> allTags=new ArrayDeque<>();
		for(int i=0;i<chunkNum;i++) {
			List<Tag> list = getTagsByXML( xmlDoc.substring(pos[i], pos[i+1]) );
            chunkList.add( list );
            for(int j=0; j<list.size(); j++) {
            	    allTags.addLast(list.get(j));
            }
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
		
		
		for(int i=0;i<chunkNum;i++) {
			System.out.print("Chunk"+i+" : ");
			List<Tag> list = chunkList.get(i);
			for(int j=0; j<list.size(); j++) {
				Tag tag = list.get(j);
				if(tag.getType().equals(TagType.START)) {
					System.out.print("  <"+tag.getName()+">");
				}else {
					System.out.print("  </"+tag.getName()+">");
				}
			}
			System.out.println();
		}
		System.out.println();
		
		
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


/*
 
 <A><B><C><E></E></C><D></D></B><E></E><B><B><D><E></E><D/><C><C/></B><C><E><E/></C><D><E></E></D></B><E><D></D></E><B><D></C><D></C></B><B></B></A>
 
 */
