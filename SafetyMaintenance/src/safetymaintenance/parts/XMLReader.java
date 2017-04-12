import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

	private NodeFactory nodeFactory = new NodeFactory();

	private HashMap<String, myNode> nodes = new HashMap<String, myNode>();
	private HashMap<String, ArrayList<String>> edges = new HashMap<String, ArrayList<String>>();

	private void readNodeFile(String fileName) {
		File fXmlFile = new File("data/" + fileName + ".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc;

		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			//Get the list of nodes in XML Document
			NodeList nList = doc.getElementsByTagName(fileName);

			//For each node in the XML Document
			for (int temp = 0; temp < nList.getLength(); temp++) {

				//Create the node using the NodeFactory
				myNode currentNode = nodeFactory.createNode(nList.item(temp), false);

				//Insert node into map
				nodes.put(currentNode.getID(), currentNode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readFMECAFile() {
		File fXmlFile = new File("data/FMECA.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc;

		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			//Get the list of nodes in XML Document
			NodeList nList = doc.getElementsByTagName("FMECA");

			//For each node in the XML Document
			for (int temp = 0; temp < nList.getLength(); temp++) {

				//Create the node using the NodeFactory
				myNode currentNode = nodeFactory.createNode(nList.item(temp), true);

				//Insert node into map
				nodes.put(currentNode.getID(), currentNode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, myNode> readNodes() {

		readNodeFile("Assumption");
		readNodeFile("DesignDecision");
		readNodeFile("Goal");
		readNodeFile("Requirement");
		readFMECAFile();

		//Return the map of nodes, to be stored in the graph class
		return nodes;
	}

	private void readEdgeFile(String fileName, String tagName) {
		try {    	
			File fXmlFile = new File("data/TM_" + fileName + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(tagName);

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String src_id = eElement.getElementsByTagName("ID").item(0).getTextContent();
					myNode src = nodes.get(src_id);
					
					String dst_id = eElement.getElementsByTagName("Description").item(0).getTextContent();
					myNode dst = nodes.get(dst_id);

					if( edges.containsKey(src_id) ) {
						edges.get(src_id).add(dst_id);
					} else {
						ArrayList<String> newStringArr = new ArrayList<String>();
						newStringArr.add(dst_id);
						edges.put(src_id, newStringArr);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, ArrayList<String>> readEdges() {

		readEdgeFile("Code_Requirements", "TMReqCode");
		readEdgeFile("Requirements_Assumptions", "TMAssumption");
		readEdgeFile("Requirements_Design", "TMDesign");
		readEdgeFile("Requirements_Fault", "TMFault");

		return edges;

	}

	public Set<String> getClasses() {
		Set<String> critical = new HashSet<String>();
		
		try {    	
			File fXmlFile = new File("data/TM_Code_Requirements.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("TMReqCode");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String src_id = eElement.getElementsByTagName("ID").item(0).getTextContent();
					
					critical.add(src_id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return critical;
	}

}
