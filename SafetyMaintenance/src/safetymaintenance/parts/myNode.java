package safetymaintenance.parts;


public class myNode {

	private String type;
	private String ID;
	private String description;
	private String criticality;

	public myNode(String t, String i, String d, String c){
		type = t;
		ID = i;
		description = d;
		criticality = c;
	}
	
	public String getType(){
		return type;
	}
	public String getID(){
		return ID;
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
	
	public String toString() {
		return ID + ": " + description;
	}
}
