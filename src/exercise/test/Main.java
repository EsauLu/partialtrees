package exercise.test;

import java.util.List;

import exercise.bean.Node;
import exercise.bean.PartialTree;
import exercise.bean.Step;
import exercise.partialtree.constructor.PartialTreesBuilder;
import exercise.queries.QueryExecutor;
import exercise.queries.parse.XPathParser;
import exercise.utils.Utils;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        // List<PartialTree>
        // pts=PartialTreesBuilder.getInstance().createPartialTreesByXML("<A><B><C><E></E></C><D></D>"
        // + "</B><E></E><B><B><D><E></E></D>"
        // + "<C></C></B><C><E></E></C><D><E>"
        // + "</E></D></B><E><D></D></E><B>"
        // + "<D></D><C></C></B><B></B></A>", 5);

        int[] pos = { 0, 31, 58, 86, 115, 147 };
        List<PartialTree> pts = PartialTreesBuilder.getInstance()
                .createPartialTreesByXML("<A><B><C><E></E></C><D></D></B>" + "<E></E><B><B><D><E></E></D>"
                        + "<C></C></B><C><E></E></C><D>" + "<E></E></D></B><E><D></D></E>"
                        + "<B><D></D><C></C></B><B></B></A>", pos);

        // int[] pos= {0, 9, 16, 29};
        // List<PartialTree>
        // pts=PartialTreesBuilder.getInstance().createPartialTreesByXML("<A><B><C></C><D></D></B></A>",
        // pos);

        // List<PartialTree>
        // pts=PartialTreesBuilder.getInstance().createPartialTreesByXML("<A><B><C><D></D></C></B></A>",
        // 3);

        System.out.println("====================================================================================");
        System.out.println();
        System.out.println("DFS : ");
        System.out.println();
        for (int i = 0; i < pts.size(); i++) {
            System.out.print("  pt" + i + " ==> ");
            Utils.dfsWithDepth(pts.get(i).getRoot());
            System.out.println();
        }
        System.out.println("====================================================================================");

        
        String[] xpaths= {
//                "/descendant::A[descendant::B/descendant::C/parent::B]/following-sibling::B[descendant::B/descendant::C[descendant::G/descendant::H/parent::I]/parent::B]",
                
                "/child::A/descendant::B/descendant::C/parent::B",
                "/descendant::B/following-sibling::B",
                "/descendant::B[following-sibling::B/child::C]/child::C",
                "/descendant::D[parent::B[descendant::E]]"
        };
        
        for(String xpath: xpaths) {
            
            System.out.println();
            System.out.println("XPath : " + xpath);
            System.out.println();
            System.out.println("---------------------------------------------------------------------");
            
            Step steps = XPathParser.parseXpath(xpath);

            List<List<Node>> resultLists = QueryExecutor.query(steps, pts);

            System.out.println("====================================================================================");
            
        }
        
    }

}

/*




*/
