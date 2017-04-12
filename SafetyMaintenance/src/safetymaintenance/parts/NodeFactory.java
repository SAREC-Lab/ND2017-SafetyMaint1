import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class NodeFactory {

	public myNode createNode(Node nNode, Boolean isFMECA) {
		String type = null, id = null, desc = null, crit = null;
		
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;

			type = nNode.getNodeName();
			id = eElement.getElementsByTagName("ID").item(0).getTextContent();
			desc = eElement.getElementsByTagName("Description").item(0).getTextContent();

			if(isFMECA == true){
				crit = eElement.getElementsByTagName("Criticality").item(0).getTextContent();
			}
		}
		myNode currentNode = new myNode(type, id, desc, crit);
		
		return currentNode;
	}
	
}
