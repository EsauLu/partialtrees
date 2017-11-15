package exercise.bean;

public class RemoteNode{
	public Node node;
	public int st;
	public int ed;
	
	public RemoteNode(Node node, int st, int ed) {
		super();
		this.node = node;
		this.st = st;
		this.ed = ed;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " ["+node+", "+st+", "+ed+"] ";
	}
}