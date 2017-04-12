
public class Main {

	private static Graph graph = new Graph();
	
	public static void main(String[] args) {
		for(String source: graph.getCriticalClasses()) {
			graph.traverseGraphFrom(source);
		}
		//graph.readClasses();
		//graph.readCriticalClasses();
	}
}
