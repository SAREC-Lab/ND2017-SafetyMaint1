
public class myConnection {
	private myNode source;
	private myNode destination;
	
	public myConnection(myNode s, myNode d){
		source = s;
		destination = d;
	}	

	public myNode getSource(){
		return source;
	}
	public myNode getDestination(){
		return destination;
	}
	
}