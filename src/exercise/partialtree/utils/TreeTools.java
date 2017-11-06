package exercise.partialtree.utils;

import java.util.ArrayDeque;
import java.util.Deque;

import exercise.partialtree.bean.Node;
import exercise.partialtree.bean.NodeType;

public class TreeTools {
		
	public static void bfs(Node root) {
		
		if(root==null) {
			return;
		}
		
		Deque<Node> que=new ArrayDeque<>();
		que.addLast(root);
		
		while(!que.isEmpty()) {
			
			Node node=que.removeFirst();
			
			if(NodeType.CLOSED_NODE.equals(node.getType())) {
				System.out.print(node);
			}else {
				String s=node.toString();
				s=s.substring(0,s.length()-1);
				System.out.print(s+"("+node.getStart()+", "+node.getEnd()+") ");
			}
			
			for(Node nd: node.getChildList()) {
				que.addLast(nd);
			}
			
		}
		
		System.out.println();
		
	}

}
