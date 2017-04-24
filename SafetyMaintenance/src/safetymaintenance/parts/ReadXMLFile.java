package safetymaintenance.parts;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadXMLFile {
	
  private List<myNode> allNodes;
  private List<myConnection> allConnections;
  
  public List<myNode> getAssumptions() {
	List<myNode> assumptions = new ArrayList<myNode>();
    for(myNode i:allNodes){
    	if(i.getType() == "Assumption") {
    		assumptions.add(i);
    	}
    }
	return assumptions;
  }
  
  public List<myNode> getRequirements() {
	List<myNode> reqs = new ArrayList<myNode>();
    for(myNode i:allNodes){
    	if(i.getType() == "Requirement") {
    		reqs.add(i);
    	}
    }
	return reqs;
  }
  
  public List<myNode> getDesignDecisions() {
	List<myNode> dds = new ArrayList<myNode>();
    for(myNode i:allNodes){
    	if(i.getType() == "DesignDecision") {
    		dds.add(i);
    	}
    }
	return dds;
  }
  
  public List<myNode> getFMECAs() {
	List<myNode> faults = new ArrayList<myNode>();
    for(myNode i:allNodes){
    	if(i.getType() == "FMECA") {
    		faults.add(i);
    	}
    }
	return faults;
  }

  public void parseNodes() {

	List<String> xmlFileNames = new ArrayList<String>();
	xmlFileNames.add("Assumption");
	xmlFileNames.add("DesignDecision");
	xmlFileNames.add("FMECA");
	xmlFileNames.add("Goal");
	xmlFileNames.add("Requirement");

	allNodes = new ArrayList<myNode>();
	
    try {
    	for(int i=0; i<xmlFileNames.size(); i++){
		    File fXmlFile = new File("data/" + xmlFileNames.get(i) + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		
			NodeList nList = doc.getElementsByTagName(xmlFileNames.get(i));
				
			for (int temp = 0; temp < nList.getLength(); temp++) {
		
				Node nNode = nList.item(temp);

				String type = null, id = null, desc = null, crit = null;
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		
					Element eElement = (Element) nNode;
		
					type = nNode.getNodeName();
					id = eElement.getElementsByTagName("ID").item(0).getTextContent();
					desc = eElement.getElementsByTagName("Description").item(0).getTextContent();

					if(xmlFileNames.get(i) == "FMECA"){
						crit = eElement.getElementsByTagName("Criticality").item(0).getTextContent();
					}
				}
				myNode currentNode = new myNode(type, id, desc, crit);
				allNodes.add(currentNode);
			}
			
    	}
    } catch (Exception e) {
    	e.printStackTrace();
    }

    for(myNode i:allNodes){
    	System.out.println("Node: " + i.getID());
    }
  
  } 
public void parseLinks() {
	
	allConnections = new ArrayList<myConnection>();
	
	List<String> tmFileNames = new ArrayList<String>();
	tmFileNames.add("Code_Requirements");
	tmFileNames.add("Requirements_Assumptions");
	tmFileNames.add("Requirements_Design");
	tmFileNames.add("Requirements_Fault");
	
	List<String> tmTagNames = new ArrayList<String>();
	tmTagNames.add("TMReqCode");
	tmTagNames.add("TMAssumption");
	tmTagNames.add("TMDesign");
	tmTagNames.add("TMFault");
	
    try {    	
    	for(int i=0; i<tmFileNames.size(); i++){
		    File fXmlFile = new File("data/TM_" + tmFileNames.get(i) + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
				
			NodeList nList = doc.getElementsByTagName(tmTagNames.get(i));
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		
					Element eElement = (Element) nNode;
					
					myNode src = null;
					String src_id = eElement.getElementsByTagName("ID").item(0).getTextContent();
					for(myNode n: allNodes) {
						if(n.getID() == src_id) {
							src = n;
							break;
						}
					}
					myNode dst = null;
					String dst_id = eElement.getElementsByTagName("Description").item(0).getTextContent();
					for(myNode n: allNodes) {
						if(n.getID() == dst_id) {
							dst = n;
							break;
						}
					}
		
					myConnection tmp = new myConnection(src, dst);
					allConnections.add(tmp);
					
					//System.out.println("ID : " + eElement.getElementsByTagName("ID").item(0).getTextContent());
					//System.out.println("Description : " + eElement.getElementsByTagName("Description").item(0).getTextContent());
					
				}
			}
    	}
    } catch (Exception e) {
    	e.printStackTrace();
    }
    
  }

}

