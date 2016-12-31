package path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar {
	
	public static final int EMPTY = 0;
	public static final int WALL = 1;
	public static final int PATH = 2;
	
	public static final int _MAP = 0;
	public static final int _H = 1;
	public static final int _G = 3;
	public static final int _F = 2;
	public static final int _PAR = 4;
	
	private Node[][] node;
	private Cell origin;
	private Cell target;
	
	private List<Cell> open;
	private List<Cell> closed;
	
	public AStar(int[][] map, Cell origin, Cell target){
		//this.map=map;
		node = new Node[map.length][];
		for(int i=0;i<map.length;i++){
			node[i] = new Node[map[0].length];
			for(int j=0;j<node[i].length;j++){
				node[i][j] = new Node(map[i][j]);
			}
		}
		
		this.origin=origin;
		this.target=target;
		
		open = new ArrayList<Cell>();
		closed = new ArrayList<Cell>();
	}
	
	private void resetNodes(){
		for(int i=0;i<node.length;i++){
			for(int j=0;j<node[i].length;j++){
				node[i][j].resetValues();
			}
		}
	}
	
	public void setOrigin(Cell origin){
		this.origin=origin;
	}
	
	public void setTarget(Cell target){
		this.target=target;
	}
	
	public Cell[] getPath(){
		return getPath(true);
	}
	
	public Cell[] getPath(boolean allowDiag){
		open.clear();
		closed.clear();
		resetNodes();
		
		if(origin.equals(target) || nodeFromCell(target).getMap() != 0){
//			return new Cell[]{origin,target};
			return new Cell[]{origin};
		}
		
		calculateH();
		
		Cell current = origin;
		while(nodeFromCell(current).getH()>1){
		
			closed.add(current);
			setFGParentingFromCurrent(current);
			
//			printValue(_F);
	
			current = getLowestFCell();
//			System.out.println("next lowest: "+current);
			open.remove(open.indexOf(current));
//			System.out.println("\nlength of open list: "+open.size()+" | length of closed list: "+closed.size());
			
		}
		
//		System.out.println("ponto fnal: "+current);
		nodeFromCell(target).setParent(current);
		current = target;
		List<Cell> path = new ArrayList<Cell>();
		path.add(current);
		
		while(nodeFromCell(current).getParent() != null){
			
			Cell last = current;
			current=nodeFromCell(current).getParent();
			if(!allowDiag){
				if(last.getX()!=current.getX() && last.getY()!=current.getY()){//append a corner
					Cell a = new Cell(last.getX()-(last.getX()-current.getX()),last.getY());
					Cell b = new Cell(last.getX(),last.getY()-(last.getY()-current.getY()));
					if(nodeFromCell(a).getMap()==EMPTY){
						path.add(a);
//						nodeFromCell(a).setMap(PATH);
					} else {
						path.add(b);
//						nodeFromCell(b).setMap(PATH);
					}
				}
			}
//			nodeFromCell(current).setMap(PATH);
			path.add(current);
		}
		
//		printValue(_MAP);
		
		Collections.reverse(path);
		return path.toArray(new Cell[path.size()]);
	}
	
	private void calculateH(){
		for(int i=0;i<node.length;i++){
			for(int j=0;j<node[0].length;j++){
				if(node[i][j].getMap() != EMPTY)
					node[i][j].setH(-1);
				else
					node[i][j].setH(manhattan(new Cell(j,i), target));
			}
		}
	}
	
	private void setFGParentingFromCurrent(Cell c){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				int ii = c.getY()-1+i;
				int ij = c.getX()-1+j;
				Cell borderCell = new Cell(ij,ii); //(x,y)=(j,i)
				if(!isOutOfBounds(ii,ij) && !borderCell.equals(c) && node[ii][ij].getMap()!=WALL){
					if(!open.contains(borderCell)){
//						System.out.println("adding "+borderCell);
						open.add(borderCell);
					}
					
					if(!closed.contains(borderCell)){
						int parentCost = 0;
						if(nodeFromCell(c).getParent() != null)
							parentCost = nodeFromCell(c).getG();
//							parentCost = nodeFromCell(c).getParent().getCost(c);
						
						if(nodeFromCell(borderCell).getParent() == null || nodeFromCell(c).getG()+c.getCost(borderCell) < nodeFromCell(c).getParent().getCost(borderCell)){
							nodeFromCell(borderCell).setG(c.getCost(borderCell)+parentCost);
							nodeFromCell(borderCell).setParent(c);
							/*node[ii][ij].setG(c.getCost(borderCell)+parentCost);
							node[ii][ij].setParent(c);*/
						}
						node[ii][ij].setF(node[ii][ij].getH()+node[ii][ij].getG());//calculating F
					}
					
				}
			}
		}
	}
	
	private Node nodeFromCell(Cell c){
		return node[c.getY()][c.getX()];
	}
	
	private Cell getLowestFCell(){
		
		Cell loC = open.get(0);
		int val = node[loC.getY()][loC.getX()].getF();
		
		for(int i=1;i<open.size();i++){
			Cell c = open.get(i);
			if(closed.contains(c))
				continue;
			int cval = node[c.getY()][c.getX()].getF();
			if(cval < val){
				loC = c;
				val = cval;
			}
		}
		
		return loC;
	}
	
	private boolean isOutOfBounds(int i, int j){
//		inverted XY coordinates
//		x (j) vertical
//		y (i) horizontal
//		(x,y) = (j,i) 

		return i<0 || j<0 || i >= node.length || j >= node[0].length; 
	}
	
	public void printValue(int type){
		for(int i=0;i<node.length;i++){
			System.out.println();
			for(int j=0;j<node[0].length;j++){
				if(type==_MAP){
					if(j==origin.getX() && i==origin.getY()){
						System.out.print("o");
					} else if(j==target.getX() && i==target.getY()){
						System.out.print("t");
					} else {
						switch(node[i][j].getMap()){
							case 0: System.out.print(" "); break;
							case 1: System.out.print("#"); break;
							case 2: System.out.print("+"); break; 
						}
						
					}
				} else if(type==_F)
					System.out.print(node[i][j].getF());
				else if(type==_G)
					System.out.print(node[i][j].getG());
				else if(type==_H)
					System.out.print(node[i][j].getH());
				else if(type==_PAR)
					System.out.print(node[i][j].getParent());
				System.out.print("");
			}
		}
	}
	
	public static int manhattan(Cell a, Cell b){
		return Math.abs(a.getX()-b.getX())+Math.abs(a.getY()-b.getY());
	}
	
	public static double euclidean(Cell a, Cell b){
		return Math.sqrt(Math.pow(a.getX()-b.getX(),2)+Math.pow(a.getY()-b.getY(),2));
	}

}
