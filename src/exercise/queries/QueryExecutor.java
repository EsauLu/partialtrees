package exercise.queries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import exercise.bean.Axis;
import exercise.bean.Node;
import exercise.bean.NodeType;
import exercise.bean.PartialTree;
import exercise.bean.RemoteNode;
import exercise.bean.Step;

public class QueryExecutor {

    // Algorithm 1
    public static List<List<Node>> query(List<Step> steps, List<PartialTree> pts) {

        int p = pts.size();
        List<List<Node>> resultList = new ArrayList<List<Node>>();
        String string = "";
        string.hashCode();

        for (int i = 0; i < p; i++) {
            List<Node> tem = new ArrayList<>();
            tem.add(pts.get(i).getRoot());
            resultList.add(tem);
        }

        int size = steps.size();
        for (int i = 0; i < size; i++) {

            Step step = steps.get(i);
            resultList = queryWithAixs(step.getAxis(), pts, resultList, step.getNameTest());

            String predicate = step.getPredicate();
            if (predicate != null) {
                // querying predicate
            }

            System.out.println();
            System.out.println("Step" + i + " : " + step);
            print(resultList);

        }

        return resultList;
    }

    public static List<List<Node>> queryWithAixs(Axis axis, List<PartialTree> pts, List<List<Node>> inputLists, String test) {

        if (Axis.CHILD.equals(axis)) {
            return queryChid(pts, inputLists, test);
        }

        if (Axis.DESCENDANT.equals(axis)) {
            return queryDescendant(pts, inputLists, test);
        }

        if (Axis.PARENT.equals(axis)) {
            return queryParent(pts, inputLists, test);
        }

        if (Axis.FOLLOWING_SIBLING.equals(axis)) {
            return queryFollowingSibling(pts, inputLists, test);
        }

        return null;
    }

    public static List<List<Node>> queryChid(List<PartialTree> pts, List<List<Node>> inputLists, String test) {

        List<List<Node>> outputList = new ArrayList<>();

        int p = pts.size();
        for (int i = 0; i < p; i++) {

            PartialTree pt = pts.get(i);

            List<Node> result = pt.findChilds(inputLists.get(i), test);

            outputList.add(result);

        }

        return outputList;
    }

    public static List<List<Node>> queryDescendant(List<PartialTree> pts, List<List<Node>> inputLists, String test) {
        List<List<Node>> outputList = new ArrayList<>();

        int p = pts.size();
        for (int i = 0; i < p; i++) {

            PartialTree pt = pts.get(i);

            List<Node> result = pt.findDescendants(inputLists.get(i), test);

            outputList.add(result);

        }

        return outputList;
    }

    public static List<List<Node>> queryParent(List<PartialTree> pts, List<List<Node>> inputLists, String test) {

        List<List<Node>> outputList = new ArrayList<>();

        int p = pts.size();
        for (int i = 0; i < p; i++) {

            PartialTree pt = pts.get(i);

            List<Node> result = pt.findParents(inputLists.get(i), test);

            outputList.add(result);

        }

        return shareNodes(pts, outputList);

    }

    public static List<List<Node>> shareNodes(List<PartialTree> pts, List<List<Node>> nodeLists) {

        List<Node> toBeShare = new ArrayList<Node>();

        int p = pts.size();
        for (int i = 0; i < p; i++) {
            for (Node node : nodeLists.get(i)) {
                if (!NodeType.CLOSED_NODE.equals(node.getType())) {
                    toBeShare.add(node);
                }
            }
        }

        for (int i = 0; i < p; i++) {

            Set<Node> set = new HashSet<Node>();
            List<Node> inputList = nodeLists.get(i);
            PartialTree pt = pts.get(i);

            set.addAll(inputList);
            set.addAll(pt.findCorrespondingNodes(toBeShare));

            inputList.clear();
            inputList.addAll(set);

        }

        return nodeLists;
    }

    public static List<List<Node>> queryFollowingSibling(List<PartialTree> pts, List<List<Node>> inputLists, String test) {

        List<List<Node>> outputList = new ArrayList<List<Node>>();
        int p = pts.size();

        // Local query
        for (int i = 0; i < p; i++) {
            PartialTree pt = pts.get(i);
            List<Node> result = pt.findFollowingSiblings(inputLists.get(i), test);
            outputList.add(result);
        }

        // Preparing remote query
        List<RemoteNode> toBeQueried = new ArrayList<RemoteNode>();
        for (int i = 0; i < p; i++) {
            for (Node n : inputLists.get(i)) {
                Node parent = n.getParent();
                if (!NodeType.RIGHT_OPEN_NODE.equals(n.getType()) && !NodeType.PRE_NODE.equals(n.getType()) && parent != null
                        && (NodeType.RIGHT_OPEN_NODE.equals(parent.getType()) || NodeType.PRE_NODE.equals(parent.getType()))) {
                    toBeQueried.add(new RemoteNode(parent, i + 1, parent.getEnd()));
                }
            }
        }

        // Regroup nodes by partial tree id
        List<List<Integer>> uidLists = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            List<Integer> uidList = new ArrayList<Integer>();
            Set<Integer> uidSet = new HashSet<Integer>();
            for (RemoteNode rn : toBeQueried) {
                if (rn.st <= i && rn.ed >= i) {
                    uidSet.add(rn.node.getUid());
                }
            }
            uidList.addAll(uidSet);
            uidLists.add(uidList);
        }

        List<List<Node>> remoteInputList = new ArrayList<List<Node>>();
        for (int i = 0; i < p; i++) {
            PartialTree pt = pts.get(i);
            List<Node> remoteInput = pt.findNodesByUid(uidLists.get(i));
            remoteInputList.add(remoteInput);
        }

        // Remote query
        List<List<Node>> remoteOutputList = queryChid(pts, remoteInputList, test);

        // Merge results of local query and remote query
        for (int i = 0; i < p; i++) {
            List<Node> result = outputList.get(i);
            List<Node> remoteResult = remoteOutputList.get(i);

            Set<Node> set = new HashSet<Node>();
            set.addAll(result);
            set.addAll(remoteResult);

            result.clear();
            result.addAll(set);
        }

        return outputList;

    }

    public static void print(List<List<Node>> results) {

        System.out.println();
        int p = results.size();
        for (int j = 0; j < p; j++) {
            List<Node> result = results.get(j);
            System.out.print("  pt" + j + " : ");

            for (Node node : result) {
                System.out.print(node);
            }

            System.out.println();
        }
        System.out.println();
        System.out.println("---------------------------------------------------------------------");

    }

}
