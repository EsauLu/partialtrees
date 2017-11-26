package exercise.queries;

import java.util.ArrayList;
import java.util.List;

import exercise.bean.Axis;
import exercise.bean.Node;
import exercise.bean.PNode;
import exercise.bean.PartialTree;
import exercise.bean.Step;
import exercise.utils.Utils;

public class PQueryExecutor {

    public static List<List<Node>> predicateQuery(Step psteps, List<PartialTree> pts, List<List<Node>> inputLists) {
        
        return inputLists;
    }
    
    public static List<List<PNode>> pQuery(Step psteps, List<PartialTree> pts, List<List<PNode>> inputLists) {

        List<List<PNode>> resultLists = new ArrayList<List<PNode>>();

        Step pstep = psteps;
        while (pstep != null) {

            resultLists = pQueryWithAixs(pstep.getAxis(), pts, inputLists, pstep.getNameTest());

            Step predicate = pstep.getPredicate();
            if (predicate != null) {
                // Querying predicate. his block will be executed when a query has a predicate.
                resultLists = pQuery(psteps, pts, resultLists);
            }

            System.out.println();
            System.out.println("Predicate Step" + " : " + pstep);
//            Utils.print(resultLists);

            pstep = pstep.getNext();

        }

        return resultLists;
    }

    public static List<List<PNode>> pQueryWithAixs(Axis axis, List<PartialTree> pts, List<List<PNode>> inputLists, String test) {
        return null;
    }

     public static List<List<PNode>> preparePredicate( List<List<Node>> inputLists){
         
         List<List<PNode>> outputLists=new ArrayList<List<PNode>>();
         
         for(int i=0;i<inputLists.size();i++) {
             List<Node> list=inputLists.get(i);
             List<PNode> plist=new ArrayList<>();
             for(Node node: list) {
                 PNode pNode=new PNode();
                 pNode.setNode(node);
                 pNode.setLink(new Link(i, node.getUid()));
                 plist.add(pNode);
             }
             outputLists.add(plist);
         }
         
         return outputLists;
         
     }

}

















































