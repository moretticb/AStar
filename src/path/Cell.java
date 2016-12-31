package path;

public class Cell {

	private int x;
	private int y;
	
	public Cell(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public int getCost(Cell c){
		if(c.x==this.x || c.y==this.y)
			return 10;
		else
			return 14;
	}
	
	public int getX(){
		return this.x;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setY(int y){
		this.y=y;
	}

	public boolean equals(Object cell){
		Cell c = (Cell) cell;
		return c.getX()==this.x && c.getY()==this.y;
	}
	
	public String toString(){
		return String.format("x%dy%d", x,y);
	}

}
