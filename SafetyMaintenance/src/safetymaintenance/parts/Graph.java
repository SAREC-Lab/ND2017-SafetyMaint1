import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
	
	private static Map<String, myNode> nodes = new HashMap<String, myNode>();
	private static Map<String, ArrayList<String>> edges = new HashMap<String, ArrayList<String>>();
	private static Set<String> criticalClasses = new HashSet<String>();
	
	private static XMLReader xmlReader = new XMLReader();
	
	public Graph() {
		buildGraph();
	}
	
	public void readTraceLinksFrom(String src) {
		if(!edges.containsKey(src)) {
			System.out.println("This node has no links");
			return;
		}
		for(String link: edges.get(src)) {
			System.out.println(nodes.get(link).toString());
		}
	}
	
	public void traverseGraphFrom(String sourceCode) {
		if(criticalClasses.contains(sourceCode)) {
			System.out.println(sourceCode);
			traverseGraph(sourceCode, "");
		} else {
			System.out.println("This class is not safety critical");
		}
	}
	
	//This can cause an error because our XML is not complete
	private void traverseGraph(String id, String indents) {
		if(!edges.containsKey(id)) return;

		indents += "\t";
		for(String link: edges.get(id)) {
			System.out.print(indents);
			System.out.println(nodes.get(link).toString());
			traverseGraph(link, indents);
		}
	}

	public void readNodes() {
		for(String id: nodes.keySet()) {
			System.out.println(nodes.get(id).toString());
		}
	}
	
	public void readCriticalClasses() {
		for(String critical: criticalClasses) {
			System.out.println("Critical Class: " + critical);
		}
	}

	private void buildGraph() {
		nodes = xmlReader.readNodes();
		edges = xmlReader.readEdges();
		criticalClasses = xmlReader.getCriticalClasses();
	}
	
	
	
}
