
public class Main {

	private static GraphClass graph = new GraphClass();
	private static View visualGraph = new View();
	
	public static void main(String[] args) {
		for(String source: graph.getCriticalClasses()) {
			graph.traverseGraphFrom(source);
		}
		//graph.readClasses();
		//graph.readCriticalClasses();
		//visualGraph.createVisualGraph();
		
		for (String source: graph.getCriticalClasses()) {
			System.out.println(source);	
		}
	}
}
