import java.util.ArrayList;
import java.util.List;

public class myNode {

	private String type;
	private String ID;
	private String description;
	private String criticality;
	private List<myNode> connections;
	
	public myNode(){
		type = null;
		ID = null;
		description = null;
		criticality = null;
		connections = new ArrayList<myNode>();
	}
	
	public void setType(String t){
		type = t;
	}
	public String getType(){
		return type;
	}
	public void setID(String i){
		ID = i;
	}
	public String getID(){
		return ID;
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
