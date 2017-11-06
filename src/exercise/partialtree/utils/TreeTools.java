package exercise.partialtree.utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

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
				System.out.print(s+" ");
			}
			
			for(Node nd: node.getChildList()) {
				que.addLast(nd);
			}
			
		}
		
		System.out.println();
		
	}	
	
	public static void bfsWithRanges(Node root) {
		
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
	
	public static void bfsWithDepth(Node root) {
		
		if(root==null) {
			return;
		}
		
		Deque<Node> que=new ArrayDeque<>();
		que.addLast(root);
		
		while(!que.isEmpty()) {
			
			Node node=que.removeFirst();

			String s=node.toString();
			s=s.substring(0,s.length()-1);
			System.out.print(s+"("+node.getDepth()+") ");
			
			for(Node nd: node.getChildList()) {
				que.addLast(nd);
			}
			
		}
		
		System.out.println();
		
	}
	
	public static void dfsWithDepth(Node root) {
		
		Deque<Node> stack=new ArrayDeque<Node>();
		
		stack.push(root);
		
		while(!stack.isEmpty()) {
			Node node=stack.pop();

			String s=node.toString();
			s=s.substring(0,s.length()-1);
			System.out.print(s+"("+node.getDepth()+") ");
			
			List<Node> childList=node.getChildList();
			for(int i=childList.size()-1;i>=0;i--) {
				stack.push(childList.get(i));
			}
			
		}
		System.out.println();
		
	}

	
	public static void dfs(Node root) {
		
		Deque<Node> stack=new ArrayDeque<Node>();
		
		stack.push(root);
		
		while(!stack.isEmpty()) {
			Node node=stack.pop();

			String s=node.toString();
			s=s.substring(0,s.length()-1);
			System.out.print(s+" ");
			
			List<Node> childList=node.getChildList();
			for(int i=childList.size()-1;i>=0;i--) {
				stack.push(childList.get(i));
			}
			
		}
		System.out.println();
		
	}

}






























