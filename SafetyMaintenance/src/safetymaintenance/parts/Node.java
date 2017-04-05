import java.util.ArrayList;
import java.util.List;

public class Node {

	private String type = null;
	private String description = null;
	private String criticality = null;
	private List<Node> connections = new ArrayList<Node>();
	
	public void setType(String t){
		type = t;
	}
	public String getType(){
		return type;
	}
	public void setDescription(String d){
		description = d;
	}
	public String getDescription(){
		return description;
	}
	public void setCriticality(String c){
		criticality = c;
	}
	public String getCriticality(){
		return criticality;
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
