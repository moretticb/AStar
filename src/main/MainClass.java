package main;

import java.awt.Point;
import java.awt.geom.Point2D;

import path.AStar;
import path.Cell;

public class MainClass {
	
	public static void main(String[] args){

		int width = 9;
		int height = 8;

//		inverted XY coordinates
//		x (j) vertical
//		y (i) horizontal
//		(x,y) = (j,i)
		Cell o = new Cell(2, 3);
		Cell t = new Cell(1, 6);

		int[][] map = new int[][]{
				{1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,0,1},
				{1,0,0,1,1,1,0,0,1},
				{1,0,0,1,0,0,0,1,1},
				{1,1,1,1,0,0,1,1,1},
				{1,0,0,0,0,1,1,0,1},
				{1,0,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1},
		};
//		int[][] map = new int[height][];
//		for(int j=0;j<height;j++){
//			map[j] = new int[width];
//			for(int i=0;i<width;i++){
//				map[j][i] = AStar.EMPTY;
//			}
//		}
//		//walls on the map
//		map[2][3] = map[4][3] = map[5][3] = map[4][1] = AStar.WALL;
		
		
		
		AStar as = new AStar(map,o,t);
		as.printValue(AStar._MAP);
		Cell[] path = as.getPath();
		for(int i=0;i<path.length;i++){
			System.out.println(path[i]);
		}
		
	}

}
