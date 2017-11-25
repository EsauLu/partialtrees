package exercise.queries.parse;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exercise.bean.Axis;
import exercise.bean.Step;

public class XPathParser {

    public static void test(String xpath) {

    }

//    public static List<Step> parseXPathToSteps(String xpath) {
//
//        System.out.println("\nXPath : " + xpath);
//        System.out.println("\n---------------------------------------------------------------------");
//        // Pattern pattern =
//        // Pattern.compile("/([a-zA-Z\\-:]+)(\\[([a-zA-Z:/\\-]*)\\])?");
//        Pattern pattern = Pattern.compile("/(([a-zA-Z\\-]*)(::)*([a-zA-Z])*(\\[([^\\]]*)\\])?)");
//        Matcher matcher = pattern.matcher(xpath);
//
//        List<Step> steps = new ArrayList<Step>();
//        while (matcher.find()) {
//
//            Step step = new Step();
//
//            step.setNameTest(matcher.group(4));
//            step.setAxis(Axis.parseString(matcher.group(2)));
//            step.setPredicateStr(matcher.group(6));
//
//            steps.add(step);
//
//        }
//        parseXpath(xpath);
//        return steps;
//    }

    public static Step parseXpath(String xpath) {

        StringBuffer sb = new StringBuffer(xpath);
        if (sb.charAt(0) == '/') {
            sb.deleteCharAt(0);
        }

        int c = 0, st = 0;
        Step root = new Step();
        Step pre = root;
        for (int i = 0; i < sb.length(); i++) {

            if (c == 0 && sb.charAt(i) == '/') {
                String stepStr = sb.substring(st, i);
                st = i + 1;
                pre.setNext(parseStep(stepStr));
                pre = pre.getNext();
            }

            if (sb.charAt(i) == '[') {
                c++;
            }

            if (sb.charAt(i) == ']') {
                c--;
            }

        }
        String stepStr = sb.substring(st);
        pre.setNext(parseStep(stepStr));
        pre = pre.getNext();

        return root.getNext();
    }

    public static Step parseStep(String stepStr) {

        Step step = new Step();
        int i = stepStr.indexOf("::");
        step.setAxis(Axis.parseString(stepStr.substring(0, i)));

        int j = stepStr.indexOf("[");
        if (j == -1) {
            step.setNameTest(stepStr.substring(i + 2));
        } else {
            step.setNameTest(stepStr.substring(i + 2, j));
            Step predicate = parseXpath(stepStr.substring(j + 1, stepStr.length() - 1));
            step.setPredicate(predicate);
        }

        return step;
    }

}
