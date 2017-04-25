package safetymaintenance.parts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Main {

	private static GraphClass graph = new GraphClass();
	private static View visualGraph = new View();
	
	public static void main(String[] args) {
	
		//graph.readClasses();
		//graph.readCriticalClasses();
		//visualGraph.createVisualGraph();
		
		// Functions that will help get design decisions and fmeca for certain requirement
		Set<String> cc = graph.getCriticalClasses();
        Iterator<String> it = cc.iterator();
        String criticalClass = it.next();
		
		ArrayList<String> requirementList = graph.getRequirements(criticalClass);
		ArrayList<String> designList = graph.getDesign(requirementList.get(0));
		ArrayList<String> FMECAList = graph.getFMECA(requirementList.get(0));
		
		System.out.println(requirementList.get(0));
		for (String d: designList) {
			System.out.println(d);
		}
		for (String f: FMECAList) {
			System.out.println(f);
		}
		
		for(String source: graph.getCriticalClasses()) {
			graph.traverseGraphFrom(source);
		}
		
		
		/*for (String source: graph.getCriticalClasses()) {
			System.out.println(source);	
			ArrayList<String> requirementList = graph.getRequirements(source);
		}*/
		
		
	}
}
