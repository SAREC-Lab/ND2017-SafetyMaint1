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

	List<String> fileNames = new ArrayList<String>();
	fileNames.add("Assumption");
	fileNames.add("DesignDecision");
	fileNames.add("FMECA");
	fileNames.add("Goal");
	fileNames.add("Requirement");
	
    try {
    	for(int i=0; i<fileNames.size(); i++){
		    File fXmlFile = new File("data/" + fileNames.get(i) + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
			NodeList nList = doc.getElementsByTagName(fileNames.get(i));
		
			System.out.println("----------------------------");
		
			for (int temp = 0; temp < nList.getLength(); temp++) {
		
				Node nNode = nList.item(temp);
		
				System.out.println("\nCurrent Element : " + nNode.getNodeName());
		
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		
					Element eElement = (Element) nNode;
		
					System.out.println("ID : " + eElement.getElementsByTagName("ID").item(0).getTextContent());
					System.out.println("Description : " + eElement.getElementsByTagName("Description").item(0).getTextContent());
					if(fileNames.get(i) == "FMECA"){
						System.out.println("Criticality : " + eElement.getElementsByTagName("Criticality").item(0).getTextContent());
					}
		
				}
			}
    	}
    } catch (Exception e) {
    	e.printStackTrace();
    }
  }

}

