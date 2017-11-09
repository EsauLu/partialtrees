package exercise.queries.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exercise.bean.Axis;
import exercise.bean.Step;

public class XPathTools {
	
	public static List<Step> parseXPathToSteps(String xpath) {

		System.out.println("XPath : "+xpath);
//		Pattern pattern = Pattern.compile("/([a-zA-Z\\-:]+)(\\[([a-zA-Z:/\\-]*)\\])?");
		Pattern pattern = Pattern.compile("/(([a-zA-Z\\-]*)(::)*([a-zA-Z])*(\\[([^\\]]*)\\])?)");
		Matcher matcher = pattern.matcher(xpath);
		
		int i=0;
		
		System.out.println();
		List<Step> steps=new ArrayList<Step>();
		while (matcher.find()) {
			System.out.print("step"+i+" : ");
			
			Step step=new Step();
			
			int c=matcher.groupCount();
			
			step.setNameTest(matcher.group(4));
			step.setAxis(Axis.parseString(matcher.group(2)));
			step.setPredicate(matcher.group(6));
						
			steps.add(step);
			
			System.out.println(step);
			i++;
		}
		System.out.println("---------------------------------------------------------------------");
		
		return steps;
	}

	public List<Step> parsePrecicate(String predicate){
		List<Step> psteps=new ArrayList<Step>();
		return psteps;
	}
	
}
