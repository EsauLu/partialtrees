package exercise.bean;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class PartialTree {
	
	private int pid;
	private Map<Integer, Node> nodeMap;
	private Node root;
	
	public PartialTree() {
		super();
		// TODO Auto-generated constructor stub
		nodeMap=new HashMap<>();
	}
	public Map<Integer, Node> getNodeMap() {
		return nodeMap;
	}
	public void setNodeMap(Map<Integer, Node> nodeMap) {
		this.nodeMap = nodeMap;
	}
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
		bfs();
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public Node findNodeByUid(int uid) {
		Node target=nodeMap.get(uid);
		return target;
	}
	
	private void bfs() {		
		
		if(root==null) {
			return;
		}
		root.setDepth(0);
		
		Deque<Node> que=new ArrayDeque<>();
		que.addLast(root);
		
		while(!que.isEmpty()) {
			
			Node node=que.removeFirst();
			
			if(!nodeMap.containsKey(node.getUid())) {
				nodeMap.put(node.getUid(), node);
			}
			
			Node presib=null;
			for(Node nd: node.getChildList()) {
				que.addLast(nd);
				nd.setDepth(node.getDepth()+1);
				nd.setParent(node);
				if(presib!=null) {
					nd.setPresib(presib);
					presib.setFlosib(nd);
				}
				presib=nd;
			}
			
		}
		
	}

}
