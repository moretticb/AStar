package path;

public class Node {
	
	private int map;      //map value
	private Cell parent; //node to reach
	private int f;        //g+h
	private int g;        //Movement cost
	private int h;        //Heuristic (Distance (manhattan))
	
	public Node(int map){
		this.map=map;
		this.h=this.f=-1;
		this.g=0;
	}
	
	public void resetValues(){
		parent = null;
		this.h=this.f=-1;
		this.g=0;
	}
	
	public int getMap(){
		return this.map;
	}
	
	public void setMap(int map){
		this.map=map;
	}
	
	public Cell getParent(){
		return this.parent;
	}
	
	public void setParent(Cell parent){
		this.parent=parent;
	}
	
	public int getF(){ return this.f; }
	public void setF(int f){ this.f=f; }
	public int getG(){ return this.g; }
	public void setG(int g){ this.g=g; }
	public int getH(){ return this.h; }
	public void setH(int h){ this.h=h; }
}
