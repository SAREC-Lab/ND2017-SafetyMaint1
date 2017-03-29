import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
 
public class XMLReader {
	
	DocumentBuilderFactory factory;
	File xmlFile;
	Document doc;
	
	XMLReader(File xml) {
		factory = DocumentBuilderFactory.newInstance();
		xmlFile = xml;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	

}
