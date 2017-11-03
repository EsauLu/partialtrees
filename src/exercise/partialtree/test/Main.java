package exercise.partialtree.test;

import exercise.partialtree.constructor.PartialTreesBuilder;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		PartialTreesBuilder.getInstance().createPartialTreesByXML("<A><B><C><E></E></C><D></D>"
//				                                                + "</B><E></E><B><B><D><E></E></D>"
//				                                                + "<C></C></B><C><E></E></C><D><E>"
//				                                                + "</E></D></B><E><D></D></E><B>"
//				                                                + "<D></D><C></C></B><B></B></A>", 5);

		PartialTreesBuilder.getInstance().createPartialTreesByXML("<A><B><C><E></E></C><D></D>"
				                                                + "</B><E></E><B><B><D><E></E></D>"
				                                                + "<C></C></B><C><E></E></C><D><E>"
				                                                + "</E></D></B><E><D></D></E><B>"
				                                                + "<D></D><C></C></B><B></B></A>", 5);
		
	}

}
