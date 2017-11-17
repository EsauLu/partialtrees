package exercise.bean;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartialTree {

    private int pid;
    private Map<Integer, Node> nodeMap;
    private Node root;

    public PartialTree() {
        super();
        // TODO Auto-generated constructor stub
        nodeMap = new HashMap<>();
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
        Node target = nodeMap.get(uid);
        return target;
    }

    public List<Node> findChilds(List<Node> inputList, String test) {

        List<Node> outputList = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++) {
            Node inputNode = inputList.get(i);
            Node originNode = nodeMap.get(inputNode.getUid());
            for (Node ch : originNode.getChildList()) {
                if (ch.getTagName().equals(test)) {
                    outputList.add(ch);
                }
            }
        }

        return outputList;

    }

    public List<Node> findDescendants(List<Node> inputList, String test) {

        List<Node> outputList = new ArrayList<>();
        setIsChecked(false);

        for (int i = 0; i < inputList.size(); i++) {

            Node node = nodeMap.get(inputList.get(i).getUid());
            if (node == null) {
                continue;
            }

            Deque<Node> stack = new ArrayDeque<>();
            stack.push(node);

            while (!stack.isEmpty()) {
                Node nt = stack.pop();
                if (nt.isChecked()) {
                    continue;
                }
                nt.setChecked(true);
                List<Node> childList = nt.getChildList();
                for (int j = 0; j < childList.size(); j++) {
                    Node ch = childList.get(j);
                    if (ch.getTagName().equals(test)) {
                        outputList.add(ch);
                    }
                    stack.push(ch);
                }
            }

        }

        return outputList;
    }

    public List<Node> findParents(List<Node> inputList, String test) {

        List<Node> outputList = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++) {
            Node node = nodeMap.get(inputList.get(i).getUid());
            if (node == null) {
                continue;
            }

            Node parent = node.getParent();
            if (parent != null) {
                outputList.add(parent);
            }

        }

        return outputList;
    }

    public List<Node> findCorrespondingNodes(List<Node> inputList) {
        List<Node> outputList = new ArrayList<Node>();

        for (int i = 0; i < inputList.size(); i++) {
            Node node = nodeMap.get(inputList.get(i).getUid());
            if (node != null) {
                outputList.add(node);
            }
        }

        return outputList;

    }

    public List<Node> findFollowingSiblings(List<Node> inputList, String test) {

        List<Node> outputList = new ArrayList<Node>();

        setIsChecked(false);

        for (int i = 0; i < inputList.size(); i++) {

            Node node = inputList.get(i);

            while (!node.isChecked() && node.getFlosib() != null) {

                node.setChecked(true);
                node = node.getFlosib();

                if (node.getTagName().equals(test)) {
                    outputList.add(node);
                }

            }

        }

        return outputList;

    }

    public List<Node> findNodesByUid(List<Integer> uids) {

        List<Node> outputList = new ArrayList<Node>();

        for (int i = 0; i < uids.size(); i++) {
            Node node = nodeMap.get(uids.get(i));
            if (node != null) {
                outputList.add(node);
            }
        }

        return outputList;

    }

    private void bfs() {

        if (root == null) {
            return;
        }
        root.setDepth(0);

        Deque<Node> que = new ArrayDeque<>();
        que.addLast(root);

        while (!que.isEmpty()) {

            Node node = que.removeFirst();

            if (!nodeMap.containsKey(node.getUid())) {
                nodeMap.put(node.getUid(), node);
            }

            Node presib = null;
            for (Node nd : node.getChildList()) {
                que.addLast(nd);
                nd.setDepth(node.getDepth() + 1);
                nd.setParent(node);
                if (presib != null) {
                    nd.setPresib(presib);
                    presib.setFlosib(nd);
                }
                presib = nd;
            }

        }

    }

    private void setIsChecked(boolean isChecked) {

        for (Integer uid : nodeMap.keySet()) {
            Node node = nodeMap.get(uid);
            node.setChecked(isChecked);
        }

    }

}
