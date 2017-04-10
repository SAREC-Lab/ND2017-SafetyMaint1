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

  public static void main(String argv[]) {

	List<String> xmlFileNames = new ArrayList<String>();
	xmlFileNames.add("Assumption");
	xmlFileNames.add("DesignDecision");
	xmlFileNames.add("FMECA");
	xmlFileNames.add("Goal");
	xmlFileNames.add("Requirement");

	List<String> tmFileNames = new ArrayList<String>();
	tmFileNames.add("Code_Requirements");   //TMReqCode
	tmFileNames.add("Requirements_Assumptions"); //TMAssumption
	tmFileNames.add("Requirements_Design"); //TMDesign
	tmFileNames.add("Requirements_Fault"); //TMFault
	
	List<String> tmTagNames = new ArrayList<String>();
	tmTagNames.add("TMReqCode");
	tmTagNames.add("TMAssumption");
	tmTagNames.add("TMDesign");
	tmTagNames.add("TMFault");
	
	List<myNode> allNodes = new ArrayList<myNode>();
	
    try {
    	for(int i=0; i<xmlFileNames.size(); i++){
		    File fXmlFile = new File("data/" + xmlFileNames.get(i) + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
			NodeList nList = doc.getElementsByTagName(xmlFileNames.get(i));
		
			//System.out.println("----------------------------");
		
			for (int temp = 0; temp < nList.getLength(); temp++) {
		
				Node nNode = nList.item(temp);
				
				myNode currentNode = new myNode();
		
				//System.out.println("\nCurrent Element : " + nNode.getNodeName());
				currentNode.setType(nNode.getNodeName());
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		
					Element eElement = (Element) nNode;
		
					currentNode.setID(eElement.getElementsByTagName("ID").item(0).getTextContent());
					currentNode.setDescription(eElement.getElementsByTagName("Description").item(0).getTextContent());
					//System.out.println("ID : " + eElement.getElementsByTagName("ID").item(0).getTextContent());
					//System.out.println("Description : " + eElement.getElementsByTagName("Description").item(0).getTextContent());
					if(xmlFileNames.get(i) == "FMECA"){
						currentNode.setCriticality(eElement.getElementsByTagName("Criticality").item(0).getTextContent());
						//System.out.println("Criticality : " + eElement.getElementsByTagName("Criticality").item(0).getTextContent());
					}
		
				}
				allNodes.add(currentNode);
			}
			
    	}
    } catch (Exception e) {
    	e.printStackTrace();
    }

    for(myNode i:allNodes){
    	System.out.println("Node: " + i.getID());
    }
  
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
		
					System.out.println("ID : " + eElement.getElementsByTagName("ID").item(0).getTextContent());
					System.out.println("Description : " + eElement.getElementsByTagName("Description").item(0).getTextContent());
					
				}
			}
    	}
    } catch (Exception e) {
    	e.printStackTrace();
    }
    
  }

}

