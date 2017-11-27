package exercise.queries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                List<List<PNode>> intermadiate = preparePredicate(resultLists);
                intermadiate = pQuery(predicate, pts, intermadiate);
            }

            System.out.println();
            System.out.println("Predicate Step" + " : " + pstep);
            // Utils.print(resultLists);

            pstep = pstep.getNext();

        }

        return resultLists;
    }

    public static List<List<PNode>> pQueryWithAixs(Axis axis, List<PartialTree> pts, List<List<PNode>> inputLists, String test) {
        return null;
    }

    public static List<List<PNode>> preparePredicate(List<List<PNode>> inputLists) {

        List<List<PNode>> outputLists = new ArrayList<List<PNode>>();

        for (int i = 0; i < inputLists.size(); i++) {
            List<PNode> list = inputLists.get(i);

            Set<Node> set = new HashSet<Node>();
            for (PNode pnode : list) {
                set.add(pnode.getNode());
            }

            List<PNode> plist = new ArrayList<>();
            for (Node node : set) {
                PNode pNode = new PNode();
                pNode.setNode(node);
                pNode.setLink(new Link(i, node.getUid()));
                plist.add(pNode);
            }

            outputLists.add(plist);
        }

        return outputLists;

    }
    
    public static List<List<PNode>> filterPNode(List<List<PNode>> intermadiate,  List<List<Node>> inputLists){

        List<List<PNode>> outputLists = new ArrayList<List<PNode>>();

        int p=intermadiate.size();
        
        List<HashMap<Node, List<Link>>> pnodeMap=new ArrayList<HashMap<Node, List<Link>>>();
        for(int i=0;i<p;i++) {
            
            HashMap<Node, List<Link>> map=new HashMap<Node, List<Link>>();
            pnodeMap.add(map);
            
            List<PNode> list=intermadiate.get(i);
            for(PNode pNode: list) {
                List<Link> links=map.get(pNode.getNode());
                
                if(links==null) {
                    links=new ArrayList<Link>();
                    map.put(pNode.getNode(), links);
                }
                
                links.add(pNode.getLink());
            }
            
        }
        
        for(int i=0;i<p;i++) {
            HashMap<Node, List<Link>> map=pnodeMap.get(i);
            List<PNode> result=new ArrayList<>();
            List<Node> nodes=inputLists.get(i);
            
            for(Node node: nodes) {
                List<Link> links=map.get(node);
                if(links!=null) {
                    for(Link link: links) {
                        PNode pNode = new PNode();
                        pNode.setNode(node);
                        pNode.setLink(link);
                        result.add(pNode);
                    }
                }
            }           
            
            outputLists.add(result);
        }
        
        return outputLists;
        
    }

    public static List<List<Node>> proccessPredicate(List<PartialTree> pts, List<List<PNode>> inputLists) {
        
        List<Link> allLinks=new ArrayList<>();
        for(List<PNode> list: inputLists) {
            for(PNode pNode: list) {
                allLinks.add(pNode.getLink());
            }
        }
        
        int p=pts.size();
        List<List<Integer>> uidLists=new ArrayList<List<Integer>>();
        for(int i=0;i<p;i++) {
            uidLists.add(new ArrayList<Integer>());
        }
        
        for(Link link: allLinks) {
            List<Integer> uids=uidLists.get(link.getPid());
            if(uids!=null) {
                uids.add(link.getUid());
            }
        }
        
        List<List<Node>> resultLists=new ArrayList<List<Node>>(); 
        for(int i=0;i<p;i++) {
            PartialTree pt=pts.get(i);
            List<Integer> uids=uidLists.get(i);
            resultLists.add(pt.findNodesByUid(uids));
        }
        
        return QueryExecutor.shareNodes(pts, resultLists);
    }

    public static List<List<PNode>> preparePredicate2(List<List<Node>> inputLists) {

        List<List<PNode>> outputLists = new ArrayList<List<PNode>>();

        for (int i = 0; i < inputLists.size(); i++) {
            List<Node> list = inputLists.get(i);
            List<PNode> plist = new ArrayList<>();
            for (Node node : list) {
                PNode pNode = new PNode();
                pNode.setNode(node);
                pNode.setLink(new Link(i, node.getUid()));
                plist.add(pNode);
            }
            outputLists.add(plist);
        }

        return outputLists;

    }

}
