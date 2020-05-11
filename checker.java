import java.util.ArrayList;

public class checker {
	
	// initializing variables
	private int index;
	private String name;
	ArrayList<checker> parent = new ArrayList<checker>( );
	ArrayList<checker> child = new ArrayList<checker>( );

	//creating constructor
	 public checker(int index , String name) {
		 this.index= index;
		 this.name= name;
	 }
	 
	 
	// create get for index
	public int getIndex() {
		return index;
	}
	
	// create set for index

	public void setIndex(int index) {
		this.index = index;
	}
	
	// create get for name
	public String getName() {
		return name;
	}
	
	// create set for index
	public void setName(String name) {
		this.name = name;
	}
	
	//return the parent array
	public ArrayList<checker> getParent() {
		return parent;
	}
	
	//add element to the parent array
	public void setParent(checker parent) {
		this.parent.add(parent);
	}
	
	//return the child array
	public ArrayList<checker> getChild() {
		return child;
	}
	
	//add element to the child array
	public void setChild(checker child) {
		this.child.add(child);
	}
	
	

}
