package exercise.partialtree.factory;

import exercise.partialtree.bean.Node;
import exercise.partialtree.bean.NodeType;

public class NodeFactory {
	
	public static Node createNode(String tagName, NodeType type, int uid) {
		Node node = new Node();
		node.setTagName(tagName);
		node.setType(type);
		node.setUid(uid);
		node.setStart(uid);
		node.setEnd(uid);
		return node;
	}

}
