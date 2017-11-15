package exercise.queries.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exercise.bean.Axis;
import exercise.bean.Step;

public class XPathTools {
	
	public static List<Step> parseXPathToSteps(String xpath) {

		System.out.println("\nXPath : "+xpath);
		System.out.println("\n---------------------------------------------------------------------");
//		Pattern pattern = Pattern.compile("/([a-zA-Z\\-:]+)(\\[([a-zA-Z:/\\-]*)\\])?");
		Pattern pattern = Pattern.compile("/(([a-zA-Z\\-]*)(::)*([a-zA-Z])*(\\[([^\\]]*)\\])?)");
		Matcher matcher = pattern.matcher(xpath);
		
		List<Step> steps=new ArrayList<Step>();
		while (matcher.find()) {
			
			Step step=new Step();
			
			step.setNameTest(matcher.group(4));
			step.setAxis(Axis.parseString(matcher.group(2)));
			step.setPredicate(matcher.group(6));
						
			steps.add(step);
			
		}
		
		return steps;
	}

	public List<Step> parsePrecicate(String predicate){
		List<Step> psteps=new ArrayList<Step>();
		return psteps;
	}
	
}
