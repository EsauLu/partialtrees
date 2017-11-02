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
		
		List<List<Node>> subTreeList=buildSubTrees(chunkList);
		
		return null;
	}
	
	private List<List<Node>> buildSubTrees( List<List<Tag>> chunkList ){
		
		for(int i=0;i<chunkList.size();i++) {
			buildTreeByTags(chunkList.get(i));
		}
		
		return null;
	}
	
	private Node buildTreeByTags(List<Tag> tagList) {
		
		Deque<Tag> stack = new ArrayDeque<Tag>();
		
		Deque<Node> nodeList=new ArrayDeque<Node>();
		
		int uid=0;
		
		for(int i=0;i<tagList.size();i++) {
			Tag tag=tagList.get(i);
			if(tag.getType().equals(TagType.START)) {
				stack.push(tag);
			}else {
				Tag tag2=stack.peek();
				if(tag2!=null && tag2.getName().equals(tag.getName())) {
					stack.pop();
					Node node=NodeFactory.createNode(tag.getName(), NodeType.CLOSED_NODE, uid++);
					nodeList.addLast(node);
				}else {
					Node node=NodeFactory.createNode(tag.getName(), NodeType.LEFT_OPEN_NODE, uid++);
					nodeList.addLast(node);
				}
			}
		}
		
		while(!stack.isEmpty()) {
			Tag tag=stack.pop();
			Node node=NodeFactory.createNode(tag.getName(), NodeType.RIGHT_OPEN_NODE, uid++);
			nodeList.addLast(node);
		}
		
		while(!nodeList.isEmpty()) {
			Node node=nodeList.removeFirst();
			if(node.getType().equals(NodeType.CLOSED_NODE)) {
				System.out.print(node.getTagName()+node.getUid()+"  ");
			}
			if(node.getType().equals(NodeType.LEFT_OPEN_NODE)) {
				System.out.print("+"+node.getTagName()+node.getUid()+"  ");
			}
			if(node.getType().equals(NodeType.RIGHT_OPEN_NODE)) {
				System.out.print(node.getTagName()+node.getUid()+"+  ");
			}
		}
		
		System.out.println();
		
		return null;
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
		
//		String[] chunks=new String[chunkNum];
//		for(int i=0;i<chunkNum;i++) {
//			chunks[i]=xmlDoc.substring(pos[i], pos[i+1]);
//			System.out.println("chunks"+i+" : "+chunks[i]);
//		}
		
		for(int i=0;i<chunkNum;i++) {
//			System.out.print("chunks"+i+" : ");
            chunkList.add( getTagsByXML( xmlDoc.substring(pos[i], pos[i+1]) ) );
		}
		
		
		for(List<Tag> list: chunkList) {
			for(Tag tag: list) {
				if(tag.getType().equals(TagType.START)) {
					System.out.print("  <"+tag.getName()+">");
				}else {
					System.out.print("  </"+tag.getName()+">");
				}
			}
			System.out.println();
		}
		
		
		return chunkList;
	}
	
	private List<Tag> getTagsByXML(String chunkStr){

//		System.out.println(chunkStr);
		
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
	
	
	private List<List<Tag>> _getChunksByXML(String xmlDoc, int chunkNum){
		
		String tem=xmlDoc.substring(1, xmlDoc.length()-1);
		
		String[] tagsArr=tem.split("><");
		
//		for(String)
		
		List<List<Tag>> chunkList=new ArrayList<List<Tag>>();

		for(int i=0;i<chunkNum;i++) {
			chunkList.add(new ArrayList<Tag>());
		}
		
		int tagNum=(int)Math.ceil((double)tagsArr.length/chunkNum);
		for(int i=0;i<tagNum;i++) {		
			for(int j=0;j<chunkNum;j++) {
				if(i+j*tagNum>=tagsArr.length) {
					break;
				}
				String s=tagsArr[i+j*tagNum].trim();
				Tag tag=new Tag();
				if(s.contains("/")) {
					tag.setName(s.substring(s.indexOf('/')).trim());
					tag.setType(TagType.START);
				}else {
					tag.setName(s);
					tag.setType(TagType.START);
				}
				chunkList.get(j).add(tag);
			}			
		}
		
		for(List<Tag> list: chunkList) {
			for(Tag tag: list) {
				if(tag.getType().equals(TagType.START)) {
					System.out.print("  <"+tag.getName()+">");
				}else {
					System.out.print("  /<"+tag.getName()+">");
				}
			}
			System.out.println();
		}
		
		return null;
	}
	
}


/*
 
 <A><B><C><E></E></C><D></D></B><E></E><B><B><D><E></E><D/><C><C/></B><C><E><E/></C><D><E></E></D></B><E><D></D></E><B><D></C><D></C></B><B></B></A>
 
 */
