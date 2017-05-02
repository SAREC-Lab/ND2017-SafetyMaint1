package safetymaintenance.parts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class UnitTests {

	@Test
	public void ReadXMLFileUnitTest() {
		try {
			XMLReader xmlReader = new XMLReader();
			Map<String, myNode> nodes = new HashMap<String, myNode>();
			Map<String, ArrayList<String>> edges = new HashMap<String, ArrayList<String>>();
			
			nodes = xmlReader.readNodes();
			edges = xmlReader.readEdges();
			
			String tempNode = nodes.toString().substring(0, 42);
			String tempEdge = edges.toString().substring(0, 42);
			
			assertEquals(tempNode, "{A1=A1: Longitude is measured in degrees, ");
			assertEquals(tempEdge, "{model.drone.fleet.RuntimeDroneTypes.java=");
		}  catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void GraphClassUnitTest() {
		try {
			GraphClass graph = new GraphClass();
			Set<String> cc = graph.getCriticalClasses();	

			assertEquals(cc.isEmpty(), false);
		}  catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void GraphClassUnitTest2() {
		try {
			GraphClass graph = new GraphClass();
			ArrayList<String> design = graph.getDesign("R20");
			ArrayList<String> assumption = graph.getAssumptions("R20");
			ArrayList<String> fmeca = graph.getFMECA("R20");
			
			assertEquals(design.toString(), "[D1]");
			assertEquals(fmeca.toString(), "[F2, F3, F5, F6]");
			assertEquals(assumption.toString(), "[A4, A5, A6, A7, A8, A9]");

		}  catch (Exception e){
			e.printStackTrace();
		}
	}
	

}
