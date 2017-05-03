package safetymaintenance.parts;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class UnitTests {

	@Test
	public void NodeFactoryUnitTest() {
		try {
			NodeFactory nf = new NodeFactory();
			
			File fXmlFile = new File("data/Requirement.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			Document doc;

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			//Get the list of nodes in XML Document
			NodeList nList = doc.getElementsByTagName("Requirement");

			//Create the node using the NodeFactory
			myNode currentNode = nf.createNode(nList.item(0), false);
			
			assertEquals(currentNode.getType(), "Requirement");
			assertEquals(currentNode.getID(), "R1");
			assertEquals(currentNode.getDescription(), "A fleet of drones will be generated at simulation startup.");
			assertEquals(currentNode.getCriticality(), null);
			
		}  catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void myNodeUnitTest() {
		try {
			
			myNode node = new myNode("FMECA", "F1", "Insufficient voltage. Drone lands prematurely and is unable to return to base.", "Critical");
			
			assertEquals(node.getType(), "FMECA");
			assertEquals(node.getID(), "F1");
			assertEquals(node.getDescription(), "Insufficient voltage. Drone lands prematurely and is unable to return to base.");
			assertEquals(node.getCriticality(), "Critical");
			
		}  catch (Exception e){
			e.printStackTrace();
		}
	}

	
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
	
	@Test
	public void GraphClassUnitTest3() {
		try {
			GraphClass graph = new GraphClass();
			Set<String> critical = graph.getCriticalClasses();
			
			assert(critical.contains("controller.movement.Roundabout.java"));
			assertEquals(graph.getRequirementDescription("R4"), "R4: When the system is in virtual mode only virtual drones will be used.");

		}  catch (Exception e){
			e.printStackTrace();
		}
	}

}
