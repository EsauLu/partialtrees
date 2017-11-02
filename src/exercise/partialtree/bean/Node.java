package exercise.partialtree.bean;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private int uid;
	
	private String tagName;
	
	private NodeType type;
	
	private Node parent;
	
	private Node presib;
	
	private Node flosib;
	
	private List<Node> childList;

	public Node() {
		super();
		// TODO Auto-generated constructor stub
		
		this.type=NodeType.CLOSED_NODE;
		this.childList=new ArrayList<Node>();
				
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getPresib() {
		return presib;
	}

	public void setPresib(Node presib) {
		this.presib = presib;
	}

	public Node getFlosib() {
		return flosib;
	}

	public void setFlosib(Node flosib) {
		this.flosib = flosib;
	}

	public List<Node> getChildList() {
		return childList;
	}

	public void setChildList(List<Node> childList) {
		this.childList = childList;
	}

	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	

}
