package exercise.bean;

import exercise.queries.Link;

public class PNode {
    
    private Node node;
    
    private Link link;

    public PNode() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "(" + node + ", " + link + ")";
    }   

}
